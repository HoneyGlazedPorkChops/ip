package seedu.iris.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks and provides operations to manage them.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param t the task to add
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index the 0-based index of the task to remove
     * @return the task that was removed
     */
    public Task remove(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        return task;
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index the 0-based index of the task to mark
     * @return the marked task at specified index
     */
    public Task mark(int index) {
        Task t = tasks.get(index);
        t.mark();
        return t;
    }

    /**
     * Unmarks the task at the specified index as not completed.
     *
     * @param index the 0-based index of the task to unmark
     * @return the unmarked task at specified index
     */
    public Task unmark(int index) {
        Task t = tasks.get(index);
        t.unmark();
        return t;
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the 0-based index of the task to unmark
     * @return the task at specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Retrieves the size of the task list.
     *
     * @return integer representing size task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves the list of all tasks.
     *
     * @return all tasks in a list.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}
