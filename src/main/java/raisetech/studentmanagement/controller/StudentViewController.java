package raisetech.studentmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;
import raisetech.studentmanagement.service.StudentService;

import java.util.Arrays;

/**
 * 受講生の検索や登録、更新などを画面（HTML）経由で操作するためのControllerです。
 */
@Controller
public class StudentViewController {

    private StudentService service;

    private static final Logger logger = LoggerFactory.getLogger(StudentViewController.class);

    @Autowired
    public StudentViewController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生一覧（全件）
     */
    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        model.addAttribute("studentList", service.searchStudentList());
        return "studentList";
    }


    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生
     */

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable Integer id, Model model) {
        StudentDetail studentDetail = service.searchStudent(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    /**
     * 受講生登録画面を表示します。
     * <p>
     * フォーム入力用の空の受講生情報を生成し、画面に渡します。
     *
     * @param model 画面に渡すモデル
     * @return 受講生登録画面
     */
    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    /**
     * 入力された受講生情報を登録します。
     * <p>
     * 入力チェックにエラーがある場合は登録画面に戻し、
     * 問題がなければ受講生情報を登録して一覧画面へリダイレクトします。
     *
     * @param studentDetail 画面から送信された受講生情報
     * @param result        バリデーション結果
     * @return 受講生一覧画面
     */

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }

        service.registerStudent(studentDetail);
        return "redirect:/studentList";
    }

    /**
     * 受講生更新です。
     *
     * @param studentDetail 受講生詳細
     * @param result        バリデーション結果
     * @return 受講生一覧画面
     */
    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }

        service.updateStudent(studentDetail);
        logger.info("update id={}", studentDetail.getStudent().getId());
        return "redirect:/studentList";
    }

}
