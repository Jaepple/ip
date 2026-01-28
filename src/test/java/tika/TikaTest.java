package tika;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TikaTest {
    @Test
    public void toggleIsDone_changesStatus() {
        ToDo todo = new ToDo("read book");
        assertFalse(todo.isDone);
        todo.toggleIsDone();
        assertTrue(todo.isDone);
        todo.toggleIsDone();
        assertFalse(todo.isDone);
    }

    @Test
    public void deadlineToString() {
        Deadline d = new Deadline("return book", "2026-02-05 1800");
        assertEquals("[D][ ] return book (by: Feb 05 2026 6:00 pm)", d.toString());
    }
}
