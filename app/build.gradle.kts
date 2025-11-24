plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.wangxingxing.wxxcomposetemplate"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.wangxingxing.wxxcomposetemplate"
        minSdk = 24
        targetSdk = 36
        versionCode = 100
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // Room schema export (可选，用于导出数据库 schema)
        // kapt {
        //     arguments {
        //         arg("room.schemaLocation", "$projectDir/schemas")
        //     }
        // }
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
        buildConfig = true
        compose = true
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
    includeCompileClasspath = false
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    
    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.navigation)
    
    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    
    // DataStore
    implementation(libs.androidx.datastore.preferences)
    
    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    
    // Startup
    implementation(libs.androidx.startup.runtime)
    
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    
    // Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.gson.core)
    
    // Image
    implementation(libs.coil.compose)
    // PictureSelector 基础 (必须)
    implementation(libs.picture.selector)
    // PictureSelector 图片压缩 (按需引入)
    implementation(libs.picture.selector.compress)
    // PictureSelector 图片裁剪 (按需引入)
    implementation(libs.picture.selector.ucrop)
    // PictureSelector 自定义相机 (按需引入)
    implementation(libs.picture.selector.camerax)
    
    // Media
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)
    
    // Chart
    implementation(libs.mpandroidchart.core)
    
    // UI
    implementation(libs.dsltablayout.core)
    implementation(libs.base.recyclerview.adapter.helper)
    implementation(libs.autosize.core)
    
    // Permission
    implementation(libs.xxpermissions.core)
    // Toaster
    implementation(libs.toaster)
    
    // Utils
    implementation(libs.utilcodex) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-android-extensions-runtime")
    }
    
    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}