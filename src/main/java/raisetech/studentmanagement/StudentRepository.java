package raisetech.studentmanagement;

//このインターフェイスはデータベースそのものみたいなもの

import org.apache.ibatis.annotations.*;

import java.util.List;

//インターフェイスは本来インスタンス生成できないが@MapperがついてるとMyBatisがインスタンス生成してくれる
@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM student")
    List<Student> searchByName();

    @Insert("INSERT INTO student (name, age) values(#{name}, #{age})")
    void registerStudent(
            //明示的なパラメータ名指定
            @Param("name") String name,
            @Param("age") int age
    );

    @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
    void updateStudent(
            @Param("name") String name,
            @Param("age") int age
    );


    @Delete("DELETE FROM student WHERE name = #{name}")
    void deleteStudent(
            @Param("name") String name
    );
}
