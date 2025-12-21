package raisetech.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

    //Spring Bootが管理しているインスタンスを自動的に紐づけてくれる
    @Autowired
    private StudentRepository repository;

    public static void main(String[] args) {
        // localhost:8080
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    @GetMapping("/student")
    public List<Student> getStudent() {
        return repository.searchByName();
    }

    @PostMapping("/student")
    public void registerStudent(String name, int age) {
        repository.registerStudent(name, age);
    }

    @PatchMapping("/student")
    public void updateStudent(String name, int age) {
        repository.updateStudent(name, age);
    }

    @DeleteMapping("/student")
    public void deleteStudent(String name) {
        repository.deleteStudent(name);
    }
}
