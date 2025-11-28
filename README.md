# Deeplink Tester

A cross-platform mobile application for testing deeplinks, built with Compose Multiplatform for iOS and Android.

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)
![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7.1-brightgreen.svg)
![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20iOS-lightgrey.svg)

## Features

- 📱 **Cross-Platform**: Runs on both Android and iOS
- 🎨 **Material 3 Design**: Modern, clean UI following Material Design 3 guidelines
- 📦 **App Organization**: Group deeplinks by app for easy management
- 🔗 **Quick Testing**: Tap any deeplink to test it immediately
- ✏️ **Edit & Delete**: Long press to edit, swipe to delete
- 💾 **Local Storage**: All data stored locally using Room for Multiplatform

## Screenshots

### App List Screen
View all your apps with deeplink counts. Swipe to delete apps.

### Deeplink Detail Screen  
View and test deeplinks for each app. Long press to edit, swipe to delete.

## Tech Stack

- **UI**: Compose Multiplatform with Material 3
- **Architecture**: MVVM with Kotlin Flows
- **Database**: Room for Multiplatform
- **Dependency Injection**: Koin
- **Language**: Kotlin 2.0.21

## Project Structure

```
├── composeApp/          # Application entry points
│   ├── androidMain/     # Android-specific code
│   └── iosMain/         # iOS-specific code
├── shared/              # Shared Kotlin Multiplatform code
│   ├── commonMain/      # Common code for all platforms
│   │   ├── data/        # Database entities, DAOs, repository
│   │   ├── di/          # Koin dependency injection modules
│   │   └── ui/          # Compose UI screens and components
│   ├── androidMain/     # Android-specific implementations
│   └── iosMain/         # iOS-specific implementations
└── iosApp/              # iOS app wrapper
```

## Building

### Android
```bash
./gradlew :composeApp:assembleDebug
```

### iOS
Open the `iosApp` directory in Xcode and build the project.

## Requirements

- Android: API 24+ (Android 7.0)
- iOS: iOS 14+
- JDK 17+

## License

```
MIT License

Copyright (c) 2018-2024 Semih Dik

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
