package com.example.alizee.starwarspriv.travelsList.presentation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.EXTRA_TRIP_ID
import com.example.alizee.starwarspriv.core.StarWarsApplication
import com.example.alizee.starwarspriv.core.utils.DividerDecoration
import com.example.alizee.starwarspriv.details.presentation.TripDetailActivity
import com.example.alizee.starwarspriv.travelsList.domain.TripListing
import javax.inject.Inject

class TravelListActivity : AppCompatActivity(), TravelListScreen {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val tripListView by lazy { findViewById<RecyclerView>(R.id.travel_list) }
    private val loaderView by lazy { findViewById<ProgressBar>(R.id.loader) }

    @Inject
    internal lateinit var presenter: TravelListPresenter

    private lateinit var tripList: List<TripListing>
    private lateinit var adapter: TravelListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_space_travels)
        toolbar.title = resources.getString(R.string.travel_list_title)
        setSupportActionBar(toolbar)

        StarWarsApplication.instance.getComponent().inject(this)

        presenter.bind(this)
        presenter.start()
    }

    override fun displayTrips(trips: List<TripListing>) {
        this.tripList = trips
        val viewManager = LinearLayoutManager(this)
        adapter = TravelListAdapter(tripList, this, ::onCallClicked)
        tripListView.layoutManager = viewManager
        tripListView.setHasFixedSize(true)
        tripListView.adapter = adapter
        tripListView.addItemDecoration(DividerDecoration(this))
    }

    override fun stopLoading() {
        loaderView.visibility = View.GONE
    }

    override fun displayError(error: Int) {
        Toast.makeText(this, resources.getString(error), Toast.LENGTH_LONG).show()
    }


    private fun onCallClicked(id: Int) {
        val intent = Intent(this, TripDetailActivity::class.java)
        intent.putExtra(EXTRA_TRIP_ID, id)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}
