package seedu.iris.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        return task;
    }

    public Task mark(int index) {
        Task t = tasks.get(index);
        t.mark();
        return t;
    }

    public Task unmark(int index) {
        Task t = tasks.get(index);
        t.unmark();
        return t;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }
}
