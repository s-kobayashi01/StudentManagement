package raisetech.studentmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.studentmanagement.controller.converter.StudentConverter;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentsCourseList();
        model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
        return "studentList";
    }

    @GetMapping("/studentsCourseList")
    public String getStudentsCourseList(Model model) {
        model.addAttribute("studentList", service.searchStudentsCourseList());
        return "studentsCourseList";
    }

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable Integer id, Model model) {
        StudentDetail studentDetail = service.searchStudent(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }


    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }

        service.registerStudent(studentDetail);
        return "redirect:/studentList";
    }


    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("student") StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }

        service.updateStudent(studentDetail);
        logger.info("update id={}", studentDetail.getStudent().getId());
        return "redirect:/studentList";
    }

}
