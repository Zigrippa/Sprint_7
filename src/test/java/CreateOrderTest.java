import io.qameta.allure.junit4.DisplayName;
import order.Order;
import order.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private Order order;


    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters(name = "Name, Surname, Scooter Color: {0}, {1}, {8}")
        public static Object[][] getTestData() {
            return new Object[][]{
                    {Order.getOrderWithColor(List.of("Black"))},
                    {Order.getOrderWithColor(List.of("Grey"))},
                    {Order.getOrderWithColor(List.of("Black", "Grey"))},
                    {Order.getOrderWithColor(List.of(""))}
            };
        }

    @Test
    @DisplayName("Проверка создания заказа при различных параметрах цвета")
    public void createOrderWithDifferentData(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.create(order);

        assertEquals("Статус код неверный при создании заказа",
                HttpStatus.SC_CREATED, response.extract().statusCode());
        assertThat("Отсутствует номер трека заказа",
                response.extract().path("track"), notNullValue());
    }

}
