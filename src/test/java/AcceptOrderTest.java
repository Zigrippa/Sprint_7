import config.Config;
import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static courier.CourierCreds.credsFrom;
import static org.junit.Assert.assertEquals;

public class AcceptOrderTest {

    private Order order;
    private int orderId;
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;


    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = Order.getOrderWithColor(List.of("Black", "Grey"));
        orderId = orderClient.create(order).extract().path("track");

        courier = new Courier().generateCourier();
        courierClient = new CourierClient();
        courierClient.create(courier);

        courierId = idExtraction(courier);

        RestAssured.baseURI = Config.BASE_URL;
    }

    @After
    public void tearDown() {
        if (courierId != 0) courierClient.delete(courierId);
    }

    //Данный метод логинит курьера и возвращает id
    public int idExtraction(Courier courier) {
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier));
        return loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Проверка принятия курьером заказа")
    public void acceptOrderTest() {
        ValidatableResponse response = orderClient.accept(orderId, courierId);

        assertEquals("Неверное боди присланное сервером при принятии курьером заказа",
                "{\"ok\":true}", response.extract().body().asString());
    } //Данный тест проходит 1 раз из 5 - баг

    @Test
    @DisplayName("Проверка принятия курьером заказа без указания Id курьера")
    public void acceptOrderWithoutCourierIdTest() {
        ValidatableResponse response = orderClient.accept(orderId);

        assertEquals("Статус код неверный при принятии курьером заказа без указания Id курьера",
                HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка принятия курьером заказа при указании неверного Id курьера")
    public void acceptOrderWithIncorrectCourierIdTest() {
        ValidatableResponse response = orderClient.accept(orderId,
                Integer.parseInt(RandomStringUtils.randomNumeric(6)));

        assertEquals("Статус код неверный при принятии курьером заказа при указании неверного Id курьера",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка принятия курьером заказа без указания Id заказа")
    public void acceptOrderWithoutOrderIdTest() {
        ValidatableResponse response = orderClient.accept(courierId, "");

        assertEquals("Статус код неверный при принятии курьером заказа без указания Id заказа",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка принятия курьером заказа при указании неверного Id заказа")
    public void acceptOrderWithIncorrectOrderIdTest() {
        ValidatableResponse response = orderClient.accept(Integer.parseInt(RandomStringUtils.randomNumeric(8))
                , courierId);

        assertEquals("Статус код неверный при принятии курьером заказа при указании неверного Id заказа",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());
    }

}

