# Weather-Sample
A simple weather app showcasing some of the material principles.

The app is multimodule, written in Kotlin using Jetpack Compose for the UI. The app consists of 2 screens, a city search screen and a weather screen. The weather screen is itself split into 2 sections, current weather and forecast.

Tech stack:

* Kotlin
* Coroutines
* Hilt
* Retrofit
* Room
* AAC Viewmodels
* AAC Navigation

To run the app please obtain a key from RapidApi (https://rapidapi.com/) for both the geo cities (https://rapidapi.com/wirefreethought/api/geodb-cities) and the open weather map (https://rapidapi.com/community/api/open-weather-map) services and then create a file named `keys.properties` inside the `certs` folder with your key, following this format

```
rapid_api_key=<your key here>
```

Then create a file named `build_number.properties` on the project root folder with this content

```
buildNumber=<your build number>
```

where `<your build number>` is any integer value.

https://user-images.githubusercontent.com/2680481/221377508-b1531f39-92a3-4431-9bf8-14e0e72b603a.mp4

