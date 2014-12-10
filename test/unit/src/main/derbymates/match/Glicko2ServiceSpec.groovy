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
package src.main.derbymates.match

import grails.test.mixin.TestFor
import spock.lang.Specification
import src.main.derbymates.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
@TestFor(Glicko2Service)
@Mock([Match, Player, MatchPlayer])
class Glicko2ServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test calculate player elo adjustments"() {
        when: "win"
            def player = new Player(rating: 1500, ratingDeviation: 200.0G, volatility: 0.06G)
            def opp = new Glicko2Player(new Player(rating: 1700, ratingDeviation: 300.0G, volatility: 0.06G))
            def result = service.calculatePlayerEloAdjustments(player, opp, 1.0G)

        then:
            result.newElo == 1601
            result.newRd.intValue() == 186

        when: "loose"
            result = service.calculatePlayerEloAdjustments(player, opp, 0.0G)

        then: 
            result.newElo == 1456
            result.newRd.intValue() == 186

        when: "draw"
            result = service.calculatePlayerEloAdjustments(player, opp, 0.5G)

        then:
            result.newElo == 1528
            result.newRd.intValue() == 186                  
    }


    void "test calculate match elo adjustments"() {
        when:
            def m = new Match(winner: true)
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

            Map<MatchPlayer, Glicko2Player> result = service.calculateMatchEloAdjustments(m)

        then:
            result.size() == 10
            result[mp0].eloDiff > 0
            result[mp5].eloDiff < 0

    }
}
