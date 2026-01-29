package seedu.iris.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    private static LocalDateTime parseDateTime(String input) {
        DateTimeFormatter dateTimeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDateTime.parse(input, dateTimeFmt);
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(input, dateFmt);
                return date.atStartOfDay();
            } catch (DateTimeParseException ex) {
                throw new DateTimeParseException("Invalid date/time format", input, 0);
            }
        }
    }

    @Test
    public void toString_newDeadline() {
        LocalDateTime date = parseDateTime("2028-10-15");
        Deadline deadline = new Deadline("return book", date);

        assertEquals("[D][ ] return book (by: 2028-10-15 00:00)", deadline.toString());
    }

    @Test
    public void toString_newDeadline_Overdue() {
        LocalDateTime date = parseDateTime("2019-10-15");
        Deadline deadline = new Deadline("return book", date);

        assertEquals("[D][ ] return book (by: 2019-10-15 00:00) ⚠ OVERDUE", deadline.toString());
    }

    @Test
    public void mark_Deadline_MarkedAsDone() {
        LocalDateTime date = parseDateTime("2028-10-15");
        Deadline deadline = new Deadline("return book", date);
        deadline.mark();

        assertEquals("[D][X] return book (by: 2028-10-15 00:00)", deadline.toString());
    }

    @Test
    public void unmark_afterMark_deadlineNotDone() {
        LocalDateTime date = parseDateTime("2028-10-15");
        Deadline deadline = new Deadline("return book", date);
        deadline.mark();
        deadline.unmark();

        assertEquals("[D][ ] return book (by: 2028-10-15 00:00)", deadline.toString());
    }

    @Test
    public void mark_twice_stillDone() {
        LocalDateTime date = parseDateTime("2028-10-15");
        Deadline deadline = new Deadline("return book", date);
        deadline.mark();
        deadline.mark();

        assertEquals("[D][X] return book (by: 2028-10-15 00:00)", deadline.toString());
    }

    @Test
    public void unmark_withoutMark_stillNotDone() {
        LocalDateTime date = parseDateTime("2028-10-15");
        Deadline deadline = new Deadline("return book", date);
        deadline.unmark();

        assertEquals("[D][ ] return book (by: 2028-10-15 00:00)", deadline.toString());
    }
}

