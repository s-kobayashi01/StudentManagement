package raisetech.studentmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;

import java.util.List;

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

    private Student student;
    private List<StudentsCourses> studentsCourses;
}
