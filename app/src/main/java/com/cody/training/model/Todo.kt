package com.cody.training.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.util.*

open class Todo(
    @SerializedName("id")
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("description")
    var description: String = "",

    @SerializedName("status")
    var status: String = "pending",

    @SerializedName("preview")
    var preview: String? = null,

    @SerializedName("notify")
    var notify: Int = 0
) : RealmObject()