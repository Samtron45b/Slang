package NNTien;

import javax.swing.*;
import NNTien.*;

import java.awt.*;
import java.awt.event.*;

/**
 * NNTien
 * Created by user
 * Date 12/16/2021 - 5:02 PM
 * Description: ...
 */
public class wordPage extends JPanel implements ActionListener {
    public JPanel slang;
    public JPanel definition;
    private JPanel slangBody;
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmdStr = e.getActionCommand();
        if(e.equals("searchSlang")){

        }
        else{

        }
    }

    private JPanel searchBar(String cmd){
        JPanel search = new JPanel();
        JTextField searchText = new JTextField(30);
        JButton searchBtn = new JButton("Search");
        searchBtn.setActionCommand(cmd);
        searchBtn.addActionListener(this);
        search.add(new JLabel("Search: "));
        search.add(searchText);
        search.add(searchBtn);

        return search;
    }

    public wordPage(Database data){
        slang = new JPanel();
        slang.setLayout(new BorderLayout());
        slang.add(searchBar("searchSlang"),BorderLayout.PAGE_END);
        JList slangList = new JList(data.title().toArray());
        slangList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList thisList = (JList) e.getSource();
                if(e.getClickCount()==2){
                    int index = thisList.locationToIndex(e.getPoint());
                    if(index>=0){
                        Object o =thisList.getModel().getElementAt(index);
//                        System.out.println(data.title().);
                    }
                }
            }
        });
        JScrollPane scrollTitle = new JScrollPane(slangList);

        definition = new JPanel();
        definition.add(searchBar("searchDefinition"));
        JList meaningList = new JList(data.meaning().toArray());
        JScrollPane scrollMeaning = new JScrollPane(meaningList);

    }


}
