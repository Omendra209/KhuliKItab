plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.padho"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.padho"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-storage")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("androidx.media3:media3-datasource:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")
    implementation ("androidx.media3:media3-session:1.1.1")
    implementation ("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-common:1.1.1")

    implementation("com.google.firebase:firebase-ml-natural-language:22.0.1")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation("com.google.android.gms:play-services-mlkit-language-id:17.0.0")
}
