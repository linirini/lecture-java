package com.lecture.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.lecture.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.is;

class MemberIntegrationTest extends IntegrationTest {

    @DisplayName("사용자가 회원가입을 한다.")
    @Test
    void signUp() {
        // given
        String signUpRequest = """
                {
                    "name": "이름",
                    "email": "user@email.com",
                    "phoneNumber": "01012345678",
                    "password": "password1",
                    "role": "student"
                }
                """;
        int expectedId = 1;

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/members")
                .then().log().all()
                .assertThat().statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, "/members/" + expectedId)
                .body("id", is(expectedId));
    }
}
