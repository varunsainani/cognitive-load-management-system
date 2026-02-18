public class Task {
    private int taskId;
    private String title;
    private int cognitiveLoad;  // 1-10
    private int deadlineDays;   // remaining days
    private Status status;
    private double complexity;  // load * deadline

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED
    }

    public Task(int taskId, String title, int load, int deadlineDays) {
        this.taskId = taskId;
        this.title = title;
        this.cognitiveLoad = load;
        this.deadlineDays = deadlineDays;
        this.status = Status.PENDING;
        this.complexity = calculateComplexity();
    }

    // Getters and Setters
    public int getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public int getCognitiveLoad() { return cognitiveLoad; }
    public int getDeadlineDays() { return deadlineDays; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public double getComplexity() { return complexity; }
    public void setComplexity(double complexity) { this.complexity = complexity; }

    // Calculate complexity
    public double calculateComplexity() {
        return cognitiveLoad * deadlineDays;
    }

    @Override
    public String toString() {
        return "ID: " + taskId +
                " | Task: " + title +
                " | Load: " + cognitiveLoad +
                " | Deadline: " + deadlineDays + " days" +
                " | Status: " + status +
                " | Complexity: " + String.format("%.2f", complexity);
    }
}
