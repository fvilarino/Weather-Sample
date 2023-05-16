# Weather-Sample

## Introduction
Weather Sample is a simple weather app showcasing some of the material principles.

The app is multimodule, written in Kotlin using Jetpack Compose for the UI. The app consists of 3 screens, a city search screen, a weather screen for a specific city split into current weather and forecast, and a carousel of favorite cities displaying current weather and forecast for each city.

## Getting set up
1. Clone the project
```
$ git clone git@github.com:fvilarino/Weather-Sample.git
```
2. Obtain a key from [RapidApi](https://rapidapi.com/) for both the [Geo Cities](https://rapidapi.com/wirefreethought/api/geodb-cities) and the [Open Weather Map](https://rapidapi.com/community/api/open-weather-map)
3. Create a file named `keys.properties` inside the `certs` folder with your key, following this format


```
rapid_api_key=<your key here>
```
4. Create a file named `release.properties` inside the `certs` folder with the release signing credentials, following this format
```
store=./certs/release.keystore.jks (use your own release keystore here)
alias=<you alias>
storePass=<your store password>
keyPass=<your key password>
```
5.Build the project, using the following command
```
$ ./gradlew assembleDebug -PbuildNumber=1
```

## Tech stack:

* Kotlin
* Coroutines
* Hilt
* Retrofit
* Room
* Jetpack Compose
* AAC Viewmodels
* AAC Navigation
* MVI

https://user-images.githubusercontent.com/2680481/221377508-b1531f39-92a3-4431-9bf8-14e0e72b603a.mp4

