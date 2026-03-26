package raisetech.student.management.controller.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

class StudentConverterTest {
    private StudentConverter sut = new StudentConverter();

    @Test
    void 受講生に紐づく受講生コース情報をマッピング() {
        Student student = new Student();
        student.setId(1);
        student.setName("小林聖");
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseName("javaコース");
        StudentCourse studentCourse2 = new StudentCourse();
        studentCourse2.setStudentId(2);
        studentCourse2.setCourseName("WordPress副業コース");

        List<StudentCourse> studentCourseList = List.of(studentCourse, studentCourse2);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);


        Assertions.assertEquals(1, actual.size());
        StudentDetail result = actual.get(0);
        Assertions.assertEquals(1, result.getStudent().getId());
        Assertions.assertEquals("小林聖", result.getStudent().getName());
        Assertions.assertEquals(1, result.getStudentCourseList().get(0).getStudentId());
        Assertions.assertEquals("javaコース", result.getStudentCourseList().get(0).getCourseName());
        Assertions.assertEquals(1, result.getStudentCourseList().size());
    }

    @Test
    void 複数の受講生に対して紐づく受講生コース情報をマッピング() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("小林聖");
        Student student2 = new Student();
        student2.setId(2);
        student2.setName("山田太郎");
        List<Student> studentList = List.of(student1, student2);

        StudentCourse studentCourse1 = new StudentCourse();
        studentCourse1.setStudentId(student1.getId());
        studentCourse1.setCourseName("javaコース");
        StudentCourse studentCourse2 = new StudentCourse();
        studentCourse2.setStudentId(student2.getId());
        studentCourse2.setCourseName("AWSコース");

        List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        StudentDetail result1 = actual.stream()
                .filter(studentDetail -> studentDetail.getStudent().getId().equals(1))
                .findFirst()
                .orElseThrow();

        StudentDetail result2 = actual.stream()
                .filter(studentDetail -> studentDetail.getStudent().getId().equals(2))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(1, result1.getStudent().getId());
        Assertions.assertEquals("小林聖", result1.getStudent().getName());
        Assertions.assertEquals(1, result1.getStudentCourseList().size());
        Assertions.assertEquals(1, result1.getStudentCourseList().get(0).getStudentId());
        Assertions.assertEquals("javaコース", result1.getStudentCourseList().get(0).getCourseName());
        Assertions.assertEquals(2, result2.getStudent().getId());
        Assertions.assertEquals("山田太郎", result2.getStudent().getName());
        Assertions.assertEquals(1, result2.getStudentCourseList().size());
        Assertions.assertEquals(2, result2.getStudentCourseList().get(0).getStudentId());
        Assertions.assertEquals("AWSコース", result2.getStudentCourseList().get(0).getCourseName());
    }

    @Test
    void 受講生に対して複数の受講生コース情報をマッピング() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("小林聖");
        List<Student> studentList = List.of(student1);
        StudentCourse studentCourse1 = new StudentCourse();
        studentCourse1.setStudentId(student1.getId());
        studentCourse1.setCourseName("javaコース");
        StudentCourse studentCourse2 = new StudentCourse();
        studentCourse2.setStudentId(student1.getId());
        studentCourse2.setCourseName("AWSコース");

        List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        StudentDetail result = actual.stream()
                .filter(studentDetail -> studentDetail.getStudent().getId().equals(1))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(1, result.getStudent().getId());
        Assertions.assertEquals("小林聖", result.getStudent().getName());
        Assertions.assertEquals(2, result.getStudentCourseList().size());
        
        Assertions.assertEquals(1, result.getStudentCourseList().get(0).getStudentId());
        Assertions.assertEquals("javaコース", result.getStudentCourseList().get(0).getCourseName());

        Assertions.assertEquals(1, result.getStudentCourseList().get(1).getStudentId());
        Assertions.assertEquals("AWSコース", result.getStudentCourseList().get(1).getCourseName());
    }

    @Test
    void 受講生に対してコースが一切紐づかないケース() {
        Student student = new Student();
        student.setId(1);
        student.setName("小林聖");
        List<Student> studentList = List.of(student);

        StudentCourse studentCourse1 = new StudentCourse();
        studentCourse1.setStudentId(1000);

        List<StudentCourse> studentCourseList = List.of(studentCourse1);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getStudent().getId());
        Assertions.assertEquals("小林聖", actual.get(0).getStudent().getName());
        Assertions.assertTrue(actual.get(0).getStudentCourseList().isEmpty());


    }

    @Test
    void studentListがnullの場合NullPointerExceptionが発生すること() {
        Assertions.assertThrows(NullPointerException.class,
                () -> sut.convertStudentDetails(null, new ArrayList<>()));
    }

    @Test
    void studentCourseListがnullの場合NullPointerExceptionが発生すること() {
        Student student = new Student();
        student.setId(1);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        Assertions.assertThrows(NullPointerException.class,
                () -> sut.convertStudentDetails(studentList, null));
    }

    @Test
    void studentListとstudentCourseListが空の場合() {
        List<StudentDetail> result = sut.convertStudentDetails(new ArrayList<>(), new ArrayList<>());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void 存在しないstudentIdを持つ受講生コース情報はマッピングされないこと() {
        Student student = new Student();
        student.setId(1);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1000); //存在しないstudentId

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, List.of(studentCourse));

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1, actual.get(0).getStudent().getId());
        Assertions.assertTrue(actual.get(0).getStudentCourseList().isEmpty());
    }
}