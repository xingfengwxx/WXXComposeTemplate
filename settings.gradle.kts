pluginManagement {
    repositories {

        // ✅ 阿里云镜像仓库（加速 Gradle 插件下载）
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/jcenter")
        }

        maven {
            url = uri("https://jitpack.io")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // ✅ 阿里云镜像仓库（用于依赖下载）
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/jcenter")
        }
        maven {
            url = uri("https://jitpack.io")
        }

        google()
        mavenCentral()
    }
}

rootProject.name = "WXXComposeTemplate"
include(":app")
 