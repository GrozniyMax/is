plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.14.2"
    id("org.openapi.generator") version "7.8.0"

}

group = "com.maxim.is"
version = "0.0.1-SNAPSHOT"
description = "lab2"

openApiGenerate {
    generatorName.set("spring")
    library.set("spring-boot")
    generateApiTests.set(false)
    generateApiDocumentation.set(false) // Отключаем генерацию документации если не нужна

    inputSpec.set("$rootDir/../contract.yaml")
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    apiPackage.set("com.maxim.is.generated.openapi.api")
    modelPackage.set("com.maxim.is.generated.dto")
    invokerPackage.set("com.maxim.is.generated.openapi.invoker")

    configOptions.set(
        mapOf(
            "useBeanValidation" to "true",
            "useSpringBoot3" to "true",
            "interfaceOnly" to "true",
            "useTags" to "true",
            "skipDefaultInterface" to "true",
            "openApiNullable" to "false",
            "serializationLibrary" to "jackson",
            "requestMappingModel" to "api_interfaces"
        )
    )
}

tasks {
    compileJava.get().dependsOn(openApiGenerate)
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //DB
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.hibernate.orm:hibernate-spatial:6.6.26.Final")
    implementation("org.hibernate:hibernate-jcache:6.6.26.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.ehcache:ehcache:3.10.8")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.7")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // S3
    implementation("io.minio:minio:8.6.0")

    //Mapstruct
    implementation("org.mapstruct:mapstruct:1.6.3") // Use the latest stable version
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3") // Use the same version

    // Others
    implementation("io.swagger.core.v3:swagger-annotations:2.2.18")

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
            srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
        }
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
