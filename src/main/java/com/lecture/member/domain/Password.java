package com.lecture.member.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Password {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 10;
    private static final String INVALID_LENGTH_MESSAGE = String.format("비밀번호는 최소 %d자 이상, %d자 이하여야 합니다.", MIN_LENGTH, MAX_LENGTH);
    private static final String INVALID_REGEX_MESSAGE = "비밀번호는 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합해야 합니다.";
    private static final String PASSWORD_REGEX = "^(?=(.*[a-z])(?=.*[A-Z])|(?=.*[a-z])(?=.*\\d)|(?=.*[A-Z])(?=.*\\d)).*$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Column(length = MAX_LENGTH)
    private String password;

    public Password(String password) {
        password = trim(password);
        validate(password);
        this.password = password;
    }

    private String trim(String email) {
        if (Objects.isNull(email)) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
        return email.trim();
    }

    private void validate(String email) {
        if (email.length() < MIN_LENGTH || email.length() > MAX_LENGTH) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
        if (!PASSWORD_PATTERN.matcher(email).matches()) {
            throw new LectureException(INVALID_REGEX_MESSAGE);
        }
    }
}
