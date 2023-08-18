import ApiHelpers.BaseApiHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static ApiHelpers.Urls.AUTH_USER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class UpdateUserInfoParametrizedTests extends BaseApiHelper {

    private final String json;

    public UpdateUserInfoParametrizedTests(String json) {
        this.json = json;
    }

    @Parameterized.Parameters(name = "Обновление данных пользователя: {0}")
    public static Object[][] createOrderRequest() {
        return new Object[][]{
                {"{ \"name\": \"new_name\"}"},
                {"{ \"email\": \"new_email@yandex.ru\"}"}
        };
    }

    @Test
    @DisplayName("Обновление данных авторизованного пользователя")
    public void updateAuthorizationUserInfoTest() {
        Response response = createUser();
        String token = getAccessToken(response);
        given().header("Authorization", token)
                .and()
                .body(json)
                .when().patch(AUTH_USER)
                .then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
        deleteUser(response);
    }
}
