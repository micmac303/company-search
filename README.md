# *Developer Test:* company-search REST API

Company Search Service ( **spring-service** Developer test for Trunarrative) 

## Running the application
Build the project using *./gradlew build* from the root of the project.
Execute *./gradlew bootRun* from the root of the project to start the application.

Navigate to http://localhost:8080/swagger-ui/index.html

Calls can be made from this interface or from a tool such as Postman.

The **API Key is required as a parameter** in the request so that it is not added to the git repository.

Execute with activeCompanies parameter set to true the API key and example payload

{
"companyName": "",
"companyNumber": "06500244"
}

Note the log: *Company not returned from the database, calling TruProxy API*

Repeat the same call to see the response from the database. Evidenced by alternative log: *Company found in database*

Example unit tests provided for the controller and service classes.

Example integration test provided using Wiremock and Cucumber BDD
with Testcontainers so require Docker to be installed and running.

*./gradlew test* will run the unit tests.

*./gradlew integrationTest* will run the integration tests.

*./gradlew check* will run both the unit and integration tests.

Results can be seen at *build/reports/tests/test/index.html*

and *build/reports/tests/integrationTest/index.html*

## Development notes
Use of RestTemplateBuilder creates thread safe RestTemplate instances.

Defensive database exception handling implemented as example code despite the fact that the database is in memory (HyperSQL) and will not have network connectivity exceptions.

Fetching of companies and officers from the database handled by the controller rather than TruProxyService as example of single responsibility principle and separation of concerns.

Companies saved with full list of working officers after being fetched from TruProxyAPI.
