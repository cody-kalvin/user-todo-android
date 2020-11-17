package com.cody.training.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import java.util.*

open class Todo(
    var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var status: String = "pending",
    var preview: String? = null,
    var notify: Int = 0
) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt()
    )

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(description)
        parcel.writeString(status)
        parcel.writeString(preview)
        parcel.writeInt(notify)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Todo> {
        override fun createFromParcel(parcel: Parcel): Todo {
            return Todo(parcel)
        }

        override fun newArray(size: Int): Array<Todo?> {
            return arrayOfNulls(size)
        }
    }
}