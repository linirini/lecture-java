package com.lecture.member.domain;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import com.lecture.exception.LectureException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 10;
    private static final String INVALID_LENGTH_MESSAGE = String.format("이름은 %d자 이상, %d자 이하로 설정해주세요.", MIN_LENGTH, MAX_LENGTH);

    @Column(length = MAX_LENGTH)
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
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
    }
}
