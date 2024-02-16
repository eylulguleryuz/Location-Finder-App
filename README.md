# Location Finder App

An app that finds a user's location given by a MAC address.
Please also refer to the backend: https://github.com/eylulguleryuz/Location-Finder-App-Backend

## Technical Information

This project was built on Android Studio and targeted for Android 13. 

### Usage

The app consists of 6 screens. 

The first fragment is the main page where the user can see a grid of measurement points acquired from database tables. 
```
0 (red) - no measurements 
1 (green) there are measurements
```
![Screenshot 2024-02-15 215043](https://github.com/eylulguleryuz/Location-Finder-App/assets/20710032/d0172799-6c66-4228-b875-0f991b572984)


Users can navigate to the user list:

![Screenshot 2024-02-15 215131](https://github.com/eylulguleryuz/Location-Finder-App/assets/20710032/7e975307-af56-47a7-97a6-0862f8df8a3d)
```
Here, a user can be edited, deleted, or located. 
```

Locating a user:

![Screenshot 2024-02-15 215255](https://github.com/eylulguleryuz/Location-Finder-App/assets/20710032/7dfe47fb-9d34-4c8f-8394-78935eead209)
```
Once the button is pressed, the location grid shows up again and marks the location of the user.
The location of the user is calculated by the **nearest neighbor search method**.
```

Fragments for adding a user(top) and signal list(bottom):

![Screenshot 2024-02-15 215157](https://github.com/eylulguleryuz/Location-Finder-App/assets/20710032/f6ccb145-e3c8-454e-9017-d916a22abe1a)
![Screenshot 2024-02-15 215225](https://github.com/eylulguleryuz/Location-Finder-App/assets/20710032/0935a4e5-a7ca-4fe8-988a-eecbd65b4025)


## Local Components
```
The local **Room** database is used to store:
  • user-specific data (MAC address, signal strengths);
  • cache of signal strength map that is available from the remote database.
```
```
LiveData is used so that Room queries do not run on the main thread;
ViewModel is used for UI data to survive configuration changes and share data between fragments.
```
```
Material design navigation;
RecyclerViews for lists;
Action bar;
Floating action button.
```

## Remote Components

A REST API service that provides the app with data was created with Retrofit. The data is retrieved only once per lifetime of the app.
Please also refer to the backend: https://github.com/eylulguleryuz/Location-Finder-App-Backend where SpringBoot is used to establish the connection to the remote database.

