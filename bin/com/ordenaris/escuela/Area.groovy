package com.ordenaris.escuela
import java.util.UUID

class Area {

    static hasMany = [ empleados: Empleados ]

    String uuid = UUID.randomUUID().toString().replaceAll('\\-','')
    String nombre
    int status = 1

    static constraints = {
        uuid unique:true
        empleados nullable: true, blank: true
    }

    static mapping = {
        version false
    }
}
