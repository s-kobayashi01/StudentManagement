package raisetech.studentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.service.StudentService;

import java.util.List;

@RestController
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/studentList")
    public List<Student> getStudentList() {
        return service.searchStudentList();
    }

    @GetMapping("/studentsCourseList")
    public List<StudentsCourses> getStudentsCourseList() {
        return service.searchStudentsCourseList();
    }
}
