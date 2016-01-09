package view;

import controller.TaskController;
import model.Task;

import java.util.List;

public interface ViewFactory {
    
    TasksView createView(List<Task> tasks, TaskController taskController);
}
