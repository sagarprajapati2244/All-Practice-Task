package com.example.firebasenotification.objectDatabase

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class DataModel(
    @Id var id: Long = 0,
    var text: String? = null,
    var comment: String? = null,
    var date: Date? = null
)