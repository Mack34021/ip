public class Event extends Task{

    private String startsAt;
    private String endsAt;

    public Event(String task, String startsAt, String endsAt) {
        super(task);
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + startsAt + " to: " + endsAt + ")";
    }
}
