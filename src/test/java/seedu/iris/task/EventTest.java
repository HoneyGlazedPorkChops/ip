package seedu.iris.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
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
    public void toString_newEvent() {
        LocalDateTime start = parseDateTime("2030-12-02 16:00");
        LocalDateTime end = parseDateTime("2030-12-03 18:00");
        Event Event = new Event("project meeting", start, end);

        assertEquals("[E][ ] project meeting (from: 2030-12-02 16:00 to: 2030-12-03 18:00)", Event.toString());
    }

    @Test
    public void toString_newEvent_isOver() {
        LocalDateTime start = parseDateTime("2010-12-02 16:00");
        LocalDateTime end = parseDateTime("2010-12-03 18:00");
        Event Event = new Event("project meeting", start, end);

        assertEquals("[E][ ] project meeting (from: 2010-12-02 16:00 to: 2010-12-03 18:00) OVER :(", Event.toString());
    }

    @Test
    public void mark_Event_MarkedAsDone() {
        LocalDateTime start = parseDateTime("2010-12-02 16:00");
        LocalDateTime end = parseDateTime("2010-12-03 18:00");
        Event Event = new Event("project meeting", start, end);
        Event.mark();

        assertEquals("[E][X] project meeting (from: 2010-12-02 16:00 to: 2010-12-03 18:00) OVER :(", Event.toString());
    }

    @Test
    public void unmark_afterMark_EventNotDone() {
        LocalDateTime start = parseDateTime("2030-12-02 16:00");
        LocalDateTime end = parseDateTime("2030-12-03 18:00");
        Event Event = new Event("project meeting", start, end);
        Event.mark();
        Event.unmark();

        assertEquals("[E][ ] project meeting (from: 2030-12-02 16:00 to: 2030-12-03 18:00)", Event.toString());
    }

    @Test
    public void mark_twice_stillDone() {
        LocalDateTime start = parseDateTime("2030-12-02 16:00");
        LocalDateTime end = parseDateTime("2030-12-03 18:00");
        Event Event = new Event("project meeting", start, end);
        Event.mark();
        Event.mark();

        assertEquals("[E][X] project meeting (from: 2030-12-02 16:00 to: 2030-12-03 18:00)", Event.toString());
    }

    @Test
    public void unmark_withoutMark_stillNotDone() {
        LocalDateTime start = parseDateTime("2030-12-02 16:00");
        LocalDateTime end = parseDateTime("2030-12-03 18:00");
        Event Event = new Event("project meeting", start, end);
        Event.unmark();

        assertEquals("[E][ ] project meeting (from: 2030-12-02 16:00 to: 2030-12-03 18:00)", Event.toString());
    }
}