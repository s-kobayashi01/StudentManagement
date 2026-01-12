package raisetech.studentmanagement.repository;

//このインターフェイスはデータベースそのものみたいなもの

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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

    @Insert("INSERT INTO students (name, kana_name, email) values (#{name}, #{kanaName}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertStudent(Student student);


    @Insert("INSERT INTO students_courses (students_id, course_name) values (#{studentsId}, #{courseName})")
    void insertStudentsCourses(StudentsCourses studentsCourses);
}
