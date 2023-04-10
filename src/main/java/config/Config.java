package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {


    public final static String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public final static String COURIER_PATH = "/api/v1/courier";
    public final static String LOGIN_PATH = "/api/v1/courier/login";
    public final static String COURIER_ID_PATH = "/api/v1/courier/";

    public final static String ORDER_PATH = "/api/v1/orders";
    public final static String ACCEPT_ORDER_PATH = "/api/v1/orders/accept/";
    public final static String GET_ORDER_PATH = "/api/v1/orders/track";

    public static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build()
                .log()
                .all();
    }


}
