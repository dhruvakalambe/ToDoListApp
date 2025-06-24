package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Task;
import utils.FileHandler;

import java.time.LocalDate;

public class TaskController {

    @FXML
    private TextField taskInput;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ListView<Task> taskList;

    private ObservableList<Task> tasks;

    @FXML
    public void initialize() {
        tasks = FXCollections.observableArrayList(FileHandler.loadTasks());
        taskList.setItems(tasks);

        // Set default value and items for priority combo box
        priorityComboBox.setValue("Medium"); // Default
    }

    @FXML
    private void handleAddTask() {
        String description = taskInput.getText().trim();
        LocalDate dueDate = dueDatePicker.getValue();
        String priority = priorityComboBox.getValue();

        if (!description.isEmpty() && dueDate != null && priority != null) {
            Task newTask = new Task(description, dueDate, priority);
            tasks.add(newTask);

            taskInput.clear();
            dueDatePicker.setValue(null);
            priorityComboBox.setValue("Medium");

            FileHandler.saveTasks(tasks); // Save after adding
        } else {
            showAlert("Please fill all fields: Task, Date, and Priority.");
        }
    }

    @FXML
    private void handleMarkAsDone() {
        Task selected = taskList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setCompleted(true);
            taskList.refresh();
            FileHandler.saveTasks(tasks);
        }
    }

    @FXML
    private void handleDeleteTask() {
        Task selected = taskList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tasks.remove(selected);
            FileHandler.saveTasks(tasks);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
