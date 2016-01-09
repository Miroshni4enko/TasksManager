package controller;

import model.*;
import view.*;
import java.util.*;

/**
 * Created by Слава on 22.12.2015.
 */
public class TaskController {
    public static void main(String[] args) {
        TaskController controller = new TaskController(new TextTaskIO());
        controller.createView(SwingTasksView.getFactory());
    }
    private List views = new ArrayList();
    private List<Task> tasks;
    private TaskIO taskIO;


    public TaskController( TaskIO taskIO) {
        this.taskIO = taskIO;
        readTasks();
    }

    public void readTasks(){
        tasks = new ArrayList<Task>();
        tasks =  taskIO.read();
    }

    public void writeTasks(List<Task> tasks){
        taskIO.write(tasks);
    }

    public SortedMap<Date,Set<Task>> getMapCalendar(List<Task> tasks,Date start, Date end){
        return Tasks.calendar(tasks,start,end);
    }

    public void createView(ViewFactory factory) {
        TasksView view = factory.createView(tasks,this);
        views.add(view);
        view.showView();
    }

}