import org.apache.tools.ant.taskdefs.condition.Os
import java.lang.UnsupportedOperationException

description = "computer vision with OpenCV by using Java"

plugins {
    java
    jacoco

    id("com.adarshr.test-logger").version("2.0.0")
    id("org.owasp.dependencycheck").version("5.2.1")
    id("com.github.spotbugs").version("3.0.0")
}

allprojects {
    repositories {
        jcenter()
    }
}

subprojects {
    apply(plugin= "java")
    apply(plugin= "jacoco")
    apply(plugin= "com.adarshr.test-logger")
    apply(plugin= "org.owasp.dependencycheck")
    apply(plugin= "com.github.spotbugs")

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    jacoco {
        toolVersion = "0.8.4"
    }

    spotbugs {
        toolVersion = "4.1.4"
        effort = "min"
        reportLevel = "high"
    }

    fun os(): String {
        val arch = System.getenv("sun.arch.data.model")
        val families = listOf(Os.FAMILY_MAC, Os.FAMILY_UNIX, Os.FAMILY_WINDOWS)
        return when (val family = families.firstOrNull { Os.isFamily(it) }) {
            Os.FAMILY_UNIX -> "linux-${arch}"
            Os.FAMILY_MAC -> "${family}osx-x86_64"
            Os.FAMILY_WINDOWS -> "${family}-${arch}"
            else -> throw UnsupportedOperationException("OS is unsupported")
        }
    }

    dependencies {

        implementation("org.bytedeco:javacv:1.4")
        implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3")
        implementation("org.bytedeco.javacpp-presets:opencv:3.4.3-1.4.3:${os()}")

        implementation("com.google.guava:guava:27.0.1-jre")

        implementation("ch.qos.logback:logback-classic:1.2.3")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.2")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.2")
        testImplementation("org.mockito:mockito-core:3.1.0")
        testImplementation("org.mockito:mockito-junit-jupiter:3.1.0")
    }

    tasks {

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
            setSlowThreshold(5000)
        }

        check {
            dependsOn(jacocoTestCoverageVerification)
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "6.7.1"
    }
}