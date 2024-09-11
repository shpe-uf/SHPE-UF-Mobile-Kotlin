import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.apollographql.apollo3").version("4.0.0-beta.4")
    id("com.google.devtools.ksp")
}

android {

    namespace = "com.example.shpe_uf_mobile_kotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shpe_uf_mobile_kotlin"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        val localPropertiesFile = rootProject.file("local.properties")
        val localProperties = Properties()

        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }


        buildConfigField("String", "CALENDAR_ID", "\"${localProperties["calendar_id"]}\"")
        buildConfigField("String", "CALENDAR_API_KEY", "\"${localProperties["CALENDAR_API_KEY"]}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    implementation("androidx.datastore:datastore-core-android:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core-jvm:1.1.1")
    val nav_version = "2.8.0"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    ksp( "androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.fragment:fragment-ktx:1.5.6")

//    val nav_version = "2.7.7"
//
//    // Jetpack Compose integration
//    implementation("androidx.navigation:navigation-compose:$nav_version")



    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material:1.6.8")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.auth:google-auth-library-oauth2-http:1.19.0")
    implementation ("com.google.api-client:google-api-client:1.32.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Might remove in the future.
    implementation ("com.google.accompanist:accompanist-pager:0.27.1")

    // Used for Jetpack Compose's new Paging library.
    implementation("androidx.compose.ui:ui-util:$version")
    implementation("androidx.compose.ui:ui:$version")

    // THIS NEEDS TO BE MERGED CANNOT BE LEFT OUT, KEEPS ON DISAPPEARING RANDOMLY
    implementation("com.apollographql.apollo3:apollo-runtime:4.0.0-beta.4")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")

}


apollo {
    service("service") {
        packageName.set("com.example.shpe_uf_mobile_kotlin")
    }
}
