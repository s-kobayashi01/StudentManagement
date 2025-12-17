package raisetech.StudentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class StudentManagementApplication {
   private String name = "Kobayashi Satoru";
   private String age = "31";
   private Map<String, String> student = new HashMap<>();

	public static void main(String[] args) {
    // localhost:8080
		SpringApplication.run(StudentManagementApplication.class, args);
	}

    @GetMapping("/studentInfo")
    public String getStudentInfo() {
        return name + " " + age + "歳";
    }

  @GetMapping("/student")
  public String getStudentName() {
    return student.toString();
  }

  @PostMapping("/studentMap")
  public void setStudentMap(String name, String age) {
    student.put(name,age);
  }

    @PostMapping("/studentInfo")
    public void setStudentInfo(String name, String age) {
      this.name = name;
      this.age = age;
    }

    @PostMapping("/studentName")
    public void updateStudentName(String name) {
        this.name = name;
    }
}
