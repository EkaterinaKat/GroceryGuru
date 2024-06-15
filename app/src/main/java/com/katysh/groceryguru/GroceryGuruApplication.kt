package com.katysh.groceryguru

import android.app.Application
import com.katysh.groceryguru.di.DaggerApplicationComponent

class GroceryGuruApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}