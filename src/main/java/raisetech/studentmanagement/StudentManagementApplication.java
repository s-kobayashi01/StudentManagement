package raisetech.studentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

    //Spring Bootが管理しているインスタンスを自動的に紐づけてくれる
    @Autowired
    private StudentRepository repository;
    @Autowired
    private StudentsCoursesRepository coursesRepository;

    public static void main(String[] args) {
        // localhost:8080
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    @GetMapping("/studentList")
    public List<Student> getStudentList() {
        return repository.search();
    }

    @GetMapping("/studentCourseList")
    public List<StudentsCourses> getStudentCourseList() {
        return coursesRepository.searchCourses();
    }

}
