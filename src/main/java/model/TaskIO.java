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
     */
    abstract public List<Task> read();
    /**
     * This method should write the task in
     * file and read them in the List.
     * @param tasks the list for read task
     */
    abstract public void write(List<Task> tasks);
}
