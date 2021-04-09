package com.assignment.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.assignment.R
import com.assignment.data.api.ApiService
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import com.assignment.databinding.FragmentDashboardBinding
import com.assignment.model.FactsResponseModel
import com.assignment.viewmodels.FactsViewModel
import com.assignment.adapters.DashboardAdapter
import com.assignment.room.DatabaseBuilder
import com.assignment.room.DatabaseHelperImpl
import com.assignment.room.FactsModel

/**
 * @author Harpreet
 */
class DashboardFragment :
    BaseFragment<FactsViewModel, FragmentDashboardBinding, FactsRepository>() {
    private var list = mutableListOf<FactsResponseModel.Rows>()
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getFactsData()
    }

    override fun getViewModel() = FactsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
    }

    override fun getFragmentRepository(): FactsRepository {
        return FactsRepository(remoteDataSource.buildApi(ApiService::class.java))
    }

    private fun getFactsData() {
        viewModel.fetchFacts()
        viewModel.factsList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                list.clear()
                for (item in it) {
                    val model =
                        FactsResponseModel.Rows(item.title, item.description, item.imageHref)
                    list.add(model)
                }
                callAdapter()
            } else {
                fetchDataFromApi()
            }
        })
    }

    private fun fetchDataFromApi() {
        viewModel.factsApi().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    setProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    setProgressVisibility(View.GONE)
                    getActionBar()?.title = it.value.title
                    list = it.value.rows.toMutableList()
                    callAdapter()
                    saveDataToDataBase()
                }
                else -> setProgressVisibility(View.GONE)
            }
        })
    }

    private fun saveDataToDataBase() {
        var myList: MutableList<FactsModel> = mutableListOf<FactsModel>()
        viewModel.clearAll()
        for (item in list) {
            val model = FactsModel(0, item.title, item.description, item.imageHref)
            myList.add(model)
        }
        viewModel.insert(myList)
    }

    private fun callAdapter() {
        dashboardAdapter = DashboardAdapter(
            requireContext(), list
        )
        binding.recyclerView.adapter = dashboardAdapter
    }

    private fun setProgressVisibility(visibility: Int) {
        binding.progress.visibility = visibility
    }

}