package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.presentation.login.LoginDirection
import uz.gita.appbuilderuser.presentation.login.LoginDirectionImp

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {
    @Binds
    fun provideDirection(impl: LoginDirectionImp): LoginDirection

}