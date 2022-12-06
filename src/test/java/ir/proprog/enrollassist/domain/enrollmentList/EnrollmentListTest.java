package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.domain.EnrollmentRules.*;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.major.Major;
import ir.proprog.enrollassist.domain.program.Program;
import ir.proprog.enrollassist.domain.section.ExamTime;
import ir.proprog.enrollassist.domain.section.PresentationSchedule;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class EnrollmentListTest {
    private EnrollmentList enrollmentList;
    private final int expectedViolationsCount;
    private List<EnrollmentRuleViolation> expectedViolations;

    // courses
    private static Course math1, phys1;
    private static Course math2, phys2;
    private static Course farsi, english, history, sport;
    private static Course programming, ap, dm, os, internship;
    private static Major ce;
    private static Program ceProgram;
    private static Student mahdi;

    // sections
    private static Section math1_1, phys1_1;
    private static Section math2_1, math2_2;
    private static Section phys2_1, phys2_2;
    private static Section english_1, farsi_1, history_1, sport_1;
    private static Section ap_1, dm_1, os_1, internship_1;

    public EnrollmentListTest(EnrollmentList enrollmentList, int expectedViolationsSize, List<EnrollmentRuleViolation> expectedViolations) {
        this.enrollmentList = enrollmentList;
        this.expectedViolationsCount = expectedViolationsSize;
        this.expectedViolations = expectedViolations;
    }

//    @BeforeAll
//    public static void setup() throws Exception {
//        populateCourses();
//    }

    static void populateCourses() throws Exception {
        math1 = new Course("8101101", "MATH1", 3, "Undergraduate");
        phys1 = new Course("8101102", "PHYS1", 3, "Undergraduate");
        math2 = new Course("8101103", "MATH2", 3, "Undergraduate").withPre(math1);
        phys2 = new Course("8101104", "PHYS2", 3, "Undergraduate").withPre(math1, phys1);
        farsi = new Course("8101105", "FA", 2, "Undergraduate");
        history = new Course("8101111", "HISTORY", 2, "Undergraduate");
        english = new Course("8101106", "EN", 2, "Undergraduate");
        programming = new Course("8101107", "PROG", 3, "Undergraduate");
        ap = new Course("8101108", "AP", 3, "Undergraduate").withPre(programming);
        dm = new Course("8101109", "DM", 3, "Undergraduate").withPre(math1);
        os = new Course("8101110", "OS", 4, "Undergraduate").withPre(ap);
        internship = new Course("8101112", "INTERN", 3, "Undergraduate");
        sport = new Course("8101113", "SPORT", 2, "Undergraduate");

        ce = new Major("8101", "CE", "Engineering");
        ceProgram = new Program(ce, "Undergraduate", 140, 140, "Major");
        ceProgram.addCourse(math1, math2, phys1, phys2, farsi, english, programming, ap, dm, os);

        mahdi = new Student("810198465", "Undergraduate")
                .setGrade("13981", math1, 17.5)
                .setGrade("13981", phys1, 9)
                .setGrade("13981", programming, 20);
        mahdi.addProgram(ceProgram);

        ExamTime exam0 = new ExamTime("2021-07-10T09:00", "2021-07-10T11:00");
        ExamTime exam1 = new ExamTime("2021-07-11T09:00", "2021-07-11T11:00");
        ExamTime exam2 = new ExamTime("2021-07-12T09:00", "2021-07-12T11:00");
        ExamTime exam3 = new ExamTime("2021-07-13T09:00", "2021-07-13T11:00");
        ExamTime exam4 = new ExamTime("2021-07-14T09:00", "2021-07-14T11:00");
        ExamTime exam5 = new ExamTime("2021-07-15T09:00", "2021-07-15T11:00");
        ExamTime exam6 = new ExamTime("2021-07-16T09:00", "2021-07-16T11:00");
        ExamTime exam7 = new ExamTime("2021-07-17T09:00", "2021-07-17T11:00");
        ExamTime exam8 = new ExamTime("2021-07-18T09:00", "2021-07-18T11:00");
        ExamTime exam9 = new ExamTime("2021-07-19T09:00", "2021-07-19T11:00");
        ExamTime exam10 = new ExamTime("2021-07-20T09:00", "2021-07-20T11:00");

        math1_1 = new Section(math1, "01", exam0, Collections.singleton(new PresentationSchedule("Sunday", "07:30", "09:00")));
        phys1_1 = new Section(phys1, "01", exam1, Collections.singleton(new PresentationSchedule("Monday", "09:00", "10:30")));
        math2_1 = new Section(math2, "01", exam0, Collections.singleton(new PresentationSchedule("Saturday", "07:30", "09:00")));
        math2_2 = new Section(math2, "02", exam1, Collections.singleton(new PresentationSchedule("Saturday", "14:00", "15:30")));
        phys2_1 = new Section(phys2, "01", exam2, Collections.singleton(new PresentationSchedule("Monday", "09:00", "10:30")));
        phys2_2 = new Section(phys2, "02", exam2, Collections.singleton(new PresentationSchedule("Tuesday", "10:30", "12:00")));
        farsi_1 = new Section(farsi, "01", exam3, Collections.singleton(new PresentationSchedule("Wednesday", "14:00", "15:30")));
        history_1 = new Section(history, "01", exam4, Collections.singleton(new PresentationSchedule("Wednesday", "10:00", "12:00")));
        english_1 = new Section(english, "01", exam5, Collections.singleton(new PresentationSchedule("Wednesday", "08:00", "10:00")));
        ap_1 = new Section(ap, "01", exam6, Collections.singleton(new PresentationSchedule("Tuesday", "10:30", "12:00")));
        dm_1 = new Section(dm, "01", exam7, Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        os_1 = new Section(os, "01", exam8, Collections.singleton(new PresentationSchedule("Monday", "10:30", "12:00")));
        internship_1 = new Section(internship, "01", exam6, Collections.singleton(new PresentationSchedule("Thursday", "16:00", "19:00")));
        sport_1 = new Section(sport, "01", exam9, Collections.singleton(new PresentationSchedule("Thursday", "16:00", "17:30")));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws Exception {
        populateCourses();

        EnrollmentList enrollmentListWithValid = new EnrollmentList("valid", mahdi);
        enrollmentListWithValid.addSections(math2_1, ap_1, dm_1, english_1, history_1);

        EnrollmentList enrollmentListWithPreReqNotPassed = new EnrollmentList("pre-requisite not token", mahdi);
        enrollmentListWithPreReqNotPassed.addSections(math2_1, dm_1, os_1, english_1);

        EnrollmentList enrollmentListWithCourseAlreadyPassed = new EnrollmentList("course already passed", mahdi);
        enrollmentListWithCourseAlreadyPassed.addSections(math1_1, phys1_1, ap_1, farsi_1, history_1);

        EnrollmentList enrollmentListWithCourseRequestedTwice = new EnrollmentList("course requested twice", mahdi);
        enrollmentListWithCourseRequestedTwice.addSections(math2_1, math2_2, ap_1, english_1, farsi_1);

        EnrollmentList enrollmentListWithMaxCreditsLimitAndExamConflict = new EnrollmentList("max credits limit and exam conflict", mahdi);
        enrollmentListWithMaxCreditsLimitAndExamConflict.addSections(math2_1, phys1_1, ap_1, dm_1, history_1, farsi_1, english_1, internship_1);

        EnrollmentList enrollmentListWithClassScheduleConflict = new EnrollmentList("presentation schedule conflict", mahdi);
        enrollmentListWithClassScheduleConflict.addSections(math2_1, phys1_1, english_1, history_1, internship_1, sport_1);

        EnrollmentList enrollmentListWithExamTimeCollision = new EnrollmentList("exam time collision", mahdi);
        enrollmentListWithExamTimeCollision.addSections(math2_1, phys1_1, ap_1, history_1, english_1, internship_1);

        EnrollmentList minCreditsLimitEnrollmentList = new EnrollmentList("EnrollmentListTest", mahdi);

        return Arrays.asList(new Object[][]{
                {enrollmentListWithValid, 0, Arrays.asList()},
                {enrollmentListWithPreReqNotPassed, 1, Arrays.asList(new PrerequisiteNotTaken(os, ap))},
                {enrollmentListWithCourseAlreadyPassed, 1, Arrays.asList(new RequestedCourseAlreadyPassed(math1))},
                {enrollmentListWithCourseRequestedTwice, 1, Arrays.asList(new CourseRequestedTwice(math2))},
                {enrollmentListWithMaxCreditsLimitAndExamConflict, 2, Arrays.asList(new MaxCreditsLimitExceeded(20), new ExamTimeCollision(ap_1, internship_1))},
                {enrollmentListWithClassScheduleConflict, 1, Arrays.asList(new ConflictOfClassSchedule(internship_1, sport_1))},
                {enrollmentListWithExamTimeCollision, 1, Arrays.asList(new ExamTimeCollision(ap_1, internship_1))},
                {minCreditsLimitEnrollmentList, 1, Arrays.asList(new MinCreditsRequiredNotMet(12))},
        });
    }

    @Test
    public void testEnrollmentRules() {
        List<EnrollmentRuleViolation> violations = enrollmentList.checkEnrollmentRules();
        Assertions.assertEquals(violations.size(), this.expectedViolationsCount);
        violations.sort(Comparator.comparing(Object::toString));
        this.expectedViolations.sort(Comparator.comparing(Object::toString));
        Assertions.assertEquals(this.expectedViolations.toString(), violations.toString());
    }
}
