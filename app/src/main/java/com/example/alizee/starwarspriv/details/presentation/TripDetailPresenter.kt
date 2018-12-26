package com.example.alizee.starwarspriv.details.presentation

import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.di.IoScheduler
import com.example.alizee.starwarspriv.core.di.MainThreadScheduler
import com.example.alizee.starwarspriv.details.data.repository.TripDetailRepository
import com.example.alizee.starwarspriv.details.domain.TripDetail
import com.example.alizee.starwarspriv.details.domain.TripDetailTransformer
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException
import javax.inject.Inject

internal class TripDetailPresenter @Inject constructor(
    @MainThreadScheduler private val mainThreadScheduler: Scheduler, @IoScheduler private val ioScheduler: Scheduler,
    private val tripDetailRepository: TripDetailRepository, private val tripDetailTransformer: TripDetailTransformer
) {
    private var screen: TripDetailScreen? = null
    private val compositeDisposable = CompositeDisposable()

    fun bind(screen: TripDetailScreen) {
        this.screen = screen
    }

    fun unbind() {
        this.screen = null
        compositeDisposable.clear()
    }

    fun start(id: Int) {
        if (id == -1) throw IllegalStateException("The id of the trip is invalid")

        compositeDisposable.add(
            tripDetailRepository.fetchTrip(id).subscribeOn(ioScheduler).observeOn(
                mainThreadScheduler
            ).subscribe({
                screen?.stopLoading()
                val tripDetail = tripDetailTransformer.tripToTripDetail(it)
                displayTrip(tripDetail)
            }, {
                screen?.stopLoading()
                screen?.displayError(R.string.error_detail)
            })
        )
    }

    private fun displayTrip(trip: TripDetail) {
        screen?.displayForegroundPlanet(trip.dropoffImage)
        screen?.displayBackgroundPlanet(trip.pickupImage)
        screen?.displayPilotAvatar(trip.pilotAvatar)
        screen?.displayPilotName(trip.pilotName)
        screen?.displayPickupName(trip.pickupName)
        screen?.displayPickupTime(trip.pickupTime)
        screen?.displayDropoffName(trip.dropoffName)
        screen?.displayDropoffTime(trip.dropoffTime)
        screen?.displayDistance(trip.distance)
        screen?.displayDuration(trip.duration)
        if (trip.isRated) {
            screen?.displayRatings(
                trip.star1Filled, trip.star2Filled, trip.star3Filled, trip.star4Filled,
                trip.star5Filled
            )
        } else screen?.hideRatings()
    }
}