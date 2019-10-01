plugins {
    java
    application
    jacoco
}

repositories {
    jcenter()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "org.javacv.Main"
}

jacoco {
    toolVersion = "0.8.4"
}

dependencies {
        //JavaCV and more
    implementation("org.bytedeco:javacv:1.4")
    implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3")
    implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3:windows-x86_64")
    implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3:macosx-x86_64")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.google.code.gson:gson:2.8.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:27.0.1-jre")

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}

tasks {
    "wrapper"(Wrapper::class) {
        gradleVersion = "5.4"
    }
}