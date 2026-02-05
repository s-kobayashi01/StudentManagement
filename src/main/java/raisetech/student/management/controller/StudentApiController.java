package raisetech.student.management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentApiController {

    private StudentService service;

    private static final Logger logger = LoggerFactory.getLogger(StudentApiController.class);


    @Autowired
    public StudentApiController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生詳細一覧（全件）
     */
    @GetMapping("/api/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    /**
     * 受講生詳細の検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    @GetMapping("/api/student/{id}")
    public StudentDetail getStudent(@PathVariable @Min(1) @Max(999) Integer id) {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細の登録です。
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PostMapping("/api/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail resoponseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(resoponseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います。（論理削除）
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @PutMapping("/api/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        logger.info("update id={}", studentDetail.getStudent().getId());
        return ResponseEntity.ok("更新処理が成功しました。");
    }

}
