package com.assignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.assignment.R
import com.assignment.data.api.ApiService
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import com.assignment.databinding.FragmentDashboardBinding
import com.assignment.model.FactsResponseModel
import com.assignment.viewmodels.FactsViewModel
import com.assignment.adapters.DashboardAdapter
import com.assignment.room.FactsModel
import com.assignment.utility.Utility.isNetworkAvailable
import com.assignment.utility.Utility.showErrorMsg

/**
 * @author Harpreet Singh
 */
class DashboardFragment :
    BaseFragment<FactsViewModel, FragmentDashboardBinding, FactsRepository>(),
    SwipeRefreshLayout.OnRefreshListener {
    private var factsList = mutableListOf<FactsResponseModel.Rows>()
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
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
        // fetching data from database, if not available then fetch from api
        viewModel.fetchFacts()
        viewModel.factsList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                factsList.clear()
                for (item in it) {
                    //adding item in facts list and call adapter
                    val model =
                        FactsResponseModel.Rows(item.title, item.description, item.imageHref)
                    factsList.add(model)
                }
                callAdapter()
            } else {
                if (isNetworkAvailable(context))
                    fetchDataFromApi()
                else
                    view?.showErrorMsg(getString(R.string.no_data))
            }
        })
    }

    private fun fetchDataFromApi() {
        binding.swipeRefreshLayout.isRefreshing = false
        viewModel.factsApi().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    setProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    setProgressVisibility(View.GONE)
                    //setting action bar title
                    getActionBar()?.title = it.value.title
                    //assign rows values to the facts list and call adapter
                    //save these facts to the local database
                    factsList = it.value.rows.toMutableList()
                    callAdapter()
                    saveDataToDataBase()
                }
                else -> setProgressVisibility(View.GONE)
            }
        })
    }

    private fun saveDataToDataBase() {
        var myList: MutableList<FactsModel> = mutableListOf<FactsModel>()
        //clear the previous values stored in the database
        viewModel.clearAll()
        for (item in factsList) {
            val model = FactsModel(0, item.title, item.description, item.imageHref)
            myList.add(model)
        }
        //adding updated values to the database
        viewModel.insert(myList)
    }

    private fun callAdapter() {
        //show error message if fact list is empty, otherwise call adapter
        if (factsList.isEmpty()) {
            view?.showErrorMsg(getString(R.string.no_data))
        } else {
            dashboardAdapter = DashboardAdapter(
                requireContext(), factsList
            )
            binding.recyclerView.adapter = dashboardAdapter
        }
    }

    /**
     * this will set the progress bar visibility
     * @param visibility
     */
    private fun setProgressVisibility(visibility: Int) {
        binding.progress.visibility = visibility
    }

    /**
     * this function will be called on swipe down refresh
     */
    override fun onRefresh() {
        fetchDataFromApi()
    }

}