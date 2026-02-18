package raisetech.student.management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    @Mock
    private StudentRepository repository;


    @Mock
    private StudentConverter converter;

    private StudentService sut;

    private Integer id = 1;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出されていること() {
        //事前準備
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        //実行
        sut.searchStudentList();

        //検証
        verify(repository, times(1)).search();
        verify(repository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
    }

    @Test
    void 受講生詳細の検索_受講生とコース情報取得処理が呼び出され受講生詳細が返ること() {

        Student student = new Student(id);
        List<StudentCourse> studentCourse = new ArrayList<>();
        StudentDetail expected = new StudentDetail(student, studentCourse);
        when(repository.searchStudent(id)).thenReturn(student);
        when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourse);

        StudentDetail actual = sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(student.getId());
        verify(repository, times(1)).searchStudentCourse(student.getId());
        Assertions.assertEquals(expected, actual);
    }
}