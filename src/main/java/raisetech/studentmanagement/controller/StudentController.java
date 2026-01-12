package raisetech.studentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.studentmanagement.controller.converter.StudentConverter;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

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

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail detail = new StudentDetail();
        detail.setStudentsCourses(new ArrayList<>());
        detail.getStudentsCourses().add(new StudentsCourses());
        model.addAttribute("studentDetail", detail);
        return "registerStudent";
    }


    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }

        service.create(studentDetail.getStudent(), studentDetail.getStudentsCourses());


        //model.addAttribute("name", studentDetail.getStudent().getName());


        //List<Student> student = new ArrayList<>();
        //List<StudentDetail> studentDetails = new ArrayList<>();

        //studentDetail.setStudent(student.get(name));
        //System.out.println(studentDetail.getStudent().getName() + "さんが新規受講生として登録されました。");
        return "redirect:/studentList";
    }
}
