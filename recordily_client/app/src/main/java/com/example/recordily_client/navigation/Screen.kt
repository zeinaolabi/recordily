package com.example.recordily_client.navigation

sealed class Screen(val route: String){
    object LoginPage: Screen(route = "login_screen")
    object RegistrationPage: Screen(route = "registration_screen")
    object CommonLandingPage: Screen(route = "common_landing_page")
    object CommonProfilePage: Screen(route = "common_profile_page")
    object UnreleasedPage: Screen(route = "unreleased_page")
    object RecordPage: Screen(route = "record_page")
    object ViewsStatsPage: Screen(route = "views_stats_page")
    object SongsStatsPage: Screen(route = "views_stats_page")
    object CommonSearchPage: Screen(route = "common_search_page")
    object CommonLiveEventsPage: Screen(route = "common_search_page")


}
