package com.lecture.enrollment;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.lecture.IntegrationTest;
import com.lecture.fixture.CourseRequestFixture;
import com.lecture.fixture.EnrollmentRequestFixture;
import com.lecture.fixture.SignUpRequestFixture;
import com.lecture.member.service.dto.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.is;

class EnrollmentIntegrationTest extends IntegrationTest {

    @Disabled
    @DisplayName("학생이 3개의 강의에 수강신청을 시도합니다.")
    @TestFactory
    Stream<DynamicTest> enroll() {
        AtomicLong teacherId = new AtomicLong();
        AtomicLong studentId = new AtomicLong();
        AtomicLong course1Id = new AtomicLong();
        AtomicLong course2Id = new AtomicLong();
        AtomicLong course3Id = new AtomicLong();
        return Stream.of(
                DynamicTest.dynamicTest("강사가 회원가입을 합니다.", () ->
                        teacherId.set(signUp(SignUpRequestFixture.createTeacher("teach@email.com", "01012345678")))
                ),
                DynamicTest.dynamicTest("학생이 회원가입을 합니다.", () ->
                        studentId.set(signUp(SignUpRequestFixture.createStudent("learn@email.com", "01087654321")))
                ),
                DynamicTest.dynamicTest("강사가 3개의 강의(강의1, 강의2, 강의3)를 개설합니다.", () -> {
                    course1Id.set(registerCourse(teacherId, "강의1", 1));
                    course2Id.set(registerCourse(teacherId, "강의2", 1));
                    course3Id.set(registerCourse(teacherId, "강의3", 1));
                }),
                DynamicTest.dynamicTest("학생이 강의 1에 수강 신청을 합니다.", () -> enroll(studentId, course1Id)),
                DynamicTest.dynamicTest("강사가 강의 2에 수강 신청을 합니다.", () -> enroll(teacherId, course2Id)),
                DynamicTest.dynamicTest("학생 1이 강의1, 강의2, 강의3을 한번에 신청합니다.", () ->
                        RestAssured.given().log().all()
                                .contentType(ContentType.JSON)
                                .header(HttpHeaders.AUTHORIZATION, studentId)
                                .body(EnrollmentRequestFixture.create(course1Id, course1Id, course2Id, course3Id))
                                .when().post("/enrollment")
                                .then().log().all()
                                .assertThat().statusCode(HttpStatus.OK.value())
                                .body("enrollments[0].id", is(1))
                                .body("enrollments[0].status", is("FAILURE"))
                                .body("enrollments[0].message", is("이미 수강 신청이 완료된 강좌입니다."))
                                .body("enrollments[1].id", is(2))
                                .body("enrollments[1].status", is("FAILURE"))
                                .body("enrollments[1].message", is("이미 최대 수강 가능 인원을 초과했습니다."))
                                .body("enrollments[2].id", is(3))
                                .body("enrollments[2].status", is("SUCCESS"))
                                .body("enrollments[2].message", is("수강신청이 완료되었습니다."))
                )
        );
    }

    private int signUp(SignUpRequest signUpRequest) {
        return (int) RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/members")
                .then().extract().body().jsonPath().get("id");
    }

    private int registerCourse(AtomicLong memberId, String title, long capacity) {
        return (int) RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, memberId)
                .body(CourseRequestFixture.create(title,capacity))
                .when().post("/courses")
                .then().extract().body().jsonPath().get("id");
    }

    private void enroll(AtomicLong memberId, AtomicLong courseId) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, memberId)
                .body(EnrollmentRequestFixture.create(courseId))
                .when().post("/enrollment")
                .then().log().all()
                .assertThat().statusCode(HttpStatus.OK.value());
    }
}
