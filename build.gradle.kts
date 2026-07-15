// Top-level build file - yahan sari project level plugins define hoti hain
plugins {
    alias(libs.plugins.android.application) apply false

    // Firebase ke liye Google Services plugin - zaroor chahiye Firebase kaam karne ke liye
    id("com.google.gms.google-services") version "4.4.0" apply false
}