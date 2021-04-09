package com.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.data.repository.BaseRepository
import com.assignment.data.repository.FactsRepository
import com.assignment.room.DatabaseHelper

class ViewModelFactory(private val repository: BaseRepository,private val dbHelper : DatabaseHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FactsViewModel::class.java) -> FactsViewModel(
                    repository as FactsRepository,
            dbHelper) as T

            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}
