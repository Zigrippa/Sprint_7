import config.Config;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GetOrderById {

    private Order order;
    private int orderId;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getOrderWithColor(List.of("Black", "Grey"));
        orderId = orderClient.create(order).extract().path("track");

        RestAssured.baseURI = Config.BASE_URL;
    }


    @Test
    @DisplayName("Проверка получения заказа по номеру")
    public void getOrderByIdTest() {
        ValidatableResponse response = orderClient.getById(orderId);

        assertNotNull("Сервер не возвращает заказ",
                response.extract().body().asString());
    }

    @Test
    @DisplayName("Проверка получения заказа по номеру без указания номера")
    public void getOrderByIdWithoutIdTest() {
        ValidatableResponse response = orderClient.getById();

        assertEquals("Статус код неверный при попытке получения заказа по номеру без указания номера",
                HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка получения заказа по номеру с указанием неверного номера")
    public void getOrderByIdWithIncorrectIdTest() {
        ValidatableResponse response = orderClient.getById(Integer.parseInt(RandomStringUtils.randomNumeric(6)));

        assertEquals("Статус код неверный при попытке получения заказа по номеру с указанием неверного номера",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());
    }

}

