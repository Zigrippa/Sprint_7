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
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when().log().all()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Логин Курьера")
    public ValidatableResponse login(CourierCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when().log().all()
                .post(LOGIN_PATH)
                .then().log().all();
    }

    @Step("Удаление Курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .when().log().all()
                .delete(COURIER_ID_PATH + courierId)
                .then().log().all();
    }

    @Step("Удаление Курьера без id")
    public ValidatableResponse delete() {
        return given()
                .when().log().all()
                .delete(COURIER_ID_PATH)
                .then().log().all();
    }

}
