package raisetech.student.management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

import java.util.List;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * 受講生情報と、その受講生が受講しているコース情報をまとめて扱うクラスです。
 *
 * ControllerやService層において、画面表示やAPIレスポンス用に
 * 受講生の詳細情報をまとめて扱うために使用します。
 */
public class StudentDetail {

    @Schema(description = "受講生")
    @Valid
    private Student student;

    @Schema(description = "受講コース情報")
    @Valid
    private List<StudentCourse> studentCourseList;
}
