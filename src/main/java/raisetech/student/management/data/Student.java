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
    @Pattern(regexp = "^[ァ-ヶー]+$",
            message = "カナは全角カタカナで入力してください"
    )
    @NotBlank
    private String kanaName;
    private String nickName;
    @Email
    @NotBlank
    private String email;
    private String area;
    @Min(0)
    @Max(120)
    private int age;
    private String gender;
    private String job;
    private String remark;
    private boolean deleted;
}
