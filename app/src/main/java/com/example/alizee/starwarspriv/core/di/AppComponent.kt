package com.example.alizee.starwarspriv.core.di

import com.example.alizee.starwarspriv.details.presentation.TripDetailActivity
import com.example.alizee.starwarspriv.travelsList.presentation.TravelListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: TravelListActivity)
    fun inject(activity: TripDetailActivity)
}