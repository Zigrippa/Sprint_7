
import config.Config;
import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static courier.CourierCreds.credsFrom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = new Courier().generateCourier();
        courierClient = new CourierClient();
        RestAssured.baseURI = Config.BASE_URL;
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        if (courierId != 0) courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Проверка логина курьера")
    public void loginCourierTest() {
        ValidatableResponse response = courierClient.login(credsFrom(courier));

        assertEquals("Статус код неверный при логине курьера",
                HttpStatus.SC_OK, response.extract().statusCode());

        courierId = response.extract().path("id");
    }

    @Test
    @DisplayName("Проверка логина курьера с неверно указанным логином")
    public void loginCourierWithIncorrectLoginTest() {
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse response = courierClient.login(credsFrom(courier));

        assertEquals("Статус код неверный при попытке логина курьера с неверным логином",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());

    }

    @Test
    @DisplayName("Проверка логина курьера с неверно указанным паролем")
    public void loginCourierWithIncorrectPasswordTest() {
        courier.setPassword(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse response = courierClient.login(credsFrom(courier));

        assertEquals("Статус код неверный при попытке логина курьера с неверным паролем",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());

    }

    @Test
    @DisplayName("Проверка логина курьера с несуществующими данными")
    public void loginCourierWithNonExistCourierDataTest() {
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        courier.setPassword(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse response = courierClient.login(credsFrom(courier));

        assertEquals("Статус код неверный попытке логина под несуществующим пользователем",
                HttpStatus.SC_NOT_FOUND, response.extract().statusCode());

    }

    @Test
    @DisplayName("Проверка возвращения ID курьера при успешном логине")
    public void correctLoginReturnIdTest() {
        ValidatableResponse response = courierClient.login(credsFrom(courier));

        assertNotNull("При успешном логине не возвращается id", response.extract().path("id"));

        courierId = response.extract().path("id");

    }

}

