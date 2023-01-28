package com.booker.testing.tests.auth;

import com.booker.testing.base.testsetup.AbstractBaseTest;
import com.booker.testing.model.User;
import com.booker.testing.utilities.commons.TestGroups;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class AuthTest extends AbstractBaseTest {
    private static final String AUTH_PATH = "/auth";

    @Parameters({"BaseURL"})
    public AuthTest(String baseUrl) { super(baseUrl); }

    @Test(groups = {TestGroups.SMOKE, TestGroups.AUTH})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creates a new auth token to use for access to the PUT and DELETE /booking - Happy Paths")
    @Parameters({"userName", "password", "contentType"})
    @Issue("")
    public void createTokenHappyPath(String userName, String password, String contentType, ITestContext context) {

        Gson gson = new Gson();
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(user)).getAsJsonObject();

        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .body(bodyPayload.toString())
                .post(AUTH_PATH);

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/auth/AuthDTO-schema.json"));
        String token = response.then().extract().path("token").toString();
        context.setAttribute("token", token);
    }

}
