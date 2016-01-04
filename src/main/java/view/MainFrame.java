package view;


import model.Task;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Слава on 21.12.2015.
 */

public class MainFrame extends JFrame {
    private DataPanel dp;
    private MainList list;
    private Menu menu;
    private static final Logger log = Logger.getLogger(MainFrame.class);

    public MainFrame() {
        super("Manager");
        createGUI();
    }

    public Menu getMenu() {
        return menu;
    }

    public DataPanel getDp() {
        return this.dp;
    }

    public MainList getList() {
        return list;
    }

    void createGUI() {
        log.debug("Start Program");
        menu = new Menu();
        this.setJMenuBar(menu);

        final JPanel panel = new JPanel(new MigLayout());
        list = new MainList(null);
        JScrollPane jsp = new JScrollPane(list);
        jsp.setPreferredSize(new Dimension(100, 100));
        panel.add(jsp);

        JPanel mp = startPanel();
        dp = new DataPanel(null);
        panel.add(mp);
        panel.add(dp,"wrap");


        this.add(panel);
        this.setSize(430, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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



}
