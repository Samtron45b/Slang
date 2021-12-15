package NNTien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

import NNTien.Database;
/**
 * NNTien
 * Created by user
 * Date 12/15/2021 - 3:04 PM
 * Description: ...
 */
public class GUI extends JPanel implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        Database data = new Database();
        if(data!=null)
        {
            List<String> temp=data.title();
            System.out.println(temp.get(0));
        }
    }
}
