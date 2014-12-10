<%@ page import="java.text.DecimalFormat" %>
<g:set var="formatter" value="${new DecimalFormat("#.##")}" />

<head>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        Dashboard
    </content>
    <g:if test="${!matches.finished.isEmpty() || !matches.awards.isEmpty() || !matches.open.isEmpty()}">
        <div class="row">
            <div class="box box-success">
                <div class="box-header">
                </div><!-- /.box-header -->
                <div class="box-body">
                    <g:if test="${!matches.finished.isEmpty()}">
                        <div class="callout callout-danger">
                            <h4>Finished matches pending to get result or awards!</h4>
                            <ul class="list-unstyled">
                                <g:each in="${matches.finished}" var="match">
                                    <li><a href="${createLink(controller:'match', action: 'show',id: match.id)}"><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></a></li>
                                </g:each>
                            </ul>                        
                        </div>                    
                    </g:if>
                    <g:if test="${!matches.awards.isEmpty()}">
                        <div class="callout callout-warning">
                            <h4>You have medals yo give!</h4>
                            <ul class="list-unstyled">
                                <g:each in="${matches.awards}" var="match">
                                    <li><a href="${createLink(controller:'match', action: 'show',id: match.id)}"><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></a></li>
                                </g:each>
                            </ul>
                        </div>
                    </g:if>
                    <g:if test="${!matches.open.isEmpty()}">
                        <div class="callout callout-info">
                            <h4>Join available <a href="${createLink(controller:'match', action: 'available')}">matches</a>!</h4>
                        </div>
                    </g:if>
                </div><!-- /.box-body -->
            </div><!-- /.box -->
        </div> <!-- /.row -->
    </g:if>
    <g:each in="${derbies}" var="derby">
        <div class="row">
            <div class="box box-success">
                <div class="box-header">
                    <h3 class="box-title">${derby.name}</h3>
                </div><!-- /.box-header -->
                <div class="box-body table-responsive">
                    <table id="derby${derby.id}" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>Player</th>
                                <th>Matches</th>
                                <th>Wins</th>
                                <th>Wins %</th>
                                <th><i class="fa fa-trophy"></i>MVP</th>
                                <th><i class="fa fa-bomb"></i>Striker</th>
                                <th><i class="fa fa-lock"></i>Defender</th>
                                <th><i class="fa fa-child"></i>Goalkeeper</th>
                                <th>Rating</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each in="${derby.getDerbyPlayers()}" var="dp">
                                <tr>
                                    <td>
                                        <g:if test="${dp.player.lastRatingDiff && dp.player.lastRatingDiff > 0}">
                                            <span class="badge bg-green"><i class="fa fa-arrow-up"></i></span>
                                        </g:if>
                                        <g:elseif test="${dp.player.lastRatingDiff && dp.player.lastRatingDiff < 0}">
                                        <span class="badge bg-red"><i class="fa fa-arrow-down"></i></span>
                                        </g:elseif>                        
                                        ${dp.player.nickname}
                                    </td>
                                    <td>${dp.matchs}</td>
                                    <td>${dp.wins}</td>
                                    <td>${dp.matchs>0?formatter.format(dp.wins/dp.matchs):'0.00'}</td>
                                    <td>${dp.mvp}</td>
                                    <td>${dp.striker}</td>
                                    <td>${dp.defender}</td>
                                    <td>${dp.goalkeeper}</td>
                                    <td>${dp.player.rating}</td>
                                </tr>                            
                            </g:each>
                        </tbody>
                    </table>
                </div><!-- /.box-body -->
            </div><!-- /.box -->
        </div> <!-- /.row --> 
        <script type="text/javascript">
            $("#derby${derby.id}").dataTable({
                "bPaginate": false,
                "bFilter": false,
                "bInfo": false,
                "sort": [[ 8, "desc" ]]
            });
        </script>  
    </g:each>
</body>
