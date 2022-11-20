package com.ordenaris.escuela

import java.util.UUID

class Empleados {

    static hasMany = [ areas: Area ]
    static belongsTo = Area

    String uuid = UUID.randomUUID().toString().replaceAll('\\-','')
    String noEmpleado
    String nombre
    String paterno
    String materno

    Boolean sexo //true masculino
    Date fechaNaciento

    String calle
    String exterior
    String interior
    String colonia
    String cp

    String telefono
    String correo

    int status =1 // 1 activo 2 inactivo 3 eliminado
    Date fechaRegistro = new Date()

    
    static constraints = {
        uuid unique: true
        noEmpleado unique: true, nullable: true, blank: true
        materno nullable:true, blank: true
        interior nullable: true, blank:true
        correo nullable: true
    }

    static mapping = {
        version false
    }

    def afterInsert(){
        Empleado.withNewSession {
            this.noEmpleado = generarNumeroEmpleado( this.fechaNaciento, this.sexo, this.nombre, this.paterno )
        }
    }

    def generarNumeroEmpleado( fechaNaciento, sexo, nombre, paterno ){
        def totalEmpleados = Empleado.count() + 1
        def letraSexo = ( sexo ) ? "M" : "F"
        def codigo = "${nombre[0].toUpperCase()}${paterno[0].toUpperCase}${fechaNacimiento.format("ddMMyyyy")}${letraSexo}"
        def serie = totalEmpleados.toString().padLeft(5, "0")
        return "${codigo}-${serie}"
    }

}
