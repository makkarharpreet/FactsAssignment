package com.assignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.databinding.DashboardAdapterItemBinding
import com.assignment.model.FactsResponseModel
import com.assignment.utility.Utility.loadImage

/**
 * @author Harpreet Singh
 */
class DashboardAdapter(
    private val context: Context,
    private var list: List<FactsResponseModel.Rows>
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DashboardAdapterItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder( binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dashboardAdapterItemBinding.title.text = list[position].title
        holder.dashboardAdapterItemBinding.image.loadImage(list[position].imageHref)
        holder.dashboardAdapterItemBinding.description.text = list[position].description
    }

    /**
     * this inner class is created to bind the adapter item
     */
    inner class ViewHolder(var dashboardAdapterItemBinding: DashboardAdapterItemBinding) :
        RecyclerView.ViewHolder(dashboardAdapterItemBinding.root)
}
