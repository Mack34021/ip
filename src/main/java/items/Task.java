package items;

public class Task{
    private String task;
    private boolean isTaskDone;

    public Task(String task) {
        this.task = task;
        this.isTaskDone = false;
    }

    public boolean getIsTaskDone() {
        return isTaskDone;
    }

    public String getTaskName() {
        return task;
    }

    public void setTaskDone(boolean taskDone) {
        this.isTaskDone = taskDone;
    }

    @Override
    public String toString() {
        return task;
    }
}