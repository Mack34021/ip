public class Task{
    private String task;
    private boolean isTaskDone;

    public Task(String task) {
        this.task = task;
        this.isTaskDone = false;
    }

    public String getTaskString() {
        return task;
    }

    public void setTaskString(String task) {
        this.task = task;
    }

    public boolean getIsTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.isTaskDone = taskDone;
    }
}