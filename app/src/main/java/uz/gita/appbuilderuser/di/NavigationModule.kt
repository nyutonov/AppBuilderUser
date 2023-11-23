package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.navigator.NavigationDispatcher
import uz.gita.appbuilderuser.navigator.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule{

    @Binds
    fun bindNavigationHandler(imp: NavigationDispatcher): NavigationHandler

    @Binds
    fun bindAppNavigator(imp: NavigationDispatcher): AppNavigator
}