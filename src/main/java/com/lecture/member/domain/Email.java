package com.lecture.member.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Email {

    private static final String INVALID_REGEX_MESSAGE = "올바르지 않은 이메일 형식입니다.";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private String email;

    public Email(String email) {
        email = trim(email);
        validate(email);
        this.email = email;
    }

    private String trim(String email) {
        if (Objects.isNull(email)) {
            throw new LectureException(INVALID_REGEX_MESSAGE);
        }
        return email.trim();
    }

    private void validate(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new LectureException(INVALID_REGEX_MESSAGE);
        }
    }
}
