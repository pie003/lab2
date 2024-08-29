/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import CommonMath.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author User
 */
public class Gui extends JFrame {
    private final JTextArea textArea;
    private final JButton importButton;
    private final JButton exportButton;
    private final JButton closeButton;
    private final JScrollPane scrollpane;
    private CommonMath[] mathArray;
    
    public Gui (MathDirector director, Xlsx xslxworker){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(700, 500); 
        this.setLayout(new BorderLayout());
        this.closeButton = new JButton("Exit");
        this.exportButton = new JButton("Export to xlsx");
        this.importButton = new JButton("Import from xlsx");
        this.textArea = new JTextArea();
        this.scrollpane = new JScrollPane(this.textArea);
        
        this.closeButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        
        this.importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField pathField = new JTextField(100);
                JTextField sheetField = new JTextField(5);
                JPanel myPanel = new JPanel(new GridLayout(0, 1));
                myPanel.add(new JLabel("Введите путь к файлу для импорта:"));
                myPanel.add(pathField);
                myPanel.add(new JLabel("Введите лист из Excel файла:"));
                myPanel.add(sheetField);
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Импорт из xlsx", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String path = pathField.getText();
                    try {
                        int sheet = Integer.parseInt(sheetField.getText());
                        try {
                            HashMap<Integer,ArrayList<Object>> dists = xslxworker.readFromFile(sheet, path);
                            StringBuilder resultText = new StringBuilder();
                            mathArray = new CommonMath[dists.size()];
                            for (int i=0; i<dists.size();i++){
                                MathBuilder builder = new MathBuilder();
                                resultText.append(dists.get(i).get(0).toString()).append("\n");
                                mathArray[i] = director.doAllMath(dists.get(i), builder);
                                resultText.append(mathArray[i].getStatisticCharact()).append("\n");
                                resultText.append("-----" + "\n");  
                            }
                            textArea.setText(resultText.toString());
                            
                        } catch (Exception ex){
                            textArea.setText("Error reading the file. Please check the path and file format.");
                            JOptionPane.showMessageDialog(null,
                                    "Error reading the file. Please check the path and file format.",
                                    "File Error", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NumberFormatException n){
                        //textArea.setText("Error reading the file. Please write only integer sheet numbers");
                        JOptionPane.showMessageDialog(null,
                                "Error reading the file. Please write only integer sheet numbers",
                                "File Error", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });
        
        this.exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField pathField = new JTextField(100);
                JTextField sheetField = new JTextField(100);
                JPanel myPanel = new JPanel(new GridLayout(0, 1));
                myPanel.add(new JLabel("Введите путь к файлу для экспорта:"));
                myPanel.add(pathField);
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Эскпорт в xlsx", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String path = pathField.getText();
                    try {
                        String[] headers = {"Среднее геометрическое","Оценка стандартного отклонения","Размах","Количество элементов в выборке","Коэффициент вариации","Доверительный интервал для мат. ожидания","Оценка дисперсии","Максимум","Минимум","Коэффициенты ковариации для всех пар случайных чисел"};

                        Object[][] data;
                        
                        for (CommonMath math: mathArray){
                            
                            data = new Object[math.getCov().length][math.getCov().length+headers.length];
                            data[0][0] = math.getGeomMean();
                            data[0][1] = math.getStandartDeviation();
                            data[0][2] = math.getSpan();
                            data[0][3] = math.getN();
                            data[0][4] = math.getKvar();
                            data[0][5] = math.getCoinfidenceInt();
                            data[0][6] = math.getVar();
                            data[0][7] = math.getMax();
                            data[0][8] = math.getMin();
                            
                            for (int i=0; i<math.getCov().length;i++){
                                for (int j=0; j<math.getCov()[i].length; j++){
                                    data[i][j+headers.length]=math.getCov()[i][j];
                                }
                            }
                            
                            xslxworker.writeToExcelSheet(path, headers, data, math.getName()); 
                        }
                        
                    } catch (Exception ex){
                            JOptionPane.showMessageDialog(null,
                                    "Error writing to file. Please check the path and file format.",
                                    "File Error", JOptionPane.WARNING_MESSAGE);
                        }
                }
            }
        });    
    
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(this.exportButton);
        panel.add(this.importButton);
        panel.add(this.closeButton);
        
        this.add(this.scrollpane, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }
    
}
