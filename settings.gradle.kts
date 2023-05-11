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
include(":core:coroutines")
include(":core:dispatcher")
include(":core:network")
include(":core:time:api")
include(":core:time:impl")
include(":core:type")
include(":data:repository:city:api")
include(":data:repository:city:impl")
include(":data:repository:favorite:api")
include(":data:repository:favorite:impl")
include(":data:repository:recents:api")
include(":data:repository:recents:impl")
include(":data:repository:weather:api")
include(":data:repository:weather:impl")
include(":domain:interactor:city:api")
include(":domain:interactor:city:impl")
include(":domain:interactor:weather:api")
include(":domain:interactor:weather:impl")
include(":ui:feature:city")
include(":ui:feature:landing")
include(":ui:feature:weather")
include(":ui:shared:assets")
include(":ui:shared:composable:common")
include(":ui:shared:composable:weather")
include(":ui:shared:deviceclass")
include(":ui:shared:lookup:api")
include(":ui:shared:lookup:impl")
include(":ui:shared:mvi")
include(":ui:shared:route")
include(":ui:shared:styles")
include(":testing:fake")
include(":testing:macrobenchmark")
