plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(projects.core.dispatcher)
    implementation(projects.core.time.timeApi)
    implementation(projects.core.type.either)
    implementation(projects.data.repository.city.cityrepoApi)
    implementation(projects.data.repository.favorite.favoriterepoApi)
    implementation(projects.data.repository.recents.recentsrepoApi)
    implementation(projects.domain.interactor.city.cityinteractorApi)
    testImplementation(testFixtures(projects.core.dispatcher))
    testImplementation(testFixtures(projects.data.repository.city.cityrepoApi))

    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.paging.paging.common.ktx)
}
