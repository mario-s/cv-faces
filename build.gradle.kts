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
    testImplementation("junit:junit:4.12")
}

tasks {
    wrapper {
        gradleVersion = "5.6.2"
    }
    register<Copy>("copy") {
        description = "Copy training data to resource folders."
        val target = "$buildDir/resources/%s/org/javacv/train"
        from("$rootDir/data/train")
        include("*.*")
        into(String.format("$target", "main"))
        into(String.format("$target", "test"))
    }
}
