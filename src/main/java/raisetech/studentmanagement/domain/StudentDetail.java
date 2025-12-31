package raisetech.studentmanagement.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;

import java.util.List;

@Getter
@Setter
public class StudentDetail {

    private Student student;
    private List<StudentsCourses> studentsCourses;
}
