# GMP (Game Management Platform)

Small Java REST API using Jersey + Grizzly, backed by an embedded H2 database and jOOQ for generated types.
Used for managing simple-game operations.

Key points
- Java 21
- Gradle (Kotlin DSL)
- Jersey (JAX-RS) with Grizzly HTTP server
- Jackson for JSON
- H2 embedded database with Flyway migrations
- jOOQ code generation (task included)

Quick summary
- Main class: `org.barahi.ApiServer` (starts Grizzly server at `http://localhost:8080/`)
- Default DB: H2 file at `build/db/gmp`

## Requirements
- JDK 21
- Gradle (wrapper provided) — use `./gradlew` on macOS/Linux

## Recommended: SDKMAN for managing Java and Gradle versions
If you want an easy way to install and switch between JVMs and Gradle versions on macOS (zsh) or Linux, consider using [SDKMAN!](https://sdkman.io/).

> Note: this README intentionally does not include installation instructions for SDKMAN — see the linked site for install and usage details. The project includes a Gradle wrapper (`./gradlew`) so you don't strictly need a system-wide Gradle installation.

## Build
From the project root run:

```bash
# build the project (runs Flyway migrations and jOOQ generation)
./gradlew build
```

Notes:
- The `generateJooq` task depends on `flywayMigrate` which runs Flyway migrations against the local H2 DB located at `build/db/gmp`.
- The `cleanDb` task rewrites and executes `src/main/resources/db/clean-db.sql` to reset the database before migrations.

## Run (development)
Start the server directly from the command line (uses the classpath built by Gradle):

```bash
# run the ApiServer main class
./gradlew run --no-daemon -q -PmainClass=org.barahi.ApiServer
```

Alternatively, run the packaged jar (if you build with `./gradlew jar`):

```bash
# build and run the jar
./gradlew jar
java -cp build/libs/gmp-1.0-SNAPSHOT.jar org.barahi.ApiServer
```

The server listens at:

```
http://localhost:8080/
```

Hit Ctrl-C (or send EOF / press Enter depending on how you started it) to stop the server.

## Database
- The project uses H2 with the JDBC URL configured in `build.gradle.kts`:

```
jdbc:h2:file:${projectDir}/build/db/gmp;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE
```

- Flyway migrations are stored at `src/main/resources/db/migration` and run automatically by the Gradle tasks wired in `build.gradle.kts`.

### Flyway migration filenames and versioning
Flyway determines migration order and type from the migration filenames. A few common patterns:

- Versioned migrations: `V<version>__<description>.sql` (examples: `V1__initialize_tables.sql`, `V2__add_player_column.sql`)

Important notes:
- Use a double underscore (`__`) between the version and the description.
- Descriptions should be readable (underscores are common) and avoid spaces.

See Flyway's docs for the full naming/versioning rules and examples:
https://flywaydb.org/documentation/concepts/migrations#naming-and-versioning

- To reset the database and re-run migrations manually:

```bash
./gradlew cleanDb flywayMigrate
```

## API Endpoints
The project registers the following resource classes in `ApiServer`:
- `org.barahi.server.resource.DummyResource` — a placeholder/example resource
- `org.barahi.server.resource.PlayerResource` — player-related endpoints

Look at `src/main/java/org/barahi/server/resource` for specific endpoints and payload shapes. The player API uses the `org.barahi.serviceapi.player.Player` model and `PlayerSerializer`/`PlayerJson` for JSON mapping.

## Code generation (jOOQ)
- The `generateJooq` task will run after `flywayMigrate` to generate jOOQ classes into `build/generated-src/jooq/main`.
- The main `compileJava` depends on `generateJooq` so `./gradlew build` will produce generated sources automatically.

## Testing
There are no unit tests included by default. You can add tests under `src/test/java` and run them with:

```bash
./gradlew test
```

## Development notes
- Java compatibility is set to 21 in `build.gradle.kts`.
- Guice is used for dependency injection across binder classes in `org.barahi.*.Binder` packages.
- The project currently prints a startup message and waits for stdin to stop. Running from a non-interactive environment may require sending EOF or using a different run target.

## Troubleshooting
- If Flyway migrations fail, inspect `src/main/resources/db/migration` and the H2 database files under `build/db/`.
- If jOOQ generation fails, ensure migrations created the expected schema and that H2 is accessible at the configured URL.
- If you get Java version errors, make sure your `java -version` points to JDK 21.
