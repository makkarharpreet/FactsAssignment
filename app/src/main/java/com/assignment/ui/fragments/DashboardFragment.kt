package com.assignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.assignment.R
import com.assignment.data.api.ApiService
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import com.assignment.databinding.FragmentDashboardBinding
import com.assignment.model.FactsResponseModel
import com.assignment.viewmodels.FactsViewModel
import com.assignment.adapters.DashboardAdapter
/**
 * @author Harpreet
 */
class DashboardFragment : BaseFragment<FactsViewModel, FragmentDashboardBinding, FactsRepository>() {
    private var list = listOf<FactsResponseModel.Rows>()
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getFactsData()
    }

    override fun getViewModel() = FactsViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : ViewDataBinding {
        return  DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
    }

    override fun getFragmentRepository(): FactsRepository {
        return  FactsRepository(remoteDataSource.buildApi(ApiService::class.java))
    }

    private fun getFactsData() {
        viewModel.factsApi().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    setProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    setProgressVisibility(View.GONE)
                    getActionBar()?.title=it.value.title
                    list=it.value.rows
                    dashboardAdapter = DashboardAdapter(
                        requireContext(),  list)
                    binding.recyclerView.adapter=dashboardAdapter
                }
                is Resource.Failure -> {
                    setProgressVisibility(View.GONE)
                }
            }
        })
    }

    private fun setProgressVisibility(visibility: Int) {
        binding.progress.visibility=visibility
    }

}