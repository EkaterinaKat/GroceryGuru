package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.presentation.EntryEditActivity
import com.katysh.groceryguru.presentation.MainActivity
import com.katysh.groceryguru.presentation.ProductEditActivity
import com.katysh.groceryguru.presentation.ProductListFragment
import com.katysh.groceryguru.presentation.ProductsActivity
import com.katysh.groceryguru.presentation.SelectProductActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(activity: ProductEditActivity)

    fun inject(activity: ProductsActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: EntryEditActivity)

    fun inject(activity: SelectProductActivity)

    fun inject(fragment: ProductListFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}