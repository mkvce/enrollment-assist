package ir.proprog.enrollassist.domain.section;

import ir.proprog.enrollassist.Exception.ExceptionList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PresentationScheduleTest {
    PresentationSchedule presentationSchedule;
    String defaultDay = "Saturday";

    @BeforeEach
    public void setup() throws ExceptionList {
        presentationSchedule = new PresentationSchedule(defaultDay, "09:00", "10:30");
    }

    @Test
    public void DifferentDaysHasNotConflict() throws ExceptionList {
        PresentationSchedule otherPresentationSchedule = new PresentationSchedule("Monday",
                presentationSchedule.getStartTime(), presentationSchedule.getEndTime());
        Assertions.assertFalse(presentationSchedule.hasConflict(otherPresentationSchedule));
    }

    @Test
    public void SameDaysWithSeparateTimesHasNotConflict() throws ExceptionList {
        PresentationSchedule otherPresentationSchedule = new PresentationSchedule(defaultDay,
                "16:00", "17:30");
        Assertions.assertFalse(presentationSchedule.hasConflict(otherPresentationSchedule));
    }

    @Test
    public void ScheduleEndsBeforeAnotherHasNotConflict() throws ExceptionList {
        PresentationSchedule otherPresentationSchedule = new PresentationSchedule(defaultDay,
                "07:00", "09:00");
        Assertions.assertFalse(presentationSchedule.hasConflict(otherPresentationSchedule));
    }

    @Test
    public void ScheduleStartsAfterOtherHasNotConflict() throws ExceptionList {
        PresentationSchedule otherPresentationSchedule = new PresentationSchedule(defaultDay,
            "10:30", "12:00");
        Assertions.assertFalse(presentationSchedule.hasConflict(otherPresentationSchedule));
    }

    @Test
    public void ScheduleSurroundedByOtherHasConflict() throws ExceptionList {
        PresentationSchedule otherPresentationSchedule = new PresentationSchedule(defaultDay,
                "08:00", "12:00");
        Assertions.assertTrue(presentationSchedule.hasConflict(otherPresentationSchedule));
    }

    @Test
    public void ScheduleStartsInMiddleOfAnotherHasConflict() throws ExceptionList {
        PresentationSchedule otherPresentationSchedule = new PresentationSchedule(defaultDay,
                "10:00", "12:00");
        Assertions.assertTrue(presentationSchedule.hasConflict(otherPresentationSchedule));
    }
}
