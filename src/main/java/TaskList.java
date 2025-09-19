import items.Deadline;
import items.Event;
import items.Task;

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

    public void removeTaskFromList(int index) {
        taskList.remove(index);
    }

    public void addDeadlineToList(String str, String dueBy) {
        taskList.add(new Deadline(str, dueBy));
    }

    public void addEventToList(String str, String startTime, String endTime) {
        taskList.add(new Event(str, startTime, endTime));
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
