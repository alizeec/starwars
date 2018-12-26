package com.example.alizee.starwarspriv.travelsList.presentation

import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.di.IoScheduler
import com.example.alizee.starwarspriv.core.di.MainThreadScheduler
import com.example.alizee.starwarspriv.travelsList.data.repository.TravelListRepository
import com.example.alizee.starwarspriv.travelsList.domain.toPresentationList
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

internal class TravelListPresenter @Inject constructor(
    @MainThreadScheduler private val mainThreadScheduler: Scheduler, @IoScheduler private val ioScheduler: Scheduler,
    private val travelListRepository: TravelListRepository
) {
    private var screen: TravelListScreen? = null
    private val compositeDisposable = CompositeDisposable()

    fun bind(screen: TravelListScreen) {
        this.screen = screen
    }

    fun unbind() {
        this.screen = null
        compositeDisposable.clear()
    }

    fun start() {
        compositeDisposable.add(
            travelListRepository.fetchAllTrips().subscribeOn(ioScheduler).observeOn(mainThreadScheduler).subscribe(
                { trips ->
                    screen?.stopLoading()
                    val travelList = trips.map { it.toPresentationList() }
                    screen?.displayTrips(travelList)

                },
                {
                    screen?.stopLoading()
                    screen?.displayError(R.string.error_list)
                })
        )
    }
}