package NNTien;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

/**
 * NNTien
 * Created by user
 * Date 12/16/2021 - 6:08 PM
 * Description: ...
 */
public class History extends JPanel {
    private List<String> slang;
    private List<String> meaning;
    private JList slangList;
    private JList meaningList;
    private JPanel slangPane;
    private JPanel meaningPane;

    public History(){
        try{
            BufferedReader slang_br = new BufferedReader(new FileReader("history/slang.txt"));
            BufferedReader meaning_br = new BufferedReader(new FileReader("history/meaning.txt"));
            String line;

            slang=new ArrayList<>();
            meaning=new ArrayList<>();
            while ((line=slang_br.readLine())!=null){
                slang.add(0,line);
            }

            while ((line=meaning_br.readLine())!=null){
                meaning.add(0,line);
            }

            setLayout(new FlowLayout());

            slangPane = new JPanel();
            slangList = new JList(slang.toArray());
            slangPane.add(slangList);

            meaningPane = new JPanel();
            meaningList = new JList(meaning.toArray());
            meaningPane.add(meaningList);

            add(slangPane);
            add(meaningPane);

        }catch (Exception ex){
        }
    }

    public void updateSlang(String str){

        slang.add(0,str);

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("history/slang.txt",true));
            bw.write(str);
            bw.newLine();
            bw.close();
        }catch (Exception ex){
        }
    }

    public void updateMeaning(String str){

        meaning.add(0,str);

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("history/meaning.txt",true));
            bw.write(str);
            bw.newLine();
            bw.close();
        }catch (Exception ex){
        }
    }

}
