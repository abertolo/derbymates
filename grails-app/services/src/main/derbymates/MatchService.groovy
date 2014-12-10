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
import grails.transaction.Transactional
import src.main.derbymates.MatchCommand

/**
 * @author <a href='mailto:alejandrobertolo@gmail.com'>Alejandro Bertolo</a>
 */
@Transactional
class MatchService {

    def glicko2Service
    def awardService
    def derbyService

    def springSecurityService


    public void quitMatch(MatchPlayer mp){
        assert mp, 'Match player is required'

        mp.delete()
    }

    public void changeTeam(MatchPlayer mp){
        assert mp, 'Match player is required'

        mp.team = !mp.team
        mp.save()
    }

    /**
    *   Set the team of every MatchPlayer of a Match. 
    **/
    public void balanceTeams(Match match){
        List<MatchPlayer> players = match.getMatchPlayers()

        List<List<MatchPlayer>> teams = balancedGroups(players.collectEntries{ [it, it.player.rating] })

        teams.eachWithIndex{ List<MatchPlayer> team, index ->
            team.each { MatchPlayer mp ->
                mp.team = index
                mp.save()
            }
        }
    }

    /**
    *   Divide a map keys in 2 groups of objects in such a way that 
    *   the sum of values of each group difference is the closest to 0.
    **/
    public List<List<Object>> balancedGroups(Map<Object, Integer> groupable){
        Integer groupSize = (groupable.size() / 2).intValue()
        
        List bestBGroup = []
        Integer bestDifference = null

        List aGroup = []
        for(int i=0; i < groupSize; i++){
            groupable.keySet().each{ o ->
                if (aGroup.size() < groupSize && !aGroup.contains(o)){
                    aGroup << o
                }else{
                    List bGroup = groupable.keySet().findAll{ !aGroup.contains(it) } as List

                    Integer aScore = aGroup.collect{ groupable[it] }.sum()
                    Integer bScore = bGroup.collect{ groupable[it] }.sum()
                    Integer diff = (aScore - bScore).abs()

                    if(bestDifference == null ||  diff < bestDifference){
                        bestDifference = diff
                        bestBGroup = bGroup
                    }

                    if(!aGroup.contains(o)){
                        aGroup.remove(i)
                        aGroup.add(i, o)
                    }
                }
            }            
        }

        aGroup = groupable.keySet().findAll{ !bestBGroup.contains(it) } as List
        return [aGroup, bestBGroup]
    }

    /*
    *   Update the derby players with recalculated elo according
    *   the match result and medals given.
    *   Change de match status.
    */
    public void closeMatch(Match match){
        assert match.status == MatchStatus.AWARDS, "Match is closeable only in status: AWARDS"
        
        Map<MatchPlayer, Glicko2Player> calculations = glicko2Service.calculateMatchEloAdjustments(match)
        calculations.each{
            Map<MatchMedal, Integer> awards = it.key.getAwards()

            derbyService.updateDerbyPlayers(match, it.key.player, awards)
            
            Integer percentage = awardService.calculateGainPercentage(awards)
            it.value.eloDiff = it.value.eloDiff + ((percentage * it.value.eloDiff)/100).intValue().abs()

            it.key.player.rating += it.value.eloDiff
            it.key.player.lastRatingDiff = it.value.eloDiff
            it.key.player.volatility += it.value.volDiff
            it.key.player.ratingDeviation += it.value.rdDiff
            it.key.player.save()
        }
        match.status = MatchStatus.CLOSED
        match.save()

        match.derby.matchesPlayed = match.derby.matchesPlayed + 1
        match.derby.save()
    }

    /*
    *   Create a new match
    */
    public Match create(MatchCommand mc){
        assert !mc.hasErrors()

        def principal = springSecurityService.principal
        assert principal, 'User must be authenticated'

        def user = User.load(principal.id)
        def match = new Match()
        match.admin = user
        match.derby = mc.derby
        match.pitch = mc.pitch
        match.date = mc.date
        match.teamsRequired = mc.teamsRequired
        match.totalPlayers = mc.totalPlayers

        match.save()
    }

    public void finish(Match match){
        assert match, 'Match is required.'
        assert match.status == MatchStatus.OPEN, 'Match status should be open.'
        match.status = MatchStatus.FINISHED
        match.save()
    }

    public void awards(Match match){
        assert match, 'Match is required.'
        assert match.status == MatchStatus.FINISHED, 'Match status should be open.'
        assert match.goalsA != null && match.goalsB != null, 'Match need a result.'

        match.status = MatchStatus.AWARDS
        match.save()
    } 

    public void giveAward(MatchPlayer mp, MatchPlayer voter, MatchMedal medal){
        assert mp, 'Match player is required.'
        assert voter, 'Voter is required.'
        assert medal, 'Medal is required.'

        def unique = MatchPlayerMedal.findByVoterAndMedal(voter, medal)
        assert unique == null, 'Medal already given'

        def mpm = new MatchPlayerMedal()
        mpm.matchPlayer = mp
        mpm.medal = medal
        mpm.voter = voter
        mpm.save()
    }    

    public void removeAward(MatchPlayer voter, MatchMedal medal){
        assert voter, 'Voter is required.'
        assert medal, 'Medal is required.'

        MatchPlayerMedal.findByVoterAndMedal(voter, medal)?.delete()
    }    


    public void setResult(Match match, Integer goalsA, Integer goalsB){
        assert match, 'Match is required.'
        assert match.status == MatchStatus.FINISHED, 'Match status should be finished.'
        assert goalsA >= 0 && goalsB >= 0, 'Both scores should be greater equal than zero.'

        match.goalsA = goalsA
        match.goalsB = goalsB
        match.winner = goalsA > goalsB? true : (goalsB > goalsA? false : null)
        match.save()
    }

}
