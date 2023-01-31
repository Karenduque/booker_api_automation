# FinaMetrica Profiler API Testing Suite

This framework will be used for the stage 2 of the development, focused on API, so QA team will be able to cover the testing process and automate it.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Built With

This following suite uses the following technologies and frameworks:
* [Java](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
* [Maven](https://maven.apache.org/)
* [TestNg](https://testng.org/doc/)
* [RestAssured](http://rest-assured.io/)
* [Allure Test Report](http://allure.qatools.ru/)

## Running the tests

### Run the Suite

To run the suite you need to execute: 
```
mvn clean test site
```

This will execute the tests and generate a html report in ```./target/site/``` folder.

### Generate and server Allure reporter

After executing the test you can start a web server that serves the html report with:

```
mvn io.qameta.allure:allure-maven:serve
```

This command open the web browser and displays the current html report.

## About the Postman Collection

This test suite has a manual Postman collection for reference, more information can be found [here](postman/README.md).

## About the Performance Testing

This test suite has a Jmeter performaance test scripts, more information can be found [here](performance/README.md).