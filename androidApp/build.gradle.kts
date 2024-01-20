plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.incture.kmp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    namespace = "com.incture.kmp"
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":composeApp"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.runtime:runtime:1.4.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.0")


    // SAP Cloud Android SDK dependencies
    implementation("com.sap.cloud.android:foundation:7.1.0")
    implementation("com.sap.cloud.android:foundation-app-security:7.1.0")
    implementation("com.sap.cloud.android:onboarding-compose:7.1.0")
    implementation("com.sap.cloud.android:flows-compose:7.1.0")
    implementation("com.sap.cloud.android:fiori-composable-theme:7.1.0")
    implementation("com.sap.cloud.android:fiori-compose-ui:7.1.0")
    implementation("com.sap.cloud.android:permission-request-tracker:7.1.0")

    // Android Architecture Components
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.paging:paging-common-ktx:3.0.0")
    implementation("androidx.paging:paging-compose:1.0.0-alpha16")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    implementation("androidx.work:work-runtime-ktx:2.6.0")
    implementation("com.google.guava:guava:27.0.1-android")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}