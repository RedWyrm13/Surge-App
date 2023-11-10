# Surge-App
Surge is a ridesharing app that aims to charge riders a fair price based on the distance and the time of the trip. Surge will charge a monthly subscription to the drivers for using the app. Using the subscription based model, we aim to offer rates to passengers at costs much lower than Uber, Lyft and any other platforms out there.

The app is built in Kotlin using Android Studio, which can be downloaded [here](https://developer.android.com/studio).

[This](https://developer.android.com/courses/android-basics-compose/course) is a very helpful starting point if you are unfamiliar with Android Studio or Kotlin.

# APP LAYOUT

On create - The initial screen a user sees will be asking them to sign up or log in.

Sign up - The user will enter their information to create their account. Exact parameters of User class tbd. After account is created, they will go immediately to the main screen.

Log In - User will enter their account information. If the information does not match, an error message will appear. Once logged in the user will be directed to the main screen.

Main Screen - This will display a Google Maps view centered around the user's location. There will be a search bar at the top of the screen where the user can input their destination and pickup location. There will also be a settings/user profile button to allow them to make changes to their account and do other things of that nature. Once entering their destination and place of pickup into the search area at the top of the screen a pricing screen will be displayed.

User Settings - User can make changes to their account

Pricing Screen - This screen will show available or soon to be available drivers. Each driver will have an estimated arrival time and price associated with the trip. If the driver is selected, the driver will be able to accept or deny the trip. If the trip is denied by the driver, the user will be prompted to select another driver. Once a driver is found we move to the waiting for pickup screen.

Waiting for pickup - This will show a tracker for where the driver is along with an estimated wait time. Once the passengers are in the vehicle, the ride will start and the screen will move to the ride screen. If the driver cannot make the pickup and must cancel, the passenger will be cycled back to the pricing screen.

Ride Screen - The user will be able to see the ride progress including the distance and estimated time until reaching their destination. Once the ride is over, we move to the post ride screen.

Post Ride Screen - The passengers will rate the driver. After rating the driver, the app will display the main screen again.
