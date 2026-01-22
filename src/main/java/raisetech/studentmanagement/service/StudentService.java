package raisetech.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StudentService.class);


    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        return repository.search();
    }

    public List<StudentsCourses> searchStudentsCourseList() {
        return repository.searchStudentCoursesList();
    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());

        logger.info("inserted id={}", studentDetail.getStudent().getId());


        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            studentsCourses.setStudentsId(studentDetail.getStudent().getId());
            studentsCourses.setCourseStartAt(LocalDateTime.now());
            studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
            repository.registerStudentsCourses(studentsCourses);
        }

    }

    public StudentDetail searchStudent(Integer id) {
        Student student = repository.SearchStudent(id);
        List<StudentsCourses> studentsCourses = repository.SearchStudentCourses(student.getId());
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(studentsCourses);
        return studentDetail;

    }

    public void updateStudent(StudentDetail studentDetail) {

        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            repository.updateStudentsCourses(studentsCourses);
        }
    }
}
