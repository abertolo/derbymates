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

import src.main.derbymates.derby.*
import src.main.derbymates.match.*
import grails.util.Environment
import src.main.derbymates.*
import grails.util.Holders

/**
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
class BootStrap {

    def init = { servletContext ->
        if(Environment.current != Environment.PRODUCTION){
            Role role = new Role(authority: 'DERBY_USER')
            role.save()

            def springSecurityService = Holders.grailsApplication.mainContext.getBean('springSecurityService')
            
            def allPass = 'pass1234!'

            def su = new User(username: 'abertolo', password: allPass)
            su.save()
            def derby = new Derby(admin: su, name:'wololo', secureCode: 'wololosecret', matchesPlayed: 0)
            derby.save()
            def match = new Match(admin: su, derby: derby, pitch: 'Garden', date: (new Date()+7), status: MatchStatus.OPEN)
            def match2 = new Match(admin: su, derby: derby, pitch: 'Garden', date: (new Date()-1), status: MatchStatus.OPEN)
            match.save()
            match2.save(validate:false)
            [
                [
                    user: su,
                ],
                [
                    user: 'Gabriel'
                ],
                [
                    user: 'Federico'
                ],
                [
                    user: 'Martin'
                ],
                [
                    user: 'Gustavo'
                ],
                [
                    user: 'Fernando'
                ],
                [
                    user: 'Nicolas'
                ],
                [
                    user: 'Juan'
                ],
                [
                    user: 'Matias'
                ],
                [
                    user: 'Bruno'
                ]
            ].each{
                def user = it.user instanceof String? (new User(username: it.user, password: allPass)): it.user
                user.save()
                def ur = new UserRole(user: user, role: role)
                ur.save()             
                def player = new Player(user)
                player.save()
                def dp = new DerbyPlayer(derby, user.getPlayer())
                dp.save()
                def mp = new MatchPlayer(match: match, player: player)
                mp.save()
                def mp2 = new MatchPlayer(match: match2, player: player)
                mp2.save()                
            }
        }
    }

    def destroy = {
    }
}
