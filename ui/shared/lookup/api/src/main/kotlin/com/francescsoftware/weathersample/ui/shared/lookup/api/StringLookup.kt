package com.francescsoftware.weathersample.ui.shared.lookup.api

import androidx.annotation.StringRes

/** Loads strings from resources */
interface StringLookup {

    /**
     * Loads a string from resources
     *
     * @param id - the string id
     * @return - the String from resources
     */
    fun getString(@StringRes id: Int): String

    /**
     * Loads a formatted string from resources
     *
     * @param id - the string id
     * @param formatArgs - the string format to apply
     * @return the formatted string
     */
    fun getString(@StringRes id: Int, vararg formatArgs: Any): String
}
