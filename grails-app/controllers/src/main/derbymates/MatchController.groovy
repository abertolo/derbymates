/*
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

    In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
    IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
*/
package src.main.derbymates

import org.grails.databinding.BindingFormat
import src.main.derbymates.match.*
import groovy.time.TimeCategory

/**
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
class MatchController {

    def matchService

    def show(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        render view: "show_${m.status.toString().toLowerCase()}", model: [match: m, user: u]
    }

    def join(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }

        def mp = MatchPlayer.findByMatchAndPlayer(m, u.getPlayer())
        if(!mp){
            mp = new MatchPlayer()
            mp.match = m
            mp.player = u.getPlayer()
            mp.save()
        }
        redirect(action: 'show', id: m.id)
    }

    def quit(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        matchService.quitMatch(MatchPlayer.findByMatchAndPlayer(m, u.getPlayer()))
        redirect(action: 'show', id: m.id)
    }

    def close(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        matchService.closeMatch(m)
        redirect(action: 'show', id: m.id)
    }

    def finish(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        matchService.finish(m)
        redirect(action: 'show', id: m.id)
    }

    def awards(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        matchService.awards(m)
        redirect(action: 'show', id: m.id)
    }

    def balance(Long id){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        matchService.balanceTeams(m)
        redirect(action: 'show', id: m.id)
    }

    def changeTeam(Long id){
        def u =  getAuthenticatedUser()
        def mp = MatchPlayer.get(id)
        if(!mp){
            response.sendError(404)
            return
        }
        matchService.changeTeam(mp)
        redirect(action: 'show', id: mp.match.id)
    }

    def create() {
        def u =  getAuthenticatedUser()
        ['derbys': u.getDerbys(), 'user': u]
    }

    def creating(MatchCommand mc) {
        if (mc.validate()){
            use( TimeCategory ) {
                mc.date = mc.date + mc.hh.hours + mc.mm.minutes
            }            
            def m = matchService.create(mc)
            if(m){
                redirect(action: 'show', id: m.id)
                return
            }
        }
        render view: 'create', model: [matchCommand: mc, 'derbys': getAuthenticatedUser().getDerbys()]
    }

    def available() {
        def u =  getAuthenticatedUser()
        def derbies = u.getDerbys()
        def available = []
        derbies.each{
            available.addAll(it.getOpenMatches())
        }
        ['available': available, 'user': u]
    }

    def setResult(Long id, Integer goalsA, Integer goalsB){
        def u =  getAuthenticatedUser()
        def m = Match.get(id)
        if(!m){
            response.sendError(404)
            return
        }
        matchService.setResult(m, goalsA, goalsB)
        redirect(action: 'show', id: m.id)
    }

    def giveMedal(Long id, String medal){
        def u =  getAuthenticatedUser()
        def mp = MatchPlayer.get(id)
        if(!mp){
            response.sendError(404)
            return
        }
        def voter = mp.match.getMatchPlayers().find{it.player.id == u.getPlayer().id}
        matchService.giveAward(mp, voter, MatchMedal.valueOf(medal))
        redirect(action: 'show', id: mp.match.id)
    }


    def history() {
        def u =  getAuthenticatedUser()
        def derbies = u.getDerbys()
        def history = []
        derbies.each{
            history.addAll(Match.findAllByDerbyAndStatus(it, MatchStatus.CLOSED, [max: 10]))
        }
        ['history': history, 'user': u]
    }

}


class MatchCommand {

    Derby derby
    String pitch
    Integer teamsRequired
    Integer totalPlayers

    @BindingFormat('dd/MM/yyyy')
    Date date

    String time
    Integer hh
    Integer mm


    static constraints = {
        importFrom Match
        time validator: { val, obj ->
            def v = (val.size() == 5 && val[2] == ':')
            if(v){
                try {
                    def hh = new Integer(val[0..1])
                    def mm = new Integer(val[3..4])
                    v = (hh >= 0 && hh <= 59 && mm >= 0 && mm <= 59 )
                    if (v){
                        obj.hh = hh
                        obj.mm = mm
                    }
                }catch(Exception e) {
                    v = false
                }
            }
            v
        }        
    }

}
