# amaroTest
### A demonstration of MVP pattern using the trending libraries of the android world.
 
App is based on the [Google TODO MVP pattern](https://github.com/googlesamples/android-architecture/tree/todo-mvp-rxjava), this pattern was choosed to make the business logic easily testable.
- If you have any doubts or to get the lastest build on fabric you can email me(<lyra.gibran@gmail.com>), you can grab the apk [here](https://github.com/GibranLyra/amaroTest/blob/master/app-debug.apk).

##Some Notes

- I created a exclusive module exclusively to demonstrate a way to let the service be tottaly decoupled from the app. That helps when the service is the same in multiple front ends.
- Made one unit test and one instrumented test to show how with this architecture we make things easier to test.

## Used Libraries
- [Retrofit](http://square.github.io/retrofit)
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [Timber](https://github.com/JakeWharton/timber)
- [Glide](https://github.com/bumptech/glide)
- [JodaTimeAndroid](https://github.com/dlew/joda-time-android)
- JUnit
- [Espresso](https://developer.android.com/training/testing/espresso/index.html)
 
 # Tests locations
 - Unit tests under [test folder](https://github.com/GibranLyra/amaroTest/tree/master/app/src/test/java/com/example/gibranlyra/amarotest).
 - Instrumentation test under [androidtests folder](https://github.com/GibranLyra/amaroTest/blob/master/app/src/androidTest/java/com/example/gibranlyra/amarotest/HomeScreenTest.java).

License
==========

Copyright (c) pakoito 2015

GNU GENERAL PUBLIC LICENSE

Version 3, 29 June 2007

See LICENSE.md
