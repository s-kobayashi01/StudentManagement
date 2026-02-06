package raisetech.student.management.data;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクトの設計図
 */

@Getter
@Setter
public class Student {

    private Integer id;

    @Size(min = 1, max = 20)
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^[ァ-ヶー]+$",
            message = "カナは全角カタカナで入力してください"
    )
    private String kanaName;

    @NotBlank
    private String nickName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String area;

    @NotBlank
    @Min(0)
    @Max(120)
    private int age;

    @NotBlank
    private String gender;

    @NotBlank
    private String job;
    private String remark;
    private boolean deleted;
}
