package com.sawrose.daggerdemo.model

/**
 * Created by sawrose on 1/13/18.
 */
data class User(
        var uid:String,
        var name:String,
        val email:String,
        val photoUrl:String,
        var token:String
)