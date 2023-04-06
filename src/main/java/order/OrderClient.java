package order;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static config.Config.GET_ORDER_PATH;
import static config.Config.ORDER_PATH;
import static io.restassured.RestAssured.given;

public class OrderClient {

    public OrderClient() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Step("Создание Заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when().log().all()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Получение Списка Заказов")
    public ValidatableResponse receiveList() {
        return given()
                .when().log().all()
                .get(ORDER_PATH)
                .then().log().all();
    }

    @Step("Принятие Заказа")
    public ValidatableResponse accept(int orderId, int courierId) {
        return given()
                .queryParam("courierId", courierId)
                .when().log().all()
                .put(Config.ACCEPT_ORDER_PATH + orderId)
                .then().log().all();
    }

    @Step("Принятие Заказа Без Id Курьера")
    public ValidatableResponse accept(int orderId) {
        return given()
                .when().log().all()
                .put(Config.ACCEPT_ORDER_PATH + orderId + "?courierId=")
                .then().log().all();
    }

    @Step("Принятие Заказа Без Id Заказа")
    public ValidatableResponse accept(int courierId, String string) {
        return given()
                .when().log().all()
                .put(Config.ACCEPT_ORDER_PATH + "?courierId=" + courierId)
                .then().log().all();
    }

    @Step("Получение заказа по его номеру")
    public ValidatableResponse getById(int orderId) {
        return given()
                .queryParam("t", orderId)
                .when().log().all()
                .get(GET_ORDER_PATH)
                .then().log().all();
    }

    @Step("Получение заказа по его номеру")
    public ValidatableResponse getById() {
        return given()
                .when().log().all()
                .get(GET_ORDER_PATH)
                .then().log().all();
    }

}
