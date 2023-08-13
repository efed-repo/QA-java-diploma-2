import ApiHelpers.BaseApiHelper;
import ApiHelpers.CreateUserRequestModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static ApiHelpers.Urls.CREATE_USER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserApiTests extends BaseApiHelper {

    @Test
    @DisplayName("Создание пользователя, позитивный кейс")
    public void createUserSuccessTest() {
        Response response = createUser();
        response.then().assertThat().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        deleteUser(response);
    }

    @Test
    @DisplayName("Создание пользователя с уже существующим email-ом, негативный кейс")
    public void createUserWithExistingEmailTest() {
        Response responseFirstRegistration = createUser();
        String email = getUserEmail(responseFirstRegistration);
        CreateUserRequestModel userModel = new CreateUserRequestModel(email, "password", "name");
        Response responseSecondRegistration = given()
                .header("Content-type", "application/json")
                .and()
                .body(userModel)
                .when()
                .post(CREATE_USER);
        responseSecondRegistration.then().assertThat().statusCode(403)
                .and().assertThat().body("message", equalTo("User already exists"));
        deleteUser(responseFirstRegistration);
    }
}