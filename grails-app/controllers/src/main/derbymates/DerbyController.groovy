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

import src.main.derbymates.derby.*

/**
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
class DerbyController {

    def list() {
        ['derbys': getAuthenticatedUser().getDerbys()]
    }

    def join() {}

    def joining(DerbyCommand command){
        if(command.validate()){
            Derby derby = Derby.findByNameAndSecureCode(command.name, command.secureCode)
            if(derby){
                def user = getAuthenticatedUser()
                if(!(new DerbyPlayer(derby, user.getPlayer()).save())){
                    flash.error = g.message('asdkkkkk')             
                }else{
                    redirect controller: 'derby', action: 'list'
                    return
                }
                
            }else{
                flash.error = g.message('asd')
            }
        }
        
        forward action: 'join', model: [derbyCommand: command]
    }

    def create() {}
    
    def creating(DerbyCommand command){
        boolean created = command.validate() && Derby.withTransaction { status ->
            def user = getAuthenticatedUser()
            Derby derby = new Derby(
                admin: user,
                name: command.name,
                secureCode: command.secureCode,
                matchesPlayed: 0
            )

            if (!derby.validate() || !derby.save()) {
                status.setRollbackOnly()
                return false
            }
            
            if(!(new DerbyPlayer(derby, user.getPlayer()).save())){
                status.setRollbackOnly()
                return false                
            }

            return true
        }

        if (created) {
            redirect controller: 'derby', action: 'list'
            return
        }

        forward action: 'create', model: [derbyCommand: command]
    }

}

class DerbyCommand {

    String name
    String secureCode

    static constraints = {
        importFrom Derby
    }

}
