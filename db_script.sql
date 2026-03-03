USE blue_seals_db;

SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM answers;
DELETE FROM questions;
DELETE FROM enrollments;
DELETE FROM courses;
DELETE FROM users;

ALTER TABLE answers AUTO_INCREMENT = 1;
ALTER TABLE questions AUTO_INCREMENT = 1;
ALTER TABLE enrollments AUTO_INCREMENT = 1;
ALTER TABLE courses AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO users (id, name, email, password, role) VALUES 
(1, 'Prof. Andrei Ionescu', 'ionescu@test.com', 'pass123', 'Professor'),
(2, 'Prof. Mihaela Popescu', 'popescu@test.com', 'pass123', 'Professor'),
(3, 'Prof. Radu Dumitrescu', 'dumitrescu@test.com', 'pass123', 'Professor'),
(4, 'Ana Maria', 'ana@test.com', 'pass123', 'Student'),
(5, 'Bogdan Stancu', 'bogdan@test.com', 'pass123', 'Student'),
(6, 'Cristian Munteanu', 'cristi@test.com', 'pass123', 'Student');

INSERT INTO courses (id, course_name, course_code, professor_id) VALUES 
(1, 'Programming 3', 'CS301', 1),
(2, 'Operating Systems', 'CS302', 2),
(3, 'Databases', 'CS303', 3);

INSERT INTO enrollments (user_id, course_id) VALUES 
(4, 1), (4, 2), (4, 3),
(5, 1), (5, 2),
(6, 3);

INSERT INTO questions (id, title, content, author_id, course_id, is_anonymous) VALUES 
(1, 'Java Streams', 'How do I filter a list using Streams?', 4, 1, FALSE),
(2, 'Spring Boot Annotation', 'What does @Autowired actually do?', 5, 1, FALSE),
(3, 'Deadlock vs Starvation', 'Can someone explain the difference?', 4, 2, FALSE),
(4, 'Linux Permissions', 'I cannot execute my script. chmod 777?', 5, 2, TRUE),
(5, 'Normalization', 'Why do we need 3NF?', 4, 3, FALSE),
(6, 'SQL Join', 'Inner join vs Left join?', 6, 3, FALSE);

INSERT INTO answers (content, author_id, question_id) VALUES 
('You use .stream().filter(x -> condition).collect(Collectors.toList())', 1, 1),
('Deadlock is when processes block each other forever.', 5, 3),
('Inner join returns matching rows only. Left join keeps all left rows.', 3, 6);

CREATE OR REPLACE VIEW view_unanswered_questions AS
SELECT 
    q.id AS question_id,
    q.title,
    c.course_name,
    u.name AS student_name,
    q.created_at
FROM questions q
JOIN courses c ON q.course_id = c.id
JOIN users u ON q.author_id = u.id
LEFT JOIN answers a ON q.id = a.question_id
WHERE a.id IS NULL;

CREATE OR REPLACE VIEW view_course_stats AS
SELECT 
    c.course_name,
    p.name AS professor_name,
    COUNT(DISTINCT e.user_id) AS total_students,
    COUNT(DISTINCT q.id) AS total_questions
FROM courses c
JOIN users p ON c.professor_id = p.id
LEFT JOIN enrollments e ON c.id = e.course_id
LEFT JOIN questions q ON c.id = q.course_id
GROUP BY c.id;