package Gui;

import CommonMath.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame {

    private final JButton importButton;
    private final JButton exportButton;
    private final JButton closeButton;

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;

    private CommonMath[] mathArray;

    private static final String[] TABLE_HEADERS = {
            "Название",
            "Среднее геометрическое",
            "Станд. отклонение",
            "Размах",
            "N",
            "Коэф. вариации",
            "Дов. интервал",
            "Дисперсия",
            "Максимум",
            "Минимум",
            "Ковариация"
    };

    public Gui(MathDirector director, Xlsx xslxworker) {

        setTitle("Статистический анализ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(TABLE_HEADERS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        importButton = new JButton("Import from xlsx");
        exportButton = new JButton("Export to xlsx");
        closeButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> System.exit(0));

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                String path = fileChooser.getSelectedFile().getAbsolutePath();

                List<String> sheetNames;
                try {
                    sheetNames = xslxworker.getSheetNames(path);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            Gui.this,
                            "Ошибка чтения Excel файла",
                            "Ошибка",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                JComboBox<String> sheetComboBox = new JComboBox<>();
                for (int i = 0; i < sheetNames.size(); i++) {
                    sheetComboBox.addItem((i + 1) + " — " + sheetNames.get(i));
                }

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Выберите лист Excel:"));
                panel.add(sheetComboBox);

                if (JOptionPane.showConfirmDialog(
                        Gui.this,
                        panel,
                        "Импорт из xlsx",
                        JOptionPane.OK_CANCEL_OPTION
                ) != JOptionPane.OK_OPTION) {
                    return;
                }

                int sheetIndex = sheetComboBox.getSelectedIndex();

                try {
                    HashMap<Integer, ArrayList<Object>> dists =
                            xslxworker.readFromFile(sheetIndex, path);

                    tableModel.setRowCount(0);
                    mathArray = new CommonMath[dists.size()];

                    for (int i = 0; i < dists.size(); i++) {
                        MathBuilder builder = new MathBuilder();
                        mathArray[i] = director.doAllMath(dists.get(i), builder);
                        CommonMath m = mathArray[i];

                        tableModel.addRow(new Object[]{
                                m.getName(),
                                m.getGeomMean(),
                                m.getStandartDeviation(),
                                m.getSpan(),
                                m.getN(),
                                m.getKvar(),
                                m.getCoinfidenceInt(),
                                m.getVar(),
                                m.getMax(),
                                m.getMin(),
                                m.getCov()
                        });
                    }

                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(
                            Gui.this,
                            "Листа с таким номером не существует",
                            "Ошибка",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            Gui.this,
                            "Ошибка чтения файла",
                            "Ошибка",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (mathArray == null || mathArray.length == 0) {
                JOptionPane.showMessageDialog(
                        Gui.this,
                        "Нет данных для экспорта",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить файл Excel");
            fileChooser.setSelectedFile(new java.io.File("result.xlsx"));

            int chooserResult = fileChooser.showSaveDialog(Gui.this);
            if (chooserResult != JFileChooser.APPROVE_OPTION) {
                return;
            }

            String path = fileChooser.getSelectedFile().getAbsolutePath();

            // если пользователь не указал расширение
            if (!path.toLowerCase().endsWith(".xlsx")) {
                path += ".xlsx";
            }

            String[] headers = {
                    "Среднее геометрическое",
                    "Станд. отклонение",
                    "Размах",
                    "N",
                    "Коэф. вариации",
                    "Дов. интервал",
                    "Дисперсия",
                    "Максимум",
                    "Минимум",
                    "Ковариация"
            };

            try {
                for (CommonMath math : mathArray) {

                    Object[][] data = new Object[1][headers.length];

                    data[0][0] = math.getGeomMean();
                    data[0][1] = math.getStandartDeviation();
                    data[0][2] = math.getSpan();
                    data[0][3] = math.getN();
                    data[0][4] = math.getKvar();
                    data[0][5] = math.getCoinfidenceInt();
                    data[0][6] = math.getVar();
                    data[0][7] = math.getMax();
                    data[0][8] = math.getMin();
                    data[0][9] = math.getCov();

                    xslxworker.writeToExcelSheet(
                            path,
                            headers,
                            data,
                            math.getName()
                    );
                }

                JOptionPane.showMessageDialog(
                        Gui.this,
                        "Файл успешно сохранён:\n" + path,
                        "Экспорт завершён",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        Gui.this,
                        "Ошибка записи файла",
                        "Ошибка",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    });

    }
}
