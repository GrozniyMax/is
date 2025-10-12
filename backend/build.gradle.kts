import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.14.2"
    id("org.openapi.generator") version "7.8.0"

}

group = "com.maxim"
version = "0.0.1-SNAPSHOT"
description = "lab2"

openApiGenerate {
    generatorName.set("spring")
    skipOperationExample.set(true)

    globalProperties.apply {
        put("modelDocs", "true")
        put("generateSupportingFiles", "true")
    }

    configOptions.apply {
        put("useBeanValidation", "true")
        put("modelMutable", "false")
        put("gradleBuildFile", "false")
        put("interfaceOnly", "true")
        put("serializationLibrary", "jackson")
        put("enumPropertyNaming", "UPPERCASE")
        put("useSpringBoot3", "true")
        put("useTags", "true")

        // Опции, характерные только для Java
        put("hideGenerationTimestamp", "true")
        put("sourceFolder", "src/gen/java")
        put("library", "spring-boot")
    }

    inputSpec.set("${layout.projectDirectory}/../contract.yaml")
    outputDir.set("${layout.buildDirectory.asFile.get()}/generated/openapi")

    val packageString = "com.maxim.api"
    packageName.set(packageString)
    apiPackage.set("$packageString.api")
    invokerPackage.set("$packageString.invoker")
    modelPackage.set("$packageString.model")
}


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
    archiveFileName.set("lab2.jar")
    manifest {
        attributes["Main-Class"] = "com.maxim.lab1.Lab1Application"
    }
}

sourceSets {
    main {
        java {
            // подключаем путь, где лежит сгенерированный код OpenAPI
            srcDir("$buildDir/generated/openapi/src/gen/java")
        }
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
