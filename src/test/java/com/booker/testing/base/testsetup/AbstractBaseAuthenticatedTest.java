package com.booker.testing.base.testsetup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;

public abstract class AbstractBaseAuthenticatedTest extends AbstractBaseTest{
    private static final Logger logger = LogManager.getLogger(AbstractBaseAuthenticatedTest.class);

    public AbstractBaseAuthenticatedTest(String baseUrl) {
        super(baseUrl);
    }
//
//    @Step
//    private void authenticationSetup(String loginURL, String authHeader) throws TokenSetupException {
//        List<Header> headersList = new ArrayList<Header>();
//        headersList.add(new Header("Authorization","Basic " + authHeader));
//
//       try {
//           Response authResponse = given()
//                   .headers(new Headers(headersList))
//                   .post(String.format("https://%s/token/oauth", loginURL));
//
//           if(authResponse.getStatusCode() == HttpStatus.SC_UNAUTHORIZED){
//               throw new TokenSetupException();
//           }
//
//           Assert.assertEquals(authResponse.getStatusCode(), HttpStatus.SC_OK);
//           request.headers("Authorization","Bearer " + authResponse.path("access_token"));
//           request.headers("Accept-Charset","UTF-8");
//           request.headers("Accept-Encoding","gzip, deflate, br");
//           request.headers("Connection","keep-alive");
//           request.headers("Accept","*/*");
//       } catch (Exception ex){
//           throw new TokenSetupException();
//       }
//    }
//
//    @Parameters({ "LoginURL", "authHeader"})
//    @BeforeClass(alwaysRun = true)
//    public void beforeClass(Method method, String loginURL, String authHeader) throws TokenSetupException{
//        this.authenticationSetup(loginURL, authHeader);
//        request.filter(new AllureRestAssured());
//    }
}
