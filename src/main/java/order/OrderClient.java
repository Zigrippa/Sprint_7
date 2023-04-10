package order;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static config.Config.*;
import static io.restassured.RestAssured.given;

public class OrderClient {

    public OrderClient() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Step("Создание Заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .and()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение Списка Заказов")
    public ValidatableResponse receiveList() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Принятие Заказа")
    public ValidatableResponse accept(int orderId, int courierId) {
        return given()
                .spec(getSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(Config.ACCEPT_ORDER_PATH + orderId)
                .then();
    }

    @Step("Принятие Заказа Без Id Курьера")
    public ValidatableResponse accept(int orderId) {
        return given()
                .spec(getSpec())
                .when()
                .put(Config.ACCEPT_ORDER_PATH + orderId + "?courierId=")
                .then();
    }

    @Step("Принятие Заказа Без Id Заказа")
    public ValidatableResponse accept(int courierId, String string) {
        return given()
                .spec(getSpec())
                .when()
                .put(Config.ACCEPT_ORDER_PATH + "?courierId=" + courierId)
                .then();
    }

    @Step("Получение заказа по его номеру")
    public ValidatableResponse getById(int orderId) {
        return given()
                .spec(getSpec())
                .queryParam("t", orderId)
                .when()
                .get(GET_ORDER_PATH)
                .then();
    }

    @Step("Получение заказа по его номеру")
    public ValidatableResponse getById() {
        return given()
                .spec(getSpec())
                .when()
                .get(GET_ORDER_PATH)
                .then();
    }

}
