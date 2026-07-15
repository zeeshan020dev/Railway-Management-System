plugins {
    alias(libs.plugins.android.application)
    // Firebase ke liye Google Services plugin add kiya
    id("com.google.gms.google-services")
}

android {
    namespace = "com.superior.railwayapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.superior.railwayapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Java 11 use kar rahe hain compatibility ke liye
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Default libraries jo pehle se thi
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // RecyclerView - list dikhane ke liye (trains, routes)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // CardView - card style UI ke liye
    implementation("androidx.cardview:cardview:1.0.0")

    // Firebase Authentication - login/register ke liye
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // Firebase Realtime Database - trains/routes ka data store karne ke liye
    implementation("com.google.firebase:firebase-database:20.3.1")

    // Firebase Firestore - bookings store karne ke liye
    implementation("com.google.firebase:firebase-firestore:24.10.3")

    // iText PDF - ticket PDF generate karne ke liye
    implementation("com.itextpdf:itext7-core:7.2.5")

    // Glide - images load karne ke liye
    implementation("com.github.bumptech.glide:glide:4.16.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}