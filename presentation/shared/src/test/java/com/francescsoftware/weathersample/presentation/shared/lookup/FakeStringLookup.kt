package com.francescsoftware.weathersample.presentation.shared.lookup

class FakeStringLookup : StringLookup {
    var result: String = ""

    override fun getString(id: Int): String = result

    override fun getString(id: Int, vararg formatArgs: Any): String = result
}
