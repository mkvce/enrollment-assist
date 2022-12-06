package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.studyRecord.Grade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class checkValidGPALimitTest {
    private Student owner;
    private EnrollmentList enrollmentList;

    @BeforeEach
    public void setup() {
        owner = Mockito.mock(Student.class);
        enrollmentList = new EnrollmentList("my-list", owner);
        enrollmentList.sections = new ArrayList<>();
    }


    @Test
    public void creditsLessThanGraduateLevelMinIsInvalid() throws Exception {
        when(owner.calculateGPA()).thenReturn(new Grade(18));
        when(owner.getGraduateLevel()).thenReturn(GraduateLevel.PHD);
        Course course = new Course("8101101", "math", 1, GraduateLevel.Undergraduate.toString());
        enrollmentList.sections.add(new Section(course, "1"));
        Assertions.assertEquals(1, enrollmentList.checkValidGPALimit().size());
    }

    @Test
    public void undergraduateWithGPAZeroAndMoreThan20CreditsIsInvalid() throws Exception {
        when(owner.calculateGPA()).thenReturn(Grade.ZERO);
        when(owner.getGraduateLevel()).thenReturn(GraduateLevel.Undergraduate);
        Course course = new Course("8101101", "math", 4, GraduateLevel.Undergraduate.toString());
        for (int i = 0; i < 6; i++) {
            enrollmentList.sections.add(new Section(course, Integer.toString(i)));
        }
        Assertions.assertEquals(1, enrollmentList.checkValidGPALimit().size());
    }

    @Test
    public void undergraduateWithGPALessThan12AndMoreThan14CreditsIsInvalid() throws Exception {
        when(owner.calculateGPA()).thenReturn(new Grade(11));
        when(owner.getGraduateLevel()).thenReturn(GraduateLevel.Undergraduate);
        Course course = new Course("8101101", "math", 3, GraduateLevel.Undergraduate.toString());
        for (int i = 0; i < 5; i++) {
            enrollmentList.sections.add(new Section(course, Integer.toString(i)));
        }
        Assertions.assertEquals(1, enrollmentList.checkValidGPALimit().size());
    }

    @Test
    public void undergraduateWithGPALessThan17AndMoreThan20CreditsIsInvalid() throws Exception {
        when(owner.calculateGPA()).thenReturn(new Grade(16));
        when(owner.getGraduateLevel()).thenReturn(GraduateLevel.Undergraduate);
        Course course = new Course("8101101", "math", 3, GraduateLevel.Undergraduate.toString());
        for (int i = 0; i < 7; i++) {
            enrollmentList.sections.add(new Section(course, Integer.toString(i)));
        }
        Assertions.assertEquals(1, enrollmentList.checkValidGPALimit().size());
    }

    @Test
    public void creditsMoreThenGraduateLevelMaxIsInvalid() throws Exception {
        when(owner.calculateGPA()).thenReturn(new Grade(20));
        when(owner.getGraduateLevel()).thenReturn(GraduateLevel.Masters);
        Course course = new Course("8101101", "math", 4, GraduateLevel.Undergraduate.toString());
        for (int i = 0; i < 4; i++) {
            enrollmentList.sections.add(new Section(course, Integer.toString(i)));
        }
        Assertions.assertEquals(1, enrollmentList.checkValidGPALimit().size());
    }
}
