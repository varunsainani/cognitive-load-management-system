import java.util.*;

public class TaskManager {
    private List<Task> tasks;
    private LoadAnalyzer analyzer;
    private int taskCounter = 0;

    public TaskManager(LoadAnalyzer analyzer) {
        tasks = FileHandler.loadTasks();
        this.analyzer = analyzer;
        taskCounter = tasks.size(); // continue IDs
    }

    public void addTask(String title, int load, int deadlineDays) {
        taskCounter++;
        Task task = new Task(taskCounter, title, load, deadlineDays);
        tasks.add(task);
        FileHandler.saveTasks(tasks);
        System.out.println("\nTask added completely!");
    }

    public void viewTasks() {
        System.out.println("\n=== All Tasks ===");
        for (Task t : tasks) {
            System.out.println(t);
        }
    }


    public void deleteTask(int id) {
        tasks.removeIf(t -> t.getTaskId() == id);
        FileHandler.saveTasks(tasks);
        System.out.println("Task deleted successfully!");
    }

    public void completeTask(int id) {
        for (Task t : tasks) {
            if (t.getTaskId() == id && t.getStatus() != Task.Status.COMPLETED) {
                analyzer.completeTask(t);
                System.out.println("Task completed!");
                FileHandler.saveTasks(tasks);
                return;
            }
        }
        System.out.println("Task not found or already completed.");
    }

    public Task getNextTask() {
        // Sort tasks by complexity descending, pending only
        List<Task> pending = new ArrayList<>();
        for (Task t : tasks) if (t.getStatus() == Task.Status.PENDING) pending.add(t);

        pending.sort((a, b) -> Double.compare(b.getComplexity(), a.getComplexity()));
        if (pending.isEmpty()) return null;

        Task next = pending.get(0);
        if (analyzer.getCurrentLoad() + next.getCognitiveLoad() > analyzer.getMaxCapacity()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Warning: Load will exceed safe capacity if you take this task.");
            System.out.print("Do you still want to get it? (yes/no): ");
            String ans = sc.nextLine();
            if (!ans.equalsIgnoreCase("yes")) return null;
        }

        analyzer.addLoad(next.getCognitiveLoad());
        next.setStatus(Task.Status.IN_PROGRESS);
        FileHandler.saveTasks(tasks);
        return next;
    }

    public void optimizeTaskOrder() {
        tasks.sort((a, b) -> Double.compare(b.getComplexity(), a.getComplexity()));
        System.out.println("\n=== Optimized Task Order ===");
        for (Task t : tasks) System.out.println(t);
    }

    public List<Task> getTasks() { return tasks; }
}
