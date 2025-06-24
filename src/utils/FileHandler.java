package utils;

import model.Task;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "tasks.txt";

    public static void saveTasks(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                // Format: completed::description::dueDate::priority
                writer.write(task.isCompleted() + "::" +
                        task.getDescription() + "::" +
                        task.getDueDate() + "::" +
                        task.getPriority());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return tasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse line: completed::description::dueDate::priority
                String[] parts = line.split("::", 4);
                if (parts.length == 4) {
                    boolean completed = Boolean.parseBoolean(parts[0]);
                    String description = parts[1];
                    LocalDate dueDate = LocalDate.parse(parts[2]);
                    String priority = parts[3];

                    Task task = new Task(description, dueDate, priority);
                    task.setCompleted(completed);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}
