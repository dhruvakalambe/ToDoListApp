package model;

import java.time.LocalDate;

public class Task {
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private String priority;

    public Task(String description, LocalDate dueDate, String priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return (completed ? "[✓] " : "[ ] ") + description +
                " | Due: " + dueDate +
                " | Priority: " + priority;
    }
}
