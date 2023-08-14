import ApiHelpers.BaseApiHelper;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class CreateOrderTests extends BaseApiHelper {

    //@Test
    @DisplayName("Создание заказа без авторизации,но с ингридиентами")
    public void createOrderWithoutAuthorizationTest(){

    }

    //@Test
    @DisplayName("Создание заказа с авторизацией и ингридиентами")
    public void createOrderPositiveTest(){

    }

   // @Test
    @DisplayName("Создание заказа с авторизацией, но без ингредиентов")
    public void createOrderWithoutIngredients(){

    }

   // @Test
    @DisplayName("Создание заказа с авторизаций и невалидными хешами ингридиентов")
    public void createOrderWithInvalidIngredients(){

    }
}
