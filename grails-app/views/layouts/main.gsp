<!DOCTYPE html>
<html class="bg-black">
    <head>
        <meta charset="UTF-8">
        <title><g:layoutTitle default="DerbyMates"/></title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>

        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.png')}" type="image/vnd.microsoft.icon" />
        
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <asset:stylesheet src="lte/css/AdminLTE.css"/>

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->

        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
        <g:layoutHead/>
    </head>
    <body class="bg-black">

        <div class="form-box" id="login-box">
            <!-- <div class="header">Register New Membership</div> -->
            <g:layoutBody/>
        </div>
        <asset:javascript src="lte/js/bootstrap.min.js"/>
    </body>
</html>