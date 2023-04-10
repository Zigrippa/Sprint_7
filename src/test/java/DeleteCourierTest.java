import config.Config;
import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static courier.CourierCreds.credsFrom;
import static org.junit.Assert.assertEquals;

public class DeleteCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courier = new Courier().generateCourier();
        courierClient = new CourierClient();
        RestAssured.baseURI = Config.BASE_URL;
        courierClient.create(courier);
    }

    //Данный метод логинит курьера и возвращает id
    public int idExtraction(Courier courier) {
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier));
        return loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Проверка удаления курьера")
    public void deleteCourierTest() {

        ValidatableResponse response = courierClient.delete(idExtraction(courier));

        assertEquals("Статус код неверный при удалении курьера",
                HttpStatus.SC_OK, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка удаления курьера без отправки id")
    public void deleteCourierWithoutSendingIdTest() {

        ValidatableResponse response = courierClient.delete();

        assertEquals("Статус код неверный при удаления курьера без отправки id",
                HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());
    } // Данный тест выявил баг(или ошибку в документации),
    // при отправке запроса на удаления курьера без id возращается неверный статус код

    @Test
    @DisplayName("Проверка удаления курьера с несуществующим id")
    public void deleteCourierWithNonExistIdTest() {

        ValidatableResponse response = courierClient.delete(Integer.parseInt(RandomStringUtils.randomNumeric(8)));

        assertEquals("Статус код неверный при удалении курьера с несуществующим id",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка ответа сервера при удалении курьера")
    public void correctResponseWhenCreateCourierTest() {
        ValidatableResponse response = courierClient.delete(idExtraction(courier));

        assertEquals("Неверное боди присланное сервером при удалении курьера",
                "{\"ok\":true}", response.extract().body().asString());
    }

}
