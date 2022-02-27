<h1 align="center">Eazy Learn <br> [the link]</h1>

<h2 align="center">Swagger</h2>
<hr>

**Swagger UI page** with API representation is available [here](http://localhost:8080/swagger-ui/index.html) after app starts up. <br>
**JSON API representation** is available [here](http://localhost:8080/swagger-ui/index.html)
after app starts up. <br>
Instructions about how to start the app up are placed in the **Docker section**.


<h2 align="center">Docker</h2>
<hr>

1. Go to the root project folder.
So your path will look like something that: `../EazyLearn`.
2. Run `docker-compose up` command in order to start up containers based on `docker-compose.yml` file.
3. Run `docker-compose down` in order to stop containers and remove them, networks, volumes, and images created by `up`.
4. After pull of new changes from the server you should remove outdated data and create new docker images:
    * Remove outdated images using `docker rmi` command.
    * Remove outdated containers using `docker rm` command.
    * Run `docker-compose up` command.

Make sure that the following ports are free:
 * 5432 - Postgres;
 * 8080 - Spring boot application;
 * 5032, 5005 - Report-manager.
 

<h2 align="center">Database and domain model</h2>
<hr>

[Database schema](https://dbdiagram.io/d/613ce070825b5b0146fd85d9)



<h2 align="center">Postman API collection </h2>
<hr>
// todo



<h2 align="center">Getting started with development</h2>
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