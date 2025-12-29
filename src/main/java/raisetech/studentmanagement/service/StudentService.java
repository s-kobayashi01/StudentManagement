package raisetech.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        List<Student> all = repository.search();
        List<Student> thirties = new ArrayList<>();
        //絞り込みをする。年齢が30台の人のみを抽出する。
        //抽出したリストをコントローラーに返す。
        for (Student s : all) {
            if (s.getAge() >= 30 && s.getAge() < 40) {
                thirties.add(s);
            }
        }
        return thirties;

    }

    public List<StudentsCourses> searchStudentsCourseList() {
        List<StudentsCourses> all = repository.searchCourses();
        List<StudentsCourses> javaCourse = new ArrayList<>();
        //絞り込み検索で「Javaコース」のコース情報のみを抽出する。
        //抽出したリストをコントローラーに返す。
        for (StudentsCourses c : all) {
            if ("Javaコース".equals(c.getCourseName())) {
                javaCourse.add(c);
            }
        }
        return javaCourse;
    }
}
