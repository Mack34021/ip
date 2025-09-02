public class Task{
    private String task;
    private boolean taskDone;

    public Task(String task) {
        this.task = task;
        this.taskDone = false;
    }

    public String getTaskString() {
        return task;
    }

    public void setTaskString(String task) {
        this.task = task;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }
}