import ApiHelpers.BaseApiHelper;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static ApiHelpers.Urls.AUTH_USER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserInfoTest extends BaseApiHelper {

    @Test
    @DisplayName("Обновление данных без авторизации")
    public void updateUserInfoWithoutAuthorizationTest(){
        given().when().patch(AUTH_USER)
                .then().statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}
