package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.section.ExamTime;
import ir.proprog.enrollassist.domain.section.PresentationSchedule;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class checkExamTimeConflictsTest {
    private EnrollmentList enrollmentList;
    static private Course math, phys;

    @BeforeAll
    static void generateCourses() throws ExceptionList {
        math = new Course("8101101", "Math", 3, GraduateLevel.Undergraduate.toString());
        phys = new Course("8101102", "Physic", 3, GraduateLevel.Undergraduate.toString());
    }

    @BeforeEach
    public void setup() throws ExceptionList {
        Student student = new Student("810198465", GraduateLevel.Undergraduate.toString());
        enrollmentList = new EnrollmentList("my-list", student);
    }

    @Test
    public void singleExamTimeHasNotConflict() throws Exception {
        Section mathSection = new Section(math, "1", new ExamTime("2021-07-10T09:00", "2021-07-10T11:00"), Collections.singleton(new PresentationSchedule()));
        Section physSection = new Section(phys, "1");
        enrollmentList.addSections(mathSection, physSection);
        Assertions.assertEquals(0, enrollmentList.checkExamTimeConflicts().size());
    }

    @Test
    public void overlappedExamTimesHaveConflict() throws Exception {
        Section mathSection = new Section(math, "1", new ExamTime("2021-07-10T09:00", "2021-07-10T11:00"), Collections.singleton(new PresentationSchedule()));
        Section physSection = new Section(phys, "1", new ExamTime("2021-07-10T08:00", "2021-07-10T10:00"), Collections.singleton(new PresentationSchedule()));
        enrollmentList.addSections(mathSection, physSection);
        Assertions.assertEquals(1, enrollmentList.checkExamTimeConflicts().size());
    }

    @Test
    public void separateExamTimesHaveNotConflict() throws Exception {
        Section mathSection = new Section(math, "1", new ExamTime("2021-07-10T09:00", "2021-07-10T11:00"), Collections.singleton(new PresentationSchedule()));
        Section physSection = new Section(phys, "1", new ExamTime("2021-07-11T08:00", "2021-07-11T10:00"), Collections.singleton(new PresentationSchedule()));
        enrollmentList.addSections(mathSection, physSection);
        Assertions.assertEquals(0, enrollmentList.checkExamTimeConflicts().size());
    }
}
