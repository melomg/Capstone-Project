# WonderAndWander

<p align="center"><img src="https://github.com/melomg/Capstone-Project/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png" alt="WonderAndWander" height="120px"></p>

:zap: WonderAndWander app is a simple app that helps you to decide whether a city is the one you are looking for, in every aspect. You can search cities, see most important things from safety to cost of living about cities. WonderAndWander app will store the cities you favorited if you logged in. This way you never need to remember which city you liked once upon a time. It also helps you to compare the cities so you can decide better. Don't wait, wonder and wander now.

[![Build Status](https://circleci.com/gh/melomg/Capstone-Project/tree/master.svg?style=shield)](https://circleci.com/gh/melomg/Capstone-Project)

### Main features:
  - Logs users with FirebaseUI Auth
  - Makes a city search and shows basic information about selected city. (Housing, Cost of
Living, Travel Connectivity, Safety, etc.)
  - Favorites a city and stores them in Firebase Realtime Database if the user logged in.
  - Compares two cities’s basic information.
  
### Tech Stack:
Demonstrates using Dagger 2.15+ in MVVM app with Android Architecture Components and Repository pattern.
  - MVVM
  - Repository
  - Dagger2
  - Data Binding
  - Architecture Components
  - Retrofit 2.3.0+

## Getting Started
In order to run the project successfully, you need to:
  - add your Fabric key to Manifest.xml file. For full integration of Fabric, check [Crashlytics install](https://fabric.io/kits/android/crashlytics/install)
  - create a Firebase project and enable Maps, Places Sdks for Android.
  - activate Google identity provider from **Authentication** page. For full integration of Firebase, check [Firebase Setup](https://firebase.google.com/docs/android/setup).
  - create a keys.xml file in `/app/src/free/res/values` directory and put your own id values for `banner_ad_id` and `map_id` keys. 
  - download `google_services.json` file and place it in the `app/` directory (at the root of the Android Studio app module).
  - add `keystore.properties` file in root directory which includes:
    ```
    keyAlias={keyAlias}
    keyPassword={keyPassword}
    storeFile={storeFile}
    storePassword {storePassword}
    ```
 - and `jks` keystore file to root directory.
  
## Dependency

  - [Support Libraries](https://developer.android.com/topic/libraries/support-library/setup): for better design and compatibility.
  - [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/): for MVVM design pattern and clean
code.
  - [Room](https://developer.android.com/topic/libraries/architecture/room): for easy data storage by providing an abstraction layer over
SQLite.
  - [Dagger](https://google.github.io/dagger/): for dependency injection to write more testable and maintainable
code.
  - [Retrofit](https://github.com/square/retrofit): a rest client which makes easy to retrieve and upload JSON via a
rest based web service.
  - [Okhttp3](https://github.com/square/okhttp): an HTTP & HTTP/2 client that covers many things.
  - [Glide](https://github.com/bumptech/glide): provides a framework for image loading.
  - [Timber](https://github.com/JakeWharton/timber): a logger which provides utility on top of Android's normal Log
class.
  - [Leak Canary](https://github.com/square/leakcanary): A memory leak detection library.
  - [Map Sdk](https://developers.google.com/maps/documentation/android-sdk/intro): to show location of cities.
  - [Firebase UI Database](https://github.com/firebase/FirebaseUI-Android/blob/master/database/README.md): to store users’ favorite cities at Firebase Realtime
Database
  - [Firebase UI Auth](https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md): to authenticate the user
  - [Ads Play Services](https://developers.google.com/admob/android/quick-start): Ads will be shown to free app’s users. The app
displays test ads in debug variant.
  - [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart): To show scores of a city as chart. 
  - [Fabric Crashlytics](https://fabric.io/kits/android/crashlytics/install): Crash reporting tool.

## Versioning

WonderAndWander uses [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/melomg/Capstone-Project/tags). 

## Authors

* **Melih Gültekin** - *Initial work* - [melomg](https://github.com/melomg)

See also the list of [contributors](https://github.com/melomg/Capstone-Project/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
