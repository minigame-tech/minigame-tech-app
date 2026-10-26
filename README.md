# 🕹️ Minigame Tech

> A lightweight retro arcade platform featuring classic game remakes, designed for web play and packaged as an Android app through a native WebView wrapper.

[![Website](https://img.shields.io/badge/website-live-success.svg)](https://minigame-tech.github.io/)
[![Android App](https://img.shields.io/badge/platform-Android-blue.svg)](#running-the-app)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE.md)

---

## Overview

Minigame Tech is a lightweight, cross-platform retro arcade project inspired by the golden age of arcades. The core experience is delivered as a responsive web platform, and the repository also includes an Android application that wraps the web app in a native shell using Java and WebView.

This combination allows the project to be:

- easy to distribute on the web,
- mobile-friendly and touch-optimized,
- packaged natively for Android,
- lightweight and fast to load.

The app loads the hosted web experience from:

<https://minigame-tech.github.io/>

---

## Features

- Classic arcade-inspired games and remakes
- Responsive design for desktop and mobile
- Touch-friendly controls for smartphones and tablets
- Android native wrapper via WebView
- Smooth fullscreen experience
- Offline error state for the Android app
- Lightweight architecture with minimal dependencies

---

## Project Structure

```text
minigame-tech-app/
├── app/
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/
│   │   │   │   └── com/example/mini_game_tech_app/
│   │   │   │       └── MainActivity.java
│   │   │   ├── res/
│   │   │   └── keepRules/
│   └── release/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── gradle.properties
├── README.md
├── LICENSE.md
├── CONTRIBUTING.md
├── CODE_OF_CONDUCT.md
├── SECURITY.md
└── Logo.jpeg
```

The Android app is defined in `app/` and uses `MainActivity.java` to:

- initialize a `WebView`,
- load the public website,
- handle back navigation,
- detect connectivity,
- show an offline fallback page,
- use immersive fullscreen mode.

---

## Tech Stack

- **Android:** Java, Android SDK, WebView
- **UI:** XML layouts, Material components
- **Web platform:** HTML, CSS, JavaScript
- **Hosting:** GitHub Pages
- **Build system:** Gradle / Kotlin DSL

---

## Running the App

### Prerequisites

- Android Studio
- JDK 11+
- Android SDK configured for API 24+ (project target: 37)

### Build steps

1. Clone the repository:

   ```bash
   git clone https://github.com/minigame-tech/minigame-tech-app.git
   cd minigame-tech-app
   ```

2. Open the project in Android Studio.
3. Let Gradle sync dependencies.
4. Run the `app` configuration on an emulator or a physical Android device.

---

## Web Experience

The app is designed to display the hosted game platform at:

<https://minigame-tech.github.io/>

This allows the browser-based experience to remain lightweight while still being accessible from a native Android wrapper.

---

## Offline Behavior

The Android app includes a custom offline page shown when no network is available. This provides a fallback experience instead of a blank or broken WebView.

---

## Contributing

Contributions are welcome.

If you want to help:

1. Fork the project
2. Create a new branch
3. Commit your changes
4. Open a pull request

Please also read:

- [`CONTRIBUTING.md`](https://github.com/minigame-tech/minigame-tech-app?tab=contributing-ov-file)
- [`CODE_OF_CONDUCT.md`](https://github.com/minigame-tech/minigame-tech-app?tab=coc-ov-file)

---

## License

This project is distributed under the MIT License. See [LICENSE.md](LICENSE.md) for details.

---

## Credits

Built for retro arcade enthusiasts and designed to bring classic gameplay back into a modern, mobile-friendly environment.

---
## Project Status

The repository is structured as an Android wrapper around a web-based arcade experience, with a live web deployment and native mobile access.
