package com.booker.testing.tests.booking;

import com.booker.testing.model.Booking;
import com.booker.testing.model.Bookingdates;
import com.booker.testing.tests.auth.AuthTest;
import com.booker.testing.utilities.commons.TestGroups;
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

public class BookingTest extends AuthTest {

    private static final String BOOKING_PATH = "/booking";

    @Parameters({"BaseURL"})
    public BookingTest(String baseUrl) { super(baseUrl); }

    @Test(groups = {TestGroups.SMOKE, TestGroups.BOOKING})
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

    @Test(dependsOnMethods = {"createTokenHappyPath"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creates a new booking in the API")
    @Parameters({"contentType"})
    @Issue("")
    public void createBooking(String contentType, ITestContext context){
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
                .body(bodyPayload.toString())
                .post(BOOKING_PATH);

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingObjectDTO-schema.json"));

        String bookingId = response.then().extract().path("bookingid").toString();
        context.setAttribute("bookingid", bookingId);
    }

    @Test(dependsOnMethods = {"createBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Returns a specific booking based upon the booking id provided")
    @Issue("")
    public void getBooking(ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        Response response = given().spec(request)
                .get(BOOKING_PATH+String.format("/%s", bookingid));
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingObjectDTO-schema.json"));
    }

    @Test(dependsOnMethods = {"createBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Updates a current booking")
    @Parameters({"contentType", "accept"})
    @Issue("")
    public void updateBooking(String contentType, String accept, ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        String token = context.getAttribute("token").toString();
        String cookie= "token="+token;
        String bearer = "bearer "+token;

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
                .header("Cookie", cookie)
                .header("Authorization", bearer)
                .body(bodyPayload.toString())
                .put(BOOKING_PATH+String.format("/%s", bookingid));

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingObjectDTO-schema.json"));
    }

    @Test(dependsOnMethods = {"updateBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Updates a current booking with a partial payload")
    @Parameters({"contentType", "accept"})
    @Issue("")
    public void partialUpdateBooking(String contentType, String accept, ITestContext context){
        String bookingid = context.getAttribute("bookingid").toString();
        String token = context.getAttribute("token").toString();
        String cookie= "token="+token;
        String bearer = "bearer "+token;

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
                .header("Cookie", cookie)
                .header("Authorization", bearer)
                .body(bodyPayload.toString())
                .put(BOOKING_PATH+String.format("/%s", bookingid));

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/booking/BookingObjectDTO-schema.json"));
    }

    @Test(dependsOnMethods = {"partialUpdateBooking"}, groups = {TestGroups.SMOKE, TestGroups.BOOKING})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Returns the ids of all the bookings that exist within the API. Can take optional query strings to search and return a subset of booking ids")
    @Parameters({"contentType"})
    @Issue("")
    public void deleteBooking(String contentType, ITestContext context){

        String bookingid = context.getAttribute("bookingid").toString();
        String token = context.getAttribute("token").toString();
        String cookie= "token="+token;
        String bearer = "bearer "+token;

        Response response = given().spec(request)
                .header("Content-Type", contentType)
                .header("Cookie", cookie)
                .header("Authorization", bearer)
                .delete(BOOKING_PATH+String.format("/%s",bookingid));
        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }
}
