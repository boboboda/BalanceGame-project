package com.bobodroid.balancegame.viewmodels.dataViewModels

import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.HashMap

data class Compatibility(
    var id: String = UUID.randomUUID().toString(),
    var compatibilitySaveName: String = "",
    var zero: String = "",
    var one: String = "",
    var two: String = "",
    var three: String = "",
    var four: String = "",
    var five: String = "",
    var six: String = "",
    var seven: String = "",
    var eight: String = "",
    var nine: String = "",
    var ten: String = "", )

{
    constructor(data: QueryDocumentSnapshot) : this() {
        this.id = data.id
        this.compatibilitySaveName = data["compatibilitySaveName"] as String? ?: ""
        this.zero = data["zero"] as String? ?: ""
        this.one = data["one"] as String? ?: ""
        this.two = data["two"] as String? ?: ""
        this.three = data["three"] as String? ?: ""
        this.four = data["four"] as String? ?: ""
        this.five = data["five"] as String? ?: ""
        this.six = data["six"] as String? ?: ""
        this.seven = data["seven"] as String? ?: ""
        this.eight = data["eight"] as String? ?: ""
        this.nine = data["nine"] as String? ?: ""
        this.ten = data["ten"] as String? ?: ""
    }

    fun asHasMap() : HashMap<String, Any>{
        return hashMapOf(
            "id" to this.id,
            "compatibilitySaveName" to this.compatibilitySaveName,
            "zero" to this.zero,
            "one" to this.one,
            "two" to this.two,
            "three" to this.three,
            "four" to this.four,
            "five" to this.five,
            "six" to this.six,
            "seven" to this.seven,
            "eight" to this.eight,
            "nine" to this.nine,
            "ten" to this.ten)
    }
}