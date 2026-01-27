import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public void remove(int index) {
        tasks.remove(index);
    }

    public void mark(int index) {
        Task t = tasks.get(index);
        t.mark();
    }

    public void unmark(int index) {
        Task t = tasks.get(index);
        t.unmark();
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
