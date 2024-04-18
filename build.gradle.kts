plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.apache.flink:flink-streaming-java:1.19.0")
    compileOnly("org.apache.flink:flink-core:1.19.0")
    implementation("org.apache.flink:flink-clients:1.19.0")
    compileOnly("org.apache.flink:flink-table-api-java-bridge:1.19.0")
    compileOnly("org.apache.flink:flink-table-common:1.19.0")




}



tasks.test {
    useJUnitPlatform()
}
