import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class ApiDifferentsTests extends BaseTest {
    @Test
    public void getEmailUserTest() {
        get("/users/2").then().log().all().body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    public void getListUserTest() {
        get("/users?page=2").then().log().all().body("data.id", hasItems(7, 8, 9, 10, 11, 12));
    }

    @Test
    public void getIdUserTest() {
        get("/users?page=2").then().log().all().statusCode(200).body("data[0].id", is(7));
    }

    @Test
    public void createUserTest() {
        String createUser = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
        given().body(createUser).contentType(JSON).when().log().all().post("/users").then().log().all().statusCode(201).body("name", containsString("morpheus"));
    }

    @Test
    public void deleteUserTest() {
        given().when().log().all().delete("/users/2").then().log().all().statusCode(204);
    }
}
