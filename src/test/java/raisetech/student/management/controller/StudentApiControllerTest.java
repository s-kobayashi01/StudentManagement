package raisetech.student.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentApiController.class)
class StudentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {

        mockMvc.perform(get("/api/studentList"))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudentList();
    }

    @Test
    void 受講生の検索が実行できて空のリストが返ってくること() throws Exception {
        Integer id = 999;
        mockMvc.perform(get("/api/student/{id}", id))
                .andExpect(status().isOk());

        verify(service, times(1)).searchStudent(id);
    }

    @Test
    void 受講生の登録が実行できること() throws Exception {
        Student student = new Student(
                1,
                "小林聖",
                "コバヤシサトル",
                "こば",
                "kobayashi@gmail.com",
                "埼玉県",
                "男"
        );
        StudentDetail studentDetail = new StudentDetail(student, new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(studentDetail);
        mockMvc.perform(post("/api/registerStudent")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<StudentDetail> argumentCaptor = ArgumentCaptor.forClass(StudentDetail.class);
        verify(service, times(1)).registerStudent(argumentCaptor.capture());

        StudentDetail capturedDetail = argumentCaptor.getValue();
        assertThat(capturedDetail.getStudent().getId()).isEqualTo(1);
        assertThat(capturedDetail.getStudent().getName()).isEqualTo("小林聖");
    }

    @Test
    void 受講生の更新が実行できること() throws Exception {
        Student student = new Student(
                1,
                "小林聖",
                "コバヤシサトル",
                "こば",
                "kobayashi@gmail.com",
                "埼玉県",
                "男"
        );
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);
        studentCourse.setId(1);
        studentCourse.setCourseName("javaコース");
        StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourse));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(studentDetail);
        mockMvc.perform(put("/api/updateStudent")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<StudentDetail> argumentCaptor = ArgumentCaptor.forClass(StudentDetail.class);
        verify(service, times(1)).updateStudent(argumentCaptor.capture());

        StudentDetail capturedDetail = argumentCaptor.getValue();
        assertThat(capturedDetail.getStudent().getId()).isEqualTo(1);
        assertThat(capturedDetail.getStudent().getName()).isEqualTo("小林聖");
        assertThat(capturedDetail.getStudentCourseList().stream().anyMatch(c -> c.getId().equals(1))).isTrue();
    }

    @Test
    void 受講生コース情報の検索失敗すること() throws Exception {
        mockMvc.perform(get("/api/searchStudentCourseList"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
        Student student = new Student();
        student.setId(1);
        student.setName("小林聖");
        student.setKanaName("コバヤシサトル");
        student.setNickName("こば");
        student.setEmail("kobayashi@gmail.com");
        student.setArea("埼玉県");
        student.setGender("男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
        Student student = new Student();
        student.setId(null);
        student.setName("小林聖");
        student.setKanaName("コバヤシサトル");
        student.setNickName("こば");
        student.setEmail("kobayashi@gmail.com");
        student.setArea("埼玉県");
        student.setGender("男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void 受講生詳細の受講生でkanaNameにカナ以外を用いた時に入力チェックに掛かること() {
        Student student = new Student();
        student.setId(1);
        student.setName("小林聖");
        student.setKanaName("a");
        student.setNickName("こば");
        student.setEmail("kobayashi@gmail.com");
        student.setArea("埼玉県");
        student.setGender("男");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("カナ文字のみ入力するようにしてください。");
    }

    @Test
    void 受講生詳細の受講生コース情報で適切な値を入力した時に入力チェックに異常が発生しないこと() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(1);
        studentCourse.setStudentId(1);
        studentCourse.setCourseName("javaコース");

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void 受講生詳細の受講生コース情報でidに数字以外を用いた時に入力チェックに掛かること() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(null);
        studentCourse.setStudentId(1);
        studentCourse.setCourseName("javaコース");

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

        assertThat(violations.size()).isEqualTo(1);
    }
}