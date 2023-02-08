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
include(":app")
include(":business:interactor:city:api")
include(":business:interactor:city:impl")
include(":business:interactor:weather:api")
include(":business:interactor:weather:impl")
include(":core:coroutines")
include(":core:dispatcher")
include(":core:network")
include(":core:time:api")
include(":core:time:impl")
include(":core:type")
include(":data:repository:city:api")
include(":data:repository:city:impl")
include(":data:repository:recents:api")
include(":data:repository:recents:impl")
include(":data:repository:weather:api")
include(":data:repository:weather:impl")
include(":data:storage:city:api")
include(":data:storage:city:impl")
include(":presentation:feature:city")
include(":presentation:feature:landing")
include(":presentation:feature:weather")
include(":presentation:shared:assets")
include(":presentation:shared:composable")
include(":presentation:shared:deviceclass")
include(":presentation:shared:lookup:api")
include(":presentation:shared:lookup:impl")
include(":presentation:shared:mvi")
include(":presentation:shared:route")
include(":presentation:shared:styles")
include(":testing:mock")
include(":testing:macrobenchmark")
include(":utils")
