<head>
    <meta name='layout' content='admin'/>
</head>
<body>
    <content tag="contentHeader">
        Create group
    </content>
    <div class="row">
        <div class="box box-primary">
            <!-- form start -->
            
            <g:hasErrors bean="${derbyCommand}">
                <div class="box box-solid bg-red">
                    <div class="box-body">
                        <g:renderErrors bean="${derbyCommand}" as="list"/>
                    </div><!-- /.box-body -->
                </div>
            </g:hasErrors>            
            <form role="form" action="${createLink(controller:"derby", action:"creating")}" method="POST">
                <div class="box-body">
                    <div class="form-group">
                        <label for="groupName">Name</label>
                        <input type="text" class="form-control" id="groupName" name="name" placeholder="My Friends">
                    </div>
                    <div class="form-group">
                        <label for="invitationCode">Invitation code</label>
                        <input type="text" class="form-control" id="invitationCode" name="secureCode" placeholder="My code">
                    </div>
                </div><!-- /.box-body -->

                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div><!-- /.box -->    
    </div>    
</body>
