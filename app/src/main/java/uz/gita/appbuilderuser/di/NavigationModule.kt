package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.testcleanafb6.presentation.navigator.AppNavigator
import uz.gita.testcleanafb6.presentation.navigator.NavigationDispatcher
import uz.gita.testcleanafb6.presentation.navigator.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule{

    @Binds
    fun bindNavigationHandler(imp: NavigationDispatcher): NavigationHandler

    @Binds
    fun bindAppNavigator(imp: NavigationDispatcher): AppNavigator
}