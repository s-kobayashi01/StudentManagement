package raisetech.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentmanagement.controller.converter.StudentConverter;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StudentService.class);


    private StudentRepository repository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行うので、条件検索は行いません。
     *
     * @return 受講生一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentsCourses> studentsCoursesList = repository.searchStudentCoursesList();
        return converter.convertStudentDetails(studentList, studentsCoursesList);
    }


    /**
     * 受講生検索です。
     * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 受講生ID
     * @return 受講生
     */

    public StudentDetail searchStudent(Integer id) {
        Student student = repository.SearchStudent(id);
        List<StudentsCourses> studentsCourses = repository.SearchStudentCourses(student.getId());
        return new StudentDetail(student, studentsCourses);

    }

    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());

        logger.info("inserted id={}", studentDetail.getStudent().getId());


        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            studentsCourses.setStudentsId(studentDetail.getStudent().getId());
            studentsCourses.setCourseStartAt(LocalDateTime.now());
            studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
            repository.registerStudentsCourses(studentsCourses);
        }
        return studentDetail;

    }


    public void updateStudent(StudentDetail studentDetail) {

        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            repository.updateStudentsCourses(studentsCourses);
        }
    }
}
