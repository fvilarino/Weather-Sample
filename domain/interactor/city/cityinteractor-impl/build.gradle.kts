plugins {
    id("weathersample.kotlin.library")
    id("weathersample.android.di")
    id("weathersample.kotlin.test.library")
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:time-api"))
    implementation(project(":core:type:either"))
    implementation(project(":data:repository:city:cityrepo-api"))
    implementation(project(":data:repository:favorite:favoriterepo-api"))
    implementation(project(":data:repository:recents:recentsrepo-api"))
    implementation(project(":domain:interactor:city:cityinteractor-api"))
    implementation(libs.bundles.coroutines)
}
