import ApiHelpers.BaseApiHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static ApiHelpers.Urls.ORDERS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTests extends BaseApiHelper {

    @Test
    @DisplayName("Создание заказа без авторизации,но с ингридиентами")
    public void createOrderWithoutAuthorizationTest() {
        String id = getIngredientsIds();
        String json = "{\"ingredients\": [\"" + id + "\"]}";
        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(ORDERS).then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингридиентами")
    public void createOrderPositiveTest() {
        String id = getIngredientsIds();
        String json = "{\"ingredients\": [\"" + id + "\"]}";
        Response responsePreparation = createUser();
        String token = getAccessToken(responsePreparation);
        Response response = createOrder(token, json);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("order.status", equalTo("done"));
        deleteUser(responsePreparation);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией, но без ингредиентов")
    public void createOrderWithoutIngredients() {
        String json = "{}";
        Response responsePreparation = createUser();
        String token = getAccessToken(responsePreparation);
        Response response = createOrder(token, json);
        response.then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Ingredient ids must be provided"));
        deleteUser(responsePreparation);
    }

    @Test
    @DisplayName("Создание заказа с авторизаций и невалидными хешами ингридиентов")
    public void createOrderWithInvalidIngredients() {
        String json = "{\"ingredients\" : [\"61c0c5a71d1f820010000000\"]}";
        Response responsePreparation = createUser();
        String token = getAccessToken(responsePreparation);
        Response response = createOrder(token, json);
        response.then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("One or more ids provided are incorrect"));
        deleteUser(responsePreparation);
    }

    private Response createOrder(String token, String json) {
        return given().header("Authorization", token)
                .and()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(ORDERS);
    }
}
