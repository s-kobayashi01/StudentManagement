package raisetech.studentmanagement.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentsCourses {
    private Integer id;
    private Integer studentsId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
}
