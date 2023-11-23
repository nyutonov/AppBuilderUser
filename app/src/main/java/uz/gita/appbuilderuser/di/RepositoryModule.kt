package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.data.repository.RepositoryImpl
import uz.gita.appbuilderuser.domain.repository.Repository

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindRepository(impl : RepositoryImpl) : Repository
}