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

import grails.transaction.Transactional
import src.main.derbymates.derby.*
import src.main.derbymates.match.*

/**
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
@Transactional
class DerbyService {

    def grailsApplication

    /*
    *   Update the medal's stats of a player in a derby.
    */
    public void updateDerbyPlayers(Match match, Player player, Map<MatchMedal, Integer> awards) {
        def proxy = grailsApplication.mainContext.derbyService

        DerbyPlayer derbyPlayer = DerbyPlayer.findByDerbyAndPlayer(match.derby, player) 
        assert derbyPlayer, "No derby player found for: ${match.derby} and ${player}"

        derbyPlayer.matchs = derbyPlayer.matchs + 1

        def mp = match.getMatchPlayers().find{ it.player.id == player.id }

        if(match.winner == mp.team) derbyPlayer.wins = derbyPlayer.wins + 1

        proxy.updateDerbyPlayersMedals(derbyPlayer, awards)
    }

    /*
    *   Update the medal's stats of a player in a derby.
    */
    public void updateDerbyPlayersMedals(DerbyPlayer derbyPlayer, Map<MatchMedal, Integer> awards) {
        awards.keySet().each{ medal->
            if(DerbyPlayer.STATS_FIELDS.contains(medal.statField)){
                derbyPlayer[medal.statField] = derbyPlayer[medal.statField] + awards[medal]
            }
        }
        derbyPlayer.save()
    }
}
