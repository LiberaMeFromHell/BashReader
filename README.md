# Bash Reader

The simple reader which uploads quotes from popular web quote databases like bash.im.

### Structure 

The app implements MVVM pattern

### Functionality

The app has two activities: The Main and settings screen. The main activity holds Recycler View which keeps quotes. Settings screen allows you to chose between different sources and qoute number per source

### Used APIs

The app implemetns MVVM pattern and uses the next libraries:

* Android Jetpack
* Retrofit2
* OkHttp
* Room
* LiveData
* Dagger2

## TODO
* Replace synchronyous calls for different sources by ansyncronyous one by using Rxjava
* Add unity tests
* Main screen do not follow Material Design patterns. To be fixed
