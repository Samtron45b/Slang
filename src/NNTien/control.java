package NNTien;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import NNTien.content;
/**
 * NNTien
 * Created by user
 * Date 12/15/2021 - 5:34 PM
 * Description: ...
 */
public class control implements ActionListener, MenuListener {
    public control(){
    };
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }

    @Override
    public void menuSelected(MenuEvent e) {
        CardLayout cl = (CardLayout)(content.card.getLayout());
        cl.show(content.card,"history");
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
