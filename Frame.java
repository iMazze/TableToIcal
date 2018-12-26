import java.awt.FlowLayout;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
public class Frame {
    
    private static String excel_path="";

    public static void main(String s[]) {

        JFrame frame = new JFrame("Table to iCal");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Chose excel file");

        JButton button_run = new JButton();
        button_run.setEnabled(false);
        button_run.setText("Generate iCal");
        button_run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableToIcal tic = new TableToIcal();
                tic.run(excel_path);
            }
        });
        
        JButton button_update = new JButton();
        button_update.setText("Update iCal");
        button_update.setEnabled(false);
        button_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableToIcal tic = new TableToIcal();
                tic.runUpdate(excel_path);
            }
        });
        
        JButton button = new JButton();
        button.setText("...");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Excel Files", "xls", "xlsx");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    excel_path = chooser.getSelectedFile().getAbsolutePath();
                    button_run.setEnabled(true);
                    button_update.setEnabled(false);
                }
                
            }
        });
        
        
        

        panel.add(label);
        panel.add(button);
        panel.add(button_run);

        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}