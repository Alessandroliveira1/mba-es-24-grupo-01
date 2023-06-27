package com.br.impacta.doemais.di

import android.nfc.tech.MifareUltralight
import androidx.paging.PagingConfig
import com.br.impacta.doemais.data.andamento.AndamentoPagingSource
import com.br.impacta.doemais.data.andamento.AndamentoRepository
import com.br.impacta.doemais.data.andamento.AndamentoRepositoryImpl
import com.br.impacta.doemais.data.doacao.*
import com.br.impacta.doemais.data.finalizado.FinalizadoPagingSource
import com.br.impacta.doemais.data.finalizado.FinalizadoRepository
import com.br.impacta.doemais.data.finalizado.FinalizadoRepositoryImpl
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideRootRef() = Firebase.database.reference

    @Provides
    fun provideProductsPagingSource(
        db: DatabaseReference
    ) = OrderPagingSource(
        db = db
    )

    @Provides
    fun provideAndamentoPagingSource(
        db: DatabaseReference
    ) = AndamentoPagingSource(
        db = db
    )

    @Provides
    fun provideFinalizadoPagingSource(
        db: DatabaseReference
    ) = FinalizadoPagingSource(
        db = db
    )


    @Provides
    fun provideFragmentTal(
        db: DatabaseReference
    ): DatabaseRepository = DatabaseRepositoryImpl(
        db = db
    )

    @Provides
    fun providePagingConfig() = PagingConfig(
        pageSize = MifareUltralight.PAGE_SIZE
    )

    @Provides
    fun provideProductsRepository(
        source: OrderPagingSource,
        config: PagingConfig
    ): OrderRepository = OrderRepositoryImpl(
        source = source,
        config = config
    )

    @Provides
    fun provideAndamentoRepository(
        source: AndamentoPagingSource,
        config: PagingConfig
    ): AndamentoRepository = AndamentoRepositoryImpl(
        source = source,
        config = config
    )

    @Provides
    fun provideFinalizadoRepository(
        source: FinalizadoPagingSource,
        config: PagingConfig
    ): FinalizadoRepository = FinalizadoRepositoryImpl(
        source = source,
        config = config
    )

}