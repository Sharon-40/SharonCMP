import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.21"

    id("app.cash.sqldelight") version "2.0.1"

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
//            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            // koin di
            implementation("io.ktor:ktor-client-android:2.3.7")
            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-android:3.4.3")

            // sqldelight
            implementation("app.cash.sqldelight:android-driver:2.0.1")


        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            // kamel image loading
            implementation("media.kamel:kamel-image:0.9.1")

            // ktor client
            implementation("io.ktor:ktor-client-core:2.3.7")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

            // precompose
            api("moe.tlaster:precompose:1.5.10")
            api("moe.tlaster:precompose-viewmodel:1.5.10")
            api("moe.tlaster:precompose-koin:1.5.10")

            // koin
            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-compose:1.1.0")

            // sqldelight ext
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

        }
        iosMain.dependencies {
            // darwin ktor client for ios
            implementation("io.ktor:ktor-client-darwin:2.3.7")

            // sql delight ios driver
            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }
    }

    sqldelight{
        databases{
            create("AppDatabase"){
                packageName.set("app_db")
                srcDirs("src/commonMain/sqldelight")
            }
        }
    }

}

android {
    namespace = "com.gaur.himanshu"
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.gaur.himanshu"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
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
}

