public class Deadline extends Task{

    private String dueBy;

    public Deadline(String task, String dueBy) {
        super(task);
        this.dueBy = dueBy;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + dueBy + ")";
    }
}
