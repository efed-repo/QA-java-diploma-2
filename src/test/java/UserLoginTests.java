import ApiHelpers.BaseApiHelper;
import ApiHelpers.LoginUserRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static ApiHelpers.Urls.USER_LOGIN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTests extends BaseApiHelper {

    @Test
    @DisplayName("Логин юзера, позитивный сценарий")
    public void userLoginTest() {
        Response user = createUser();
        String email = getUserEmail(user);
        LoginUserRequest loginUser = new LoginUserRequest(email, "12345678");
        Response response = login(loginUser);
        response.then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        deleteUser(response);
    }


    @Test
    @DisplayName("Логин несуществующего юзера, негативный сценарий")
    public void unexistingUserLoginTest() {
        LoginUserRequest loginUser = new LoginUserRequest("IncorrectUser_1@rrr.ru", "12345678");
        Response response = login(loginUser);
        response.then().statusCode(401)
                .and()
                .assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Логин с правильной почтой, но неверным паролем")
    public void loginWithExistingEmailAndIncorrectPassword() {
        Response user = createUser();
        String email = getUserEmail(user);
        LoginUserRequest loginUser = new LoginUserRequest(email, "incorrect");
        Response response = login(loginUser);
        response.then().statusCode(401)
                .and()
                .assertThat().body("success", equalTo(false));
        deleteUser(user);
    }

    private static Response login(LoginUserRequest loginUser) {
        return given()
                .header("Content-type", "application/json")
                .header("Accept","application/json")
                .body(loginUser)
                .when()
                .post(USER_LOGIN);
    }
}
