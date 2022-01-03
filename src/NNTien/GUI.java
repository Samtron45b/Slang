package NNTien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.*;
import java.util.List;

/**
 * NNTien
 * Created by user
 * Date 12/15/2021 - 3:04 PM
 * Description: ...
 */
public class GUI extends JPanel {

    private static JFrame frame=null;
    private static JPanel card;
    private static controlAction conA;
    private static controlMenu conM;
    private static Database data;

    static class controlAction implements ActionListener {
        public controlAction(){
        };
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }

    static class controlMenu implements MenuListener{
        @Override
        public void menuSelected(MenuEvent e) {
            System.out.println("hi");
            CardLayout cl = (CardLayout)(card.getLayout());
            cl.show(card,"history");
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }

    static class slangPage extends JPanel{

    }

    public GUI(){
        setLayout(new BorderLayout());
        card = new JPanel(new CardLayout());
        History history = new History();
        card.add(history,"history");

        add(card);
    }


    public static void createGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent layout = new GUI();
        frame.setJMenuBar(new menu(conA,conM));
        frame.setContentPane(layout);
        layout.setOpaque(true);

        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        data = new Database();
        conA = new controlAction();
        conM = new controlMenu();
        javax.swing.SwingUtilities.invokeLater(GUI::createGUI);
    }
}
