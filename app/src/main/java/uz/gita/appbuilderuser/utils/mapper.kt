package uz.gita.appbuilderuser.utils

import com.google.firebase.database.DataSnapshot
import uz.gita.appbuilderuser.data.model.ComponentsModel

fun DataSnapshot.toUserData(): ComponentsModel = ComponentsModel(
    componentsName = child("componentsName").getValue(String::class.java) ?: "",
    input = child("input").getValue(String::class.java) ?: "",
    type = child("type").getValue(String::class.java) ?: "",
    color = child("color").getValue(Int::class.java) ?: 0,
    selectorDataQuestion = child("selectorDataQuestion").getValue(String::class.java) ?: "",
    selectorDataAnswers = child("selectorDataAnswers")?.children?.map {
        val key = it.key as String
        val value = it.getValue(String::class.java) as String
        Pair(key, value)
    } ?: emptyList(),
    preselected = child("preselected")?.let {
        val key = it.child("first").getValue(String::class.java) ?: ""
        val value = it.child("second").getValue(String::class.java) ?: ""
        Pair(key, value)
    } ?: Pair("", ""),
    multiSelectDataQuestion = child("multiSelectDataQuestion").getValue(String::class.java) ?: "",
    datePicker = child("datePicker").getValue(String::class.java) ?: ""
)