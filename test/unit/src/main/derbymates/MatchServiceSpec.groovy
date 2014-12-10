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

import grails.test.mixin.TestFor
import spock.lang.Specification
import src.main.derbymates.derby.*
import src.main.derbymates.match.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
@TestFor(MatchService)
@Mock([Match, Player, MatchPlayer, DerbyPlayer, Derby])
class MatchServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test balanced groups of 2"() {
        when:
            def groups = [
                'a':3,
                'b':2,
                'c':1,
                'd':4
            ]
            def r = service.balancedGroups(groups)
        then:
            r[0].collect{groups[it]}.sum() == 5
            r[1].collect{groups[it]}.sum() == 5

        when:
            groups = [
                'a':3,
                'b':1,
                'c':2,
                'd':4
            ]
            r = service.balancedGroups(groups)
        then:
            r[0].collect{groups[it]}.sum() == 5
            r[1].collect{groups[it]}.sum() == 5
    }

    void "test balanced groups of 3"() {
        when:
            def groups = [
                'a':3,
                'b':2,
                'c':1,
                'd':4,
                'e':5,
                'f':5
            ]
            def r = service.balancedGroups(groups)
        then:
            r[0].collect{groups[it]}.sum() == 10
            r[1].collect{groups[it]}.sum() == 10

        when:
            groups = [
                'a':3,
                'b':3,
                'c':2,
                'd':2,
                'e':1,
                'f':1
            ]
            r = service.balancedGroups(groups)
        then:
            r[0].collect{groups[it]}.sum() == 6
            r[1].collect{groups[it]}.sum() == 6

    }

    void "test balanced groups of 5"() {
        when:
            def groups = [
                'Tincho':1454,
                'Ale':1508,
                'Fede':1550,
                'Gus':1439,
                'Gaby':1567,
                'Feca':1433,
                'Nico':1553,
                'Juan':1592,
                'Dinu':1526,
                'Bruno':1504
            ]
            //'Carni:':1400
            def r = service.balancedGroups(groups)
            println "-----> $r"
        then:
            r[0].collect{groups[it]}.sum() - r[1].collect{groups[it]}.sum() < 20

    }

    void "test update a match players rating"(){
        setup:
            service.derbyService = Mock(DerbyService)
            service.awardService = Mock(AwardService)
            service.glicko2Service = Mock(Glicko2Service)

        when:
            def d = new Derby(name:'wololo', secureCode: 'wololosecret', matchesPlayed: 0)
            d.save(validate:false)

            def m = new Match(winner: true, status: MatchStatus.AWARDS, derby: d)
            m.save(validate:false)

            def p0 = new Player(rating: 1550, ratingDeviation: 200.0G, volatility: 0.06G)
            def p1 = new Player(rating: 1650, ratingDeviation: 200.0G, volatility: 0.06G)
            def p2 = new Player(rating: 1750, ratingDeviation: 200.0G, volatility: 0.06G)
            def p3 = new Player(rating: 1850, ratingDeviation: 200.0G, volatility: 0.06G)
            def p4 = new Player(rating: 1500, ratingDeviation: 200.0G, volatility: 0.06G)
            def p5 = new Player(rating: 1500, ratingDeviation: 200.0G, volatility: 0.06G)
            def p6 = new Player(rating: 1600, ratingDeviation: 200.0G, volatility: 0.06G)
            def p7 = new Player(rating: 1700, ratingDeviation: 200.0G, volatility: 0.06G)
            def p8 = new Player(rating: 1800, ratingDeviation: 200.0G, volatility: 0.06G)
            def p9 = new Player(rating: 1600, ratingDeviation: 200.0G, volatility: 0.06G)
            [p0,p1,p2,p3,p4,p5,p6,p7,p8,p9].each { it.save(validate:false) }

            def mp0 = new MatchPlayer(match: m, player: p0, team: true)
            def mp1 = new MatchPlayer(match: m, player: p1, team: true)
            def mp2 = new MatchPlayer(match: m, player: p2, team: true)
            def mp3 = new MatchPlayer(match: m, player: p3, team: true)
            def mp4 = new MatchPlayer(match: m, player: p4, team: true)
            def mp5 = new MatchPlayer(match: m, player: p5, team: false)
            def mp6 = new MatchPlayer(match: m, player: p6, team: false)
            def mp7 = new MatchPlayer(match: m, player: p7, team: false)
            def mp8 = new MatchPlayer(match: m, player: p8, team: false)
            def mp9 = new MatchPlayer(match: m, player: p9, team: false)
            [mp0,mp1,mp2,mp3,mp4,mp5,mp6,mp7,mp8,mp9].each { it.save(validate:false) }

            service.closeMatch(m)

        then:
            m.status == MatchStatus.CLOSED

    }
}
