import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class ApiTestBase {

    RequestSpecification specs() {
        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/")
                .log(LogDetail.ALL)
                .build();
    }

    AllureRestAssured filter() {
        return new AllureRestAssured()
                .setRequestTemplate("http-request.ftl")
                .setResponseTemplate("http-response.ftl");
    }
}
