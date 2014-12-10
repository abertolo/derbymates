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

import src.main.derbymates.*

/**
 * Result of a calculation of glicko2 algorithm.
 *
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
class Glicko2Player {

    public static final Integer DEFAULT_RATING = 1500
    public static final BigDecimal DEFAULT_VOL = 0.06G
    public static final BigDecimal DEFAULT_RD = 350G
    public static final BigDecimal GC_CONSTANT = 173.7178G

    Integer elo
    BigDecimal vol
    BigDecimal rd

    //Auxiliar
    BigDecimal elo2
    BigDecimal rd2

    //result calculation
    Integer newElo
    BigDecimal newVol
    BigDecimal newRd
    
    Integer eloDiff
    BigDecimal volDiff
    BigDecimal rdDiff

    public Glicko2Player(Player player){
        elo = player.rating
        vol = player.volatility
        rd = player.ratingDeviation

        calculateAux()
    }

    public Glicko2Player(List<MatchPlayer> opponents){
        elo = 0
        vol = DEFAULT_VOL
        rd = 0G
        opponents.each{ o->
            add(new Glicko2Player(o.player))
        }
        divide(opponents.size())
        calculateAux()
    }


    public Glicko2Player(){
        elo = 0
        vol = DEFAULT_VOL
        rd = 0G
    }

    public void calculateAux(){
        elo2 = (elo - DEFAULT_RATING)/GC_CONSTANT
        rd2 = rd/GC_CONSTANT        
    }

    public void add(Glicko2Player p2){
        elo += p2.elo
        vol += p2.vol
        rd += p2.rd
    }

    public void divide(Integer i){
        if (i && i > 0){
            elo = (elo/i).intValue()
            vol = (vol/i)
            rd = (rd/i)
        }
    }
}
