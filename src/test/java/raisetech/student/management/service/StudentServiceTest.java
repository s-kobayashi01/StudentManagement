package raisetech.student.management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

        Student student = new Student(1);
        List<StudentCourse> studentCourse = new ArrayList<>();
        StudentDetail expected = new StudentDetail(student, studentCourse);
        when(repository.searchStudent(id)).thenReturn(student);
        when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourse);

        StudentDetail actual = sut.searchStudent(id);

        verify(repository, times(1)).searchStudent(student.getId());
        verify(repository, times(1)).searchStudentCourse(student.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 新規受講生の登録_新規受講生のと受講生に紐づくコース情報が呼び出され受講生詳細が返ること() {
        Student student = new Student(1);

        StudentCourse course1 = new StudentCourse();
        course1.setStudentId(student.getId());

        StudentCourse course2 = new StudentCourse();
        course2.setStudentId(student.getId());

        StudentDetail studentDetail = new StudentDetail(student, List.of(course1, course2));

        sut.registerStudent(studentDetail);

        ArgumentCaptor<StudentCourse> captor = ArgumentCaptor.forClass(StudentCourse.class);
        verify(repository, times(2)).registerStudentCourse(captor.capture());
        verify(repository, times(1)).registerStudent(student);

        List<StudentCourse> capturedCourses = captor.getAllValues();
        Assertions.assertTrue(
                capturedCourses.stream().allMatch(c -> c.getStudentId().equals(student.getId()))
        );
    }

    @Test
    void 受講生コース情報の設定_受講生ID受講開始日受講修了予定日の取得() {
        Student student = new Student(1);
        LocalDateTime before = LocalDateTime.now();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);
        StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourse));

        sut.registerStudent(studentDetail);

        LocalDateTime after = LocalDateTime.now();

        ArgumentCaptor<StudentCourse> captor = ArgumentCaptor.forClass(StudentCourse.class);
        verify(repository, times(1)).registerStudentCourse(captor.capture());
        StudentCourse captured = captor.getValue();
        //受講生のIDと受講生コース情報の受講生IDが同一なことsetStudentId(1)が上書きされていること
        Assertions.assertEquals(student.getId(), captured.getStudentId());
        //studentCourseのCourseStartAtが空でないこと
        Assertions.assertNotNull(captured.getCourseStartAt());
        //受講開始日がbeforeより前ではないことかつafterより後ではないこと
        Assertions.assertTrue(!captured.getCourseStartAt().isBefore(before)
                && !captured.getCourseStartAt().isAfter(after));
        //受講開始日に1年を足した日時と受講修了予定日が同じこと
        Assertions.assertEquals(captured.getCourseStartAt().plusYears(1), captured.getCourseEndAt());

    }

    @Test
    void 受講生詳細の更新_受講生とコース情報取得処理が呼び出されること() {
        Student student = new Student(1);
        StudentCourse course1 = new StudentCourse();
        course1.setStudentId(student.getId());

        StudentCourse course2 = new StudentCourse();
        course2.setStudentId(student.getId());

        StudentDetail studentDetail = new StudentDetail(student, List.of(course1, course2));

        sut.updateStudent(studentDetail);

        verify(repository, times(1)).updateStudent(studentDetail.getStudent());

        ArgumentCaptor<StudentCourse> captor = ArgumentCaptor.forClass(StudentCourse.class);
        verify(repository, times(2)).updateStudentCourse(captor.capture());
    }

    @Test
    void コース情報の検索_コース情報が呼び出されること() {
        sut.searchStudentCourseList();
        verify(repository, times(1)).searchStudentCourseList();
    }

    @Test
    void searchStudent_idがnullなら例外() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.searchStudent(null));
        verifyNoInteractions(repository);
    }

    @Test
    void searchStudent_studentが見つからない場合例外() {
        Integer id = 1;
        when(repository.searchStudent(id)).thenReturn(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.searchStudent(id));
        verify(repository).searchStudent(id);
        verify(repository, never()).searchStudentCourse(anyInt());
    }


    @Test
    void registerStudent_StudentDetailがnullなら例外() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.registerStudent(null));
        verifyNoInteractions(repository);
    }

    @Test
    void registerStudent_Studentがnullなら例外() {
        StudentDetail detail = new StudentDetail(null, List.of(new StudentCourse()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.registerStudent(detail));
        verifyNoInteractions(repository);
    }

    @Test
    void registerStudent_StudentCourseListがnullなら例外() {
        StudentDetail detail = new StudentDetail(new Student(), null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.registerStudent(detail));
        verifyNoInteractions(repository);
    }

    @Test
    void registerStudent_StudentCourseListにnullが含まれているなら例外() {
        StudentDetail detail = new StudentDetail(new Student(1), Arrays.asList(new StudentCourse(), null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.registerStudent(detail));
        verifyNoInteractions(repository);
    }

    @Test
    void updateStudent_StudentDetailがnullなら例外() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updateStudent(null));
        verifyNoInteractions(repository);
    }

    @Test
    void updateStudent_Studentがnullなら例外() {
        StudentDetail detail = new StudentDetail(null, List.of(new StudentCourse()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updateStudent(detail));
        verifyNoInteractions(repository);
    }

    @Test
    void updateStudent_StudentCourseListがnullなら例外() {
        StudentDetail detail = new StudentDetail(new Student(), null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updateStudent(detail));
        verifyNoInteractions(repository);
    }

    @Test
    void updateStudent_StudentCourseListにnullが含まれているなら例外() {
        StudentDetail detail = new StudentDetail(new Student(1), Arrays.asList(new StudentCourse(), null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updateStudent(detail));
        verifyNoInteractions(repository);
    }

    @Test
    void updateStudent_idにnullが含まれているなら例外() {
        StudentDetail detail = new StudentDetail(new Student(null), List.of(new StudentCourse()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updateStudent(detail));
        verifyNoInteractions(repository);
    }
}