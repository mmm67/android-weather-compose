# Weather App using Jetpack Compose

This project is a simple weather application built using Jetpack Compose for Android. It retrieves weather data from the OpenWeatherMap API and displays it in a clean and user-friendly interface.

## Features

- **Current Weather:** Displays the current temperature, "feels like" temperature, high/low temperatures, wind speed, and humidity for a given city.
- **City Search:** Allows users to search for weather information by entering a city name.
- **5-Day Forecast:** Provides a forecast for the next five days, including high and low temperatures.
- **Clean UI:** Uses Jetpack Compose for a modern and responsive UI.
- **API Powered:** Uses the OpenWeatherMap API to get accurate and up-to-date weather data.

## Screenshot

![App Screenshot](weather-app.gif)
*Note: Add a different screenshot link if you don't want to use the given link*


## Technologies Used

- **Jetpack Compose:** Android's modern toolkit for building native UI.
- **Kotlin:** The primary programming language.
- **OpenWeatherMap API:**  Provides the weather data.
- **Retrofit:** For making network requests.
- **Coroutines:** For asynchronous tasks.

## Getting Started

### Prerequisites

-   Android Studio (latest version)
-   A basic understanding of Kotlin and Jetpack Compose.
-   An account on [OpenWeatherMap](https://openweathermap.org/) to get an API Key.

### Setup
1.  **Clone the repository:**
    ```bash
    git clone [your-repo-url]
    ```
2.  **Open in Android Studio:**
    Open the cloned project in Android Studio.
3.  **Get your OpenWeatherMap API Key:**
    -   Sign up at [OpenWeatherMap](https://openweathermap.org/) and generate your API key
    -   Go to `local.properties` file and add your API Key:
        ```properties
        OPEN_WEATHER_API_KEY="your_api_key_here"
        ```
4. **Build and Run:** Build and run the project on an emulator or physical device.

## Architecture

The app follows a simple MVVM (Model-View-ViewModel) architectural pattern:
- **Model:** Data classes representing weather information
- **View:** Jetpack Compose components handling UI rendering.
- **ViewModel:** Responsible for handling UI logic, fetching data and providing it to the View.

## OpenWeatherMap API

This application uses the OpenWeatherMap API to fetch weather information. You can visit [OpenWeatherMap](https://openweathermap.org/) to learn more about it. Make sure you have obtained your API Key from OpenWeatherMap and insert it in your local.properties file.

## Future Enhancements

- Add caching mechanism to reduce API calls.
-  Location based weather forecast.
-  Make the UI more customizable and themeable.
-  Handle edge cases such as network errors.

## Contributing

If you would like to contribute to this project, feel free to fork the repository and submit pull requests. Any contributions are welcome!

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author
[Your Name]
[Your Github Profile Link]
