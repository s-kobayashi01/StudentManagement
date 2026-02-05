package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 受講生コース情報を扱うオブジェクトの設計図
 */

@Getter
@Setter
public class StudentCourse {
    private Integer id;
    private Integer studentsId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
}
