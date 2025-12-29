package raisetech.studentmanagement.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentsCourses {
    private String id;
    private String studentsId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
}
