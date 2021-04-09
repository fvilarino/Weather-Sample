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
include(":core:repository")
include(":presentation:feature")
include(":presentation:shared")
