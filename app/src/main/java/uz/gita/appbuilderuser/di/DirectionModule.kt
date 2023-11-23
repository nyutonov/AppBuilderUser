package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.presenter.login.LoginDirection
import uz.gita.appbuilderuser.presenter.login.LoginDirectionImp
import uz.gita.appbuilderuser.presenter.main.MainContract
import uz.gita.appbuilderuser.presenter.main.MainDirection

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {
    @Binds
    fun provideDirection(impl: LoginDirectionImp): LoginDirection

    @Binds
    fun bindMainDirection(impl: MainDirection): MainContract.Direction
}