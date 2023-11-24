package uz.gita.appbuilderuser.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.getValue
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData
import java.util.UUID

fun DataSnapshot.toUserData(): ComponentsModel = ComponentsModel(
    componentsName = child("componentsName").getValue(String::class.java) ?: "",

    componentId = child("componentId").getValue(Int::class.java) ?: 0,

    placeHolder = child("placeHolder").getValue(String::class.java) ?: "",
    input = child("input").getValue(String::class.java) ?: "",
    type = child("type").getValue(String::class.java) ?: "",

    text = child("text").getValue(String::class.java) ?: "",
    color = child("color").getValue(Int::class.java) ?: 0,

    selectorDataQuestion = child("selectorDataQuestion").getValue(String::class.java) ?: "",
    selectorDataAnswers = child("selectorDataAnswers").getValue(String::class.java)?.split(":")
        ?: listOf(),

    multiSelectDataQuestion = child("multiSelectDataQuestion").getValue(String::class.java) ?: "",
    multiSelectorDataAnswers = child("multiSelectorDataAnswers").getValue(String::class.java)
        ?.split(":") ?: listOf(),

    datePicker = child("datePicker").getValue(String::class.java) ?: "",
    visibility = child("visibility").getValue(Boolean::class.java) ?: false,
    idVisibility = child("idVisibility").getValue(String::class.java) ?: "",
    operator = child("operator").getValue(String::class.java) ?: "",
    value = child("value").getValue(String::class.java) ?: "",
    id = child("id").getValue(String::class.java) ?: ""
)

fun DataSnapshot.toDrawsData(): DrawsData = DrawsData(
    key = child("key").getValue(String::class.java) ?: "",
    components = child("components").children.map { it.toUserData() },
    state = child("state").getValue(Boolean::class.java) ?: false
)