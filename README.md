# *Developer Test:* company-search REST API

Company Search Service ( **spring-service** Developer test for Trunarrative ) 

## Running the application
To start the application (from the root of the project) execute: 

**./gradlew bootRun**

Navigate to http://localhost:8080/swagger-ui/index.html to see the Swagger (Open API Spec) UI.

Calls can be made from this interface or from a tool such as Postman.

### Example call (to illustrate database caching)
Execute with activeCompanies parameter set to true the API key and example payload

{
"companyName": "",
"companyNumber": "06500244"
}

Note the log: *Company not returned from the database, calling TruProxy API*

Repeat the same call to see the response from the database. Evidenced by alternative log: *Company found in database*

## Development notes
The **API Key is required as a parameter** in the request so that it is not added to the git repository.

Example unit tests provided for the controller and service classes.

Example integration test provided using Wiremock and Cucumber BDD
with Testcontainers so **require Docker to be installed and running**.

*./gradlew test* will run the unit tests.

*./gradlew integrationTest* will run the integration tests.

*./gradlew check* will run both the unit and integration tests.

Results can be seen at *build/reports/tests/test/index.html*

and *build/reports/tests/integrationTest/index.html*

Use of RestTemplateBuilder creates thread safe RestTemplate instances.

Defensive database exception handling implemented as example code despite the fact that the database is in memory (HyperSQL) and will not have network connectivity exceptions.

Companies saved with full list of working officers after being fetched from TruProxyAPI.

Valid format of the company number not specified in the requirements so validation not implemented.

If no company found from query to TruProxyAPI, the response body: { "totalResults": 0, "items": [] } with a status code 200 is returned rather than a 404.

## Future improvements
Add more unit tests for the TruProxyService classes.
Add more integration tests.
