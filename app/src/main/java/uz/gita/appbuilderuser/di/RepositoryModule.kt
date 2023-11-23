package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.domain.repository.impl.AppRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAppRepository(impl: AppRepositoryImpl): AppRepository

}