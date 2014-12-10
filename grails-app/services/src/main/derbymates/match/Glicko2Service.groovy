package src.main.derbymates.match

import grails.transaction.Transactional
import src.main.derbymates.derby.*
import src.main.derbymates.*
/**
 * A port of https://github.com/RobKohr/glicko implementation of
 * glicko-2 rating system along with a team based setup.
 */
@Transactional
class Glicko2Service {

    private final LOG10E = Math.log10(Math.E);

    public final GLICO_2_DEFAULT_SETTINGS = [
        'factor' : 1,
        'system_constant': 0.5
    ]

    def grailsApplication

    /*
        Glicko-2 main function (attempts to follow the paper)
        Calculate the adjustments to a player after playing against a set of opponents.
        Returns an output of an Glicko2Player object.

        rating_period should be the time in seconds in which a player typically plays
        10-15 games. Default is a month. Currently not used in calculations

        For system_constants, the paper says pick a number .3 to 1.2.
        Adjust to achieve better predictive accuracy. Default is .5
        (perhaps a function should be written to calculate a good system_constant)

        factor is something I added that isn't in the paper. All outputs are multiplied by
        the factor. This is useful if you want to adjust the value of the calculation -
        for example 0.5 to half the weight when you are playing on a team of two players.

        result: win:1, loose:0,  draw: 0.5
    */
    public Glicko2Player calculatePlayerEloAdjustments(Player player, Glicko2Player opp, BigDecimal result){
        
        def t = GLICO_2_DEFAULT_SETTINGS['system_constant']
        def factor = GLICO_2_DEFAULT_SETTINGS['factor']

        def thisPlayer = new Glicko2Player(player)
        
        def pi = Math.PI
        def ln = { val ->
            return Math.log(val) / LOG10E;
        }

        //step 3
        def g = { rd ->
            return 1/(
            Math.sqrt(
                1 + (
                (3*(rd**2))/
                    (pi**2)
                )
            )
            );
        };

        //the paper has three values comming in for the two players, this just takes the player objects
        def E = { p1, p2->
            return 1 / (1 + Math.exp((-g(p2.rd2)*(p1.elo2-p2.elo2))));
        }

        //iterate over all opponents he played against to calculate variance
        def v_sum = ( (g(opp.rd2))**2 ) * E(thisPlayer, opp) * (1 - E(thisPlayer, opp) );
        def v = v_sum**-1

        //step 4
        def part_delta_sum = g(opp.rd2) * (result - E(thisPlayer, opp) );
       
        //delta is the change in rating
        def delta = v * part_delta_sum;

        //step 5
        //5.1
        def a = ln(thisPlayer.vol**2)
        def f = { x ->
            return (
            (Math.exp(x)*((delta**2)-(thisPlayer.rd2**2)-v-Math.exp(x))) / 
                (
                2* (((thisPlayer.rd2**2) + v + Math.exp(x) )**2)
                )
            ) - (
            (x - a)/((t**2))
            )
        }
        def e = 0.000001;//convergence tolerance

        //5.2
        def A = a;
        def B
        if( (delta**2) > ((thisPlayer.rd2**2) + v) ){
            B = ln((delta**2) - (thisPlayer.rd2**2) - v);
        }else{
            def k = 1
            while(f(a - (k*Math.abs(t))) < 0){
                k = k+1;
            }
            B = a - k * Math.abs(t);
        }

        //5.3
        def fa = f(A);
        def fb = f(B);

        //5.4
        while((Math.abs(B-A) > e)){
            def C = A+(A-B)*fa/(fb-fa);
            def fc = f(C);
            if((fc*fb)<0){
                A = B;
                fa = fb;
            }else{
                fa = fa/2;
            }
            B = C;
            fb = fc;
        }
        def vol_prime = Math.exp(A/2);

        //Step 6
        def rd2_star = Math.sqrt((thisPlayer.rd2**2) + (vol_prime**2));
        //Step 7
        def rd2_prime = 1/(
            Math.sqrt( 
            ( (1 / ((rd2_star**2))) + 1 / v )
            ));

        def rating2_prime_sum = g(opp.rd2)*(result - E(thisPlayer, opp));
        def rating2_prime = thisPlayer.elo2 + (rd2_prime**2) * rating2_prime_sum;

        //step 8 convert back to original scale
        def auxRating = rating2_prime * Glicko2Player.GC_CONSTANT + Glicko2Player.DEFAULT_RATING;
        def auxRd = rd2_prime * Glicko2Player.GC_CONSTANT;

        thisPlayer.eloDiff = (auxRating - thisPlayer.elo)*factor;
        thisPlayer.rdDiff = (auxRd - thisPlayer.rd)*factor;
        thisPlayer.volDiff = (vol_prime - thisPlayer.vol)*factor;

        thisPlayer.newElo = thisPlayer.eloDiff + thisPlayer.elo;
        thisPlayer.newRd = thisPlayer.rdDiff + thisPlayer.rd;
        thisPlayer.newVol = thisPlayer.volDiff + thisPlayer.vol; 

        thisPlayer
    }

    /* 
        Team match calculator 
        This function works by calculating what the change would be for each player against all the players
        on the opposing team, averaging them, and then dividing by the number of players on that player's team. 
    */
    public Map<MatchPlayer, Glicko2Player> calculateMatchEloAdjustments(Match match){
        def proxy = grailsApplication.mainContext.glicko2Service
        def teams = match.getTeams()
        def calculation = [:]

        def calculateTeamPlayers = { List<MatchPlayer> players, List<MatchPlayer> opponents->
            
            def teamAsPlayer = new Glicko2Player(opponents);
            BigDecimal won = match.winner==null?0.5G:(match.winner==players.first().team?(1.0G):(0.0G))

            players.each{ p->
                def result = proxy.calculatePlayerEloAdjustments(p.player, teamAsPlayer, won);
                calculation[p] = result
            }
        }

        calculateTeamPlayers(teams[0],teams[1])
        calculateTeamPlayers(teams[1],teams[0])

        calculation
    }

}
