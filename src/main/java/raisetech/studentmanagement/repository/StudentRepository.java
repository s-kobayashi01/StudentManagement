package raisetech.studentmanagement.repository;

//このインターフェイスはデータベースそのものみたいなもの

import org.apache.ibatis.annotations.*;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;

import java.util.List;

//インターフェイスは本来インスタンス生成できないが@MapperがついてるとMyBatisがインスタンス生成してくれる
@Mapper
public interface StudentRepository {
    @Select("SELECT * FROM students")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchCourses();

    @Insert("INSERT INTO students (name, kana_name, nick_name, email, area, age, gender, remark, is_deleted, job) " +
            "values (#{name}, #{kanaName}, #{nickName}, #{email}, #{area}, #{age}, #{gender}, #{remark}, false, #{job})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);


    @Insert("INSERT INTO students_courses (students_id, course_name, course_start_at, course_end_at) " +
            "values (#{studentsId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentsCourses(StudentsCourses studentsCourses);


    @Select("SELECT * FROM students WHERE id = #{id}")
    Student student(Integer id);


    @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nick_name = #{nickName}, email = #{email}, " +
            "area = #{area}, age = #{age}, gender = #{gender}, remark = #{remark}, job = #{job} WHERE id = #{id}")
    int updateStudent(Student student);
}
