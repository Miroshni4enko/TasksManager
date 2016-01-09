package view;

import com.toedter.calendar.JDateChooser;
import controller.TaskController;
import model.Task;
import model.Tasks;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Слава on 23.12.2015.
 */
public class Calendar extends JFrame {

    private TaskController taskController;
    private JList listDate;
    private JList listTask;
    private JDateChooser to;
    private JDateChooser from;
    private JButton go;
    private List<Task> tasks;

    public Calendar(TaskController taskController,List<Task> tasks) {
        super("Calendar");
        this.taskController = taskController;
        this.tasks = tasks;
        createGUI();
    }

    public JList getListTask() {
        return listTask;
    }

    public JButton getGoButton() {
        return go;
    }

    public JList getListDate() {
        return listDate;
    }

    public JDateChooser getTo() {
        return to;
    }

    public JDateChooser getFrom() {
        return from;
    }


    void createGUI() {
        final JPanel panel = new JPanel(new MigLayout());

        final JLabel from = new JLabel("From");
        final JLabel to = new JLabel("to");
        panel.add(from);
        panel.add(to, "wrap");

        this.from = new JDateChooser();
        this.from.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        panel.add(this.from);

        this.to = new JDateChooser();
        this.to.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        panel.add(this.to);

        go = new JButton("Go");
        panel.add(go, "wrap");

        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date from = getFrom().getDate();
                Date to = getTo().getDate();
                if (from == null | to == null){
                    JOptionPane.showMessageDialog(null, "One of the fields are filled");
                    return;
                }
                SortedMap<Date,Set<Task>> test2  = taskController.getMapCalendar(tasks,getFrom().getDate(),getTo().getDate());
                Set<SortedMap.Entry<Date, Set<Task>>> set = test2.entrySet();

                DefaultListModel dateModel = new DefaultListModel();
                final Set[] s = new Set[set.size()];
                int i = 0;
                for (Map.Entry<Date, Set<Task>> entry : set) {
                    dateModel.addElement(entry.getKey());
                    s[i++] = entry.getValue();
                }
                getListDate().setModel(dateModel);
                getListDate().addListSelectionListener(
                        new ListSelectionListener() {
                            public void valueChanged(ListSelectionEvent e) {
                                int index = getListDate().getAnchorSelectionIndex();
                                final DefaultListModel taskModel = new DefaultListModel();
                                Iterator<Task> it = s[index].iterator();
                                while (it.hasNext()) {
                                    Task element = it.next();
                                    taskModel.addElement(element.getTitle());
                                }
                                getListTask().setModel(taskModel);

                            }
                        }
                );
            }
        });


    listDate = new JList();
        JScrollPane jsp = new JScrollPane(listDate);
        jsp.setPreferredSize(new Dimension(215, 100));
        panel.add(jsp);

        listTask = new JList();
        JScrollPane jsp1 = new JScrollPane(listTask);
        jsp1.setPreferredSize(new Dimension(200, 100));
        panel.add(jsp1);

        this.add(panel);
        this.setSize(450, 220);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}



