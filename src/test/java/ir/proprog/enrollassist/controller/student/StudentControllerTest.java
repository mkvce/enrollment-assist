package ir.proprog.enrollassist.controller.student;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.student.StudentNumber;
import ir.proprog.enrollassist.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class StudentControllerTest {
    private StudentController studentController;
    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private SectionRepository sectionRepo;
    private EnrollmentListRepository enrollmentListRepo;
    private UserRepository userRepo;
    private List<StudentView> studentViews;
    private List<Student> students;

    public StudentControllerTest() {
    }

    @BeforeEach
    public void setup() throws ExceptionList {
        studentRepo = Mockito.mock(StudentRepository.class);
        sectionRepo = Mockito.mock(SectionRepository.class);
        userRepo = Mockito.mock(UserRepository.class);

        Student s1 = new Student("810198001", GraduateLevel.Undergraduate.toString());
        Student s2 = new Student("810100002", GraduateLevel.Undergraduate.toString());
        Student s3 = new Student("810100003", GraduateLevel.Undergraduate.toString());

        students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        studentViews = new ArrayList<>();
        studentViews.add(new StudentView(s1));
        studentViews.add(new StudentView(s2));
        studentViews.add(new StudentView(s3));

        studentController = new StudentController(studentRepo, courseRepo, sectionRepo, enrollmentListRepo, userRepo);
    }

    @Test
    public void testOneReturnsOneStudent() {
        Student student = students.get(1);
        StudentView studentView = studentViews.get(1);
        when(studentRepo.findByStudentNumber(student.getStudentNumber())).thenReturn(Optional.of(student));
        org.assertj.core.api.Assertions.assertThat(studentView).usingRecursiveComparison().isEqualTo(studentController.one(student.getStudentNumber().getNumber()));
    }
}
