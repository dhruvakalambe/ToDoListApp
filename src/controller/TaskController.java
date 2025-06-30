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
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ListView<Task> taskList;

    private ObservableList<Task> tasks;

    @FXML
    public void initialize() {
        tasks = FXCollections.observableArrayList(FileHandler.loadTasks());
        taskList.setItems(tasks);

        // Set default priority
        priorityComboBox.setItems(FXCollections.observableArrayList("High", "Medium", "Low"));
        priorityComboBox.setValue("Medium");
    }

    @FXML
    private void handleAddTask() {
        String description = taskInput.getText().trim();
        LocalDate dueDate = datePicker.getValue();             // ✅ Fixed here
        String priority = priorityComboBox.getValue();

        if (!description.isEmpty() && dueDate != null && priority != null) {
            Task newTask = new Task(description, dueDate, priority);
            tasks.add(newTask);

            taskInput.clear();
            datePicker.setValue(null);                         // ✅ Fixed here
            priorityComboBox.setValue("Medium");

            FileHandler.saveTasks(tasks);
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
