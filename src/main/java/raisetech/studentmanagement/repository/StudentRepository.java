package raisetech.studentmanagement.repository;

//このインターフェイスはデータベースそのものみたいなもの

import org.apache.ibatis.annotations.Mapper;
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

}
