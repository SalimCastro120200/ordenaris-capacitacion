package com.ordenaris.escuela


import grails.rest.*
import grails.converters.*

class AreaController {

    def AreaService
	static responseFormats = ['json']
	
    def gestionar() {
        def data = request.JSON
        if( !data.nombre ){
            render([ success: false, mensaje: "El nombre esta vacio" ] as JSON )
        }
        if( data.nombre.size() < 2 ){
            render([ success: false, mensaje: "El nombre debe tener minimo 2 letras" ] as JSON )
        }

        render( AreaService.gestionar( data, params.uuid ) as JSON)
    }
    def informacion() {
        render ( AreaService.informacion( params.uuid ) as JSON )
    }

    def lista() {
        render ( AreaService.lista() as JSON )
    }

    def paginar() {
        if( !params.sort ){
            render( [ success: false, mensaje: "SORT Vacio" ] as JSON )
            return
        }
        if( params.sort != "nombre" && params.sort != "estatus" ){
            render( [ success: false, mensaje: "No se puede  ordenar por esa propiedad" ] as JSON )
            return
        }
        if( !params.order ){
            render( [ success: false, mensaje: "Order Vacio" ] as JSON )
            return
        }
        if( params.order != "asc" && params.order != "desc" ){
            render( [ success: false, mensaje: "No se puede  ordenar" ] as JSON )
            return
        }
        if( !params.page ){
            render( [ success: false, mensaje: "Page Vacio" ] as JSON )
            return
        }

        try {
            if( params.page.toInteger() < 1 ) {
                render( [ success: false, mensaje: "Page debe de ser un numero positivo" ] as JSON )
            } 
        } catch ( e ) {
                render( [ success: false, mensaje: "Page debe de ser un numero" ] as JSON )
            }

        try {
            if( params.max.toInteger() < 1 ) {
                render( [ success: false, mensaje: "Max debe de ser un numero positivo" ] )
            } 
        } catch ( e ) {
                render( [ success: false, mensaje: "Max debe de ser un numero" ] )
        }

        render  ( AreaService.paginar( params ) as JSON )
    }

    def actualizarEstatus() {
        if( !params.status ) {
            render ( [ success: false, mensaje: "No viene el parametro Status" ] as JSON )
            return
        }
        
        try{
            if( params.status.toInteger() < 1 && params.status.toInteger() > 3 ) {
                render ( [ success: false, mensaje: "El Status no es valido" ] as JSON )
                return
            }
        } catch ( e ) {
            render ( [ success: false, mensaje: "El Status debe ser un número" ] as JSON )
            return
        }

        render ( AreaService.actualizarEstatus( params.uuid, params.status.toInteger() ) as JSON )

    }
}
