package com.lecture.member.domain;

import java.util.Objects;
import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    private static final int MAX_LENGTH = 10;
    private static final String INVALID_LENGTH_MESSAGE = String.format("이름은 1자 이상, %d자 이하로 설정해주세요.", MAX_LENGTH);

    private String name;

    public Name(String name) {
        name = trim(name);
        validate(name);
        this.name = name;
    }

    private String trim(String name) {
        if (Objects.isNull(name)) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
        return name.trim();
    }

    private void validate(String value) {
        if (value.isEmpty() || value.length() > MAX_LENGTH) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
    }
}
