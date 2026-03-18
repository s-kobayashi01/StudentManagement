package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 受講生コース情報を扱うオブジェクトの設計図
 */
@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

    @Schema(description = "ID", example = "20")
    private Integer id;

    @Schema(description = "受講生ID　※StudentのIDに紐づいています。", example = "10")
    private Integer studentId;

    @Schema(description = "コース名", example = "javaコース")
    private String courseName;

    @Schema(description = "受講開始日", example = "2026-02-01T13:47:10")
    private LocalDateTime courseStartAt;

    @Schema(description = "受講修了予定日", example = "2027-02-01T13:47:10")
    private LocalDateTime courseEndAt;
}
