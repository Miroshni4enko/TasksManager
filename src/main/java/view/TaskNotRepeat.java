package view;

import com.toedter.calendar.JDateChooser;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

/**
 * Created by Слава on 23.12.2015.
 */
public class TaskNotRepeat extends  NewTask {
    private JButton add;
    private JTextField title;
    private JDateChooser time;
    private JCheckBox active;
    private boolean chActive;

    public JButton getAdd() {
        return add;
    }

    public TaskNotRepeat(){
        setTaskFrame();
    }
    public void  setTaskFrame() {
        final JPanel panel = new JPanel(new MigLayout());

        title = new JTextField(15);
        time = new JDateChooser();
        time.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        active = new JCheckBox();
        active.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                chActive = true;
            }
        });
        final JLabel ltitle = new JLabel("Title: ");
        final JLabel ltime = new JLabel("Time: ");
        final JLabel lactive = new JLabel("Active:");
        add = new JButton("Add task");
        panel.add(ltitle);
        panel.add(title,"wrap");
        panel.add(ltime);
        panel.add(time, "wrap");
        panel.add(lactive);
        panel.add(active,"wrap");
        panel.add(add,"dock south");
        this.add(panel);
        this.setTitle("New");
        this.setSize(230,150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    public Task getTask(){
        if (title.getText().equals("")|time.getDate()==null) {
            JOptionPane.showMessageDialog(this, "One of the fields are filled");
        return null;
        }
        String title = this.title.getText();
        Date time = this.time.getDate();
        Task task =  new Task(title, time);
        task.setActive(chActive);
        return task;
    }

}

