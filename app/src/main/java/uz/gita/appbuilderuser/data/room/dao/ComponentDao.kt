package uz.gita.appbuilderuser.data.room.daoimport androidx.room.Daoimport androidx.room.Queryimport androidx.room.Updateimport androidx.room.Upsertimport uz.gita.appbuilderuser.data.room.entity.ComponentEntity@Daointerface ComponentDao {    @Query("SELECT * FROM ComponentEntity")    suspend fun getAllComponents(): List<ComponentEntity>    @Upsert    suspend fun addComponent(componentEntity: ComponentEntity)    @Update    suspend fun deleteComponent(componentEntity: ComponentEntity)}