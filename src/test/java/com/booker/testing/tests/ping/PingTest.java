package com.booker.testing.tests.ping;

import com.booker.testing.base.testsetup.AbstractBaseTest;
import com.booker.testing.utilities.commons.TestGroups;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class PingTest extends AbstractBaseTest {
    private static final String AUTH_PATH = "/ping";

    @Parameters({"BaseURL"})
    public PingTest(String baseUrl) { super(baseUrl); }

    @Test(groups = {TestGroups.SMOKE, TestGroups.PING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("A simple health check endpoint to confirm whether the API is up and running")
    @Issue("")
    public void healthCheck(){
        Response response = given().spec(request)
                .get(AUTH_PATH);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
   }

}
