package courier;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static config.Config.*;
import static io.restassured.RestAssured.given;

public class CourierClient {

    public CourierClient() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Step("Создание Курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Логин Курьера")
    public ValidatableResponse login(CourierCreds creds) {
        return given()
                .spec(getSpec())
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Удаление Курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(COURIER_ID_PATH + courierId)
                .then();
    }

    @Step("Удаление Курьера без id")
    public ValidatableResponse delete() {
        return given()
                .spec(getSpec())
                .when()
                .delete(COURIER_ID_PATH)
                .then();
    }

}
