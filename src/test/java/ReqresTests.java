import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresTests extends ApiTestBase {

    Faker faker = new Faker(new Locale("en"));
    String name = faker.funnyName().name();
    String job = faker.job().position();
    String email = "eve.holt@reqres.in";
    String password = faker.bothify("#########");


    @Test
    @Tag("api")
    @DisplayName("Создание пользователя")
    void createUserTest() {

        ObjectNode jsonBody = new ObjectMapper().createObjectNode();
        jsonBody.put("name", name);
        jsonBody.put("job", job);

        given()
                .spec(specs())
                .filter(filter())
                .contentType(JSON)
                .body(jsonBody)
                .when()
                .post("api/users")
                .then()
                .statusCode(201)
                .contentType(JSON)
                .body("name", is(name), "job", is(job));
    }

    @Test
    @Tag("api")
    @DisplayName("Регистрация пользователя")
    void registrationTest() {

        ObjectNode jsonBody = new ObjectMapper().createObjectNode();
        jsonBody.put("email", email);
        jsonBody.put("password", password);

        given()
                .spec(specs())
                .filter(filter())
                .contentType(JSON)
                .body(jsonBody)
                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .contentType(JSON);
    }

    @Test
    @Tag("api")
    @DisplayName("Регистрация пользователя - негативный тест")
    void registrationUnsuccessfulTest() {

        ObjectNode jsonBody = new ObjectMapper().createObjectNode();
        jsonBody.put("email", email);

        given()
                .spec(specs())
                .filter(filter())
                .contentType(JSON)
                .body(jsonBody)
                .when()
                .post("api/register")
                .then()
                .statusCode(400)
                .contentType(JSON)
                .body("error", is("Missing password"));
    }

    @Test
    @Tag("api")
    @DisplayName("Получение информации по пользователю")
    void getSingleUserTest() {

        JsonPath jsonPath = given()
                .spec(specs())
                .filter(filter())
                .param("page", 2)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().jsonPath();

        assertThat(jsonPath.getList("data.email"))
                .contains("michael.lawson@reqres.in", "george.edwards@reqres.in");
    }

    @Test
    @Tag("api")
    @DisplayName("Авторизация")
    void loginTest() {

        ObjectNode jsonBody = new ObjectMapper().createObjectNode();
        jsonBody.put("email", email);
        jsonBody.put("password", password);

        String token = given()
                .spec(specs())
                .contentType(JSON)
                .body(jsonBody)
                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().jsonPath().getString("token");

        given()
                .spec(specs())
                .filter(filter())
                .contentType(JSON)
                .body(jsonBody)
                .when()
                .post("api/login")
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("token", is(token));
    }
}