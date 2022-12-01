package ir.proprog.enrollassist.controller.student;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.user.User;
import ir.proprog.enrollassist.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

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
    private List<User> users;

    public StudentControllerTest() {
    }

    @BeforeEach
    public void setup() throws ExceptionList {
        studentRepo = Mockito.mock(StudentRepository.class);
        sectionRepo = Mockito.mock(SectionRepository.class);
        userRepo = Mockito.mock(UserRepository.class);

        Student s1 = new Student("810198001", GraduateLevel.Undergraduate.toString());
        Student s2 = new Student("810198002", GraduateLevel.Undergraduate.toString());
        Student s3 = new Student("810198003", GraduateLevel.Undergraduate.toString());

        students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        studentViews = new ArrayList<>();
        studentViews.add(new StudentView(s1));
        studentViews.add(new StudentView(s2));
        studentViews.add(new StudentView(s3));

        when(userRepo.findByUserId(any())).thenReturn(Optional.of(new User("Mahdi", "123")));

        studentController = new StudentController(studentRepo, courseRepo, sectionRepo, enrollmentListRepo, userRepo);
    }

    @Test
    // stub
    // state verification
    // classical
    public void testOneReturnsCorrectStudent() {
        Student student = students.get(1);
        StudentView studentView = studentViews.get(1);
        when(studentRepo.findByStudentNumber(student.getStudentNumber())).thenReturn(Optional.of(student));
        Assertions.assertThat(studentView).usingRecursiveComparison().isEqualTo(studentController.one(student.getStudentNumber().getNumber()));
    }

    @Test
    // spy
    // behaviour verification
    // mockisty
    public void testAddStudentSavesInStudentRepository() throws ExceptionList {
        Student student = new Student("810100101", GraduateLevel.Undergraduate.toString());
        studentController.addStudent(new StudentView(student));
        verify(studentRepo, times(1)).save(student);
    }
}
