package com.br.impacta.doemais.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "_name") val name: String?,
    @ColumnInfo(name = "_email") val email: String?
)