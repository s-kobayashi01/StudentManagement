package raisetech.student.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "受講生管理システム", description = "受講生情報を管理するためのシステムです。主に受講生の詳細情報と受講しているコース情報の管理を行えます。"))
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // localhost:8080
        SpringApplication.run(Application.class, args);
    }


}
