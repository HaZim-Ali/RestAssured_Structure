# RestAssured_Structure

A Java API test automation project using REST Assured and TestNG to test the posts and authentication endpoints provided by [DummyJSON](https://dummyjson.com/). Allure collects test results and generates a report with test summaries and visualizations.

## Technology stack

- Java 23
- Maven
- REST Assured 6.0.1
- TestNG 7.12.0
- Allure TestNG adapter 3.0.0
- Allure Maven plugin 3.0.2
- Allure Report 3.4.1

## Project structure

```text
RestAssured_Structure/
├── pom.xml
├── README.md
├── allure-report/                     
└── src/
    └── test/
        ├── java/
        │   ├── config/
        │   │   └── ApiConfig.java
        │   ├── data/
        │   │   └── PostTestData.java
        │   ├── specs/
        │   │   └── RequestSpecs.java
        │   └── tests/
        │       ├── PostsApiTest.java
        │       └── auth/
        │           └── AuthApiTest.java
        └── resources/
            ├── allure.properties
            └── testng.xml
```

The report folder is generated when the Maven `verify` phase runs. Do not edit generated report files manually.

## Prerequisites

Install and configure:

- JDK 23
- Maven
- Internet access to reach DummyJSON and download Maven dependencies

Confirm Java and Maven are available:

```bash
java -version
mvn -version
```

## Test coverage

### Posts API

| Method | Endpoint | Checks |
|---|---|---|
| GET | `/posts/1` | Status code, post ID, title, tags, and reaction counts |
| POST | `/posts/add` | Created post fields and returned ID |
| PUT | `/posts/1` | Updated post fields |
| PATCH | `/posts/1` | Updated post title |
| DELETE | `/posts/1` | Deleted post ID and deletion status |

These write operations use DummyJSON’s simulated API behavior. The returned responses do not permanently modify the service’s stored posts.

### Authentication API

| Test method | Endpoint | Checks |
|---|---|---|
| `loginReturnsAccessAndRefreshTokens` | POST `/auth/login` | Login succeeds and returns access and refresh tokens |
| `bearerTokenAuthenticatesCurrentUser` | GET `/auth/me` | The access token authenticates the user |

The authentication test logs in with DummyJSON’s sample account, extracts the access token, and sends it as a Bearer token. The test prints the access token during execution for debugging. Treat it as a credential and do not share or commit logs containing it.

DummyJSON documents the sample login credentials and authentication endpoints in its [Auth API documentation](https://dummyjson.com/docs/auth).

## Run the tests

Run all tests declared in `src/test/resources/testng.xml`:

```bash
mvn clean test
```

To run the tests and generate the Allure report in one command:

```bash
mvn clean verify
```

Check that the test adapter produced results:

```bash
ls -la target/allure-results
```

The results directory should contain Allure result files after the test run.

## Generate and view the Allure report

Generate the saved report:

```bash
mvn allure:report
```

The report is saved to:

```text
allure-report/index.html
```

Alternatively, start the local report server:

```bash
mvn allure:serve
```

Keep the terminal open while viewing the served report. Stop the server with `Ctrl+C`.

Allure’s TestNG adapter records test results, and the Maven plugin generates the report from those results. See the [Allure TestNG documentation](https://allurereport.org/docs/testng/) and [Allure Maven documentation](https://allurereport.org/docs/integrations-maven/).

## Test suite

The suite file is located at:

```text
src/test/resources/testng.xml
```

It should reference the fully qualified names of both test classes:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">

<suite name="REST Assured API Suite" parallel="false">
    <test name="DummyJSON API tests" preserve-order="true">
        <classes>
            <class name="tests.auth.AuthApiTest"/>
            <class name="tests.PostsApiTest"/>
        </classes>
    </test>
</suite>
```

Maven Surefire must be configured in `pom.xml` to use this suite file.

## Allure results configuration

The file `src/test/resources/allure.properties` should contain:

```properties
allure.results.directory=target/allure-results
```

The Allure TestNG dependency must be in the POM’s `<dependencies>` section:

```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <scope>test</scope>
</dependency>
```

The Allure BOM belongs in `<dependencyManagement>`; it manages dependency versions but does not add the TestNG adapter by itself.

## Troubleshooting

### Allure says `target/allure-results` was not found

Run the tests before generating or serving the report:

```bash
mvn clean test
```

Then check:

```bash
ls -la target/allure-results
```

If the folder is still missing:

1. Confirm Maven ran at least one test.
2. Confirm `allure-testng` is under `<dependencies>` in `pom.xml`.
3. Confirm `allure.properties` is under `src/test/resources`.
4. Confirm `testng.xml` references the correct test class package and name.
5. Reload the Maven project in IntelliJ.

### Report generation shows `BUILD SUCCESS`, but no report opens

A successful Maven exit does not guarantee a report was generated. Check the log for missing-results messages and verify that `target/allure-results` contains files.

### Run a single test class

```bash
mvn -Dtest=PostsApiTest test
```

## API references

- [DummyJSON Posts API](https://dummyjson.com/docs/posts)
- [DummyJSON Auth API](https://dummyjson.com/docs/auth)
