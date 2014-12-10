<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        <h1>Historic matches</h1>
    </content>

    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Closed</h3>
        </div><!-- /.box-header -->
        <div class="box-body no-padding">
            <table class="table">
                <tr>
                    <th style="width: 70%">Match</th>
                    <th style="width: 15%">Result</th>
                    <th style="width: 15%">Actions</th>
                </tr>
                <g:each in="${history}" var="match">
                    <g:set var="matchPlayers" value="${match.getMatchPlayers()}" />
                    <g:set var="userPlayer" value="${user.getPlayer()}" />
                    <g:set var="me" value="${matchPlayers.find {it.player.id == userPlayer.id}}" />
                    <tr>
                        <td>
                            <g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small>
                        </td>
                        <td>
                            <g:if test="${match.winner == me.team}">
                                <span class="badge bg-green">won</span>
                            </g:if>
                            <g:elseif test="${match.winner == null}">
                                <span class="badge bg-yellow">draw</span>
                            </g:elseif>
                            <g:else>
                                <span class="badge bg-red">lost</span>
                            </g:else>
                        </td>
                        <td>
                            <a href="${createLink(controller:'match', action: 'show',id: match.id)}"><i class="fa fa fa-eye"></i>Show</a>
                            </td>
                    </tr>
                </g:each>
            </table>
        </div><!-- /.box-body -->
    </div><!-- /.box -->    
</body>
