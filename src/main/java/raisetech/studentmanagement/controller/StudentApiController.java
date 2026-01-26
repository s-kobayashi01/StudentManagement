package raisetech.studentmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.studentmanagement.controller.converter.StudentConverter;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.service.StudentService;

import java.util.List;

@RestController
public class StudentApiController {

    private StudentService service;
    private StudentConverter converter;
    private static final Logger logger = LoggerFactory.getLogger(StudentApiController.class);

    @Autowired
    public StudentApiController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/api/studentList")
    public List<StudentDetail> getStudentList() {
        List<Student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();

        return converter.convertStudentDetails(students, studentsCourses);
    }


    @PostMapping("/api/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {


        service.updateStudent(studentDetail);
        logger.info("update id={}", studentDetail.getStudent().getId());
        return ResponseEntity.ok("更新処理が成功しました。");
    }

}
