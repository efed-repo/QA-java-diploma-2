package ApiHelpers;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static ApiHelpers.Urls.*;
import static io.restassured.RestAssured.given;

public class BaseApiHelper {


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }


    public Response createUser() {
        Faker faker = new Faker();
        String email = faker.animal().name() + "@yandex.ru";
        CreateUserRequestModel userApiModel = new CreateUserRequestModel(email, "12345678", "name");
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userApiModel)
                .when()
                .post(CREATE_USER);
    }

    public String getAccessToken(Response response) {
        CreateUserResponseModel responseModel = response.as(CreateUserResponseModel.class);
        return responseModel.getAccessToken();
    }

    public String getUserEmail(Response response) {
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

    public String getIngredientsIds(){
        Response response = given().when().get(INGREDIENTS);
        List<GetIngredientsHelper> listOfObjects = response.jsonPath().getList("data", GetIngredientsHelper.class);
        List<String> ids = listOfObjects.stream()
                .map(GetIngredientsHelper::get_id)
                .collect(Collectors.toList());
        Random random = new Random();
        return ids.get(random.nextInt(ids.size()));
    }

}
