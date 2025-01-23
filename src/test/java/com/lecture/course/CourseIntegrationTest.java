package com.lecture.course;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.lecture.IntegrationTest;
import com.lecture.fixture.SignUpRequestFixture;
import com.lecture.member.service.dto.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.is;

public class CourseIntegrationTest extends IntegrationTest {

    @DisplayName("회원가입한 강사가 강좌를 등록한다.")
    @TestFactory
    Stream<DynamicTest> registerCourse() {
        AtomicLong memberId = new AtomicLong();
        return Stream.of(
                DynamicTest.dynamicTest("강사로 회원가입한다", () -> memberId.set(signUp(SignUpRequestFixture.createTeacher()))),
                DynamicTest.dynamicTest("강사가 강좌를 등록한다", () -> {
                    String courseRequest = """
                            {
                                "title": "너나위의 내집마련 기초반",
                                "capacity": 10,
                                "price": 200000
                            }
                            """;
                    int expectedId = 1;

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .header(HttpHeaders.AUTHORIZATION, memberId)
                            .body(courseRequest)
                            .when().post("/courses")
                            .then().log().all()
                            .assertThat().statusCode(HttpStatus.CREATED.value())
                            .header(HttpHeaders.LOCATION, "/courses/" + expectedId)
                            .body("id", is(expectedId));
                })
        );
    }

    @DisplayName("회원가입한 학생은 강좌를 등록할 수 없다.")
    @TestFactory
    Stream<DynamicTest> cannotRegisterCourseIfStudent() {
        AtomicLong memberId = new AtomicLong();
        return Stream.of(
                DynamicTest.dynamicTest("학생으로 회원가입한다", () -> memberId.set(signUp(SignUpRequestFixture.createStudent()))),
                DynamicTest.dynamicTest("학생은 강좌를 등록할 수 없다", () -> {
                    String courseRequest = """
                            {
                                "title": "너나위의 내집마련 기초반",
                                "capacity": 10,
                                "price": 200000
                            }
                            """;

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .header(HttpHeaders.AUTHORIZATION, memberId)
                            .body(courseRequest)
                            .when().post("/courses")
                            .then().log().all()
                            .assertThat().statusCode(HttpStatus.FORBIDDEN.value())
                            .body("message", is("요청하신 작업을 처리할 권한이 없어요."));
                })
        );
    }

    @DisplayName("강사는 등록했던 본인의 강좌와 동일한 강좌명으로 등록할 수 없다.")
    @TestFactory
    Stream<DynamicTest> cannotRegisterCourseIfAlreadyExists() {
        AtomicLong memberId = new AtomicLong();
        return Stream.of(
                DynamicTest.dynamicTest("강사로 회원가입한다", () -> memberId.set(signUp(SignUpRequestFixture.createTeacher()))),
                DynamicTest.dynamicTest("강사가 강좌를 등록한다", () -> {
                    String courseRequest = """
                            {
                                "title": "너나위의 내집마련 기초반",
                                "capacity": 10,
                                "price": 200000
                            }
                            """;
                    int expectedId = 1;

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .header(HttpHeaders.AUTHORIZATION, memberId)
                            .body(courseRequest)
                            .when().post("/courses")
                            .then().log().all()
                            .assertThat().statusCode(HttpStatus.CREATED.value())
                            .header(HttpHeaders.LOCATION, "/courses/" + expectedId)
                            .body("id", is(expectedId));
                }),
                DynamicTest.dynamicTest("강사가 이미 등록한 강좌와 같은 이름으로 등록을 시도하면 실패한다", () -> {
                    String courseRequest = """
                            {
                                "title": "너나위의 내집마련 기초반",
                                "capacity": 10,
                                "price": 200000
                            }
                            """;

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .header(HttpHeaders.AUTHORIZATION, memberId)
                            .body(courseRequest)
                            .when().post("/courses")
                            .then().log().all()
                            .assertThat().statusCode(HttpStatus.BAD_REQUEST.value())
                            .body("message", is("이미 같은 이름의 강좌를 등록했어요. 다시 설정해주세요."));
                })
        );
    }

    private static int signUp(SignUpRequest Teacher) {
        return (int) RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Teacher)
                .when().post("/members")
                .then().extract().body().jsonPath().get("id");
    }

    @DisplayName("인증되지 않은 사용자는 강좌를 등록할 수 없다.")
    @Test
    void cannotRegisterIfUnknownUser() {
        // given
        long memberId = 0L;
        String courseRequest = """
                {
                    "title": "너나위의 내집마련 기초반",
                    "capacity": 10,
                    "price": 200000
                }
                """;

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, memberId)
                .body(courseRequest)
                .when().post("/courses")
                .then().log().all()
                .assertThat().statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", is("인증되지 않은 사용자입니다."));
    }
}
