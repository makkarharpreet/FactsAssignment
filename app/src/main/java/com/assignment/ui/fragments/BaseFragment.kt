package com.assignment.ui.fragments

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

/**
 * @author Harpreet Singh
 * this class will be extended by each fragment with their corresponding generic types of ViewModel,
 * repository and DataBinding
 */
abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding, R : BaseRepository> : Fragment(){

    lateinit var binding : B
    lateinit var viewModel: VM
    protected val remoteDataSource = RetrofitBuilder()
    private lateinit var  dbHelper : DatabaseHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // database connection
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

    /**
     * this method will initialise binding variable when overridden in the fragments
     */
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : ViewDataBinding

    abstract fun getFragmentRepository() : R

    /**
     * this method will initialise viewmodel variable type when overridden in the fragments
     */
    abstract fun getViewModel() : Class<VM>

    /**
     * this method will be used to set action bar title
     */
     fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as MainActivity?)?.supportActionBar
    }

}