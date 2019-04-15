package Challenges;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;


public class Apis {


    String url = "/api/books/";
    String accessToken = "";
    String noAccessToken = "";

    @Test(description = "Section:2 , Challenge:1")
    public void api_startsWithAnEmptyStores(){

        Response response = given().get(url);
        String body = response.getBody().asString();
        Assert.assertTrue(body.isEmpty());
    }

    @Test(description = "Section:2 , Challenge:2")
    public void api_TitleAndAuthorRequired(){

        RestAssured.baseURI = url;

        given().urlEncodingEnabled(true)

                .header("ContentType" , "application/json")
                .header("Authorization" , accessToken)
                .param("id" , 5)
                .param("author" , "Özgür Ozan Cömert")
                .put(url)
                .then()
                .statusCode(400)
                .assertThat()
                .body("error" , equalTo("Field 'title' is required."));


        given().urlEncodingEnabled(true)


                .header("ContentType" , "application/json")
                .header("Authorization" , accessToken)
                .param("id" , 5)
                .param("title" , "SRE 101")
                .put(url)
                .then()
                .statusCode(400)
                .assertThat()
                .body("error" , equalTo("Field 'author' is required."));
    }

    @Test(description = "Section:2 , Challenge:3")
    public void api_TitleAndAuthorNotEmpty() {

        RestAssured.baseURI = url;

        given().urlEncodingEnabled(true)

                .header("ContentType" , "application/json")
                .header("Authorization" , accessToken)
                .param("id" , 5)
                .param("author" , "")
                .param("title" , "")
                .put(url)
                .then()
                .statusCode(400)
                .assertThat()
                .body("error" , equalTo("Field 'title' cannot be empty."))
                .body("error" , equalTo("Field 'author' cannot be empty."));

    }

    @Test(description = "Section:2 , Challenge:4")
    public void api_IdFieldReadOnly() {

        RestAssured.baseURI = url;

        Map<String, String> map = new HashMap<>();
        map.put("id", "1");

        given()
                .header("ContentType" , "application/json")
                .body(map)
                .when()
                .put(url)
                .then()
                .statusCode(405);
    }

    @Test(description = "Section:2 , Challenge:5")
    public void api_createABookViaPut(){
        RestAssured.baseURI = url;

        given().header("ContentType" , "application/json")
                .header("Authorization" , accessToken)
                .param("id" , 5)
                .param("author" , "Özgür Ozan Cömert")
                .param("title" , "API AUTOMATION")
                .put(url)
                .then()
                .body("Success", equalTo(true));

        get("/api/books/5").then().body("id"
                , hasItems(5));
        get("/api/books/5").then().body("author"
                , hasItems("Özgür Ozan Cömert"));
        get("/api/books/5").then().body("title"
                , hasItems("API AUTOMATION"));


    }
    @Test(description = "Section:2 , Challenge:6" , invocationCount = 2)
    public void api_cannotCreateADuplicateBook(){
        RestAssured.baseURI = url;

        given().header("ContentType" , "application/json")
                .header("Authorization" , accessToken)
                .param("id" , 10)
                .param("author" , "Özgür Ozan Cömert")
                .param("title" , "API AUTOMATION")
                .put(url)
                .then()
                .assertThat()
                .body("error", equalTo("Another book with similar title and author already exists."));

    }

}
