/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import obfuscating.Obfuscator;

/**
 *
 * @author Sergey
 */
public class MainForm extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final ArrayList<JCheckBox> optioningCheckBoxes = new ArrayList<>();

    /**
     * Creates new form MainForm
     *
     */
    public MainForm() {
        initComponents();

        initializeOptioningCheckBoxes();

        progressLabel.setVisible(false);
        obfuscationProgressBar.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        saveCodeBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        mangledCodeTextArea = new javax.swing.JTextArea();
        loadCodeBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        sourceCodeTextArea = new javax.swing.JTextArea();
        rightPanel = new javax.swing.JPanel();
        generalOptionsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chooseAllOptionsChBox = new javax.swing.JCheckBox();
        obfuscatingOptionsPanel = new javax.swing.JPanel();
        ternaryTransformerChBox = new javax.swing.JCheckBox();
        variablesRenamingChBox = new javax.swing.JCheckBox();
        removeFormattingChBox = new javax.swing.JCheckBox();
        constantPrunerChBox = new javax.swing.JCheckBox();
        obfuscatingProcessPanel = new javax.swing.JPanel();
        obfucateBtn = new javax.swing.JButton();
        progressLabel = new javax.swing.JLabel();
        obfuscationProgressBar = new javax.swing.JProgressBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 1), new java.awt.Dimension(0, 1), new java.awt.Dimension(32767, 1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaScript Obfuscator v1.0");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        mainPanel.setLayout(new java.awt.GridBagLayout());

        leftPanel.setLayout(new java.awt.GridBagLayout());

        saveCodeBtn.setText("Сохранить файл");
        saveCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCodeBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        leftPanel.add(saveCodeBtn, gridBagConstraints);

        mangledCodeTextArea.setColumns(20);
        mangledCodeTextArea.setLineWrap(true);
        mangledCodeTextArea.setRows(5);
        jScrollPane2.setViewportView(mangledCodeTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        leftPanel.add(jScrollPane2, gridBagConstraints);

        loadCodeBtn.setText("Загрузить файл");
        loadCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCodeBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        leftPanel.add(loadCodeBtn, gridBagConstraints);

        sourceCodeTextArea.setColumns(20);
        sourceCodeTextArea.setLineWrap(true);
        sourceCodeTextArea.setRows(5);
        jScrollPane1.setViewportView(sourceCodeTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        leftPanel.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.99;
        gridBagConstraints.weighty = 0.1;
        mainPanel.add(leftPanel, gridBagConstraints);

        rightPanel.setMinimumSize(new java.awt.Dimension(250, 80));
        rightPanel.setLayout(new java.awt.GridBagLayout());

        generalOptionsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setText("Режимы обфускации:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        generalOptionsPanel.add(jLabel1, gridBagConstraints);

        chooseAllOptionsChBox.setText("выбрать все");
        chooseAllOptionsChBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseAllOptionsChBoxMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        generalOptionsPanel.add(chooseAllOptionsChBox, gridBagConstraints);

        obfuscatingOptionsPanel.setLayout(new java.awt.GridBagLayout());

        ternaryTransformerChBox.setText("Преобразование if-else");
        ternaryTransformerChBox.setToolTipText("<html>\nПреобразование if-else в тернарный<br>\nоператор ?: , если это возможно. Если <br>\nElse пусто, то заполняется мусорными <br>\nфункциями.\n</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(ternaryTransformerChBox, gridBagConstraints);

        variablesRenamingChBox.setText("Переименование переменных");
        variablesRenamingChBox.setToolTipText("<html>\nПреобразование имён всех<br>\nпеременных в непонятную <br>\nпоследовательность символов.\n</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(variablesRenamingChBox, gridBagConstraints);

        removeFormattingChBox.setSelected(true);
        removeFormattingChBox.setText("Удаление форматирования");
        removeFormattingChBox.setToolTipText("<html>\nУдаление всего форматирования<br>\nкода (пробелы, знаки табуляции <br>\nи перевода строк).\n</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(removeFormattingChBox, gridBagConstraints);

        constantPrunerChBox.setText("Сокращение констант");
        constantPrunerChBox.setToolTipText("<html>\nПоиск  простых констант в коде<br>\n(числа, строки, булевые значения)<br>\n и подстановка в местах их исполь-<br>\nзования их значений.\n</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(constantPrunerChBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        generalOptionsPanel.add(obfuscatingOptionsPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        rightPanel.add(generalOptionsPanel, gridBagConstraints);

        obfuscatingProcessPanel.setLayout(new java.awt.GridBagLayout());

        obfucateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/obfuscateBtn.png"))); // NOI18N
        obfucateBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        obfucateBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        obfucateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obfucateBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        obfuscatingProcessPanel.add(obfucateBtn, gridBagConstraints);

        progressLabel.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingProcessPanel.add(progressLabel, gridBagConstraints);

        obfuscationProgressBar.setToolTipText("");
        obfuscationProgressBar.setPreferredSize(new java.awt.Dimension(150, 20));
        obfuscationProgressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        obfuscatingProcessPanel.add(obfuscationProgressBar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 10.0;
        rightPanel.add(obfuscatingProcessPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        rightPanel.add(filler1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        mainPanel.add(rightPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        getContentPane().add(mainPanel, gridBagConstraints);

        setSize(new java.awt.Dimension(629, 543));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loadCodeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCodeBtnActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JavaScript source files (*.js)",
                "js"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File javaScriptFile = chooser.getSelectedFile();
                String sourceCode = String.join("\n",
                        Files.readAllLines(javaScriptFile.toPath()));
                sourceCodeTextArea.setText(sourceCode);
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_loadCodeBtnActionPerformed

    private void obfucateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obfucateBtnActionPerformed
        String sourceCode = sourceCodeTextArea.getText();
        try {
            Properties obfuscatingOptions = new Properties();
            obfuscatingOptions.put("constantPruner", String.valueOf(constantPrunerChBox.isSelected()));
            obfuscatingOptions.put("ternaryTransformer", String.valueOf(ternaryTransformerChBox.isSelected()));
            obfuscatingOptions.put("renaming", String.valueOf(variablesRenamingChBox.isSelected()));
            obfuscatingOptions.put("reformatting", String.valueOf(removeFormattingChBox.isSelected()));

            Obfuscator obfuscator = new Obfuscator(sourceCode, obfuscatingOptions, progressLabel);
            obfuscator.addPropertyChangeListener((
                    PropertyChangeEvent event) -> {
                        switch (event.getPropertyName()) {
                            case "progress":
                                obfuscationProgressBar.setValue((Integer) event.getNewValue());
                                break;
                            case "state":
                                switch ((StateValue) event.getNewValue()) {
                                    case STARTED:
                                        obfuscationProgressBar.setValue(0);
                                        obfuscationProgressBar.setVisible(true);
                                        progressLabel.setVisible(true);
                                        break;
                                    case DONE:
                                        obfuscationProgressBar.setVisible(false);
                                        progressLabel.setVisible(false);
                                        try {
                                            mangledCodeTextArea.setText(obfuscator.get());
                                        } catch (InterruptedException | ExecutionException interruptedException) {
                                            interruptedException.printStackTrace();
                                        }
                                        break;
                                }
                                break;
                        }
                    });
            obfuscator.execute();
        } catch (ScriptException | FileNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_obfucateBtnActionPerformed

    private void chooseAllOptionsChBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseAllOptionsChBoxMouseClicked
        boolean shouldSelectAll = chooseAllOptionsChBox.isSelected();
        optioningCheckBoxes.stream().forEach(checkbox -> {
            checkbox.setSelected(shouldSelectAll);
        });
        obfuscatingProcessPanel.setVisible(shouldSelectAll);
    }//GEN-LAST:event_chooseAllOptionsChBoxMouseClicked

    private void saveCodeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCodeBtnActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JavaScript source files (*.js)",
                "js"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File JSFile = chooser.getSelectedFile();
                String JSFilePath = JSFile.getAbsolutePath();
                if (!JSFilePath.endsWith(".js")) {
                    JSFile = new File(JSFilePath + ".js");
                }
                writeUTF8ToFile(JSFile, false, mangledCodeTextArea.getText());
                JOptionPane.showMessageDialog(this, "Файл " + JSFile.getName()
                        + " успешно записан!");
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveCodeBtnActionPerformed

    /**
     * Writes text to file regarding UTF-8 with BOM.
     *
     * @param file
     * @param append
     * @param data
     * @throws IOException
     */
    private void writeUTF8ToFile(File file, boolean append, String data)
            throws IOException {
        boolean skipBOM = append && file.isFile() && (file.length() > 0);
        Closer res = new Closer();
        try {
            OutputStream out = res.using(new FileOutputStream(file, append));
            Writer writer = res.using(new OutputStreamWriter(out, Charset
                    .forName("UTF-8")));
            if (!skipBOM) {
                writer.write('\uFEFF');
            }
            writer.write(data);
        } finally {
            res.close();
        }
    }

    private class Closer implements Closeable {

        private Closeable closeable;

        public <T extends Closeable> T using(T t) {
            closeable = t;
            return t;
        }

        @Override
        public void close() throws IOException {
            if (closeable != null) {
                closeable.close();
            }
        }
    }

    private void initializeOptioningCheckBoxes() {

        for (Component component : obfuscatingOptionsPanel.getComponents()) {
            optioningCheckBoxes.add((JCheckBox) component);
        }

        optioningCheckBoxes.stream().forEach((checkBox) -> {
            checkBox.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    chooseAllOptionsChBox.setSelected(areAllOptionsSelected());
                    obfuscatingProcessPanel.setVisible(isAnyOptionSelected());
                }
            });
        });
    }

    private boolean areAllOptionsSelected() {
        return optioningCheckBoxes.stream().allMatch((JCheckBox t) -> t.isSelected());
    }

    private boolean isAnyOptionSelected() {
        return optioningCheckBoxes.stream().anyMatch((JCheckBox t) -> t.isSelected());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chooseAllOptionsChBox;
    private javax.swing.JCheckBox constantPrunerChBox;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel generalOptionsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton loadCodeBtn;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextArea mangledCodeTextArea;
    private javax.swing.JButton obfucateBtn;
    private javax.swing.JPanel obfuscatingOptionsPanel;
    private javax.swing.JPanel obfuscatingProcessPanel;
    private javax.swing.JProgressBar obfuscationProgressBar;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JCheckBox removeFormattingChBox;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton saveCodeBtn;
    private javax.swing.JTextArea sourceCodeTextArea;
    private javax.swing.JCheckBox ternaryTransformerChBox;
    private javax.swing.JCheckBox variablesRenamingChBox;
    // End of variables declaration//GEN-END:variables

}
