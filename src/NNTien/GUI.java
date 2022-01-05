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
import java.time.LocalDate;

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
    private static addPage addSlang;
    private static editPage editSlang;
    private static DefaultListModel<String> slangModel;
    private static DefaultListModel<String> meaningModel;


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
            }else if(strCmd.equals("toAdd")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"add");
            }else if(strCmd.equals("toEdit")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"edit");
            }
            else if(strCmd.equals("refresh")){
                data=rawData;
                slangModel.clear();
                slangModel.addAll(data.title());
                meaningModel.clear();
                meaningModel.addAll(data.meaning());
            }
        }
    }

    static class controlMenu implements MenuListener{
        @Override
        public void menuSelected(MenuEvent e) {
            CardLayout cl = (CardLayout)(card.getLayout());
            cl.show(card,"history");
            slangDict.clear();
            meaningDict.clear();
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

    class addPage extends  JPanel{
        private JTextField slang;
        private JTextField meaning;
        public addPage(){
            setLayout(new BorderLayout());
            JPanel head = new JPanel(new FlowLayout(1));

            JPanel slangPanel = new JPanel(new BorderLayout());
            slang = new JTextField(20);
            slangPanel.add(new JLabel("Slang"),BorderLayout.PAGE_START);
            slangPanel.add(slang,BorderLayout.CENTER);

            JPanel meaningPanel = new JPanel(new BorderLayout());
            meaning = new JTextField(40);
            meaningPanel.add(new JLabel("Meaning"), BorderLayout.PAGE_START);
            meaningPanel.add(meaning,BorderLayout.CENTER);

            head.add(slangPanel);
            head.add(meaningPanel);

            JPanel btnPanel = new JPanel(new FlowLayout(1));
            JButton btn = new JButton("Add");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!slang.getText().isEmpty()&&!meaning.getText().isEmpty())
                    {
                        boolean available = data.addNew(slang.getText(),meaning.getText());
                        if(available) {
                            JOptionPane.showMessageDialog(frame,
                                    "Done");
                            slangModel.clear();
                            slangModel.addAll(data.title());
                            meaningModel.clear();
                            meaningModel.addAll(data.meaning());
                        }
                        else {
                            JOptionPane.showMessageDialog(frame,
                                    "Slang exist: '"+slang.getText()+":"+data.findMeaning(slang.getText())+"'",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(frame,
                                "Empty field!",
                                "Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btnPanel.add(btn);

            add(head,BorderLayout.PAGE_START);
            add(btnPanel,BorderLayout.CENTER);
        }

        public void clear(){
            slang.setText("");
            meaning.setText("");
        }
    }

    class editPage extends JPanel{
        private JList slangList;
        private JPanel slang;
        private JPanel edit;
        private JTextArea slangText;
        private JTextArea meaningText;

        public editPage(){
            setLayout(new FlowLayout());
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
                            slangText.setText(value);
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

            add(slang);

        }
    }

    public GUI(){
        setLayout(new BorderLayout());
        card = new JPanel(new CardLayout());
        history = new History();
        slangDict = new slangPage();
        meaningDict = new meaningPage();
        addSlang = new addPage();
        editSlang= new editPage();

        card.add(slangDict,"slang");
        card.add(meaningDict,"meaning");
        card.add(history,"history");
        card.add(addSlang,"add");
        card.add(editSlang,"edit");

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
        rawData=data;
        conA = new controlAction();
        conM = new controlMenu();
        javax.swing.SwingUtilities.invokeLater(GUI::createGUI);
    }
}
