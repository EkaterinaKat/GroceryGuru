package com.katysh.groceryguru.di

import android.app.Application
import com.katysh.groceryguru.db.AppDatabase
import com.katysh.groceryguru.db.EntryDao
import com.katysh.groceryguru.db.EntryRepoImpl
import com.katysh.groceryguru.db.ExpirationEntryDao
import com.katysh.groceryguru.db.ExpirationRepoImpl
import com.katysh.groceryguru.db.ProductDao
import com.katysh.groceryguru.db.ProductRepoImpl
import com.katysh.groceryguru.domain.BackupRepo
import com.katysh.groceryguru.domain.CalculationRepo
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.domain.ReportRepo
import com.katysh.groceryguru.logic.BackupRepoImpl
import com.katysh.groceryguru.logic.CalculationRepoImpl
import com.katysh.groceryguru.logic.ReportRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppModule {

    @Binds
    fun bindProductRepo(impl: ProductRepoImpl): ProductRepo

    @Binds
    fun bindExpirationRepo(impl: ExpirationRepoImpl): ExpirationRepo

    @Binds
    fun bindEntryRepo(impl: EntryRepoImpl): EntryRepo

    @Binds
    fun bindCalculationRepo(impl: CalculationRepoImpl): CalculationRepo

    @Binds
    fun bindBackupRepo(impl: BackupRepoImpl): BackupRepo

    @Binds
    fun bindReportRepo(impl: ReportRepoImpl): ReportRepo

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

        @ApplicationScope
        @Provides
        fun provideEntryDao(application: Application): EntryDao {
            return AppDatabase.getInstance(application).entryDao()
        }
    }
}