package model;


import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
public class TextTaskIO extends TaskIO {

    private static final Logger log = Logger.getLogger(TextTaskIO.class);


    public  void write(List<Task> tasks) {
        File out = new File("Manager.txt");
        BufferedWriter writer = null;
        try { writer = new BufferedWriter (new FileWriter(out));
            DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.S]");
            int r = 0,size = tasks.size();
            for (Task t : tasks) {

                writer.write("\"" + t.getTitle() + "\"");
                if (t.isRepeated()) {
                    writer.write(" from " + dateFormat.format(t.getStartTime()) + " to ");
                    writer.write(dateFormat.format(t.getEndTime()));
                    writer.write(" every [");
                    int i = t.getRepeatInterval();
                    writer.write(intervalToStr(i));
                } else writer.write(" at " + dateFormat.format(t.getTime()));

                if (t.isActive()) writer.write(" inactive");
                if (size ==++r) writer.write(".");
                else writer.write(";");
                writer.write("\n");
            }
        } catch (FileNotFoundException e) {
            log.error("File not found");
        }
         catch (IOException e) {

        }
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("Error write file");;
            }
        }
    }

    public List<Task> read() {
        BufferedReader reader = null;
        List<Task> tasks = new ArrayList<Task>();
        File in = new File("Manager.txt");
        try {
            reader = new BufferedReader(new FileReader(in));
            String s;
            while((s = reader.readLine()) !=null ){
                if(s.equals("")) return null;
                int f = s.indexOf("\"");
                int l = s.lastIndexOf("\"");
                String title = s.substring(f+1,l);
                int interval = 0;
                Task task;
                Date date = null;
                Date date1 = null;
                String s2 = s.substring(l+2);
                String equ = s2.substring(0,2);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                boolean active = true;
                String[] d = s2.split("\\[");
                if (equ.equals("at")) {
                    String[] b = d[1].split("\\]");
                    date = formatter.parse(b[0]);
                    if (b[1].equals(";")|b[1].equals(".")) active = false;
                     task = new Task(title,date);
                    task.setActive(active);
                    tasks.add(task);
                } else {
                    String[] bi = d[1].split("\\]");
                    date = formatter.parse(bi[0]);
                    String[] bi1 = d[2].split("\\]");
                    date1 = formatter.parse(bi1[0]);
                    String[] b = d[3].split("\\]");
                    String[] j = b[0].split(" ");
                    interval = strToInterval(j);
                    if (b[1].equals(";")|b[1].equals(".")) active = false;
                    task = new Task(title,date,date1,interval);
                    task.setActive(active);
                    tasks.add(task);
                }

            }

        } catch (ParseException e) {
            log.error("incorrect arguments");
        } catch (FileNotFoundException e) {
            log.error("File not found");
        } catch (IOException e) {
            log.error("Eror read file");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("Eror read file");
            }
        }
        return tasks;
    }
    public static String intervalToStr(int interval){
        int s, m, d, h;
        String string;
        if (interval < min) {
            s = interval / sec;
            string = (s + " seconds]");
        } else if (interval >= min && interval < hour) {
            m = interval / min;
            s = (interval % min) / sec;
            if(interval%min == 0){
                string = (m + " minutes]");
            }else
            string = (m + " minutes " + s + " seconds]");
        }else if(interval >= hour && interval < day) {
            h = interval / hour;
            m = (interval % hour)/min;
            s = ((interval % hour) % min) / sec;
            if(interval%hour == 0){
                string = (h + " hours]");
            }else
            string = (h + " hours " + m + " minutes " + s + " seconds]");
        } else{
            d = interval / day;
            h = (interval % day) / hour;
            m = ((interval % day) % hour)/min;
            s = (((interval % day) % hour) % min) / sec;
            if(interval%day == 0){
                string = (d + " days]");
            }else
            string = (d + " days " +  h + " hours " + m + " minutes " + s + " seconds]");
        }
        return string;
    }

    public static int strToInterval(String[] s){
        int interval = 0;
        switch (s.length){
            case 2: {
                interval = Integer.parseInt(s[0]) ;
                break;
            }
            case 4: {
                interval = Integer.parseInt(s[0]) * 60  + Integer.parseInt(s[2]);
                break;
            }
            case 6 :{
                interval =Integer.parseInt(s[0]) * 3600 + Integer.parseInt(s[2]) * 60  + Integer.parseInt(s[4]);
                break;
            }
            case 8 :{
                interval =Integer.parseInt(s[0]) * 3600*24 + Integer.parseInt(s[2]) * 3600 +  Integer.parseInt(s[4]) * 60  + Integer.parseInt(s[6]);
                break;
            }
        }
        return interval;
    }

    public static final int sec = 1;
    public static final int min = 60*sec;
    public static final int hour = 60 * min;
    public static final int day = 24 * hour;



}
