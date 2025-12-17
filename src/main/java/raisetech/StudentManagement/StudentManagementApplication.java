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
    Map<String, String> student = new HashMap<>();

	public static void main(String[] args) {
    // localhost:8080
		SpringApplication.run(StudentManagementApplication.class, args);
	}

  @GetMapping("/student")
  public String getStudentName() {
    return student.toString();
  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(String name, String age) {
    student.put(name,age);
  }

}
