package com.lecture.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.lecture.config.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Name name;

    @Column(nullable = false)
    @Embedded
    private Email email;

    @Column(nullable = false)
    @Embedded
    private PhoneNumber phoneNumber;

    @Column(nullable = false)
    @Embedded
    private Password password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Member(Name name, Email email, PhoneNumber phoneNumber, Password password, Role role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    public Member(String name, String email, String phoneNumber, String password, String role) {
        this(new Name(name), new Email(email), new PhoneNumber(phoneNumber), new Password(password), Role.findByName(role));
    }
}
