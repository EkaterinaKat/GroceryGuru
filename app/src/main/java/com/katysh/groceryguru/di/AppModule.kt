package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.db.AppDatabase
import com.katysh.groceryguru.db.EntryDao
import com.katysh.groceryguru.db.IngredientDao
import com.katysh.groceryguru.db.ProductDao
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @ApplicationScope
    @Provides
    fun provideProductDao(application: Application): ProductDao {
        return AppDatabase.getInstance(application).productDao()
    }

    @ApplicationScope
    @Provides
    fun provideEntryDao(application: Application): EntryDao {
        return AppDatabase.getInstance(application).entryDao()
    }

    @ApplicationScope
    @Provides
    fun provideIngredientDao(application: Application): IngredientDao {
        return AppDatabase.getInstance(application).ingredientDao()
    }
}