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

To run the app please obtain a key from RapidApi (https://rapidapi.com/) for both the geo cities (https://rapidapi.com/wirefreethought/api/geodb-cities) and the open weather map (https://rapidapi.com/community/api/open-weather-map) and then update create a file named `keys.properties` inside the `certs` folder with your key, following this format

```
rapid_api_key=<your key here>
```

![cities_screen resized](https://user-images.githubusercontent.com/2680481/114630400-58cc0280-9c6f-11eb-84ee-56f997bcf959.png)

![current_weather_screen resized](https://user-images.githubusercontent.com/2680481/114630404-5cf82000-9c6f-11eb-8e4e-1fae1f5d6043.png)

![forecast_screen resized](https://user-images.githubusercontent.com/2680481/114630408-5f5a7a00-9c6f-11eb-915a-54550388af5f.png)
