package br.com.digitalhouse.marmeladamovie.presenter.model

class User() {

    var name: String = ""
    var email: String = ""
    var photo: String? = null

    constructor(pName: String, pEmail: String, pPhoto: String? = null): this(){
        name = pName
        email = pEmail
        photo = pPhoto
    }

}