package uz.gita.appbuilderuser.utils

import com.google.firebase.database.DataSnapshot
import uz.gita.appbuilderuser.data.model.ComponentsModel

fun DataSnapshot.toUserData(): ComponentsModel = ComponentsModel(
    componentsName = child("componentsName").getValue(String::class.java) ?: "",

    input = child("input").getValue(String::class.java) ?: "",
    type = child("type").getValue(String::class.java) ?: "",

    text = child("text").getValue(String::class.java) ?: "",
    color = child("color").getValue(Int::class.java) ?: 0,

    selectorDataQuestion = child("selectorDataQuestion").getValue(String::class.java) ?: "",
    selectorDataAnswers = child("selectorDataAnswers").getValue(String::class.java)?.split(":") ?: listOf(),

    multiSelectDataQuestion = child("multiSelectDataQuestion").getValue(String::class.java) ?: "",
    multiSelectorDataAnswers = child("multiSelectorDataAnswers").getValue(String::class.java)?.split(":") ?: listOf(),

    datePicker = child("datePicker").getValue(String::class.java) ?: "" ,
    visibility = child("visibility").getValue(Boolean::class.java) ?: false,
    idVisibility = child("idVisibility").getValue(String::class.java) ?: "" ,
    operator = child("operator").getValue(String::class.java) ?: "" ,
    value = child("value").getValue(String::class.java) ?: "" ,
    id = child("id").getValue(String::class.java) ?: UUID.randomUUID().toString()
)

fun DataSnapshot.toDrawsData(): DrawsData = DrawsData(
    key = child("key").getValue(String::class.java)?:"",
    value=child("value").getValue(String::class.java)?:"",
    state = child("state").getValue(Boolean::class.java)?:false
)