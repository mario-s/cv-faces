description = "detect module"

plugins {
    id("application")
}

application {
    mainClassName = "org.javacv.Main"
}

dependencies {
    implementation(project(":common"))

    annotationProcessor("info.picocli:picocli-codegen:4.0.4")
    implementation("info.picocli:picocli:4.0.4")
}

tasks {
    processResources {
        dependsOn("copyTrainToMain")
    }

    register<Copy>("copyTrainToMain") {
        from("$rootDir/data/train")
        include("*.*")
        into("$buildDir/resources/main/org/javacv/train")
    }
}