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

/**
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
@TestFor(DerbyController)
@Mock([User,Player,Derby,DerbyPlayer])
class DerbyControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test show user derbys"() {
        when:
            def user = new User(username: 'test', password: 'test', enabled: true)
            user.save(validate:false)

            def player = new Player(user)
            player.save(validate:false)

            def derby = new Derby(admin: user, name:'asd', secureCode: 'asd', matchesPlayed: 0)
            derby.save(validate:false)

            def dp = new DerbyPlayer(derby, user.getPlayer())
            dp.save(validate:false)

            controller.metaClass.getAuthenticatedUser = {->
                return user
            } 
            def model = controller.list()

        then:
            model.derbys.size() == 1
    }
}
