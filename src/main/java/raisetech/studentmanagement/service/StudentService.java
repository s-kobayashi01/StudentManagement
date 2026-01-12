package raisetech.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.repository.StudentRepository;

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
        return repository.searchCourses();
    }

    @Transactional
    public void create(Student student, List<StudentsCourses> studentsCourses) {
        repository.insertStudent(student);

        logger.info("inserted id={}", student.getId());


        for (StudentsCourses sc : studentsCourses) {
            sc.setStudentsId(student.getId());
            repository.insertStudentsCourses(sc);
        }

    }
}
