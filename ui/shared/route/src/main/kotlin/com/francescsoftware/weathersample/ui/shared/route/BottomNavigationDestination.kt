package com.francescsoftware.weathersample.ui.shared.route

/** a [NavigationDestination] that is also a root bottom navigation destination */
interface BottomNavigationDestination : NavigationDestination {
    /** The content for the bottom navigation bar */
    val bottomNavContent: BottomNavContent
}
