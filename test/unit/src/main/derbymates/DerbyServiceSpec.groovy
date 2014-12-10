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
@TestFor(DerbyService)
@Mock([Player, DerbyPlayer])
class DerbyServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test update derby players medals"() {
        when:
            def p = new Player(rating: 1550, ratingDeviation: 200.0G, volatility: 0.06G)
            p.save(validate:false)
            def dp = new DerbyPlayer(player: p, mvp: 1, striker: 1, defender: 0, goalkeeper: 0)
            dp.save(validate:false)
            
            service.updateDerbyPlayersMedals(dp, [(MatchMedal.BEST_STRIKER):3,(MatchMedal.BEST_DEFENDER):2])
            dp = DerbyPlayer.get(dp.id)

        then:
            dp.mvp == 1
            dp.striker == 4
            dp.defender == 2
            dp.goalkeeper == 0
    }
}
