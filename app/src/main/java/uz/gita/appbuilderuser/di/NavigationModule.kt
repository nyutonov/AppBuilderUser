package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.utils.navigator.AppNavigator
import uz.gita.appbuilderuser.utils.navigator.NavigationDispatcher
import uz.gita.appbuilderuser.utils.navigator.NavigationHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindAppNavigator(impl : NavigationDispatcher) : AppNavigator

    @[Binds Singleton]
    fun bindNavigationHandler(impl : NavigationDispatcher) : NavigationHandler

}