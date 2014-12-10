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
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a> 
 */
@TestFor(MatchPlayer)
@Mock([Match, Player, MatchPlayer, MatchPlayerMedal])
class MatchPlayerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test search awards for a match player"() {
        when:
            def m = new Match(winner: true, status: MatchStatus.AWARDS)
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

            def mpm0 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp1)
            def mpm1 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.BEST_STRIKER, voter: mp1)
            def mpm2 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp2)
            def mpm3 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp3)
            def mpm4 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp4)
            def mpm5 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp5)
            def mpm6 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp6)
            def mpm7 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp7)
            def mpm8 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp8)
            def mpm9 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.MVP, voter: mp9)
            def mpm10 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.BEST_DEFENDER, voter: mp2)
            def mpm11 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.BEST_DEFENDER, voter: mp3)
            def mpm12 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.BEST_STRIKER, voter: mp4)
            def mpm13 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.BEST_STRIKER, voter: mp5)
            def mpm14 = new MatchPlayerMedal(matchPlayer: mp0, medal: MatchMedal.BEST_GOALKEEPER, voter: mp6)
            [mpm0,mpm1,mpm2,mpm3,mpm4,mpm5,mpm6,mpm7,mpm8,mpm9,mpm10,mpm11,mpm12,mpm13,mpm14].each { it.save(validate:false) }

            Map<MatchMedal, Integer> awards = mp0.getAwards()

        then:
            awards[MatchMedal.MVP] == 9
            awards[MatchMedal.BEST_STRIKER] == 3
            awards[MatchMedal.BEST_DEFENDER] == 2
            awards[MatchMedal.BEST_GOALKEEPER] == 1


    }
}
