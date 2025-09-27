import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.14.2"
    id("org.openapi.generator") version "7.4.0"

}

group = "com.maxim"
version = "0.0.1-SNAPSHOT"
description = "lab2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // starters
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //Postgres
    implementation("org.postgresql:postgresql:42.7.7")

    //Mapstruct
    implementation("org.mapstruct:mapstruct:1.6.3") // Use the latest stable version
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3") // Use the same version

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Jar> {
    enabled = true
    archiveFileName.set("lab1.jar")
    manifest {
        attributes["Main-Class"] = "com.maxim.lab1.Lab1Application"
    }
}

tasks.register<GenerateTask>("generateCleanModels") {
    generatorName.set("java")
    inputSpec.set("$rootDir/../contract.yaml")
    outputDir.set("$buildDir/temp-generated") // Генерируем во временную папку
    modelPackage.set("com.example.model")

    globalProperties.set(mapOf("models" to ""))
    configOptions.set(
        mapOf(
            "useBeanValidation" to "true",
            "openApiNullable" to "false"
        )
    )

    doLast {
        // Копируем только модели в финальную папку
        copy {
            from("$buildDir/temp-generated/src/main/java/com/example/model")
            into("$buildDir/generated/src/main/java/com/example/model")
        }
        // Удаляем временную папку со всем мусором
        delete("$buildDir/temp-generated")
    }
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main/java")
        }
    }
}

tasks.compileJava {
    dependsOn(tasks.named("generateCleanModels"))
}


tasks.withType<Test> {
    useJUnitPlatform()
}
