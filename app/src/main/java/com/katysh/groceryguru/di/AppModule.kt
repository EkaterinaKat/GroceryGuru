package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.db.AppDatabase
import com.katysh.groceryguru.db.ProductDao
import com.katysh.groceryguru.db.ProductRepoImpl
import com.katysh.groceryguru.domain.ProductRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppModule {

    @Binds
    fun bindProductRepo(impl: ProductRepoImpl): ProductRepo

    companion object {

        @ApplicationScope
        @Provides
        fun provideProductDao(application: Application): ProductDao {
            return AppDatabase.getInstance(application).productDao()
        }
    }
}