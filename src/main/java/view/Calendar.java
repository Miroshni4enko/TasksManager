package view;

import com.toedter.calendar.JDateChooser;
import model.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Created by Слава on 23.12.2015.
 */
public class Calendar extends JFrame {


    private JList listDate;
    private JList listTask;
    private JDateChooser to;
    private JDateChooser from;
    private JButton go;

    public Calendar() {
        super("Calendar");
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
        panel.add(to,"wrap");

        this.from = new JDateChooser();
        this.from.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        panel.add(this.from);

        this.to = new JDateChooser();
        this.to.setDateFormatString("yyyy-MM-dd HH:mm:ss.S");
        panel.add(this.to);

        go = new JButton("Go");
        panel.add(go,"wrap");

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



