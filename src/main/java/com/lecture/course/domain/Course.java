package com.lecture.course.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.lecture.config.domain.BaseEntity;
import com.lecture.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Title title;

    @Column(nullable = false)
    @Embedded
    private Capacity capacity;

    @Column(nullable = false)
    @Embedded
    private Price price;

    @Column(nullable = false)
    private Long enrollCount;

    @Column(nullable = false)
    private Double enrollRatio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Course(Title title, Capacity capacity, Price price, Member member) {
        this.title = title;
        this.capacity = capacity;
        this.price = price;
        this.enrollCount = 0L;
        this.enrollRatio = 0.0;
        this.member = member;
    }

    public Course(String title, long capacity, long price, Member member) {
        this(new Title(title), new Capacity(capacity), new Price(price), member);
    }

    public boolean isFull() {
        return capacity.isNotMoreThan(enrollCount);
    }

    public void enrolled() {
        enrollCount = enrollCount + 1;
        enrollRatio = getRatio();
    }

    private double getRatio() {
        return Math.round((enrollCount.doubleValue() / capacity.getCapacity()) * 10000) / 100.0;
    }
}
