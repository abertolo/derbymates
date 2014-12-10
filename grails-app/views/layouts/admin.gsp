<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><g:layoutTitle default="DerbyMates"/></title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>

        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.png')}" type="image/vnd.microsoft.icon" />

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Ionicons -->
        <link href="//code.ionicframework.com/ionicons/1.5.2/css/ionicons.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <asset:stylesheet src="lte/css/AdminLTE.css"/>
        <asset:javascript src="lte/js/AdminLTE/app.js"/>
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
        <g:layoutHead/>
    </head>
    <body class="skin-blue">
        <!-- header logo: style can be found in header.less -->
        <header class="header">
            <a href="${createLink(controller: 'home', view: 'index')}" class="logo" style="font-family: Montserrat,sans-serif;">
                DERBY MATES
            </a>
            <!-- Header Navbar: style can be found in header.less -->
            <nav class="navbar navbar-static-top" role="navigation">
                <!-- Sidebar toggle button-->
                <a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                        <!-- User Account: style can be found in dropdown.less -->
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="glyphicon glyphicon-user"></i>
                                <span><sec:loggedInUserInfo field="username"/><i class="caret"></i></span>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- Menu Footer-->
                                <li class="user-footer">
                                    <div class="pull-left">
                                        <a href="#" class="btn btn-default btn-flat">Profile</a>
                                    </div>                                
                                    <div class="pull-right">
                                        <form action="${createLink(controller: 'logout', action: 'index')}" method="POST">
                                        <input type="submit" class="btn btn-default btn-flat" value="Log out" />
                                        </form>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="wrapper row-offcanvas row-offcanvas-left">
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="left-side sidebar-offcanvas">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <ul class="sidebar-menu">
                        <li>
                            <a href="${createLink(controller:'player',action: 'me')}">
                                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                            </a>
                        </li>
                        <li class="treeview active">
                            <a href="#">
                                <i class="fa fa-bar-chart-o"></i>
                                <span>Groups</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="${createLink(controller:'derby',action: 'list')}"><i class="fa fa-angle-double-right"></i>My Groups</a></li>
                                <li><a href="${createLink(controller:'derby',action: 'join')}"><i class="fa fa-angle-double-right"></i> Join Group</a></li>
                                <li><a href="${createLink(controller:'derby',action: 'create')}"><i class="fa fa-angle-double-right"></i> Create Group</a></li>
                            </ul>
                        </li>
                        <li class="treeview active">
                            <a href="#">
                                <i class="fa fa-laptop"></i>
                                <span>Matches</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="${createLink(controller:'match',action: 'create')}"><i class="fa fa-angle-double-right"></i>New Match</a></li>                            
                                <li><a href="${createLink(controller:'match',action: 'available')}"><i class="fa fa-angle-double-right"></i>Available Matches</a></li>
                                <li><a href="${createLink(controller:'match',action: 'history')}"><i class="fa fa-angle-double-right"></i>Matches History</a></li>           
                            </ul>
                        </li>
                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Right side column. Contains the navbar and content of the page -->
            <aside class="right-side">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <g:if test="${pageProperty(name:'page.contentHeader')}">
                            <g:pageProperty name="page.contentHeader" />
                        </g:if>
                    </h1>
                    <g:if test="${pageProperty(name:'page.breadcrumb')}">
                        <g:pageProperty name="page.breadcrumb" />
                    </g:if>
                </section>

                <!-- Main content -->
                <section class="content">
                    <g:layoutBody/>
                </section><!-- /.content -->
            </aside><!-- /.right-side -->
        </div><!-- ./wrapper -->

        <!-- AdminLTE for demo purposes 
        <script src="../../js/AdminLTE/demo.js" type="text/javascript"></script>
        -->
    </body>
</html>
