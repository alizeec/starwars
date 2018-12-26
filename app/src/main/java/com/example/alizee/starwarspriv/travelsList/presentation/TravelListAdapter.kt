package com.example.alizee.starwarspriv.travelsList.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.travelsList.domain.TripListing

class TravelListAdapter(
    private var tripList: List<TripListing>,
    private val context: Context,
    private val onClickAction: (id: Int) -> Unit
) : RecyclerView.Adapter<TripListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.trip_item, parent, false)
        return TripListViewHolder(textView, context)
    }

    override fun getItemCount() = tripList.size

    override fun onBindViewHolder(viewHolder: TripListViewHolder, position: Int) {
        val call = tripList[position]
        viewHolder.bind(call)
        viewHolder.itemView.setOnClickListener { onClickAction(call.id) }
    }
}