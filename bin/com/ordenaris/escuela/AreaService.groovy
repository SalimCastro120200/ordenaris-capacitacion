package com.ordenaris.escuela

import grails.gorm.transactions.Transactional

@Transactional
class AreaService {

    def gestionar( data, uuid = null ) {
        Area.withTransaction { tStatus ->
            def nArea
            try {
                nArea = Area.findByUuid( uuid )
                if( !nArea ) {
                    nArea = new Area()
                }
                // def nArea = new Area([ nombre: data.nombre ])
                nArea.nombre = data.nombre
                nArea.save( flush:true, failOnError: true )
                return [ success: true ]
            } catch( e ) {
                println "AreaService - gestionar - error - ${e.getMessage()}"
                tStatus.setRollbackOnly()
                return [ success: false, mensaje: e.getMessage() ]
            }
        }
    }

    def informacion( uuid ) {
        try {
            def area = Area.findByUuid( uuid ) //select from all
            if( !area ) { 
                return [ success: false, mensaje: "Área no encontrada" ]
            }

            def informacion = [
                uuid: area.uuid,
                nombre: area.nombre,
                estatus: area.status,
                empleados: 0
            ]

            return [ success: true, informacion: informacion ]

        } catch ( e ) {
            println "${new Date()} AreasService - informacion - error - ${e.getMessage()}"
            return [ success: false, mensaje: e.getMessage()]
        }
    }

    def lista() {
        try {
            def lista = []
            Area.findAllByStatus( 1 ).each{ a ->
                lista.add([ uuid: a.uuid, nombre: a.nombre ])
            }
            return [ success: true, lista: lista ]
        } catch ( e ) {
            println "${new Date()} AreasService - informacion - error - ${e.getMessage()}"
            return [ success: false, mensaje: e.getMessage()]
        }
    }

    def paginar( data ){
        try {
            def _max = data.max ? data.max.toInteger() : 5
            def _offset = ( --data.page.toInteger() * _max.toInteger() )
            def lista = []
            def total = Area.countByStatusNotEqual( 3 )
            if( data.search ) {
                total = Area.withCriteria {
                    ne( "status", 3 )
                    or{
                        ilike( "nombre", "%${data.search}%" )
                    }
                    projections{
                        rowCount()
                    }
                }[0]
            }

            Area.withCriteria {
                ne( "status", 3 )
                if( data.search ){
                    or{
                        ilike( "nombre", "%${data.search}%" )
                    }
                }
                firstResult( _offset )
                maxResults _max
                order( data.sort, data.order )
            }.each{ a ->
                lista.add([
                    uuid: a.uuid,
                    nombre: a.nombre,
                    estatus: a.status,
                    empleados: 0
                ])
            }

            return [ success: true, lista: lista, total: total ]

        } catch ( e ) {
            println "${new Date()} AreasService - informacion - error - ${e.getMessage()}"
            return [ success: false, mensaje: e.getMessage()]
        }
    }

    def actualizarEstatus( uuid, status ) {
        Area.withTransaction{ tStatus ->   
            try {
                def area = Area.findByUuid( uuid )
                if( !area ) {
                    return [ success: false, mensaje: "No se encontro el Área" ]
                }

                area.status = status
                area.save( flush: true, failOnError: true )
                return [ success: true ]
            } catch ( e ) {
                println "${new Date()} - AreaService - actualizarEstatus - error - ${e.getMessage()}"
                tStatus.setRollbackOnly()
                return [ success: false, mensaje: e.getMessage() ]
            }
        }

    }
}
