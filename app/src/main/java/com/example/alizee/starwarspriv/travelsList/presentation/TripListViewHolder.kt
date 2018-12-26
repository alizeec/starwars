package com.example.alizee.starwarspriv.travelsList.presentation

import android.content.Context
import android.support.constraint.Group
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.BASE_URL
import com.example.alizee.starwarspriv.core.GlideApp
import com.example.alizee.starwarspriv.travelsList.domain.TripListing

class TripListViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    private val avatarView by lazy { itemView.findViewById<ImageView>(R.id.pilot_avatar) }
    private val nameView by lazy { itemView.findViewById<TextView>(R.id.pilot_name) }
    private val pickupView by lazy { itemView.findViewById<TextView>(R.id.pickup) }
    private val dropOffView by lazy { itemView.findViewById<TextView>(R.id.dropoff) }
    private val ratingGroup by lazy { itemView.findViewById<Group>(R.id.rating_group) }
    private val star1View by lazy { itemView.findViewById<ImageView>(R.id.star1) }
    private val star2View by lazy { itemView.findViewById<ImageView>(R.id.star2) }
    private val star3View by lazy { itemView.findViewById<ImageView>(R.id.star3) }
    private val star4View by lazy { itemView.findViewById<ImageView>(R.id.star4) }
    private val star5View by lazy { itemView.findViewById<ImageView>(R.id.star5) }


    fun bind(trip: TripListing) {
        GlideApp
            .with(context)
            .load(trip.pilotAvatar)
            .placeholder(R.drawable.ic_bb8)
            .centerCrop()
            .into(avatarView)

        nameView.text = trip.pilotName
        pickupView.text = trip.pickup
        dropOffView.text = trip.dropoff
        if (trip.isRated) ratingGroup.visibility = VISIBLE
        if (trip.star1Filled) star1View.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_small_filled))
        if (trip.star2Filled) star2View.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_small_filled))
        if (trip.star3Filled) star3View.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_small_filled))
        if (trip.star4Filled) star4View.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_small_filled))
        if (trip.star5Filled) star5View.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_small_filled))

    }
}