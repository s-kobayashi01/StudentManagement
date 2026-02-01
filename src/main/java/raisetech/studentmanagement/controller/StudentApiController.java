package raisetech.studentmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@RestController
public class StudentApiController {

    private StudentService service;

    private static final Logger logger = LoggerFactory.getLogger(StudentApiController.class);


    @Autowired
    public StudentApiController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生一覧（全件）
     */
    @GetMapping("/api/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生
     */

    @GetMapping("/api/student/{id}")
    public StudentDetail getStudent(@PathVariable Integer id) {
        return service.searchStudent(id);
    }

    /**
     * 受講生登録です。
     *
     * @param studentDetail 受講生詳細
     * @return 登録後の受講生情報（採番されたIDを含む）を返却します。
     */
    @PostMapping("/api/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail resoponseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(resoponseStudentDetail);
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 受講生詳細
     * @return 更新処理が成功しました。とメッセージのレスポンスを返却します。
     */
    @PostMapping("/api/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {


        service.updateStudent(studentDetail);
        logger.info("update id={}", studentDetail.getStudent().getId());
        return ResponseEntity.ok("更新処理が成功しました。");
    }

}
