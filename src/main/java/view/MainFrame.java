package view;


import controller.TaskController;
import model.Task;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Слава on 21.12.2015.
 */

public class MainFrame implements TasksView{
    private JFrame frame;
    private List<Task> tasks;
    private DataPanel dataPanel;
    private MainList list;
    private Menu menu;
    private Task taskIndex;
    private TaskController taskController;
    private  MainList timerTasks;
    private static final Logger log = Logger.getLogger(MainFrame.class);

    public MainFrame(List<Task> tasks, TaskController taskController) {
        this.taskController = taskController;
        this.tasks = tasks;
        createTimerGui();
        createGUI();
        tasksTimer(tasks);
                
    }
    public static ViewFactory getFactory() {
        return new ViewFactory() {
            public TasksView createView(List<Task> model,TaskController taskController) {
                return new MainFrame(model,taskController);
            }
        };
    }


    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void showView() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });

    }

    void createGUI() {
        frame = new JFrame("Manager");
        log.debug("Start Program");
        menu = new Menu();
        frame.setJMenuBar(menu);
        newTaskListener(menu.getRepeat(),true);
        newTaskListener(menu.getNrepeat(), false);
        changeTaskListener(menu.getChange());
        removeListener(menu.getRemove());
        calendarListener(menu.getCalendar());

        final JPanel panel = new JPanel(new MigLayout());
        list = new MainList(tasks);
        mainListListener(list);
        JScrollPane jsp = new JScrollPane(list);
        jsp.setPreferredSize(new Dimension(100, 100));
        panel.add(jsp);

        JPanel mp = startPanel();
        dataPanel = new DataPanel(null);
        panel.add(mp);
        panel.add(dataPanel,"wrap");


        frame.add(panel);
        frame.setSize(430, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        //frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                Object[] options = { "Yes", "No" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Close Manager?",
                                "Confirmation", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    taskController.writeTasks(tasks);
                    //event.getWindow().setVisible(false);
                    //event.getWindow().dispose();
                    System.exit(2);
                }
            }
        });
    }

    public JPanel startPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        final JLabel start = new JLabel("Start: ");
        final JLabel time = new JLabel("Time: ");
        final JLabel end = new JLabel("End: ");
        final JLabel interval = new JLabel("Interval: ");
        final JLabel active = new JLabel("Active:");
        panel.add(start, "wrap");
        panel.add(time, "wrap");
        panel.add(end, "wrap");
        panel.add(interval, "wrap");
        panel.add(active);
        return panel;
    }

    public void mainListListener(final JList mainList){
        mainList.addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        int index = list.getAnchorSelectionIndex();
                        if (tasks.size()==0 | index>tasks.size()-1){
                            dataPanel.setData(null);
                            return;
                        }
                        taskIndex = tasks.get(index);
                        dataPanel.setData(taskIndex);
                    }
                });
    }

    public void removeListener(JMenuItem remove){
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskIndex != null) {
                    if (taskIndex.isTimer()) taskIndex.getTimer().cancel();
                    tasks.remove(taskIndex);
                    list.removeTask(taskIndex);
                    timerTasks.removeTask(taskIndex);
                }
            }
        });
    }

    public void changeTaskListener(JMenuItem change){
        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (taskIndex != null) {
                    final TaskChange change = new TaskChange(taskIndex);
                    change.getChange().addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            change.setTask();
                            if (taskIndex.isTimer()) {
                                taskIndex.getTimer().cancel();
                                timer(taskIndex);
                            } else timer(taskIndex);
                            list.addTasks(tasks);
                            change.setVisible(false);
                        }
                    });
                }
            }
        });
    }
    public void newTaskListener(JMenuItem newTask, final boolean isRepeated){
        newTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final NewTask addTask;
                if(isRepeated)  addTask = new TaskRepeat();
                else  addTask = new TaskNotRepeat();
                addTask.getAdd().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Task newTask =  addTask.getTask();
                        if (newTask == null) return;
                        timer(newTask);
                        tasks.add(newTask);
                        list.addTask(newTask);
                        addTask.setVisible(false);
                    }
                });
            }
        });

    }

    public void  calendarListener(JMenuItem calendar){
        calendar.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final Calendar calendar = new Calendar(taskController,tasks);
            }
        });
    }
    public void createTimerGui(){
        JFrame timer = new JFrame("Timer");
        timerTasks = new MainList(null);
        final JPanel panel = new JPanel(new MigLayout());
        JScrollPane jsp = new JScrollPane(timerTasks);
        jsp.setPreferredSize(new Dimension(100, 200));
        panel.add(jsp);
        /*JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerTasks.getListModel().remove(0);
            }
        });
        panel.add(remove);*/
        timer.add(panel);
        timer.setSize(110,200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        timer.setLocation(dim.width/2+220, dim.height/2-timer.getSize().height/2);
        timer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        timer.setVisible(true);;
    }

    public void timer(final Task task){
        if(task.nextTimeAfter(new Date()) == null)return;
        final java.util.Timer timer = new java.util.Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(task.isRepeated()&& task.getEndTime().before(new Date())) {
                    timer.cancel();
                    task.setTimer(null);
                }
                while(timerTasks.removeTask(task));
                timerTasks.addTask(task);

                if (!task.isRepeated()){
                    timer.cancel();
                    task.setTimer(null);
                }
            }
        };
        if(task.isRepeated()){
            timer.scheduleAtFixedRate(timerTask,task.nextTimeAfter(new Date()),task.getRepeatInterval()*1000);
        }else {
            timer.schedule(timerTask,task.nextTimeAfter(new Date()));
        }
        task.setTimer(timer);
    }
    public void tasksTimer(List<Task> tasks) {
        for (Task task : tasks)
            timer(task);
    }
}
