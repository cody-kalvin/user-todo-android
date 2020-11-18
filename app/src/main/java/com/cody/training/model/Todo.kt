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
) : RealmObject() {
    override fun equals(other: Any?): Boolean {
        return if (this !== other) {
            if (other is Todo) {
                val copy: Todo = other
                return this.id == copy.id
                        && this.description == copy.description
                        && this.status == copy.status
                        && this.preview == copy.preview
                        && this.notify == copy.notify
            }
            false
        } else {
            true
        }
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + preview.hashCode()
        result = 31 * result + notify
        return result
    }
}