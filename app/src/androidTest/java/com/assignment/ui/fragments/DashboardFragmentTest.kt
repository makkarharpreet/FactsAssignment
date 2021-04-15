package com.assignment.ui.fragments

import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.model.FactsResponseModel
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @Test
    fun testWhenListNotEmpty() {
        val scenario = launchFragment<DashboardFragment>()
        scenario.onFragment { fragment ->
            fragment.factsList.add(0, FactsResponseModel.Rows("Canada","A country",""))
            fragment.callAdapter()
            assert(fragment.binding.recyclerView.adapter?.itemCount!=0)
        }
    }

    @Test
    fun testWhenListEmpty() {
        val scenario = launchFragment<DashboardFragment>()
        scenario.onFragment { fragment ->
            fragment.factsList.add(0, FactsResponseModel.Rows("Canada","A country",""))
            fragment.callAdapter()
            assert(fragment.binding.recyclerView.adapter?.itemCount==0)
        }
    }
}