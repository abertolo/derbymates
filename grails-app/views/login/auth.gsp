<html>
<head>
	<meta name='layout' content='main'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>


    <div class="form-box" id="login-box">
        <div class="header">DERBY MATES</div>
        <form action='${postUrl}' method="post">
            <div class="body bg-gray">
                <div class="form-group">
                    <input type="text" name="j_username" class="form-control" placeholder="<g:message code="springSecurity.login.username.label"/>"/>
                </div>
                <div class="form-group">
                    <input type="password" name="j_password" class="form-control" placeholder="<g:message code="springSecurity.login.password.label"/>"/>
                </div>          
                <div class="form-group">
                    <input type="checkbox" name="${rememberMeParameter}" <g:if test='${hasCookie}'>checked='checked'</g:if>/><g:message code="springSecurity.login.remember.me.label"/>
                </div>
            </div>
            <div class="footer">            
                <button type="submit" class="btn bg-olive btn-block">
                	${message(code: "springSecurity.login.button")}
                </button>  

            </div>
        </form>

        <div class="margin text-center">
            <span>Sign in using social networks</span>
            <br/>
            <oauth:connect provider="google" id="google-connect-link">
				<button class="btn bg-red btn-circle"><i class="fa fa-google-plus"></i></button>
            </oauth:connect>            
			<oauth:connect provider="facebook" id="facebook-connect-link">
                    <button class="btn bg-light-blue btn-circle"><i class="fa fa-facebook"></i></button>
            </oauth:connect>
        </div>
    </div>
</body>
</html>
