plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.secret.agentchat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.secret.agentchat"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.interceptor)
    implementation(libs.crypto)
    implementation(libs.bouncycastle)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.compose)
    implementation(libs.androidx.datastore)
    implementation(libs.kotlin.serialization.json)

    testImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.engine)
    testImplementation(libs.junit5.params)
    androidTestImplementation(libs.junit5.android.test.compose)

    testImplementation(libs.mockk)
    testImplementation(libs.mockk)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
}