plugins {
	java
    jacoco
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "farrel"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	 implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation ("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation ("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.12.3")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly("org.projectlombok:lombok")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    classDirectories.setFrom(files(classDirectories.files.map {fileTree(it) { exclude("**/*Application**", "**/config/**", "**/filter/**") }
    }))
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = true
        csv.required.set(false)
        html.required = true
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}