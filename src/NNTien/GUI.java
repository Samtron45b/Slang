package NNTien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

import NNTien.*;
/**
 * NNTien
 * Created by user
 * Date 12/15/2021 - 3:04 PM
 * Description: ...
 */
public class GUI extends JPanel{
    private static JFrame frame=null;
    private static JPanel card;

    public GUI(){
        add(new content());
    }


    public static void createGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent layout = new GUI();
        layout.setOpaque(true);
        frame.setContentPane(layout);
        frame.pack();
        frame.setJMenuBar(new menu());
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        Database data = new Database();
        javax.swing.SwingUtilities.invokeLater(GUI::createGUI);
    }
}
