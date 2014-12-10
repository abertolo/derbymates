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
 * Match awards
 *
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
public enum MatchMedal {

    MVP(10, 'mvp', 'fa fa-trophy', 'MVP'),

    BEST_STRIKER(5, 'striker', 'fa fa-bomb', 'Best Striker'),

    BEST_DEFENDER(5, 'defender', 'fa fa-lock', 'Best Defender'),

    BEST_GOALKEEPER(5, 'goalkeeper', 'fa fa-child', 'Best Goalkeeper')

    final Integer gainPercentage
    final String statField
    final String iconClass
    final String msg
    
    public MatchMedal(Integer f, String s, String ic, String m) {
        this.gainPercentage = f
        this.statField = s
        this.iconClass = ic
        this.msg = m
    }   

}
