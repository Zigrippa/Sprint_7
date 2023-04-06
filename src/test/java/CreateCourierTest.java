import config.Config;
import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static courier.CourierCreds.credsFrom;
import static org.junit.Assert.assertEquals;

public class CreateCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = new Courier().generateCourier();
        courierClient = new CourierClient();
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
    @DisplayName("Проверка создания курьера")
    public void createCourierTest() {
        ValidatableResponse response = courierClient.create(courier);

        assertEquals("Статус код неверный при создании курьера",
                HttpStatus.SC_CREATED, response.extract().statusCode());

        courierId = idExtraction(courier);

    }

    @Test
    @DisplayName("Проверка создания курьера с уже существующим набором данных")
    public void createTwoEqualCourierTest() {
        ValidatableResponse response = courierClient.create(courier);

        assertEquals("Статус код неверный при создании курьера",
                HttpStatus.SC_CREATED, response.extract().statusCode());

        ValidatableResponse response2 = courierClient.create(courier);

        assertEquals("Статус код неверный при попытке создать уже существующего курьера",
                HttpStatus.SC_CONFLICT, response2.extract().statusCode());

        courierId = idExtraction(courier);

    }

    @Test
    @DisplayName("Проверка создания курьера без указания логина")
    public void createCourierWithoutLoginTest() {
        courier.setLogin(null);
        ValidatableResponse response = courierClient.create(courier);

        assertEquals("Статус код неверный при создании курьера без логина",
                HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());

    }

    @Test
    @DisplayName("Проверка создания курьера без указания пароля")
    public void createCourierWithoutPasswordTest() {
        courier.setPassword(null);
        ValidatableResponse response = courierClient.create(courier);

        assertEquals("Статус код неверный при создании курьера без пароля",
                HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка создания курьера без указания имени")
    public void createCourierWithoutFirstNameTest() {
        courier.setFirstName(null);
        ValidatableResponse response = courierClient.create(courier);

        assertEquals("Статус код неверный при создании курьера без имени",
                HttpStatus.SC_CREATED, response.extract().statusCode());

        courierId = idExtraction(courier);
    }

    @Test
    @DisplayName("Проверка ответа сервера при создании курьера")
    public void correctResponseWhenCreateCourierTest() {
        ValidatableResponse response = courierClient.create(courier);

        assertEquals("Неверное боди присланное сервером при создании курьера",
                "{\"ok\":true}", response.extract().body().asString());

        courierId = idExtraction(courier);
    }

}
