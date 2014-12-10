<%--
    Based on: http://bootstraptaste.com/theme/squadfree/
--%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils as RCU" %>

<g:set var="language" value="${RCU.getLocale(request).getLanguage()}" />

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Amateur football management">
    <meta name="author" content="DerbyMates">

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.png')}" type="image/vnd.microsoft.icon" />

    <title><g:layoutTitle default="DerbyMates"/></title>

    <asset:stylesheet src="main/css/style.css"/>
    <!--[if lt IE 9]>
        <asset:stylesheet src="main/css/gh-fork-ribbon.ie.css"/>
    <![endif]-->
    <g:layoutHead/>
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-custom">
    <!-- Preloader -->
    <div id="preloader">
      <div id="load"></div>
    </div>

    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="${createLink(view: 'index')}">
                    <h1>DERBY MATES</h1>
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
              <ul class="nav navbar-nav">

                <li class="active">
                    <a href="${createLink(view: 'index')}">Home</a>
                </li>
                <li>
                    <g:link controller="login" action="auth">login</g:link>
                </li>         
                <li>
                    <oauth:connect provider="google" id="google-connect-link">
                        <button class="btn bg-red btn-circle"><i class="fa fa-google-plus"></i></button>  
                    </oauth:connect>
                </li>
                <li>
                    <oauth:connect provider="facebook" id="facebook-connect-link">
                        <button class="btn bg-light-blue btn-circle"><i class="fa fa-facebook"></i></button>
                    </oauth:connect>
                </li>
              </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
    
    <!-- TOP LEFT RIBBON -->
    <div class="github-fork-ribbon-wrapper left">
        <div class="github-fork-ribbon">
            <a href="https://github.com/abertolo/derbymates">Fork me on GitHub</a>
        </div>
    </div>

    <g:layoutBody/>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <p><a href="" style="color: white">DerbyMates</a> powered by <a href="https://grails.org/" style="color: white">Grails</a></p>
                    <!-- App: <g:meta name="app.version"/> -->
                    <!-- Groovy ${GroovySystem.getVersion()} -->
                    <!-- JVM ${System.getProperty('java.version')} -->
                </div>
            </div>  
        </div>
    </footer>
    <asset:javascript src="main/js/custom.js"/>
</body>
</html>
