import ApiHelpers.BaseApiHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static ApiHelpers.Urls.ORDERS;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetUserOrdersTests extends BaseApiHelper {
    @Test
    @DisplayName("Получение заказа без авторизации, негативный кейс")
    public void getUserOrderWithoutAuthorizationTest(){
        given().when().get(ORDERS)
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("message",equalTo("You should be authorised"));

    }

    @Test
    @DisplayName("Получение заказа авторизованого пользователя, позитивный кейс")
    public void getUserOrderTest(){
        Response response = createUser();
        String token = getAccessToken(response);
        given().header("Authorization", token)
        .when().get(ORDERS)
                .then().statusCode(200)
                .and()
                .assertThat().body("success",equalTo(true));
        deleteUser(response);
    }
}
