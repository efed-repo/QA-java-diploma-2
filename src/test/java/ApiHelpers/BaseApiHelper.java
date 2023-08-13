package ApiHelpers;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.auth.AUTH;
import org.junit.Before;

import static ApiHelpers.Urls.AUTH_USER;
import static ApiHelpers.Urls.CREATE_USER;
import static io.restassured.RestAssured.given;

public class BaseApiHelper {


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }


    public Response createUser() {
        Faker faker = new Faker();
        String email = faker.name().firstName() + "@yandex.ru";
        CreateUserRequestModel userApiModel = new CreateUserRequestModel(email, "12345678", "name");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(userApiModel)
                .when()
                .post(CREATE_USER);
        return response;
    }

    public String getAccessToken(Response response) {
        CreateUserResponseModel responseModel = response.as(CreateUserResponseModel.class);
        return responseModel.getAccessToken();
    }

    public String getUserEmail (Response response){
        CreateUserResponseModel responseModel = response.as(CreateUserResponseModel.class);
        return responseModel.getUser().getEmail();
    }

    public void deleteUser(Response response) {
        String accessToken = getAccessToken(response);
        given()
                .header("Authorization", accessToken)
                .when()
                .delete(AUTH_USER)
                .then().statusCode(202);
    }

}
