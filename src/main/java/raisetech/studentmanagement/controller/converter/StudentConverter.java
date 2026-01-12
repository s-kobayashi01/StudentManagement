package raisetech.studentmanagement.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.studentmanagement.data.Student;
import raisetech.studentmanagement.data.StudentsCourses;
import raisetech.studentmanagement.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentConverter {
    public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentsCourses> studentsCourses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        for (Student student : students) {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentsCourses> convertStudentCourses = new ArrayList<>();
            for (StudentsCourses studentsCourse : studentsCourses) {
                if (student.getId().equals(studentsCourse.getStudentsId())) {
                    convertStudentCourses.add(studentsCourse);
                }
            }

            studentDetail.setStudentsCourses(convertStudentCourses);
            studentDetails.add(studentDetail);
        }
        return studentDetails;
    }
}
