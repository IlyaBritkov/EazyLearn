<h1 align="center">Eazy Learn <br> [the link]</h1>

<h2 align="center">Swagger</h2>
// todo

<h2 align="center">Database and domain model</h2>

[Database schema](https://dbdiagram.io/d/613ce070825b5b0146fd85d9)

<h2 align="center">Postman API collection </h2>
// todo

<h2 align="center">Getting started</h2>
<hr>

<h3 align="center">System and environment requirements</h3>
1. [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html).
Set `JAVA_HOME` environment variable.
2. [Maven](https://maven.apache.org/download.cgi).
3. [Docker](https://www.docker.com/).
<hr>

- Use `mvn clean package` command to **build** the project. <br>
- Being in the root folder run `docker-compose up` command
to **start up** docker containers containing *spring boot application*
along with *PostgreSQL instance*.
Or use the [IDE tools](https://www.jetbrains.com/help/idea/docker.html#managing-images)
for Docker usage.<br>
**Port 8080** is listened for spring boot app. <br>
**Port 5453** is listened for PostgreSQL instance. <br>
- Being in the root folder run `docker-compose down` command
to **stop** docker containers.
<br>

Database, tables and all functional elements are *automatically
created and updated* by [Flyway](https://flywaydb.org/) when application is started. <br>
Use `mvn flyway:migrate`, if you want to perform database migration *manually*. <br>
If something in the database (table, column definition, etc.)
is **getting change**, all changes should be placed as a new sql-file
in the `resource/db/migration` folder according to the [Flyway](https://flywaydb.org/) rules.