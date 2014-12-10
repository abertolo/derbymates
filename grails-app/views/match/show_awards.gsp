<%@ page import="src.main.derbymates.match.MatchStatus" %>
<%@ page import="src.main.derbymates.match.MatchMedal" %>

<g:set var="matchPlayers" value="${match.getMatchPlayers()}" />
<g:set var="players" value="${matchPlayers*.player}" />
<g:set var="isAdmin" value="${match.admin.id == user.id}" />
<g:set var="userPlayer" value="${user.getPlayer()}" />

<g:set var="me" value="${matchPlayers.find {it.player.id == userPlayer.id}}" />
<g:set var="myAwards" value="${me?.getPlayerVotes()}" />

<g:set var="remainingAwards" value="${ MatchMedal.values() - myAwards.keySet()}" />

<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        <h1><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></h1>
        <ol class="breadcrumb">
            <li>Awards</li>
        </ol>    
    </content>

    <g:if test="${me}">
        <div class="small-box bg-green">
            <div class="inner">
                <h3>${MatchMedal.values().size()-myAwards.size()}/${MatchMedal.values().size()}</h3>
                <p>Medals to give</p>    
            </div>
            <g:each in="${remainingAwards}">
                <small class="badge"><i class="${it.iconClass}"></i>${it.msg}</small>
            </g:each>
            <div class="icon"><i class="ion ion-ribbon-a"></i></div>
        </div>    
    </g:if>
    <g:if test="${isAdmin}">
        <div class="box box-solid box-primary">
            <div class="box-header">
                <h3 class="box-title">Admin</h3>
            </div>
            <div class="box-body">
                <a href="${createLink(controller:'match', action: 'close', id: match.id)}" class="btn btn-default btn-lg btn-block"><i class="fa fa-database"></i>Close</a>            
            </div><!-- /.box-body -->
        </div>    
    </g:if>

    <g:set var="teamA" value="${matchPlayers.findAll { it.team }}" />
    <g:set var="teamB" value="${matchPlayers.findAll { !it.team }}" />
    <g:set var="teamAsum" value="${teamA*.player*.rating.sum()?:0}" />
    <g:set var="teamBsum" value="${teamB*.player*.rating.sum()?:0}" />
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Teams</h3>
            <div class="box-tools" style="width: 20%; float: right">
            <h5>${teamAsum} elo vs ${teamBsum} elo <small>(${(teamAsum - teamBsum).abs()} difference)</small></h5>
            </div>                
        </div><!-- /.box-header -->
        <div class="box-body no-padding">
            <g:set var="teamNames" value="${['A', 'B']}" />
            <g:set var="teamScores" value="${[match.goalsA, match.goalsB]}" />
            <g:set var="teams" value="${[teamA, teamB]}" />
            <g:each in="${teams}" var="team" status="i">
                <div class="col-md-6">
                    <h1><small>Team ${teamNames[i]}</small> <span>${teamScores[i]}</span></h1>
                    <table class="table">
                        <tr>
                            <th style="width: 80%">Player</th>
                            <th style="width: 20%">Actions</th>
                        </tr>
                        <g:each in="${team}" var="a">
                            <tr>
                                <td>
                                ${a.player.nickname}
                                <g:each in="${myAwards}">
                                    <g:if test="${it.value.id == a.id}">
                                        <span class="badge bg-yellow"><i class="${it.key.iconClass}"></i></span>
                                    </g:if>
                                </g:each>
                                </td>
                                <td>
                                    <g:each in="${remainingAwards}" var="award">
                                        <a href="${createLink(controller:'match', action: 'giveMedal', params: [id: a.id, medal: award])}">
                                            <i class="${award.iconClass}"></i>
                                        </a>
                                    </g:each>                                    
                                </td>
                            </tr>
                        </g:each>
                    </table>         
                </div>                
            </g:each>
        </div><!-- /.box-body -->
    </div><!-- /.box -->      

</body>
