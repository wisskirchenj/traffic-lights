plugins {
    application
}

application {
    mainClass.set("de.cofinpro.trafficlights.TrafficLightsApp")
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
}

configurations["compileOnly"]
    .extendsFrom(configurations["annotationProcessor"])

group = "de.cofinpro"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.22.1")

    val lombokVersion = "1.18.32"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.10.0")
    testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}