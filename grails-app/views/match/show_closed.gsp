<%@ page import="src.main.derbymates.match.MatchStatus" %>
<%@ page import="src.main.derbymates.match.MatchMedal" %>

<g:set var="matchPlayers" value="${match.getMatchPlayers()}" />
<g:set var="players" value="${matchPlayers*.player}" />

<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        <h1><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></h1>
        <ol class="breadcrumb">
            <li>Closed</li>
        </ol>    
    </content>
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
                            <th style="width: 100%">Player</th>
                        </tr>
                        <g:each in="${team}" var="a">
                            <tr>
                                <td>
                                ${a.player.nickname}
                                <g:each in="${a.getAwards()}">
                                    <span class="badge bg-yellow">${it.value}<i class="${it.key.iconClass}"></i></span>
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
