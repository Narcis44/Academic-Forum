package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @Autowired private CourseRepository courseRepo;
    @Autowired private QuestionRepository questionRepo;
    @Autowired private AnswerRepository answerRepo;
    @Autowired private UserRepository userRepo;

    // COURSES

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    // Endpoint: /courses/{id}/enroll
    @PostMapping("/courses/{id}/enroll")
    public String enroll(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");

        Course course = courseRepo.findById(id).orElse(null);
        User user = userRepo.findById(userId).orElse(null);

        if (course != null && user != null) {
            course.enrollStudent(user);
            courseRepo.save(course);
            return "Enrolled successfully";
        }
        return "Error enrolling";
    }

    // Endpoint: /courses/{id}/questions
    @GetMapping("/courses/{id}/questions")
    public List<Question> getCourseQuestions(@PathVariable Long id) {
        return questionRepo.findByCourseId(id);
    }

    // QUESTIONS & ANSWERS

    // Endpoint: /questions
    @PostMapping("/questions")
    public Question createQuestion(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String content = (String) payload.get("content");

        if (title == null || title.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error: Title cannot be empty.");
        }

        if (title.length() < 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Title is too short. Please write at least 5 characters.");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error: Question content cannot be empty.");
        }

        Long authorId = Long.parseLong(payload.get("authorId").toString());
        Long courseId = Long.parseLong(payload.get("courseId").toString());

        Question q = new Question();
        q.setTitle(title);
        q.setContent(content);

        User author = userRepo.findById(authorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        q.setAuthor(author);
        q.setCourse(course);

        return questionRepo.save(q);
    }

    // Endpoint: /questions/{id}/answers
    @PostMapping("/questions/{id}/answers")
    public Answer postAnswer(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        String content = (String) payload.get("content");

        Long authorId = Long.parseLong(payload.get("authorId").toString());

        Answer a = new Answer();
        a.setContent(content);
        a.setQuestion(questionRepo.findById(id).orElseThrow(() -> new RuntimeException("Question not found")));
        a.setAuthor(userRepo.findById(authorId).orElseThrow(() -> new RuntimeException("User not found")));

        return answerRepo.save(a);
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("courseName");
        String code = (String) payload.get("courseCode");
        Long profId = Long.valueOf(payload.get("professorId").toString());

        Course c = new Course();
        c.setCourseName(name);
        c.setCourseCode(code);
        c.setProfessor(userRepo.findById(profId).orElseThrow());

        return courseRepo.save(c);
    }

    // FEED (Latest questions from ALL enrolled courses)
    @GetMapping("/users/{userId}/feed")
    public List<Question> getStudentFeed(@PathVariable Long userId) {
        User student = userRepo.findById(userId).orElseThrow();
        // Get IDs of all courses the student is enrolled in
        List<Long> courseIds = student.getEnrolledCourses().stream()
                .map(Course::getId)
                .toList();

        return questionRepo.findByCourseIdInOrderByCreatedAtDesc(courseIds);
    }

    // SEARCH & FILTER
    @GetMapping("/courses/{id}/search")
    public List<Question> searchQuestions(@PathVariable Long id,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) boolean unansweredOnly) {
        List<Question> results;

        if (keyword != null && !keyword.trim().isEmpty()) {
            results = questionRepo.searchByKeyword(id, keyword.trim());
        } else {
            results = questionRepo.findByCourseId(id);
        }

        if (unansweredOnly) {
            results.removeIf(q -> !q.getAnswers().isEmpty());
        }

        return results;
    }
}