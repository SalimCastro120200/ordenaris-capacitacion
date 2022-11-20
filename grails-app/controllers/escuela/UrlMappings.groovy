package escuela

class UrlMappings {

    static mappings = {
        group "/ordenaris/api", {
            group "/area", {
                "/registrar"(controller:"area", action: "gestionar", method: "POST")
                "/$uuid/modificar"(controller:"area", action: "gestionar", method: "PUT")
                "/$uuid/actualizar-estatus"(controller:"area", action: "gestionar", method: "PATCH")
                "/$uuid/informacion"(controller:"area", action: "informacion", method: "GET")
                "/lista"(controller:"area", action: "lista", method: "GET")
                "/paginar"(controller:"area", action: "paginar", method: "GET")
            }
            group "/empleados", {
                "/registrar"(controller:"empleados", action: "gestionar", method: "POST")
                "/$uuid/modificar"(controller:"empleados", action: "gestionar", method: "PUT")
                "/$uuid/actualizar-estatus"(controller:"empleados", action: "gestionar", method: "PATCH")
                "/$uuid/informacion"(controller:"empleados", action: "gestionar", method: "GET")
                "/lista"(controller:"empleados", action: "lista", method: "GET")
                "/paginar"(controller:"empleados", action: "paginar", method: "GET")
            }
         
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
