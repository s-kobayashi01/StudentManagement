package raisetech.StudentManagement;

//このインターフェイスはデータベースそのものみたいなもの

import org.apache.ibatis.annotations.*;

import java.util.List;

//インターフェイスは本来インスタンス生成できないが@MapperがついてるとMyBatisがインスタンス生成してくれる
@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM student")
   List<Student> searchByName();

    @Insert("INSERT student values(#{name}, #{age})")
    void registerStudent(String name, int age);

    @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
    void updateStudent(String name, int age);

    @Delete("DELETE FROM student WHERE name = #{name}")
    void deleteStudent(String name);
}
