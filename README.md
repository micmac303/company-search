# company-search

Company Search Service (Developer test for Trunarrative) 

## Running the application
./gradlew bootRun
Navigate to http://localhost:8080/swagger-ui/index.html



### Development notes
RestTemplateBuilder creates thread safe RestTemplate instances.
HttpServerErrorException ?
API Key as a parameter in the request so that it is not added to the git repository.
Records instead of Classes?
Something better than get(0) ?

Defensive database exception handling implemented despite the fact that the database is in memory and should not throw exceptions.

Fetch Comapnies and officers from database handled by the controller so as not to even call the truproxyservice.

Companies saved with full list of working officers when fetch from truproxy api in truproxy api service

200 with empty result over 404 easier for clients
Bad request
Exception - Return 400?

Wiremocek implemented as bundled with stubbed files for simplicity
    - considered more complex testcontianers approach but did not want to assume Docker installation



