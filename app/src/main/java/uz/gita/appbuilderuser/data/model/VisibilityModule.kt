package uz.gita.appbuilderuser.data.model

data class VisibilityModule (
    val componentId : String ,
    val componentName : String ,
    val operator : String ,
    val value : String ,
    val inMultiSelectorId : String ,
    val inMultiSelectorValue : String ,
    val list : List<String> = listOf()
)