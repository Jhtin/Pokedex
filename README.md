# Pokédex

A native Android Pokémon lookup app built with Java and XML layouts. Search for a Pokémon to view its types, base stats, and animated sprite from PokéAPI.

**Java · Android Views · PokéAPI · Glide**

## Features

- Search by Pokémon name.
- View the Pokédex ID, name, and type combination.
- Compare six base stats: HP, Attack, Defense, Special Attack, Special Defense, and Speed.
- Display animated sprites with a Poké Ball placeholder.
- Clear the current search and results.

## Run locally

1. Clone the repository:

   ```sh
   git clone https://github.com/Jhtin/Pokedex.git
   ```

2. Open the project folder in Android Studio and let Gradle sync.
3. Install the Android SDK requested by the project.
4. Run the `app` module on an emulator or device running **Android 8.0 / API 26 or newer**.

An internet connection is required to retrieve Pokémon information and sprites. No API key is configured by the app.

To compile a debug APK from the project root:

```sh
./gradlew assembleDebug
```

On Windows, use `gradlew.bat assembleDebug`.

## Source guide

| File | Purpose |
| --- | --- |
| [MainActivity.java](app/src/main/java/com/example/activity_5/MainActivity.java) | Search handling, HTTP requests, JSON parsing, and UI updates |
| [activity_main.xml](app/src/main/res/layout/activity_main.xml) | Main screen layout |
| [app/build.gradle.kts](app/build.gradle.kts) | Android configuration and dependencies |

## Implementation notes

Requests run on a background thread using `HttpURLConnection`; results are applied on the UI thread. Glide loads the animated Generation V sprite returned by PokéAPI.

This is an educational project with a single activity. Some Pokémon do not have the requested animated sprite, and network or lookup failures use a shared error message. There is no offline catalogue.

## Credits

Pokémon data and sprite URLs are supplied by [PokéAPI](https://pokeapi.co/). Pokémon names and imagery belong to their respective rights holders. This project is an unofficial educational application.
