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

import src.main.derbymates.match.*
import src.main.derbymates.derby.*
/**
 * A Derby represent a league of players(and mates) that are use to play together and want
 * to keep track of results and shuffle teams automatically (based on glicko-2 rating system).
 *
 * It also prioritize the sign up to a match to let the usual players join.
 *
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
class Derby {

    User admin
    
    String name

    String secureCode

    Integer matchesPlayed
    
    static constraints = {
        name unique: true, size: 3..25
        secureCode size: 3..25
    }

    List<DerbyPlayer> getDerbyPlayers(){    
        DerbyPlayer.findAllByDerby(this)
    }

    List<Match> getOpenMatches(){
        Match.findAllByDerbyAndStatus(this, MatchStatus.OPEN)
    }

    List<Match> getFinishedMatches(){
        Match.findAllByDerbyAndStatus(this, MatchStatus.FINISHED)
    }

    List<Match> getAwardsMatches(){
        Match.findAllByDerbyAndStatus(this, MatchStatus.AWARDS)
    }

    List<Match> getClosedMatches(){
        Match.findAllByDerbyAndStatus(this, MatchStatus.CLOSED)
    }
}
