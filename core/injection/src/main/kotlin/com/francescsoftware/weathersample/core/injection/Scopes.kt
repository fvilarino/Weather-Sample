package com.francescsoftware.weathersample.core.injection

import javax.inject.Scope
import kotlin.reflect.KClass

@Suppress("UnnecessaryAbstractClass")
abstract class AppScope private constructor()

@Suppress("UnnecessaryAbstractClass")
abstract class ActivityScope private constructor()

@Scope
annotation class SingleIn(val scope: KClass<*>)
