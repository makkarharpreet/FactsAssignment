package com.assignment.ui.fragments

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.data.api.RetrofitBuilder
import com.assignment.data.repository.BaseRepository
import com.assignment.room.DatabaseBuilder
import com.assignment.room.DatabaseHelper
import com.assignment.room.DatabaseHelperImpl
import com.assignment.ui.activities.MainActivity
import com.assignment.viewmodels.ViewModelFactory

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding, R : BaseRepository> : Fragment(){

    protected lateinit var binding : B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RetrofitBuilder()
    protected lateinit var  dbHelper : DatabaseHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View? {

        binding = getFragmentBinding(inflater, container) as B

        val factory = ViewModelFactory(getFragmentRepository(),dbHelper)
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : ViewDataBinding

    abstract fun getFragmentRepository() : R

    abstract fun getViewModel() : Class<VM>

     fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)?.supportActionBar
    }

}