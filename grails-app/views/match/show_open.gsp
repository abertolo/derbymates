<%@ page import="src.main.derbymates.match.MatchStatus" %>

<g:set var="matchPlayers" value="${match.getMatchPlayers()}" />
<g:set var="players" value="${matchPlayers*.player}" />
<g:set var="isAdmin" value="${match.admin.id == user.id}" />
<g:set var="teamA" value="${matchPlayers.findAll { it.team }}" />
<g:set var="teamB" value="${matchPlayers.findAll { !it.team }}" />
<g:set var="teamAsum" value="${teamA*.player*.rating.sum()?:0}" />
<g:set var="teamBsum" value="${teamB*.player*.rating.sum()?:0}" />

<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        <h1><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></h1>
        <ol class="breadcrumb">
            <li>Open</li>
        </ol>    
    </content>

    <div class="small-box bg-green">
        <div class="inner">
            <h3>${players.size()}/${match.totalPlayers}</h3>
            <p>Match players</p>     
        </div>
        <div class="icon"><i class="ion ion-person-add"></i></div>
        <g:if test="${!players*.id.contains(user.getPlayer().id)}">
            <a href="${createLink(controller:'match', action: 'join', id: match.id)}" class="btn btn-default btn-lg btn-block"><i class="fa fa-plus"></i>Sign in</a>
        </g:if>
        <g:else>
            <a href="${createLink(controller:'match', action: 'quit', id: match.id)}" class="btn btn-default btn-lg btn-block"><i class="fa fa-times"></i>Sign out</a>
        </g:else>
    </div>
    <g:if test="${isAdmin}">
        <div class="box box-solid box-primary">
            <div class="box-header">
                <h3 class="box-title">Admin</h3>
            </div>
            <div class="box-body">
                <a href="${createLink(controller:'match', action: 'balance', id: match.id)}" class="btn btn-default btn-lg btn-block"><i class="fa fa-gavel"></i>Balance</a>
                
                <g:if test="${!teamA.isEmpty() && !teamB.isEmpty()}">
                    <a href="${createLink(controller:'match', action: 'finish', id: match.id)}" class="btn btn-default btn-lg btn-block"><i class="fa fa-stop"></i>Finish</a>                
                </g:if>

            </div><!-- /.box-body -->
        </div>    
    </g:if>
    <g:if test="${match.totalPlayers != players.size()}">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">List</h3>
                <div class="box-tools" style="width: 20%; float: right">
                    <div class="progress xs progress-striped active">
                        <div class="progress-bar progress-bar-green" style="width: ${((players.size()*100)/match.totalPlayers).intValue()}%"></div>
                    </div>
                </div>
            </div><!-- /.box-header -->
            <div class="box-body no-padding">
                <table class="table">
                    <tr>
                        <th style="width: 80%">Player</th>
                        <th style="width: 20%">Actions</th>
                    </tr>
                    <g:each in="${players}" var="player">
                        <tr>
                            <td>
                                <g:if test="${player.lastRatingDiff && player.lastRatingDiff > 0}">
                                    <span class="badge bg-green"><i class="fa fa-arrow-up"></i></span>
                                </g:if>
                                <g:elseif test="${player.lastRatingDiff && player.lastRatingDiff < 0}">
                                <span class="badge bg-red"><i class="fa fa-arrow-down"></i></span>
                                </g:elseif>
                                ${player.nickname} <small>(${player.rating})</small>
                            </td>
                            <td><a href="${createLink(controller:'player', action: 'show',id: player.id)}"><i class="fa fa fa-eye"></i>Show</a></td>
                        </tr>
                    </g:each>
                </table>
            </div><!-- /.box-body -->
        </div><!-- /.box -->    
    </g:if>
    <g:else>
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Teams</h3>
                <div class="box-tools" style="width: 20%; float: right">
                <h5>${teamAsum} elo vs ${teamBsum} elo <small>(${(teamAsum - teamBsum).abs()} difference)</small></h5>
                </div>                
            </div><!-- /.box-header -->
            <div class="box-body no-padding">
                <g:set var="teams" value="${[teamA, teamB]}" />
                <g:each in="${teams}" var="team">
                    <div class="col-md-6">
                        <table class="table">
                            <tr>
                                <th style="width: 80%">Player</th>
                                <th style="width: 20%">Actions</th>
                            </tr>
                            <g:each in="${team}" var="a">
                                <tr>
                                    <td>
                                        <g:if test="${a.player.lastRatingDiff && a.player.lastRatingDiff > 0}">
                                            <span class="badge bg-green"><i class="fa fa-arrow-up"></i></span>
                                        </g:if>
                                        <g:elseif test="${a.player.lastRatingDiff && a.player.lastRatingDiff < 0}">
                                        <span class="badge bg-red"><i class="fa fa-arrow-down"></i></span>
                                        </g:elseif>
                                        ${a.player.nickname} <small>(${a.player.rating})</small>                                    
                                    </td>
                                    <td><g:if test="${isAdmin}"><a href="${createLink(controller:'match', action: 'changeTeam',id: a.id)}"><i class="fa fa-random"></i></a></g:if></td>
                                </tr>
                            </g:each>
                        </table>         
                    </div>                
                </g:each>
            </div><!-- /.box-body -->
        </div><!-- /.box -->      
    </g:else>
</body>
