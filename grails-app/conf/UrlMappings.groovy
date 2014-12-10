class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(controller:"home", action: "index")

        "400"(view: '/error')
        "401"(controller: "login", action: "auth")
        "403"(view:'/error')
        "404"(view:'/notFound')
        "405"(view:'/error')
        "500"(view:'/error')
        "503"(view:'/error')        
	}
}
