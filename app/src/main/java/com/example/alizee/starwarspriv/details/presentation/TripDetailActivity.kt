package com.example.alizee.starwarspriv.details.presentation

import android.os.Bundle
import android.support.constraint.Group
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.BASE_URL
import com.example.alizee.starwarspriv.core.EXTRA_TRIP_ID
import com.example.alizee.starwarspriv.core.GlideApp
import com.example.alizee.starwarspriv.core.StarWarsApplication
import javax.inject.Inject

class TripDetailActivity: AppCompatActivity(), TripDetailScreen {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val avatarPilotView by lazy { findViewById<ImageView>(R.id.pilot_avatar) }
    private val namePilotView by lazy { findViewById<TextView>(R.id.pilot_name) }

    private val pickupNameView by lazy { findViewById<TextView>(R.id.departure_value) }
    private val pickupTimeView by lazy { findViewById<TextView>(R.id.departure_time) }
    private val dropoffNameView by lazy { findViewById<TextView>(R.id.arrival_value) }
    private val dropoffTimeView by lazy { findViewById<TextView>(R.id.arrival_time) }

    private val distanceView by lazy { findViewById<TextView>(R.id.distance_value) }
    private val durationView by lazy { findViewById<TextView>(R.id.duration_value) }

    private val ratingGroup by lazy { findViewById<Group>(R.id.rating_group) }
    private val emptyRatingView by lazy { findViewById<TextView>(R.id.empty_rating) }
    private val star1View by lazy { findViewById<ImageView>(R.id.star1) }
    private val star2View by lazy { findViewById<ImageView>(R.id.star2) }
    private val star3View by lazy { findViewById<ImageView>(R.id.star3) }
    private val star4View by lazy { findViewById<ImageView>(R.id.star4) }
    private val star5View by lazy { findViewById<ImageView>(R.id.star5) }

    private val loaderView by lazy { findViewById<ProgressBar>(R.id.loader) }

    private val foregroundPlanet by lazy { findViewById<ImageView>(R.id.planet_foreground) }
    private val backgroundPlanet by lazy { findViewById<ImageView>(R.id.planet_background) }

    private val tripId by lazy { intent.getIntExtra(EXTRA_TRIP_ID, -1) }

    @Inject
    internal lateinit var presenter: TripDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)

        StarWarsApplication.instance.getComponent().inject(this)

        presenter.bind(this)
        presenter.start(tripId)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun displayForegroundPlanet(urlImage: String) {
        GlideApp
            .with(this)
            .load(urlImage)
            .centerCrop()
            .into(foregroundPlanet)
    }

    override fun displayBackgroundPlanet(urlImage: String) {
        GlideApp
            .with(this)
            .load(urlImage)
            .centerCrop()
            .into(backgroundPlanet)
    }

    override fun displayPilotAvatar(urlAvatar: String) {
        GlideApp
            .with(this)
            .load(urlAvatar)
            .placeholder(R.drawable.ic_bb8)
            .centerCrop()
            .into(avatarPilotView)
    }

    override fun displayPilotName(name: String) {
        namePilotView.text = name
    }

    override fun displayPickupName(name: String) {
        pickupNameView.text = name
    }

    override fun displayPickupTime(time: String) {
        pickupTimeView.text = time
    }

    override fun displayDropoffName(name: String) {
        dropoffNameView.text = name
    }

    override fun displayDropoffTime(time: String) {
        dropoffTimeView.text = time
    }

    override fun displayDistance(distance: String) {
        distanceView.text = distance
    }

    override fun displayDuration(duration: String) {
        durationView.text = duration
    }

    override fun hideRatings() {
        ratingGroup.visibility = GONE
        emptyRatingView.visibility = VISIBLE
    }

    override fun displayRatings(
        star1Filled: Boolean,
        star2Filled: Boolean,
        star3Filled: Boolean,
        star4Filled: Boolean,
        star5Filled: Boolean
    ) {
        if (star1Filled) star1View.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_big_filled))
        if (star2Filled) star2View.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_big_filled))
        if (star3Filled) star3View.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_big_filled))
        if (star4Filled) star4View.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_big_filled))
        if (star5Filled) star5View.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_big_filled))

    }

    override fun stopLoading() {
        loaderView.visibility = View.GONE
    }

    override fun displayError(error: Int) {
        Toast.makeText(this, resources.getString(error), Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}