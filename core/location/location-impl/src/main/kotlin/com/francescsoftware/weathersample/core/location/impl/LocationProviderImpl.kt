package com.francescsoftware.weathersample.core.location.impl

import android.annotation.SuppressLint
import android.content.Context
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.ApplicationContext
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.core.location.api.LocationProvider
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class LocationProviderImpl @Inject constructor(
    @ApplicationContext context: Context,
) : LocationProvider {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Coordinates? = suspendCoroutine { cont ->
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            null,
        )
            .addOnSuccessListener { location ->
                cont.resume(
                    location?.let { loc ->
                        Coordinates(
                            longitude = loc.longitude,
                            latitude = loc.latitude,
                        )
                    }
                )
            }
            .addOnFailureListener {
                cont.resume(null)
            }
    }
}
