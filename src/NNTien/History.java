package NNTien;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * NNTien
 * Created by user
 * Date 12/16/2021 - 6:08 PM
 * Description: ...
 */
public class History extends JPanel {

    private JList slangList;
    private DefaultListModel<String> slangModel;
    private JPanel slangPane;
    private JList meaningList;
    private DefaultListModel<String> meaningModel;
    private JPanel meaningPane;

    public History(){
        try{
            BufferedReader slang_br = new BufferedReader(new FileReader("history/slang.txt"));
            BufferedReader meaning_br = new BufferedReader(new FileReader("history/meaning.txt"));
            String line;
            slangModel = new DefaultListModel<>();
            meaningModel = new DefaultListModel<>();

            while ((line=slang_br.readLine())!=null){
                slangModel.add(0,line);
            }

            while ((line=meaning_br.readLine())!=null){
                meaningModel.add(0,line);
            }

            setLayout(new FlowLayout());

            slangPane = new JPanel(new BorderLayout());
            slangPane.add(new JLabel("Slang"),BorderLayout.PAGE_START);
            if(slangModel.size()>22)
                slangModel.removeRange(22,slangModel.size()-1);
            slangList = new JList(slangModel);
            JScrollPane scrollSlang = new JScrollPane();
            scrollSlang.setViewportView(slangList);
            slangList.setPreferredSize(new Dimension(200, 400));
            slangPane.add(scrollSlang,BorderLayout.CENTER);

            meaningPane = new JPanel(new BorderLayout());
            meaningPane.add(new JLabel("Meaning"),BorderLayout.PAGE_START);
            if(meaningModel.size()>22)
                meaningModel.removeRange(22,meaningModel.size()-1);
            meaningList = new JList(meaningModel);
            JScrollPane scrollMeaning = new JScrollPane();
            scrollMeaning.setViewportView(meaningList);
            meaningList.setPreferredSize(new Dimension(200, 400));
            meaningPane.add(scrollMeaning,BorderLayout.CENTER);

            add(slangPane);
            add(meaningPane);

        }catch (Exception ex){
            System.out.println("no");
        }
    }

    public void updateSlang(String str){

        if(slangModel.size()>22)
            slangModel.removeRange(22,slangModel.size()-1);
        slangModel.add(0,str);

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("history/slang.txt",true));
            bw.write(str);
            bw.newLine();
            bw.close();
        }catch (Exception ex){
        }
    }

    public void updateMeaning(String str){

        if(meaningModel.size()>22)
            meaningModel.removeRange(22,meaningModel.size()-1);

        meaningModel.add(0,str);

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("history/meaning.txt",true));
            bw.write(str);
            bw.newLine();
            bw.close();
        }catch (Exception ex){
        }
    }

}
