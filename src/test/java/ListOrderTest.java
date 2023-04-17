import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ListOrderTest {

    @Test
    @DisplayName("Проверка получения листа заказов")
    public void receiveListOrderTest(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.receiveList();

        assertEquals("Статус код неверный при получении листа заказов",
                HttpStatus.SC_OK, response.extract().statusCode());
        assertThat("Отсутствует лист заказов",
                response.extract().path("orders"), notNullValue());
    }

}
