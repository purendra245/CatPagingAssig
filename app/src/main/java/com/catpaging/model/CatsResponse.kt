package com.catpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class CatsResponse(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("url")
    @Expose
    val url: String
)