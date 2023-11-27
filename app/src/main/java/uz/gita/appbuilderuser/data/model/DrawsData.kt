package uz.gita.appbuilderuser.data.model

data class DrawsData(
    val id: Int,
    val key: String,
    var state: Boolean,
    val components: List<ComponentsModel>
)