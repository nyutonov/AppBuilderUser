package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.presenter.edit_draft.EditContract
import uz.gita.appbuilderuser.presenter.login.LoginDirection
import uz.gita.appbuilderuser.presenter.login.LoginDirectionImp
import uz.gita.appbuilderuser.presenter.add.AddContract
import uz.gita.appbuilderuser.presenter.add.AddDirection
import uz.gita.appbuilderuser.presenter.edit_draft.EditDirection
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataContract
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataDirection

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {
    @Binds
    fun provideDirection(impl: LoginDirectionImp): LoginDirection

    @Binds
    fun bindUserDataDirection(impl: UserDataDirection): UserDataContract.Direction

    @Binds
    fun bindAddDrawDirection(impl: AddDirection): AddContract.Direction

    @Binds
    fun bindEditDraftDirection(impl: EditDirection): EditContract.Direction
}