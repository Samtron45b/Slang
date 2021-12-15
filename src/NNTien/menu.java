package NNTien;

import javax.swing.*;
import NNTien.control;
/**
 * NNTien
 * Created by user
 * Date 12/15/2021 - 6:04 PM
 * Description: ...
 */
public class menu extends JMenuBar {
    private static JMenu typeMenu;
    private static JMenu historyMenu;
    private static JMenu slangMenu;
    private static JMenu quizzMenu;
    private  JMenuItem slang;
    private  JMenuItem definition;
    private  JMenuItem add;
    private  JMenuItem edit;
    private  JMenuItem delete;
    private  JMenuItem today;
    private  JMenuItem bySlang;
    private  JMenuItem byDefinition;


    public menu(){
        control con=new control();
        typeMenu= new JMenu("Type");
        historyMenu= new JMenu("History");
        slangMenu  = new JMenu("Slang");
        quizzMenu  = new JMenu("Quizz");
        slang = new JMenuItem("Slang");
        definition = new JMenuItem("Definition");
        typeMenu.add(slang);
        typeMenu.add(definition);
        add(typeMenu);
        add(historyMenu);
        add(slangMenu);
        add(quizzMenu);
    }

}
