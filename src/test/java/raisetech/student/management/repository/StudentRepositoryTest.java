package raisetech.student.management.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(5);
    }

    @Test
    void 受講生コース情報の全件検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0).getStudentId()).isNotNull();
        assertThat(actual.size()).isEqualTo(9);
    }

    @Test
    void 受講生の検索が行えること() {
        Integer id = 1;

        Student actual = sut.searchStudent(id);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo("小林聖");
    }

    @Test
    void 受講生コース情報の検索が行えること() {
        Integer id = 1;
        List<StudentCourse> actual = sut.searchStudentCourse(id);

        assertThat(actual).isNotNull();
        assertThat(actual.get(0).getId()).isEqualTo(id);
        assertThat(actual.get(0).getCourseName()).isEqualTo("Javaコース");
    }


    @Test
    void 受講生の登録が行えること() {
        Student student = new Student();
        student.setName("小林聖");
        student.setKanaName("コバヤシサトル");
        student.setNickName("こば");
        student.setEmail("kobayashi@gmail.com");
        student.setArea("埼玉県");
        student.setAge(31);
        student.setGender("男");
        student.setJob("無職");

        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(6);
    }

    @Test
    void 受講生コース情報の登録が行えること() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);
        studentCourse.setCourseName("Javaコース");

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual.size()).isEqualTo(10);
    }


    @Test
    void 受講生の更新が行えること() {
        Student student = new Student();
        student.setId(1);
        student.setName("小林聖");
        student.setKanaName("コバヤシサトル");
        student.setNickName("こばやし");
        student.setEmail("kobayashi@gmail.com");
        student.setArea("埼玉県");
        student.setAge(31);
        student.setGender("男");
        student.setJob("エンジニア");

        sut.updateStudent(student);

        Student actual = sut.searchStudent(student.getId());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getNickName()).isEqualTo("こばやし");
        assertThat(actual.getJob()).isEqualTo("エンジニア");
    }

    @Test
    void 受講生コース情報の更新が行えること() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(1);
        studentCourse.setStudentId(1);
        studentCourse.setCourseName("WordPress副業コース");

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourseList();
        StudentCourse updated = actual.stream()
                .filter(sc -> sc.getId().equals(1))
                .findFirst()
                .orElseThrow();

        assertThat(updated.getStudentId()).isEqualTo(1);
        assertThat(updated.getCourseName()).isEqualTo("WordPress副業コース");
    }

    @Test
    void 存在しないIDを検索した場合nullが返ること() {
        Integer id = Integer.MAX_VALUE;

        Student actual = sut.searchStudent(id);

        assertThat(actual).isNull();
    }

    @Test
    void 存在しない受講生コース情報のIDを検索した場合空が返ること() {
        Integer id = Integer.MAX_VALUE;
        List<StudentCourse> actual = sut.searchStudentCourse(id);

        assertThat(actual).isEmpty();
    }

    @Test
    void 必須項目がnullの場合受講生の登録はエラーになること() {
        Student student = new Student();
        student.setNickName("こば");
        student.setArea("埼玉県");
        student.setAge(31);
        student.setGender("男");
        student.setJob("無職");

        assertThatThrownBy(() -> sut.registerStudent(student))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 必須項目がnullの場合受講生コース情報の登録はエラーになること() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);


        assertThatThrownBy(() -> sut.registerStudentCourse(studentCourse))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 存在しない受講生のIDの更新を行った場合更新されないこと() {
        Student student = new Student();
        student.setId(999);
        student.setName("小林聖");
        student.setKanaName("コバヤシサトル");
        student.setNickName("こばやし");
        student.setEmail("kobayashi@gmail.com");
        student.setArea("埼玉県");
        student.setAge(31);
        student.setGender("男");
        student.setJob("エンジニア");

        int before = sut.search().size();
        sut.updateStudent(student);

        Student actual = sut.searchStudent(student.getId());
        assertThat(actual).isNull();

        int after = sut.search().size();
        assertThat(after).isEqualTo(before);
    }

    @Test
    void 存在しない受講生コース情報のIDの更新を行った場合更新されないこと() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(999);
        studentCourse.setStudentId(999);
        studentCourse.setCourseName("WordPress副業コース");

        int before = sut.searchStudentCourseList().size();
        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourseList();
        boolean exists = actual.stream()
                .anyMatch(sc -> sc.getId().equals(999));
        assertThat(exists).isFalse();

        int after = sut.searchStudentCourseList().size();
        assertThat(after).isEqualTo(before);
    }

}