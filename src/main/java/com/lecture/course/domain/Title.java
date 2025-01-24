package com.lecture.course.domain;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Title {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;
    private static final String INVALID_LENGTH_MESSAGE = String.format("제목은 %d자 이상, %d자 이하로 설정해주세요.", MIN_LENGTH, MAX_LENGTH);

    @Column(length = MAX_LENGTH)
    private String title;

    public Title(String title) {
        title = trim(title);
        validate(title);
        this.title = title;
    }

    private String trim(String title) {
        if (Objects.isNull(title)) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
        return title.trim();
    }

    private void validate(String title) {
        if (title.length() < MIN_LENGTH || title.length() > MAX_LENGTH) {
            throw new LectureException(INVALID_LENGTH_MESSAGE);
        }
    }
}
