package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクトの設計図
 */
@Schema(description = "受講生")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Schema(description = "ID", example = "10")
    private Integer id;

    @Schema(description = "氏名", example = "山田太郎")
    @Size(min = 1, max = 20)
    @NotBlank
    private String name;

    @Schema(description = "フリガナ", example = "ヤマダタロウ")
    @NotBlank
    @Pattern(regexp = "^[ァ-ヶー]+$")
    private String kanaName;

    @Schema(description = "ニックネーム", example = "やまちゃん")
    @NotBlank
    private String nickName;

    @Schema(description = "メールアドレス", example = "abc@mail.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "エリア", example = "東京都")
    @NotBlank
    private String area;

    @Schema(description = "年齢", example = "30")
    @Min(0)
    @Max(120)
    private int age;

    @Schema(description = "性別", example = "男")
    @NotBlank
    private String gender;

    @Schema(description = "職業", example = "会社員")
    private String job;

    @Schema(description = "備考", example = "備考です。")
    private String remark;

    @Schema(description = "削除フラグ", example = "true")
    private boolean deleted;

    public Student(Integer id) {
        setId(1);
    }
}

