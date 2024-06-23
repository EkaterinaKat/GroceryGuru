package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.db.AppDatabase
import com.katysh.groceryguru.db.ExpirationEntryDao
import com.katysh.groceryguru.db.ExpirationRepoImpl
import com.katysh.groceryguru.db.ProductDao
import com.katysh.groceryguru.db.ProductRepoImpl
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppModule {

    @Binds
    fun bindProductRepo(impl: ProductRepoImpl): ProductRepo

    @Binds
    fun bindExpirationRepo(impl: ExpirationRepoImpl): ExpirationRepo

    companion object {

        @ApplicationScope
        @Provides
        fun provideProductDao(application: Application): ProductDao {
            return AppDatabase.getInstance(application).productDao()
        }

        @ApplicationScope
        @Provides
        fun provideExpirationEntryDao(application: Application): ExpirationEntryDao {
            return AppDatabase.getInstance(application).expirationDao()
        }
    }
}