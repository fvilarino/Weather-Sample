pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Weather Sample"
include(":analysis")
include(":app")
include(":core:connectivity:connectivity-api")
include(":core:connectivity:connectivity-impl")
include(":core:coroutines")
include(":core:dispatcher")
include(":core:injection")
include(":core:network")
include(":core:time:time-api")
include(":core:time:time-impl")
include(":core:type:either")
include(":core:type:weather")
include(":data:persistence:settings:settings-api")
include(":data:persistence:settings:settings-impl")
include(":data:repository:city:cityrepo-api")
include(":data:repository:city:cityrepo-impl")
include(":data:repository:favorite:favoriterepo-api")
include(":data:repository:favorite:favoriterepo-impl")
include(":data:repository:recents:recentsrepo-api")
include(":data:repository:recents:recentsrepo-impl")
include(":data:repository:weather:weatherrepo-api")
include(":data:repository:weather:weatherrepo-impl")
include(":domain:interactor:city:cityinteractor-api")
include(":domain:interactor:city:cityinteractor-impl")
include(":domain:interactor:foundation")
include(":domain:interactor:preferences:preferencesinteractor-api")
include(":domain:interactor:preferences:preferencesinteractor-impl")
include(":domain:interactor:weather:weatherinteractor-api")
include(":domain:interactor:weather:weatherinteractor-impl")
include(":testing:fake")
include(":testing:macrobenchmark")
include(":ui:feature:favorites")
include(":ui:feature:home")
include(":ui:feature:search")
include(":ui:feature:settings")
include(":ui:shared:assets")
include(":ui:shared:composable:common")
include(":ui:shared:composable:weather")
include(":ui:shared:navigation")
include(":ui:shared:styles")
include(":ui:shared:weathericon")
