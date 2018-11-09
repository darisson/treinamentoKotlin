package com.example.darisson.treinamentokotlin.modules.application

import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm

open class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}