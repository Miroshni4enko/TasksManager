package model;

import java.io.File;
import java.util.List;

/**
 * Created by Слава on 31.12.2015.
 */
public abstract class TaskIO {
    /**
     * This method should read the task of
     * file and write them in the List.
     * @param tasks the list for write task
     * @param file the file for read task
     */
    abstract public void read(List<Task> tasks, File file);
    /**
     * This method should write the task in
     * file and read them in the List.
     * @param tasks the list for read task
     * @param file the file for write task
     */
    abstract public void write(List<Task> tasks, File file);
}
