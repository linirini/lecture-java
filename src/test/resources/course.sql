INSERT INTO member(name, email, phone_number, password, role)
VALUES ('lini', 'lini@email.com', '01012345678', 'lini123', 'TEACHER');

INSERT INTO course (title, price, capacity, enroll_count, enroll_ratio, member_id, created_at, updated_at)
VALUES ('Java Programming', 10000, 10, 5, 50, 1, DATEADD('DAY', -1, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE)),
       ('Spring Framework', 15000, 20, 10, 40, 1, DATEADD('DAY', -3, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE)),
       ('Data Structures', 12000, 15, 0, 30, 1, DATEADD('DAY', 1, CURRENT_DATE), DATEADD('DAY', 1, CURRENT_DATE)),
       ('Algorithms', 8000, 10, 1, 35, 1, DATEADD('DAY', 4, CURRENT_DATE), DATEADD('DAY', 5, CURRENT_DATE));

-- 최근 등록 순: 4,3,1,2
-- 신청률 높은 순: 1,2,4,3
-- 신청 인원 많은 순: 2,1,4,3
