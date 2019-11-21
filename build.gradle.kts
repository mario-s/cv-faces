import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    java
    application
    jacoco

    id("com.adarshr.test-logger").version("1.7.0")
}

repositories {
    jcenter()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "org.javacv.Main"
}

jacoco {
    toolVersion = "0.8.4"
}

fun os(): String {
    val families = listOf(Os.FAMILY_WINDOWS, Os.FAMILY_MAC, Os.FAMILY_UNIX)
    val family = families.first { Os.isFamily(it) };
    return when (family) {
        Os.FAMILY_MAC -> "${family}osx-x86_64"
        else -> "${family}-x86_64"
    }
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen:4.0.4")
    implementation("info.picocli:picocli:4.0.4")

    implementation("org.bytedeco:javacv:1.4")
    implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3")
    implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3:${os()}")

    implementation("com.google.code.gson:gson:2.8.1")
    implementation("com.google.guava:guava:27.0.1-jre")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.2")
}

tasks {
    wrapper {
        gradleVersion = "5.6.4"
    }

    test {
        useJUnitPlatform()
        testLogging.showExceptions = true
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule { limit { minimum = BigDecimal(ext.get("min.coverage").toString()) } }
        }
    }

    testlogger {
        setTheme("mocha-parallel")
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
