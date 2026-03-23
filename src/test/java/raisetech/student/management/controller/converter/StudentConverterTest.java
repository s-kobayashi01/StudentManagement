package raisetech.student.management.controller.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
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
}