package uz.gita.appbuilderuser.utils

import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.firebase.database.DataSnapshot
import com.google.gson.Gson
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.model.VisibilityModule

fun DataSnapshot.toComponentData(): ComponentsModel = ComponentsModel(
    key = key ?: "",
    componentsName = child("componentsName").getValue(String::class.java) ?: "",

    componentId = child("componentId").getValue(Int::class.java) ?: 0,

    placeHolder = child("placeHolder").getValue(String::class.java) ?: "",
    input = child("input").getValue(String::class.java) ?: "",
    type = child("type").getValue(String::class.java) ?: "",

    isMaxLengthForTextEnabled = child("isMaxLengthForTextEnabled").getValue(Boolean::class.java) ?: false,
    maxLengthForText = child("maxLengthForText").getValue(Int::class.java) ?: 0,
    isMinLengthForTextEnabled = child("isMinLengthForTextEnabled").getValue(Boolean::class.java) ?: false,
    minLengthForText = child("minLengthForText").getValue(Int::class.java) ?: 0,
    isMaxValueForNumberEnabled = child("isMaxValueForNumberEnabled").getValue(Boolean::class.java) ?: false,
    maxValueForNumber = child("maxValueForNumber").getValue(Int::class.java) ?: 0,
    isMinValueForNumberEnabled = child("isMinValueForNumberEnabled").getValue(Boolean::class.java) ?: false,
    minValueForNumber = child("minValueForNumber").getValue(Int::class.java) ?: 0,
    isRequired = child("isRequired").getValue(Boolean::class.java) ?: false,

    text = child("text").getValue(String::class.java) ?: "",

    imageUri = child("imageUri").getValue(String::class.java) ?: "",
    color = (child("color").getValue(Long::class.java) ?: 0).toULong(),
    heightImage = child("heightImage").getValue(Float::class.java) ?: 0f,
    aspectRatio = child("aspectRatio").getValue(Float::class.java) ?: 0f,
    selectedImageSize = child("selectedImageSize").getValue(String::class.java) ?: "",
    selectedIdForImage = child("selectedIdForImage").getValue(String::class.java) ?: "",
    isIdInputted = child("isIdInputted").getValue(Boolean::class.java) ?: false,

    selectorDataQuestion = child("selectorDataQuestion").getValue(String::class.java) ?: "",
    selectorDataAnswers = child("selectorDataAnswers").getValue(String::class.java)?.split(":")
        ?: listOf(),
    preselected = child("preselected").getValue(String::class.java) ?: "",

    multiSelectDataQuestion = child("multiSelectDataQuestion").getValue(String::class.java) ?: "",
    multiSelectorDataAnswers = child("multiSelectorDataAnswers").getValue(String::class.java)
        ?.split(":") ?: listOf(),

    datePicker = child("datePicker").getValue(String::class.java) ?: "",
    visibility = child("visibility").getValue(Boolean::class.java) ?: false,
    idVisibility = child("idVisibility").getValue(String::class.java) ?: "",
    operator = child("operator").getValue(String::class.java) ?: "",
    value = child("value").getValue(String::class.java) ?: "",
    id = child("id").getValue(String::class.java) ?: "" ,
    list = if(child("list").getValue(String::class.java).isNullOrEmpty()) {
        listOf()
    }else {
        Gson().fromJson(child("list").getValue(String::class.java) ,
            object : TypeToken<List<VisibilityModule>>(){}.type
        )
    },
//rowType = child("rowType").getValue(String::class.java)?:""


    lsRow =if(child("rowType").getValue(String::class.java).isNullOrEmpty()){
        listOf()
    }else{
        Log.d("TTT", "toComponentData: ${child("rowType").getValue(String::class.java)}")
        Gson().fromJson(child("rowType").getValue(String::class.java) ,
            object : TypeToken<List<ComponentsModel>>(){}.type
        )
    }
)

fun DataSnapshot.toDrawsData(): DrawsData = DrawsData(
    key = this.key ?: "",
    components = child("components").children.map { it.toComponentData() },
    state = child("state").getValue(Boolean::class.java) ?: false,
    id = child("draftId").getValue(Int::class.java) ?: 0
)