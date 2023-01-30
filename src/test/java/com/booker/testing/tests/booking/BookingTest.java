package com.booker.testing.tests.booking;

import com.booker.testing.base.testsetup.AbstractBaseTest;
import com.booker.testing.model.Booking;
import com.booker.testing.model.Bookingdates;
import com.booker.testing.tests.auth.AuthTest;
import com.booker.testing.utilities.commons.TestGroups;
import com.booker.testing.utilities.commons.Utilities;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BookingTest extends AbstractBaseTest {

    private static final String BOOKING_PATH = "/booking";

    @Parameters({"BaseURL"})
    public BookingTest(String baseUrl) { super(baseUrl); }

    @Test(/*dependsOnMethods = {"createTokenHappyPath"},*/ groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids")
    @Issue("")
    public void getBookingIds(){
        Response response = given().spec(request)
                .get(BOOKING_PATH);
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingDTO-schema.json"));
    }

    @Test(/*dependsOnMethods = {"createTokenHappyPath"},*/ groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creates a new booking in the API")
    @Parameters({"contentType", "accept"})
    @Issue("")
    public void createBooking(String contentType, String accept, ITestContext context){
        Gson gson = new Gson();
        Booking booking = new Booking();
        booking.setFirstname("Jim");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingdates);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(booking)).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Accept", accept)
                .body(bodyPayload.toString())
                .post(BOOKING_PATH);

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingObjectDTO-schema.json"));

        String bookingId = response.then().extract().path("bookingid").toString();
        context.setAttribute("bookingid", bookingId);
    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.NORMAL)
    @Description("Creates a new booking in the API - Bad request")
    @Parameters({"contentType", "accept"})
    @Issue("")
    public void createBookingBadRequest(String contentType, String accept){
        Gson gson = new Gson();
        Booking booking = new Booking();
        //booking.setFirstname("Jim");
        //booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingdates);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(booking)).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Accept", accept)
                .body(bodyPayload.toString())
                .post(BOOKING_PATH);

        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = {"createBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Returns a specific booking based upon the booking id provided")
    @Parameters({"accept"})
    @Issue("")
    public void getBooking(String accept, ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        Response response = given().spec(request)
                .header("Accept", accept)
                .get(BOOKING_PATH+String.format("/%s", bookingid));
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingGetDTO-schema.json"));
    }

    @Test(/*dependsOnMethods = {"createTokenHappyPath"},*/ groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.NORMAL)
    @Description("Returns a specific booking based upon the booking id provided - Not Found")
    @Parameters({"accept"})
    @Issue("")
    public void getBookingBadRequest(String accept){
        String bookingid = Utilities.getIdRandom();
        Response response = given().spec(request)
                .header("Accept", accept)
                .get(BOOKING_PATH+String.format("/%s", bookingid));
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(dependsOnMethods = {"createBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Updates a current booking")
    @Parameters({"contentType", "accept", "authorization"})
    @Issue("")
    public void updateBooking(String contentType, String accept, String authorization, ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        //String token = context.getAttribute("token").toString();
        //String cookie= "token="+token;
        //String bearer = "bearer "+token;

        Gson gson = new Gson();
        Booking booking = new Booking();
        booking.setFirstname("Jim");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingdates);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(booking)).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Accept", accept)
                //.header("Cookie", cookie)
                .header("Authorization", authorization)
                .body(bodyPayload.toString())
                .put(BOOKING_PATH+String.format("/%s", bookingid));

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingGetDTO-schema.json"));
    }

    @Test(/*dependsOnMethods = {"createTokenHappyPath"},*/ groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.NORMAL)
    @Description("Updates a current booking - Bad Request")
    @Parameters({"contentType", "accept", "authorization"})
    @Issue("")
    public void updateBookingBadRequest(String contentType, String accept, String authorization, ITestContext context){
        String bookingid = Utilities.getIdRandom();
        //String token = context.getAttribute("token").toString();
        //String cookie= "token="+token;
        //String bearer = "bearer "+token;

        Gson gson = new Gson();
        Booking booking = new Booking();
        booking.setFirstname("Jim");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingdates);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(booking)).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Accept", accept)
                //.header("Cookie", cookie)
                .header("Authorization", authorization)
                .body(bodyPayload.toString())
                .put(BOOKING_PATH+String.format("/%s", bookingid));

        response.then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(dependsOnMethods = {"updateBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Updates a current booking with a partial payload")
    @Parameters({"contentType", "accept","authorization"})
    @Issue("")
    public void partialUpdateBooking(String contentType, String accept, String authorization, ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        //String token = context.getAttribute("token").toString();
        //String cookie= "token="+token;
        //String bearer = "bearer "+token;

        Gson gson = new Gson();
        Booking booking = new Booking();
        booking.setFirstname("Jim");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingdates);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(booking)).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Accept", accept)
                //.header("Cookie", cookie)
                .header("Authorization", authorization)
                .body(bodyPayload.toString())
                .put(BOOKING_PATH+String.format("/%s", bookingid));

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingGetDTO-schema.json"));
    }

    @Test(/*dependsOnMethods = {"createTokenHappyPath"},*/ groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.NORMAL)
    @Description("Updates a current booking with a partial payload - NotFound")
    @Parameters({"contentType", "accept", "authorization"})
    @Issue("")
    public void partialUpdateBookingBadRequest(String contentType, String accept, String authorization, ITestContext context){
        String bookingid = Utilities.getIdRandom();
        //String token = context.getAttribute("token").toString();
        //String cookie= "token="+token;
        //String bearer = "bearer "+token;

        Gson gson = new Gson();
        Booking booking = new Booking();
        booking.setFirstname("Jim");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Breakfast");
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");
        booking.setBookingdates(bookingdates);

        JsonObject bodyPayload = JsonParser.parseString(gson.toJson(booking)).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Accept", accept)
                //.header("Cookie", cookie)
                .header("Authorization", authorization)
                .body(bodyPayload.toString())
                .put(BOOKING_PATH+String.format("/%s", bookingid));

        response.then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(dependsOnMethods = {"partialUpdateBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids")
    @Parameters({"contentType", "authorization"})
    @Issue("")
    public void deleteBooking(String contentType, String authorization, ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        //String token = context.getAttribute("token").toString();
        //String cookie= "token="+token;
        //String bearer = "bearer "+token;

        Response response = given().spec(request)
                .header("Content-Type", contentType)
                //.header("Cookie", cookie)
                .header("Authorization", authorization)
                .delete(BOOKING_PATH+String.format("/%s",bookingid));
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test(/*dependsOnMethods = {"createTokenHappyPath"},*/ groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.NORMAL)
    @Description("Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids - Not Found")
    @Parameters({"contentType", "authorization"})
    @Issue("")
    public void deleteBookingBadRequest(String contentType, String authorization, ITestContext context){
        String bookingid = Utilities.getIdRandom();
        //String token = context.getAttribute("token").toString();
        //String cookie= "token="+token;
        //String bearer = "bearer "+token;

        Response response = given().spec(request)
                .header("Content-Type", contentType)
                //.header("Cookie", cookie)
                .header("Authorization", authorization)
                .delete(BOOKING_PATH+String.format("/%s",bookingid));
        response.then().assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
