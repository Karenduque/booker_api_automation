package com.booker.testing.base.testsetup;

import com.booker.testing.utilities.service.ServicePath;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.net.MalformedURLException;

public abstract class AbstractBaseTest {
    protected RequestSpecification request;

   public AbstractBaseTest(String baseUrl){
        try {
            RestAssured.baseURI = ServicePath.bookerLandaDevApi(baseUrl).toString();
            request = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON);
            request.filter(new AllureRestAssured());
        } catch (MalformedURLException ex){
            ex.printStackTrace();
        }
    }
}