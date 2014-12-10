<head>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        My Groups
    </content>
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <th>Name</th>
                            <th>Code</th>
                            <th style="width: 40px">Matches</th>
                        </tr>
                        <g:each in="${derbys}">
                            <tr>
                                <td>${it.name}</td>
                                <td>${it.secureCode}</td>
                                <td>${it.matchesPlayed}</td>
                            </tr>
                        </g:each>
                    </table>
                </div><!-- /.box-body -->
            </div>
        </div>
    </div>    
</body>
