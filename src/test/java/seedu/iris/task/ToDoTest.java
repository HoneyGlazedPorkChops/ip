package seedu.iris.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    @Test
    public void toString_newTodo() {
        ToDo todo = new ToDo("read book");

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void mark_todo_MarkedAsDone() {
        ToDo todo = new ToDo("read book");

        todo.mark();

        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void unmark_afterMark_todoNotDone() {
        ToDo todo = new ToDo("read book");

        todo.mark();
        todo.unmark();

        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void mark_twice_stillDone() {
        ToDo todo = new ToDo("read book");

        todo.mark();
        todo.mark();

        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void unmark_withoutMark_stillNotDone() {
        ToDo todo = new ToDo("read book");

        todo.unmark();

        assertEquals("[T][ ] read book", todo.toString());
    }
}