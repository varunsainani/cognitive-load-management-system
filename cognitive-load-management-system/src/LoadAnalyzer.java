import java.util.*;

public class LoadAnalyzer {
    private int currentLoad;
    private int maxCapacity = 40; // average human cognitive load
    private List<Task> completedTasks; // to track completed tasks in order

    public LoadAnalyzer() {
        currentLoad = 0;
        completedTasks = new ArrayList<>();
    }

    public int getCurrentLoad() { return currentLoad; }
    public int getMaxCapacity() { return maxCapacity; }

    public void addLoad(int load) { currentLoad += load; }
    public void reduceLoad(int load) { currentLoad = Math.max(0, currentLoad - load); }

    public void resetLoad() { currentLoad = 0; }

    public void completeTask(Task task) {
        task.setStatus(Task.Status.COMPLETED);
        reduceLoad(task.getCognitiveLoad());
        completedTasks.add(task); // append to trend
    }

    public void printAnalytics(List<Task> tasks) {
        double avgLoad = tasks.stream().mapToInt(Task::getCognitiveLoad).average().orElse(0);
        int peakLoad = tasks.stream().mapToInt(Task::getCognitiveLoad).max().orElse(0);

        System.out.println("\n=== Cognitive Load Analytics ===");
        System.out.println("Average Load: " + String.format("%.2f", avgLoad));
        System.out.println("Peak Load: " + peakLoad);

        if (currentLoad > maxCapacity) {
            System.out.println("Overload Alert: Current Load exceeds safe capacity!");
        } else {
            System.out.println("Overload Alert: No overload detected");
        }

        System.out.println("\nTask Complexity + Workload Trend:");
        for (Task t : tasks) {
            System.out.println("Task: " + t.getTitle() + " | Complexity: " + String.format("%.2f", t.getComplexity()));
        }

        System.out.println("\nLoad Trend (completed tasks in order):");
        if (completedTasks.isEmpty()) {
            System.out.println("No tasks completed yet.");
        } else {
            for (int i = 0; i < completedTasks.size(); i++) {
                System.out.print(completedTasks.get(i).getCognitiveLoad());
                if (i != completedTasks.size() - 1) System.out.print(" → ");
            }
            System.out.println();
        }

        System.out.println("Total Completed Tasks: " + completedTasks.size());
    }
}
