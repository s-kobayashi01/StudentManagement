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
import java.util.function.Consumer;

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
     * 新規受講生登録に使用する情報をセットします。
     *
     * @param studentsCourses 受講生コース情報
     * @param studentId       受講生ID
     */
    private void studentCoursesSet(StudentsCourses studentsCourses, Integer studentId) {
        studentsCourses.setCourseStartAt(LocalDateTime.now());
        studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
        studentsCourses.setStudentsId(studentId);
    }


    /**
     * 受講生登録と更新で共通した処理である受講生に紐づくコース情報の取得を行います。
     *
     * @param studentDetail 受講生詳細
     * @param repoAction    指示書（登録・更新のメソッドでの実行時に呼び出しで使う）
     */
    private void studentsCoursesLoop(StudentDetail studentDetail, Consumer<StudentsCourses> repoAction) {
        Integer studentsId = studentDetail.getStudent().getId();
        for (StudentsCourses sc : studentDetail.getStudentsCourses()) {
            //コース情報の呼び出し
            studentCoursesSet(sc, studentsId);
            repoAction.accept(sc);
        }

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

    /**
     * 受講生登録です。ループ処理で以下の受講生コース情報を登録します。
     * 受講生のIDと同一の受講生ID、受講開始日（現在時刻）、受講修了予定日（現在時刻から1年後）
     *
     * @param studentDetail 受講生詳細
     * @return 受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        //受講生の呼び出し
        repository.registerStudent(studentDetail.getStudent());
        logger.info("inserted id={}", studentDetail.getStudent().getId());
        //TODO: コース情報をループさせて受講生に紐づくコース情報を登録する処理の呼び出し
        studentsCoursesLoop(studentDetail, repository::registerStudentsCourses);

        return studentDetail;
    }

    /**
     * 受講生更新です。受講生情報と受講生に紐づくコース情報をまとめて更新します。
     * コースは1受講生にたいして複数件あり得るため、Listをループして1件ずつ更新します。
     *
     * @param studentDetail 受講生詳細
     */
    public void updateStudent(StudentDetail studentDetail) {
        //受講生の呼び出し
        repository.updateStudent(studentDetail.getStudent());
        //TODO: コース情報をループさせて受講生に紐づくコース情報を登録する処理の呼び出し
        studentsCoursesLoop(studentDetail, repository::updateStudentsCourses);
    }
}
