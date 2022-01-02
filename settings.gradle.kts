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
include(":core:dispatcher")
include(":core:network")
include(":core:time:api")
include(":core:time:impl")
include(":core:type")
include(":data:storage:city:api")
include(":data:storage:city:impl")
include(":data:repository:city:api")
include(":data:repository:city:impl")
include(":data:repository:weather:api")
include(":data:repository:weather:impl")
include(":presentation:feature")
include(":presentation:shared")
include(":styles")
include(":utils")
include(":testing")
