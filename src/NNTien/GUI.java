package NNTien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private static addPage addSlang;
    private static editPage editSlang;
    private static deletePage deleteSlang;
    private static randomPage randomSlang;
    private static slangQuizzPage quizzSlang;
    private static meaningQuizzPage quizzMeaning;
    private static DefaultListModel<String> slangModel;
    private static DefaultListModel<String> meaningModel;


    static class controlAction implements ActionListener {
        public controlAction(){};
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
            }else if(strCmd.equals("toDelete")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"delete");
            }
            else if(strCmd.equals("today")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"random");
            }else if(strCmd.equals("slangQuizz")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"quizzS");
            }else if(strCmd.equals("definitionQuizz")){
                CardLayout cl = (CardLayout)(card.getLayout());
                cl.show(card,"quizzM");
            }
            else if(strCmd.equals("refresh")){
                data= new Database(rawData);
                refreshModel();
            }
        }
    }

    static class controlMenu implements MenuListener{
        @Override
        public void menuSelected(MenuEvent e) {
            CardLayout cl = (CardLayout)(card.getLayout());
            cl.show(card, "history");
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
                            refreshModel();
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
        private JTextField slangText;
        private JTextField meaningText;

        public editPage(){
            final String[] value = {""};
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
                            value[0] = data.title().get(index);
                            slangText.setText(value[0]);
                            meaningText.setText(data.findMeaning(value[0]));
                        }
                    }
                }
            });
            JScrollPane titleList = new JScrollPane();
            titleList.setViewportView(slangList);
            slang = new JPanel(new BorderLayout());
            slang.add(new JLabel("Slang List"),BorderLayout.PAGE_START);
            slang.add(titleList,BorderLayout.CENTER);

            edit = new JPanel(new BorderLayout());

            slangText = new JTextField(30);

            JButton search = new JButton("Search");
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String title = slangText.getText();
                    if (title != "") {
                        String meaning = data.findMeaning(title);
                        if (meaning != null) {
                            meaningText.setText(meaning);
                        }
                        value[0]=title;
                    }
                }
            });

            JPanel editS = new JPanel(new FlowLayout(0));
            editS.add(new JLabel("Slang     "));
            editS.add(slangText);
            editS.add(search);

            meaningText = new JTextField(30);
            JPanel editM = new JPanel(new FlowLayout(0));
            editM.add(new JLabel("Meaning"));
            editM.add(meaningText);

            JButton btn = new JButton("Edit");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!slangText.getText().isEmpty()&&!meaningText.getText().isEmpty())
                    {
                        if(value[0].equals(slangText.getText()))
                        {
                            data.editSlang(slangText.getText(),meaningText.getText());
                            JOptionPane.showMessageDialog(frame,
                                    "Done");
                            refreshModel();
                        }
                        else if(data.findMeaning(slangText.getText())!=null){
                            JOptionPane.showMessageDialog(frame,
                                    "Slang exist: '"+slangText.getText()+":"+data.findMeaning(slangText.getText())+"'",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            data.delete(value[0]);
                            data.addNew(slangText.getText(),meaningText.getText());
                            JOptionPane.showMessageDialog(frame,
                                    "Done");
                            refreshModel();
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
            edit.add(editS,BorderLayout.PAGE_START);
            edit.add(editM,BorderLayout.CENTER);
            edit.add(btn,BorderLayout.PAGE_END);

            add(slang);
            add(edit);
        }

        public void clear(){
            meaningText.setText("");
            slangText.setText("");
        }
    }

    class deletePage extends JPanel{
        private JList slangList;
        private JPanel slang;
        private JPanel delete;
        private JTextField slangText;
        private JTextField meaningText;

        public deletePage(){
            final String[] value = {""};
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
                            value[0] = data.title().get(index);
                            slangText.setText(value[0]);
                            meaningText.setText(data.findMeaning(value[0]));
                        }
                    }
                }
            });
            JScrollPane titleList = new JScrollPane();
            titleList.setViewportView(slangList);
            slang = new JPanel(new BorderLayout());
            slang.add(new JLabel("Slang List"),BorderLayout.PAGE_START);
            slang.add(titleList,BorderLayout.CENTER);

            delete = new JPanel(new BorderLayout());

            slangText = new JTextField(30);

            JButton search = new JButton("Search");
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String title = slangText.getText();
                    if (title != "") {
                        String meaning = data.findMeaning(title);
                        if (meaning != null) {
                            meaningText.setText(meaning);
                            value[0]=title;
                        }
                    }
                }
            });

            JPanel editS = new JPanel(new FlowLayout(0));
            editS.add(new JLabel("Slang     "));
            editS.add(slangText);
            editS.add(search);

            meaningText = new JTextField(30);
            JPanel editM = new JPanel(new FlowLayout(0));
            editM.add(new JLabel("Meaning"));
            meaningText.setEditable(false);
            meaningText.setBackground(Color.WHITE);
            editM.add(meaningText);

            JButton btn = new JButton("Delete");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!slangText.getText().isEmpty()&&!meaningText.getText().isEmpty())
                    {
                        if(value[0].equals(slangText.getText()))
                        {
                            int n = JOptionPane.showConfirmDialog(
                                frame,
                                "Are you sure to delete'"+slangText.getText()+":"+data.findMeaning(slangText.getText())+"'?",
                                "Warnign",
                                JOptionPane.YES_NO_OPTION);
                            if(n==0){
                                data.delete(value[0]);
                                clear();
                                refreshModel();
                            }
                        }
                        else if(data.findMeaning(slangText.getText())!=null){
                            JOptionPane.showMessageDialog(frame,
                                    "Slang exist: '"+slangText.getText()+":"+data.findMeaning(slangText.getText())+"'",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            data.delete(value[0]);
                            data.addNew(slangText.getText(),meaningText.getText());
                            JOptionPane.showMessageDialog(frame,
                                    "Done");
                            refreshModel();
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
            delete.add(editS,BorderLayout.PAGE_START);
            delete.add(editM,BorderLayout.CENTER);
            delete.add(btn,BorderLayout.PAGE_END);

            add(slang);
            add(delete);
        }

        public void clear(){
            meaningText.setText("");
            slangText.setText("");
        }
    }

    class randomPage extends  JPanel{
        private JTextField slang;
        private JTextField meaning;
        private Random rand;

        public randomPage(){
            int n;
            setLayout(new BorderLayout());
            JPanel head = new JPanel(new FlowLayout(1));
            rand = new Random(100);

            JPanel slangPanel = new JPanel(new BorderLayout());
            slang = new JTextField(20);
            slang.setEditable(false);
            slang.setBackground(Color.WHITE);
            slangPanel.add(new JLabel("Slang"),BorderLayout.PAGE_START);
            slangPanel.add(slang,BorderLayout.CENTER);


            JPanel meaningPanel = new JPanel(new BorderLayout());
            meaning = new JTextField(40);
            meaning.setEditable(false);
            meaning.setBackground(Color.WHITE);
            meaningPanel.add(new JLabel("Meaning"), BorderLayout.PAGE_START);
            meaningPanel.add(meaning,BorderLayout.CENTER);

            head.add(slangPanel);
            head.add(meaningPanel);

            n=rand.nextInt(data.title().size());
            slang.setText(data.title().get(n));
            meaning.setText(data.findMeaning(data.title().get(n)));

            JPanel btnPanel = new JPanel(new FlowLayout(1));
            JButton btn = new JButton("Random!!!");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n=rand.nextInt(data.title().size());
                    slang.setText(data.title().get(n));
                    meaning.setText(data.findMeaning(data.title().get(n)));
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

    class slangQuizzPage extends  JPanel implements ActionListener{
        private JPanel slang;
        private JTextField slangText;
        private JPanel answer;
        private JButton btn1;
        private JButton btn2;
        private JButton btn3;
        private JButton btn4;
        private Random rand;

        public slangQuizzPage(){
            setLayout(new BorderLayout());
            rand=new Random();

            slang = new JPanel(new FlowLayout());
            slangText=new JTextField(30);
            slang.add(new JLabel("Slang"));
            slang.add(slangText);

            btn1 = new JButton();
            btn1.setActionCommand("btn1");
            btn1.addActionListener(slangQuizzPage.this);
            btn1.setPreferredSize(new Dimension(40,40));

            btn2 = new JButton();
            btn2.setActionCommand("btn2");
            btn2.addActionListener(slangQuizzPage.this);
            btn2.setPreferredSize(new Dimension(40,40));

            btn3 = new JButton();
            btn3.setActionCommand("btn3");
            btn3.addActionListener(slangQuizzPage.this);
            btn3.setPreferredSize(new Dimension(40,40));

            btn4 = new JButton();
            btn4.setActionCommand("btn4");
            btn4.addActionListener(slangQuizzPage.this);
            btn4.setPreferredSize(new Dimension(40,40));

            random();
            answer = new JPanel(new GridLayout(2,2));
            answer.add(btn1);
            answer.add(btn2);
            answer.add(btn3);
            answer.add(btn4);

            add(slang,BorderLayout.PAGE_START);
            add(answer,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String value = null;
            String strCmd=e.getActionCommand();

            if(strCmd.equals("btn1")){
                value=btn1.getText();
            }else if(strCmd.equals("btn2")){
                value=btn2.getText();
            }else if(strCmd.equals("btn3")){
                value=btn3.getText();
            }else if(strCmd.equals("btn4")){
                value=btn4.getText();
            }

            if(data.findMeaning(slangText.getText()).equals(value))
            {
                JOptionPane.showMessageDialog(frame,
                        "Right");
            }
            else{
                JOptionPane.showMessageDialog(frame,
                        "Correct answer: '"+slangText.getText()+":"+data.findMeaning(slangText.getText())+"'",
                        "Wrong",
                        JOptionPane.ERROR_MESSAGE);
            }

            random();
        }

        public void random(){
            int n = rand.nextInt(data.title().size());
            List<Integer> pos= new ArrayList<>();
            pos.add(n);
            for(int i=0;i<3;++i){
                int temp;
                do{
                    temp=rand.nextInt(data.title().size());
                }
                while (temp==n);
                pos.add(temp);
            }
            Collections.shuffle(pos);
            slangText.setText(data.title().get(n));
            btn1.setText(data.findMeaning(data.title().get(pos.get(0))));
            btn2.setText(data.findMeaning(data.title().get(pos.get(1))));
            btn3.setText(data.findMeaning(data.title().get(pos.get(2))));
            btn4.setText(data.findMeaning(data.title().get(pos.get(3))));
        }
    }

    class meaningQuizzPage extends  JPanel implements ActionListener{
        private JPanel meaning;
        private JTextField meaningText;
        private JPanel answer;
        private JButton btn1;
        private JButton btn2;
        private JButton btn3;
        private JButton btn4;
        private Random rand;

        public meaningQuizzPage(){
            setLayout(new BorderLayout());
            rand=new Random();

            meaning = new JPanel(new FlowLayout());
            meaningText=new JTextField(30);
            meaning.add(new JLabel("Slang"));
            meaning.add(meaningText);

            btn1 = new JButton();
            btn1.setActionCommand("btn1");
            btn1.addActionListener(meaningQuizzPage.this);
            btn1.setPreferredSize(new Dimension(40,40));

            btn2 = new JButton();
            btn2.setActionCommand("btn2");
            btn2.addActionListener(meaningQuizzPage.this);
            btn2.setPreferredSize(new Dimension(40,40));

            btn3 = new JButton();
            btn3.setActionCommand("btn3");
            btn3.addActionListener(meaningQuizzPage.this);
            btn3.setPreferredSize(new Dimension(40,40));

            btn4 = new JButton();
            btn4.setActionCommand("btn4");
            btn4.addActionListener(meaningQuizzPage.this);
            btn4.setPreferredSize(new Dimension(40,40));

            random();
            answer = new JPanel(new GridLayout(2,2));
            answer.add(btn1);
            answer.add(btn2);
            answer.add(btn3);
            answer.add(btn4);

            add(meaning,BorderLayout.PAGE_START);
            add(answer,BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String value = null;
            String strCmd=e.getActionCommand();

            if(strCmd.equals("btn1")){
                value=btn1.getText();
            }else if(strCmd.equals("btn2")){
                value=btn2.getText();
            }else if(strCmd.equals("btn3")){
                value=btn3.getText();
            }else if(strCmd.equals("btn4")){
                value=btn4.getText();
            }

            if(data.findTitle(meaningText.getText()).equals(value))
            {
                JOptionPane.showMessageDialog(frame,
                        "Right");
            }
            else{
                JOptionPane.showMessageDialog(frame,
                        "Correct answer: '"+data.findTitle(meaningText.getText())+":"+meaningText.getText()+"'",
                        "Wrong",
                        JOptionPane.ERROR_MESSAGE);
            }

            random();
        }

        public void random(){
            int n = rand.nextInt(data.meaning().size());
            List<Integer> pos= new ArrayList<>();
            pos.add(n);
            for(int i=0;i<3;++i){
                int temp;
                do{
                    temp=rand.nextInt(data.title().size());
                }
                while (temp==n);
                pos.add(temp);
            }
            Collections.shuffle(pos);
            meaningText.setText(data.meaning().get(n));
            btn1.setText(data.findTitle(data.meaning().get(pos.get(0))));
            btn2.setText(data.findTitle(data.meaning().get(pos.get(1))));
            btn3.setText(data.findTitle(data.meaning().get(pos.get(2))));
            btn4.setText(data.findTitle(data.meaning().get(pos.get(3))));
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
        deleteSlang= new deletePage();
        randomSlang = new randomPage();
        quizzSlang = new slangQuizzPage();
        quizzMeaning = new meaningQuizzPage();

        card.add(slangDict,"slang");
        card.add(meaningDict,"meaning");
        card.add(history, "history");
        card.add(addSlang,"add");
        card.add(editSlang,"edit");
        card.add(deleteSlang,"delete");
        card.add(randomSlang,"random");
        card.add(quizzSlang,"quizzS");
        card.add(quizzMeaning,"quizzM");

        add(card);
    }

    private static void refreshModel(){
        slangModel.clear();
        slangModel.addAll(data.title());
        meaningModel.clear();
        meaningModel.addAll(data.meaning());
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
        rawData=new Database(data);
        conA = new controlAction();
        conM = new controlMenu();
        javax.swing.SwingUtilities.invokeLater(GUI::createGUI);
    }
}
