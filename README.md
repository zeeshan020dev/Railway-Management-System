# Railway Management System

[![Android](https://img.shields.io/badge/Android-24%2B-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Gradle%20Kotlin-DSL-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Firebase](https://img.shields.io/badge/Firebase-Backend%20Ready-FFCA28?logo=firebase&logoColor=black)](https://firebase.google.com/)
[![Java](https://img.shields.io/badge/Java-11-007396?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)

Modern Android railway booking app for browsing trains, selecting seats, confirming reservations, and generating tickets.

## Overview

Railway Management System is an Android application built for a streamlined train booking workflow. The app supports user authentication, train and route browsing, seat selection, booking confirmation, ticket generation, and booking history management.

## Key Features

- User registration and login
- Splash screen and guided navigation flow
- Train and route listing with adapter-based UI
- Seat selection and booking confirmation
- Ticket generation in PDF format
- My Bookings screen for viewing past reservations
- Firebase integration for authentication and data storage
- RecyclerView and CardView based modern Android UI

## Tech Stack

- Android SDK 36
- Java 11
- Gradle Kotlin DSL
- Firebase Authentication
- Firebase Realtime Database
- Firebase Firestore
- iText PDF
- Glide
- RecyclerView, CardView, Material Components

## Project Structure

- app/src/main/java/com/superior/railwayapp/ - Activities, adapters, and models
- app/src/main/res/layout/ - Screen layouts and reusable item views
- app/google-services.json - Firebase configuration
- app/proguard-rules.pro - Release build rules

## Screens

- Splash Screen
- Login
- Register
- Home
- Train List
- Class Selection
- Seat Selection
- Booking Confirmation
- Ticket
- My Bookings

## Prerequisites

- Android Studio Hedgehog or newer
- JDK 11
- Android SDK 36
- A Firebase project connected to the app

## Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle and wait for dependency download.
4. Replace app/google-services.json with your own Firebase configuration if needed.
5. Run the app on an emulator or device.

## Firebase Configuration

The app uses Firebase for authentication and data storage. Make sure your Firebase project has the required services enabled and that the package name matches the Android application ID:

- Application ID: com.superior.railwayapp
- Namespace: com.superior.railwayapp

## Permissions

The app requests the following permissions where needed:

- Internet access for Firebase connectivity
- External storage access for ticket PDF handling on older Android versions

## Notes

- The app currently targets Android SDK 36 and uses a minimum SDK of 24.
- Release builds are configured with ProGuard rules in app/proguard-rules.pro.
- MainActivity remains in the manifest but is not the launcher activity.

## License

No license file is currently included. Add one before publishing or distributing the project.

