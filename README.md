# Mental Health Tracking System (JSP)

A small web app built with JSP, a Servlet and JDBC (MySQL). It is the web version of the Swing patient tracker from the Swing and JDBC lab, using the same `patients` table.

**Features:** register a patient, list records, search by ID or name, edit, delete, and live counts for total, active and high severity patients.

## Tech

- JSP and JSTL for the pages
- One servlet (`/patients`) handling list, insert, update and delete
- JDBC with `PreparedStatement` (no string concatenated SQL, output escaped with `c:out`)
- MySQL 8, Apache Tomcat 9, Maven, Java 11+

## Project structure

```
mentalhealth-jsp
├── pom.xml
├── sql/schema.sql
└── src/main
    ├── java/mentalhealth
    │   ├── patient.java          (bean)
    │   ├── dbconnection.java     (jdbc connection)
    │   ├── patientdao.java       (insert, update, delete, select)
    │   └── patientservlet.java   (controller)
    └── webapp
        ├── index.jsp
        ├── patients.jsp          (main page)
        ├── css/style.css
        └── WEB-INF/web.xml
```

## How to run

1. Create the database and table:
   ```
   mysql -u root -p < sql/schema.sql
   ```
2. Set your MySQL credentials as environment variables (defaults are `root` and `your_password`):
   ```
   set DB_USER=root
   set DB_PASS=yourpassword
   ```
   On Linux or Mac use `export` instead of `set`. Set them in the same terminal or service that starts Tomcat.
3. Build the war:
   ```
   mvn clean package
   ```
4. Copy `target/mentalhealth.war` into Tomcat's `webapps` folder and start Tomcat 9.
5. Open http://localhost:8080/mentalhealth/

Note: Tomcat 10 or newer uses `jakarta.servlet` instead of `javax.servlet`, so use Tomcat 9 for this project.

## Screenshots

Add a screenshot of the running page here before submitting.
