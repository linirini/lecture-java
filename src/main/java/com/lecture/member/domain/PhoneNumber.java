package com.lecture.member.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PhoneNumber {

    private static final String INVALID_REGEX_MESSAGE = "전화번호 형식이 유효하지 않아요.";
    private static final String PHONE_NUMBER_REGEX = "^\\d{2,3}\\d{3,4}\\d{4}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        phoneNumber = trim(phoneNumber);
        validate(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    private String trim(String phoneNumber) {
        if (Objects.isNull(phoneNumber)) {
            throw new LectureException(INVALID_REGEX_MESSAGE);
        }
        return phoneNumber.trim();
    }

    private void validate(String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new LectureException(INVALID_REGEX_MESSAGE);
        }
    }
}
