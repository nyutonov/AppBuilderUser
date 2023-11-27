package uz.gita.appbuilderuser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.appbuilderuser.presenter.add_draft.AddDraftContract
import uz.gita.appbuilderuser.presenter.add_draft.AddDraftDirection
import uz.gita.appbuilderuser.presenter.edit_draft.EditDraftContract
import uz.gita.appbuilderuser.presenter.edit_draft.EditDraftDirection
import uz.gita.appbuilderuser.presenter.login.LoginDirection
import uz.gita.appbuilderuser.presenter.login.LoginDirectionImp
import uz.gita.appbuilderuser.presenter.main.MainContract
import uz.gita.appbuilderuser.presenter.main.MainDirection
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataContract
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataDirection

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {
    @Binds
    fun provideDirection(impl: LoginDirectionImp): LoginDirection

    @Binds
    fun bindMainDirection(impl: MainDirection): MainContract.Direction

    @Binds
    fun bindUserDataDirection(impl: UserDataDirection): UserDataContract.Direction

    @Binds
    fun bindAddDrawDirection(impl: AddDraftDirection): AddDraftContract.Direction

    @Binds
    fun bindEditDraftDirection(impl: EditDraftDirection): EditDraftContract.Direction
}