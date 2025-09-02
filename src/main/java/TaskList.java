import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList = new ArrayList<Task>();

    public TaskList() {
        this.taskList.add(new Task("ROOTTASK"));
    }

    public int getTaskListLength() {
        return taskList.size();
    }

    public void addTaskToList(String str) {
        taskList.add(new Task(str));
    }

    public Task getTaskFromList(int index) {
        return taskList.get(index);
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "taskList=" + taskList +
                '}';
    }
}
