package NNTien;

import javax.swing.*;
import NNTien.History;

import java.awt.*;

/**
 * NNTien
 * Created by user
 * Date 12/16/2021 - 4:28 PM
 * Description: ...
 */
public class content extends JPanel{
    public static JPanel card;

    public content(){
        card = new JPanel(new CardLayout());
        History history = new History();
        card.add(history,"history");
    }
}
