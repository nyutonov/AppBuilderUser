package uz.gita.appbuilderuser.diimport android.content.Contextimport androidx.room.Roomimport dagger.Moduleimport dagger.Providesimport dagger.hilt.InstallInimport dagger.hilt.android.qualifiers.ApplicationContextimport dagger.hilt.components.SingletonComponentimport uz.gita.appbuilderuser.data.room.dao.ComponentDaoimport uz.gita.appbuilderuser.data.room.database.ComponentDatabaseimport javax.inject.Singleton@Module@InstallIn(SingletonComponent::class)class DatabaseModule {    @[Provides Singleton]    fun providesDatabase(@ApplicationContext context: Context): ComponentDatabase =        Room.databaseBuilder(context, ComponentDatabase::class.java, "components.db").build()    @[Provides Singleton]    fun providesComponentDao(database: ComponentDatabase): ComponentDao = database.componentDao()}