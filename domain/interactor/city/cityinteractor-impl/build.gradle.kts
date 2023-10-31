plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:time-api"))
    implementation(project(":core:type:either"))
    implementation(project(":data:repository:city:cityrepo-api"))
    implementation(project(":data:repository:favorite:favoriterepo-api"))
    implementation(project(":data:repository:recents:recentsrepo-api"))
    implementation(project(":domain:interactor:city:cityinteractor-api"))
    testImplementation(testFixtures(project(":core:dispatcher")))

    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.paging.paging.common.ktx)
}
