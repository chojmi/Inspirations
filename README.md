# Inspirations
![](https://circleci.com/gh/kix3000/Inspirations.svg?style=shield&circle-token=:circle-token)

Inspirations is an app which uses Flickr API for browsing pictures. It enables basic actions like commenting pictures, adding and removing from favourites or searching pictures by typed query.

## Google Play
App is available here: <https://play.google.com/store/apps/details?id=com.github.chojmi.inspirations>

## Architecture
During implementation of the app I was inspired by clean architecture described in <https://github.com/android10/Android-CleanArchitecture>. I divided the app on 3 modules which everyone is responsible for other layer of architecture. I applied MVP pattern and used reactive approach with RxJava 2. Also I used dependency injection with Dagger 2. 

## Things to do
 - Handle request fails by displaying dialogs with info
 - Improve visual site of the app
 - Cover more code with tests
 - Add feature of sharing pictures with the app
 - Implements groups, albums

## Other remarks
Main purpose to write this app was my education. I wanted to expand my knowledge of unit and instrumental tests, RxJava, Dagger, MVP pattern and other things which I used during implementation. 

Unfortunately my time was limited, so I wasn't able to cover much code with tests, especially in presentation module. But some classes are tested also there. Also some fails are not handled, especially no info is delivered to the user when the action wasn't successfully invoked.

Also clearly visible is that impact of designer or graphic would be useful. I didn't have much time to spend on this field so unfortunately app isn't looking very good. :)
