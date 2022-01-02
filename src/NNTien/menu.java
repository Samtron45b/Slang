package NNTien;

import javax.swing.*;
import javax.swing.event.MenuListener;

import java.awt.event.ActionListener;

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
    private  JMenuItem addPage;
    private  JMenuItem edit;
    private  JMenuItem delete;
    private  JMenuItem today;
    private  JMenuItem bySlang;
    private  JMenuItem byDefinition;


    public menu(ActionListener conA, MenuListener conM){
        typeMenu= new JMenu("Type");
        historyMenu= new JMenu("History");
        slangMenu  = new JMenu("Slang");
        quizzMenu  = new JMenu("Quizz");

        slang = new JMenuItem("Slang");
        slang.setActionCommand("toSlang");
        slang.addActionListener(conA);

        definition = new JMenuItem("Definition");
        definition.setActionCommand("toDefinition");
        definition.addActionListener(conA);

        addPage = new JMenuItem("Add");
        addPage.setActionCommand("toAdd");
        addPage.addActionListener(conA);

        edit = new JMenuItem("Edit");
        edit.setActionCommand("toEdit");
        edit.addActionListener(conA);

        delete=new JMenuItem("Delete");
        delete.setActionCommand("toDelete");
        delete.addActionListener(conA);

        today = new JMenuItem("Today slang");
        today.setActionCommand("today");
        today.addActionListener(conA);

        bySlang = new JMenuItem("Slang Quizz");
        bySlang.setActionCommand("slangQuizz");
        bySlang.addActionListener(conA);

        byDefinition = new JMenuItem("Definition Quizz");
        byDefinition.setActionCommand("definitionQuizz");
        byDefinition.addActionListener(conA);


        typeMenu.add(slang);
        typeMenu.add(definition);

        historyMenu.addMenuListener(conM);

        slangMenu.add(addPage);
        slangMenu.add(edit);
        slangMenu.add(delete);
        slangMenu.add(today);

        quizzMenu.add(bySlang);
        quizzMenu.add(byDefinition);

        add(typeMenu);
        add(slangMenu);
        add(historyMenu);
        add(quizzMenu);
    }

}
