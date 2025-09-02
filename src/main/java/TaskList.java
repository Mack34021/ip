import java.util.ArrayList;

public class TaskList {
    private ArrayList<String> taskList = new ArrayList<String>();

    public TaskList() {
        this.taskList.add("LISTROOT");
    }

    public int getTaskListLength() {
        return taskList.size();
    }

    public void addTaskToList(String str) {
        taskList.add(str);
    }

    public String getTaskFromList(int index) {
        return taskList.get(index);
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "taskList=" + taskList +
                '}';
    }
}
