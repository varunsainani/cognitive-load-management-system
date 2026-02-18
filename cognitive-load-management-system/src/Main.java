import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoadAnalyzer analyzer = new LoadAnalyzer();
        TaskManager manager = new TaskManager(analyzer);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========================================");
            System.out.println("  Cognitive Load Management System");
            System.out.println("========================================");
            System.out.println("1. Add New Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Get Next Optimal Task");
            System.out.println("4. Complete Task");
            System.out.println("5. Optimize Task Order");
            System.out.println("6. View Cognitive Load Analytics");
            System.out.println("7. Reset Cognitive Load");
            System.out.println("8. Delete Task");
            System.out.println("9. Exit");
            System.out.print("\nEnter your choice: ");

            String input = sc.nextLine();
            int choice;
            try { choice = Integer.parseInt(input); }
            catch (Exception e) { choice = -1; }

            switch (choice) {
                case 1:
                    System.out.print("Enter Task Name: ");
                    String title = sc.nextLine();
                    int load = 0, days = 0;
                    try {
                        System.out.print("Cognitive Load (1-10): ");
                        load = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Deadline (days): ");
                        days = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input! Task not added.");
                        break;
                    }
                    manager.addTask(title, load, days);
                    break;

                case 2:
                    manager.viewTasks();
                    break;

                case 3:
                    Task next = manager.getNextTask();
                    if (next != null) {
                        System.out.println("\nNext Task to do:");
                        System.out.println(next);
                        System.out.println("Current Load: " + analyzer.getCurrentLoad() + "/" + analyzer.getMaxCapacity());
                    } else {
                        System.out.println("No suitable task available.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Task ID to complete: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        manager.completeTask(id);
                    } catch (Exception e) { System.out.println("Invalid input."); }
                    break;

                case 5:
                    manager.optimizeTaskOrder();
                    break;

                case 6:
                    analyzer.printAnalytics(manager.getTasks());
                    break;

                case 7:
                    analyzer.resetLoad();
                    System.out.println("Cognitive load reset!");
                    break;

                case 8:
                    System.out.print("Enter Task ID to delete: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        manager.deleteTask(id);
                    } catch (Exception e) { System.out.println("Invalid input."); }
                    break;

                case 9:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please enter 1-9.");
            }
        }
    }
}
