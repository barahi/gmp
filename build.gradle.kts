// build.gradle.kts

plugins {
    java
    id("org.flywaydb.flyway") version "9.22.3"
}

group = "org.barahi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq:3.18.7")
    implementation("com.h2database:h2:2.2.220")
    implementation("org.flywaydb:flyway-core:9.22.3")
    implementation("com.google.inject:guice:7.0.0")

    // Jersey dependencies
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:3.1.3")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.3")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:3.1.3")

    // Jackson dependencies
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")

    // Add jOOQ code generation dependencies
    implementation("org.jooq:jooq-meta:3.18.7")
    implementation("org.jooq:jooq-codegen:3.18.7")
}

val dbUrl = "jdbc:h2:file:${projectDir}/build/db/gmp;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE"
val dbUser = "sa"
val dbPassword = ""

// Initialize H2 database directory
tasks.register("initH2") {
    doFirst {
        mkdir("build/db")
    }
}

// Task to clean the database before migrations
tasks.register<JavaExec>("cleanDb") {
    dependsOn("initH2")
    mainClass.set("org.h2.tools.RunScript")
    classpath = configurations.runtimeClasspath.get()

    args(
        "-url", dbUrl,
        "-user", dbUser,
        "-password", dbPassword,
        "-script", "${projectDir}/src/main/resources/db/clean-db.sql"
    )

    doFirst {
        file("${projectDir}/src/main/resources/db/clean-db.sql").writeText("""
            DROP ALL OBJECTS;
            SET DB_CLOSE_DELAY -1;
        """.trimIndent())
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

// Configure source sets to include generated code
sourceSets {
    main {
        java {
            srcDirs("src/main/java", "build/generated-src/jooq/main")
        }
    }
}

flyway {
    url = dbUrl
    user = dbUser
    password = dbPassword
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

// Define jOOQ code generation task
tasks.register<JavaExec>("generateJooq") {
    dependsOn("flywayMigrate")
    mainClass.set("org.jooq.codegen.GenerationTool")
    classpath = configurations.runtimeClasspath.get()

    val jooqConfigFile = file("${buildDir}/tmp/jooq-config.xml")
    inputs.files(fileTree("src/main/resources/db/migration"))
    outputs.dir("build/generated-src/jooq/main")

    doFirst {
        jooqConfigFile.parentFile.mkdirs()
        jooqConfigFile.writeText("""
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <configuration xmlns="http://www.jooq.org/xsd/jooq-codegen-3.18.0.xsd">
                <jdbc>
                    <driver>org.h2.Driver</driver>
                    <url>${dbUrl}</url>
                    <user>${dbUser}</user>
                    <password>${dbPassword}</password>
                </jdbc>
                <generator>
                    <name>org.jooq.codegen.JavaGenerator</name>
                    <database>
                        <name>org.jooq.meta.h2.H2Database</name>
                        <inputSchema>PUBLIC</inputSchema>
                        <includes>.*</includes>
                        <excludes>flyway_schema_history</excludes>
                    </database>
                    <generate>
                        <deprecated>false</deprecated>
                        <records>true</records>
                        <immutablePojos>true</immutablePojos>
                        <fluentSetters>true</fluentSetters>
                        <javaTimeTypes>true</javaTimeTypes>
                    </generate>
                    <target>
                        <packageName>org.barahi.generated</packageName>
                        <directory>build/generated-src/jooq/main</directory>
                        <clean>true</clean>
                    </target>
                </generator>
            </configuration>
        """.trimIndent())
    }

    args(jooqConfigFile)
}

tasks.named("compileJava") {
    dependsOn("generateJooq")
}

tasks.named("flywayMigrate") {
    dependsOn("cleanDb")
}
