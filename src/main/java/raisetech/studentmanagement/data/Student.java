package raisetech.studentmanagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

    private Integer id;
    private String name;
    private String kanaName;
    private String nickName;
    private String email;
    private String area;
    private int age;
    private String gender;
    private String job;
    private String remark;
    private boolean deleted;
}
