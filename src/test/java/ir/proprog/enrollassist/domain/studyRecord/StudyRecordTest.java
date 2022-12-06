package ir.proprog.enrollassist.domain.studyRecord;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class StudyRecordTest {
    private final GraduateLevel graduateLevel;
    private final String term;
    private final Course course;
    private final double grade;
    private final boolean expectedResult;

    public StudyRecordTest(GraduateLevel graduateLevel, String term, Course course, double grade, boolean expectedResult) {
        this.graduateLevel = graduateLevel;
        this.term = term;
        this.course = course;
        this.grade = grade;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ExceptionList {
        return Arrays.asList(new Object[][]{
                {GraduateLevel.Undergraduate, "14011", new Course("8101123", "Advanced Programming", 3, GraduateLevel.Undergraduate.toString()), 11, true},
                {GraduateLevel.Undergraduate, "14012", new Course("8101124", "Islamic Revolution", 2, GraduateLevel.Undergraduate.toString()), 9.5, false},
                {GraduateLevel.Masters, "14021", new Course("8101125", "Deep Learning", 3, GraduateLevel.Masters.toString()), 12, true},
                {GraduateLevel.Masters, "14022", new Course("8101126", "Formal Test", 3, GraduateLevel.Masters.toString()), 10, false},
                {GraduateLevel.PHD, "14041", new Course("8101127", "Neural Networks", 3, GraduateLevel.PHD.toString()), 15, true},
                {GraduateLevel.PHD, "14042", new Course("8101128", "Speech Recognition", 3, GraduateLevel.PHD.toString()), 11, false},
                // different graduate levels
                {GraduateLevel.Masters, "14012", new Course("8101124", "Islamic Revolution", 2, GraduateLevel.Undergraduate.toString()), 11, true},
                {GraduateLevel.PHD, "14021", new Course("8101123", "Advanced Programming", 3, GraduateLevel.Undergraduate.toString()), 10, true},
                {GraduateLevel.PHD, "14021", new Course("8101125", "Deep Learning", 3, GraduateLevel.Masters.toString()), 13, true},
        });
    }

    @Test
    public void TestIsPassed() throws ExceptionList {
        StudyRecord studyRecord = new StudyRecord(term, course, grade);
        Assert.assertEquals(expectedResult, studyRecord.isPassed(graduateLevel));
    }
}
