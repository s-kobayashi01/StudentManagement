package raisetech.studentmanagement;

//このインターフェイスはデータベースそのものみたいなもの

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//インターフェイスは本来インスタンス生成できないが@MapperがついてるとMyBatisがインスタンス生成してくれる
@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students")
    List<Student> search();

}
