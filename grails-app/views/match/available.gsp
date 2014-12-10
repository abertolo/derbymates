<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        <h1>Available matches</h1>
    </content>

    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Open</h3>
        </div><!-- /.box-header -->
        <div class="box-body no-padding">
            <table class="table">
                <tr>
                    <th style="width: 70%">Match</th>
                    <th style="width: 15%">Status</th>
                    <th style="width: 15%">Actions</th>
                </tr>
                <g:each in="${available}" var="match">
                    <g:set var="matchPlayers" value="${match.getMatchPlayers()}" />
                    <tr>
                        <td><g:formatDate format="dd/MM/yyyy 'at' HH:mm 'hs.'" date="${match.date}"/> <small>${match.pitch}</small></td>
                        <td>${matchPlayers.size()}/${match.totalPlayers}</td>
                        <td><a href="${createLink(controller:'match', action: 'show',id: match.id)}"><i class="fa fa fa-eye"></i>Show</a></td>
                    </tr>
                </g:each>
            </table>
        </div><!-- /.box-body -->
    </div><!-- /.box -->    
</body>
