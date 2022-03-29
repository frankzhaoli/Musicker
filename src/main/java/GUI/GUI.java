package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI
{
    JFrame frame;

    JLabel monthLabel;
    JComboBox monthComboBox;
    JButton button;


    public GUI()
    {
        frame=new JFrame("Musicker");

        final JLabel label=new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400, 100);

        JButton button=new JButton("Show");
        button.setBounds(200, 100, 75, 20);

        //date arrays
        //possibly create those automatically? upper bound being current date, lower bound being jan 2017
        String[] monthsArray={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] yearsArray={"2017", "2018", "2019", "2020", "2021"};
        final JComboBox comboBox=new JComboBox(monthsArray);
        comboBox.setBounds(50, 50, 90, 20);

        frame.add(label);
        frame.add(comboBox);
        frame.add(button);

        frame.setLayout(null);
        frame.setSize(350, 350);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data="Month selected:" + comboBox.getItemAt(comboBox.getSelectedIndex());
                label.setText(data);
            }
        });
    }


}
