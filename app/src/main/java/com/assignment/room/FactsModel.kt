package com.assignment.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Harpreet Singh
 */
@Entity(tableName = "facts_table")
data class FactsModel (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "imageHref") val imageHref: String?
        )
