/*
 * Copyright 2014 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package src.main.derbymates.match

/**
 * Status of a Match
 *
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
public enum MatchStatus {
    
    //Ready to sign up to play.
    OPEN,

    //The match was already played and have a result
    FINISHED,

    //The match was already played and players can award other players.
    AWARDS,

    //The match was already played and  awarded.
    CLOSED,

    //The match was not played
    CANCELLED

}
