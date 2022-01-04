package NNTien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private static Database rawData;
    private static History history;
    private static slangPage slangDict;
    private static meaningPage meaningDict;
    private DefaultListModel<String> slangModel;
    private DefaultListModel<String> meaningModel;


    static class controlAction implements ActionListener {
        public controlAction(){
        };
        @Override
        public void actionPerformed(ActionEvent e) {
            String strCmd=e.getActionCommand();
            if(strCmd.equals("toSlang")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"slang");

            }else if(strCmd.equals("toDefinition")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"meaning");
            }
        }
    }

    static class controlMenu implements MenuListener{
        @Override
        public void menuSelected(MenuEvent e) {
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

    class slangPage extends JPanel{
        private JList slangList;
        private JPanel slang;
        private JPanel definition;
        private JTextArea meaningText;
        private JTextField searchText = new JTextField(30);

        public slangPage(){
            setLayout(new BorderLayout());
            JPanel search = new JPanel();
            JButton searchBtn = new JButton("Search");

            searchBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String title = searchText.getText();
                    if (title != "") {
                        System.out.println(title);
                        String meaning = data.findMeaning(title);
                        if (meaning != null) {
                            meaningText.setText(meaning);
                        } else {
                            meaningText.setText("Not found!");
                        }
                        history.updateSlang(title);
                    }
                }
            });
            search.add(new JLabel("Search: "));
            search.add(searchText);
            search.add(searchBtn);
            add(search,BorderLayout.PAGE_START);

            slangModel = new DefaultListModel<>();
            slangModel.addAll(data.title());
            slangList = new JList(slangModel);
            slangList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JList thisList = (JList) e.getSource();
                    if(e.getClickCount()>=1){
                        int index = thisList.locationToIndex(e.getPoint());
                        if(index>=0){
                            String value = data.title().get(index);
                            searchText.setText(value);
                            meaningText.setText(data.findMeaning(value));
                        }
                    }
                }
            });
            JScrollPane titleList = new JScrollPane();
            titleList.setViewportView(slangList);
            slang = new JPanel(new BorderLayout());
            slang.add(new JLabel("Slang"),BorderLayout.PAGE_START);
            slang.add(titleList,BorderLayout.CENTER);

            meaningText= new JTextArea(10,25);

            definition = new JPanel(new BorderLayout());
            definition.add(new JLabel("Meaning"),BorderLayout.PAGE_START);
            definition.add(meaningText,BorderLayout.CENTER);

            JPanel contain = new JPanel(new FlowLayout());
            contain.add(slang);
            contain.add(definition);

            add(contain,BorderLayout.CENTER);
        }

        public void clear(){
            searchText.setText("");
            meaningText.setText("");
        }
    }

    class meaningPage extends JPanel{
        private JList meaningList;
        private JPanel meaing;
        private JPanel slang;
        private JTextArea slangText;
        private JTextField searchText = new JTextField(30);

        public meaningPage(){
            setLayout(new BorderLayout());
            JPanel search = new JPanel();
            JButton searchBtn = new JButton("Search");

            searchBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String meaning = searchText.getText();
                    if (meaning != "") {
                        System.out.println(meaning);
                        String title = data.findTitle(meaning);
                        if (title != null) {
                            slangText.setText(title);
                        } else {
                            slangText.setText("Not found!");
                        }
                        history.updateMeaning(meaning);
                    }
                }
            });
            search.add(new JLabel("Search: "));
            search.add(searchText);
            search.add(searchBtn);
            add(search,BorderLayout.PAGE_START);

            meaningModel = new DefaultListModel<>();
            meaningModel.addAll(data.meaning());
            meaningList = new JList(meaningModel);
            meaningList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JList thisList = (JList) e.getSource();
                    if(e.getClickCount()>=1){
                        int index = thisList.locationToIndex(e.getPoint());
                        if(index>=0){
                            String value = data.meaning().get(index);
                            searchText.setText(value);
                            slangText.setText(data.findTitle(value));
                        }
                    }
                }
            });
            JScrollPane titleList = new JScrollPane();
            titleList.setViewportView(meaningList);
            meaing = new JPanel(new BorderLayout());
            meaing.add(new JLabel("Meaning"),BorderLayout.PAGE_START);
            meaing.add(titleList,BorderLayout.CENTER);

            slangText= new JTextArea(10,25);

            slang = new JPanel(new BorderLayout());
            slang.add(new JLabel("Slang"),BorderLayout.PAGE_START);
            slang.add(slangText,BorderLayout.CENTER);

            JPanel contain = new JPanel(new FlowLayout());
            contain.add(meaing);
            contain.add(slang);

            add(contain,BorderLayout.CENTER);
        }

        public void clear(){
            searchText.setText("");
            slangText.setText("");
        }
    }

    public GUI(){
        setLayout(new BorderLayout());
        card = new JPanel(new CardLayout());
        history = new History();
        slangDict = new slangPage();
        meaningDict = new meaningPage();
        card.add(slangDict,"slang");
        card.add(meaningDict,"meaning");
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
