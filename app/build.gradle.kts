plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.forecastmvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.forecastmvvm"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
val kotlinVersion: String by rootProject.extra
val roomVersion: String by rootProject.extra
val navigationVersion: String by rootProject.extra
val kodeinVersion: String by rootProject.extra
val lifecycleVersion: String by rootProject.extra
val retrofitVersion: String by rootProject.extra

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Navigation

    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Kotlin Android Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")

    // Kodein
    implementation("org.kodein.di:kodein-di-generic-jvm:$kodeinVersion")
    implementation("org.kodein.di:kodein-di-framework-android-x:$kodeinVersion")

    // Better dateTime-time support even on older Android versions
    implementation("com.jakewharton.threetenabp:threetenabp:1.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.8.0")
    kapt("com.github.bumptech.glide:compiler:4.8.0")

    // Groupie RecyclerView
    implementation("com.github.lisawray.groupie:groupie:2.10.1")


    // Preference
    implementation("androidx.preference:preference:1.2.1")

    // WeatherLocation
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // New Material Design
    implementation("com.google.android.material:material:1.12.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.squareup:javapoet:1.13.0")
}
