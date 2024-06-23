package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.presentation.ExpirationEditActivity
import com.katysh.groceryguru.presentation.MainActivity
import com.katysh.groceryguru.presentation.ProductEditActivity
import com.katysh.groceryguru.presentation.ProductsActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(activity: ProductEditActivity)

    fun inject(activity: ProductsActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: ExpirationEditActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}