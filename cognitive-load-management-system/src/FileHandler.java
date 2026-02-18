import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "tasks.txt";

    // Save tasks to file
    public static void saveTasks(List<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                String line = t.getTaskId() + "|" + t.getTitle() + "|" + t.getCognitiveLoad() + "|"
                        + t.getDeadlineDays() + "|" + t.getStatus() + "|" + t.getComplexity();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Load tasks from file
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                int load = Integer.parseInt(parts[2]);
                int deadline = Integer.parseInt(parts[3]);
                Task.Status status = Task.Status.valueOf(parts[4]);
                double complexity = Double.parseDouble(parts[5]);

                Task t = new Task(id, title, load, deadline);
                t.setStatus(status);
                t.setComplexity(complexity);
                tasks.add(t);
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Delete task
    public static void deleteTask(int taskId) {
        List<Task> tasks = loadTasks();
        tasks.removeIf(t -> t.getTaskId() == taskId);
        saveTasks(tasks);
    }
}
