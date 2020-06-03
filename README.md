# Simple-Stack FTUE Sample

Sample code using [simple-stack](https://github.com/Zhuinden/simple-stack/) to display "First-Time User Experience", based on the ["Conditional Navigation" section of Jetpack Navigation documentation](https://developer.android.com/guide/navigation/navigation-conditional#first-time_user_experience).

Please beware that the original FTUE approach outlined in the documentation of Jetpack Navigation has various anti-patterns.

None of those anti-patterns are relevant in this sample (unlike in [the Jetpack Navigation FTUE sample](https://github.com/Zhuinden/jetpack-navigation-ftue-sample/)), as `simple-stack` doesn't try to guide you to your doom. 

For example, Simple-Stack already allows navigating inside "ViewModels" directly. And you would never create "ViewModels" as global services. 

The sample also features an Espresso test. People like Espresso tests, so I added one.