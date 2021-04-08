package com.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.data.repository.BaseRepository
import com.assignment.data.repository.FactsRepository

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FactsViewModel::class.java) -> FactsViewModel(
                    repository as FactsRepository
                    ) as T

            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}
