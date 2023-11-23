package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.repositoriya.AppRepositoriya
import uz.gita.appbuilderuser.repositoriya.impl.AppRepositoriyaImpl


@Module
@InstallIn(SingletonComponent::class)
interface AppRepositoryModule {

    @Binds
    fun provideAppRepositoriya(impl:AppRepositoriyaImpl):AppRepositoriya

}