dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Weather Sample"
include(":app")
include(":business:interactor")
include(":core:dispatcher")
include(":core:network")
include(":core:time:api")
include(":core:time:impl")
include(":core:type")
include(":data:city:api")
include(":data:city:impl")
include(":data:weather:api")
include(":data:weather:impl")
include(":presentation:feature")
include(":presentation:shared")
include(":presentation:storage")
include(":styles")
include(":utils")
include(":testing")
