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
include(":core:type")
include(":data:repository")
include(":presentation:feature")
include(":presentation:shared")
include(":styles")
include(":utils")
include(":testing")
