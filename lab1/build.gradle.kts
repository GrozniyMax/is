plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.14.2" // Use the latest version

}

group = "com.maxim"
version = "0.0.1-SNAPSHOT"
description = "lab1"

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

tasks.withType<Test> {
    useJUnitPlatform()
}
