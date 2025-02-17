plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.wan616.utils"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("consumer-rules.pro")
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = project.properties["GROUP_ID"] as String
                artifactId = project.properties["ARTIFACT_ID"] as String
                version = project.properties["VERSION_NAME"] as String

                pom {
                    name.set(project.properties["POM_NAME"] as String)
                    description.set(project.properties["POM_DESCRIPTION"] as String)
                    url.set(project.properties["POM_URL"] as String)
                    packaging = project.properties["POM_PACKAGING"] as String

                    //configure licenses
                    licenses {
                        license {
                            name.set(project.properties["POM_LICENCE_NAME"] as String)
                            url.set(project.properties["POM_LICENCE_URL"] as String)
                        }
                    }

                    //configure developers
                    developers {
                        developer {
                            id.set(project.properties["POM_DEVELOPER_ID"] as String)
                            name.set(project.properties["POM_DEVELOPER_NAME"] as String)
                            email.set(project.properties["POM_DEVELOPER_EMAIL"] as String)
                            url.set(project.properties["POM_DEVELOPER_URL"] as String)
                        }
                    }

                    //scm information
                    scm {
                        url.set(project.properties["POM_SCM_URL"] as String)
                        connection.set(project.properties["POM_SCM_CONNECTION"] as String)
                        developerConnection.set(project.properties["POM_SCM_DEV_CONNECTION"] as String)
                    }

                    //configure organization
                    organization {
                        name.set(project.properties["POM_ORGANIZATION_NAME"] as String)
                        url.set(project.properties["POM_ORGANIZATION_URL"] as String)
                    }

                }
            }
        }

        repositories {
            maven {
                name = "ossrh"
//                url = uri(project.properties["RELEASE_REPOSITORY_URL"] as String)
                url = uri(project.properties["SNAPSHOT_REPOSITORY_URL"] as String)
                credentials {
                    username = project.properties["ossrhUsername"] as String
                    password = project.properties["ossrhPassword"] as String
                }
            }
        }
    }

    signing {
        sign(publishing.publications["release"])
    }
}