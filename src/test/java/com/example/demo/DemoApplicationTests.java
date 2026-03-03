package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

class BlueSealsApplicationTests {

    @Test
    void testUserConstructor() {
        //Testing User Constructor
        User u = new User("Ana", "ana@test.com", "pass123", "Student");
        assertEquals("Ana", u.getName());
        assertEquals("ana@test.com", u.getEmail());
        assertEquals("Student", u.getRole());
    }

    @Test
    void testCourseConstructor() {
        //Testing Course Constructor
        Course c = new Course();
        c.setCourseName("Java");
        assertEquals("Java", c.getCourseName());
    }

    @Test
    void testQuestionConstructor() {
        //Testing Question Constructor
        Question q = new Question();
        q.setTitle("Why?");
        assertEquals("Why?", q.getTitle());
    }

    @Test
    void testEnrollmentFunctionality() {
        //Test 1: Does enrolling a student actually work?
        Course c = new Course();
        User s = new User();
        c.enrollStudent(s);

        assertFalse(c.getEnrolledStudents().isEmpty(), "Student list should not be empty after enrollment");
        assertTrue(c.getEnrolledStudents().contains(s), "List should contain the specific student");
    }

    @Test
    void testProfessorRoleLogic() {
        //Test 2: Verify role logic works
        User prof = new User("Prof", "p@test.com", "123", "Professor");
        assertEquals("Professor", prof.getRole());
    }

    @Test
    void testAnswerLinking() {
        //Test 3: Ensure answers are linked to questions
        Question q = new Question();
        Answer a = new Answer();
        a.setQuestion(q);
        assertNotNull(a.getQuestion(), "Answer must be linked to a Question");
    }
}