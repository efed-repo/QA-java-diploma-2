import ApiHelpers.BaseApiHelper;
import ApiHelpers.CreateUserRequestModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static ApiHelpers.Urls.CREATE_USER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserApiParametrizedTests extends BaseApiHelper {

    private final String email;
    private final String password;
    private final String name;

    public CreateUserApiParametrizedTests(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Создание курьера без одного обязательного параметра: {0} {1} {2}")
    public static Object[][] createCourierData() {
        return new Object[][]{
                {"", "123456", "withoutLogin"},
                {"withoutPassword@yandex.ru", "", "courierName"},
                {"withoutName@uandex.ru", "123456", ""}
        };
    }

    @Test
    @DisplayName("Создание пользователя без одного обязательного параметра, негативный кейс")
    public void creteUserWithoutEmailTest() {
        CreateUserRequestModel userModel = new CreateUserRequestModel(email, password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(userModel)
                .when()
                .post(CREATE_USER);
        response.then().assertThat().statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
