<%@ page import="src.main.derbymates.match.MatchStatus" %>

<g:set var="matchPlayers" value="${match.getMatchPlayers()}" />
<g:set var="players" value="${matchPlayers*.player}" />
<g:set var="isAdmin" value="${match.admin.id == user.id}" />

<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        <h1><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></h1>
        <ol class="breadcrumb">
            <li>Finished</li>
        </ol>    
    </content>
    <g:if test="${isAdmin}">
        <div class="box box-solid box-primary">
            <div class="box-header">
                <h3 class="box-title">Admin</h3>
            </div>
            <div class="box-body">
                <g:if test="${match.date < new Date()}">
                    <div class="row" style="padding-top: 10px;">
                        <form action="${createLink(controller:'match', action: 'setResult')}">
                            <input type="hidden" name="id" value="${match.id}" />
                            <div class="col-xs-1">
                                <label for="goalsA">Team A</label>
                                <input type="number" class="form-control" name="goalsA" placeholder="0" value="${match.goalsA}">
                            </div>
                            <div class="col-xs-1">
                                <label for="goalsB">Team B</label>
                                <input type="number" class="form-control" name="goalsB" placeholder="0" value="${match.goalsB}">
                            </div>
                            <div class="col-xs-10">
                                <input type="submit" class="btn btn-default btn-lg btn-block" style="margin-top: 14px;" value="Set result">
                            </div>                            
                        </form>
                    </div>
                </g:if>
                <g:if test="${match.goalsA != null && match.goalsB != null}">
                    <div class="row" style="padding-top: 10px;">
                        <a href="${createLink(controller:'match', action: 'awards', id: match.id)}" class="btn btn-default btn-lg btn-block"><i class="fa fa-trophy"></i>Awards</a>                
                    </div>                    
                </g:if>             
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
            <g:set var="teams" value="${[teamA, teamB]}" />
            <g:each in="${teams}" var="team" status="i">
                <div class="col-md-6">
                    <h1>Team ${teamNames[i]}</h1>
                    <table class="table">
                        <tr>
                            <th style="width: 80%">Player</th>
                            <th style="width: 20%">Actions</th>
                        </tr>
                        <g:each in="${team}" var="a">
                            <tr>
                                <td>${a.player.nickname}</td>
                                <td></td>
                            </tr>
                        </g:each>
                    </table>         
                </div>                
            </g:each>
        </div><!-- /.box-body -->
    </div><!-- /.box -->
</body>
