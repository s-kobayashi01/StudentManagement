package raisetech.studentmanagement.repository;


import org.apache.ibatis.annotations.*;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentCourse;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
//インターフェイスは本来インスタンス生成できないが@MapperがついてるとMyBatisがインスタンス生成してくれる
@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います。
     *
     * @return 受講生一覧（全件）
     */
    @Select("SELECT id, name, kana_name, nick_name, email, area, age, gender, job, remark, is_deleted AS deleted FROM students")
    List<Student> search();

    /**
     * 受講生のコース情報の全件検索を行います。
     *
     * @return 受講生のコース情報（全件）
     */
    @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生の検索を行います。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    @Select("SELECT id, name, kana_name, nick_name, email, area, age, gender, job, remark, is_deleted AS deleted FROM students WHERE id = #{id}")
    Student searchStudent(Integer id);

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     *
     * @param id 受講生ID
     * @return 受講生IDに紐づく受講生コース情報
     */
    @Select("SELECT * FROM students_courses WHERE students_id = #{studentsId}")
    List<StudentCourse> searchStudentCourse(@Param("studentsId") Integer id);


    /**
     * 受講生を新規登録します。 IDに関しては自動採番を行う。
     *
     * @param student 受講生
     */
    @Insert("INSERT INTO students (name, kana_name, nick_name, email, area, age, gender, remark, is_deleted, job) " +
            "values (#{name}, #{kanaName}, #{nickName}, #{email}, #{area}, #{age}, #{gender}, #{remark}, false, #{job})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
     *
     * @param studentCourse 受講生コース情報
     */
    @Insert("INSERT INTO students_courses (students_id, course_name, course_start_at, course_end_at) " +
            "values (#{studentsId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生を更新します。
     *
     * @param student 受講生
     */
    @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nick_name = #{nickName}, email = #{email}, " +
            "area = #{area}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{deleted}, job = #{job} WHERE id = #{id}")
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 受講生コース情報
     */
    @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
    void updateStudentCourse(StudentCourse studentCourse);
}
