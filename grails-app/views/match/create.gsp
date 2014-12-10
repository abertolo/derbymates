<head>
    <asset:stylesheet src="lte/css/timepicker/bootstrap-timepicker.min.css"/>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        Create match
    </content>
    <div class="row">
        <div class="box box-primary">
            <!-- form start -->
            <g:hasErrors bean="${matchCommand}">
                <div class="box box-solid bg-red">
                    <div class="box-body">
                        <g:renderErrors bean="${matchCommand}" as="list"/>
                    </div><!-- /.box-body -->
                </div>
            </g:hasErrors>            
            <form role="form" action="${createLink(controller:"match", action:"creating")}" method="POST">
                <div class="box-body">
                    <div class="form-group">
                        <label for="derbyName">Group</label>
                        <select class="form-control" id="groupName" name="derby.id">
                            <g:each in="${derbys}">
                                <option value="${it.id}">${it.name}</option>
                            </g:each>
                            
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="pitch">Pitch</label>
                        <input type="text" class="form-control" id="pitch" name="pitch"/>
                    </div>
                    <div class="form-group">
                        <label for="totalPlayers">Size</label>
                        <select class="form-control" id="totalPlayers" name="totalPlayers">
                            <g:each in="${10..22}">
                                <option value="${it}" ${it==10?'selected="selected"':''}>${it}</option>
                            </g:each>
                            
                        </select>
                        <input type="hidden" name="teamsRequired" value="2">
                    </div>
                <div class="form-group">
                    <label for="date">Date</label>
                    <div class="input-group">
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" id="date" name="date" data-mask/>
                    </div><!-- /.input group -->
                </div><!-- /.form group -->
                    <div class="bootstrap-timepicker">
                        <div class="form-group">
                            <label>Time:</label>
                            <div class="input-group">
                                <input type="text" name="time" class="form-control timepicker"/>
                                <div class="input-group-addon">
                                    <i class="fa fa-clock-o"></i>
                                </div>
                            </div><!-- /.input group -->
                        </div><!-- /.form group -->  
                    </div>  
                </div><!-- /.box-body -->

                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div><!-- /.box -->    
    </div>
    <script type="text/javascript">
        $(".timepicker").timepicker({
            showInputs: false,
            showMeridian: false,
            minuteStep: 30,
            defaultTime: '12:00'
        });
        $("#date").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
    </script>
</body>
