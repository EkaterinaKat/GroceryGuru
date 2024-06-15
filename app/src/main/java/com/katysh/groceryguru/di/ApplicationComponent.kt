package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.presentation.ProductEditActivity
import com.katysh.groceryguru.presentation.ProductsActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(activity: ProductEditActivity)

    fun inject(activity: ProductsActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}