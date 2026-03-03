package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

interface CourseRepository extends JpaRepository<Course, Long> {
}

interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCourseId(Long courseId);

    List<Question> findByCourseIdInOrderByCreatedAtDesc(List<Long> courseIds);

    @Query("SELECT q FROM Question q WHERE q.course.id = :courseId AND " +
            "(LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Question> searchByKeyword(@Param("courseId") Long courseId, @Param("keyword") String keyword);
}

interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}