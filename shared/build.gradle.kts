
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(libs.mvvm.core)
            export(libs.mvvm.flow)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here

            implementation(libs.mvvm.core)
            implementation(libs.mvvm.flow)
        }

        androidMain.dependencies {
            api(libs.mvvm.core)
            api(libs.mvvm.flow)
            api(libs.mvvm.flow.compose)
        }

        iosMain.dependencies {
            api(libs.mvvm.core)
            api(libs.mvvm.flow)
        }
    }
}

android {
    namespace = "com.codingambitions.kmpappsharedviewmodel.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
