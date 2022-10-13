package com.ort.servitodo.entities

class Cliente(id : Int, username : String, name : String, lastname : String, birth: String, img : String, address : String) {

    var id : Int
    var username : String
    var name : String
    var lastname : String
    var birth : String
    var img : String
    var address : String

    init {
        this.id = id
        this.username = username
        this.name = name
        this.lastname = lastname
        this.birth = birth
        this.img = img
        this.address = address
    }
}