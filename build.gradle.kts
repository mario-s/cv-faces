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
    implementation("com.google.guava:guava:27.0.1-jre")

    // Use JUnit test framework
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.2")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.2")
}

tasks {
    wrapper {
        gradleVersion = "5.6.2"
    }

    test {
        useJUnitPlatform()
        testLogging.showExceptions = true
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

    processResources {
        dependsOn("copyTrainToMain")
    }
    
    register<Copy>("copyTrainToMain") {
        from("$rootDir/data/train")
        include("*.*")
        into("$buildDir/resources/main/org/javacv/train")
    }
}
