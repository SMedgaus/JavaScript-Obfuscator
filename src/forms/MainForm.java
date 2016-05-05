/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import obfuscating.Obfuscator;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Sergey
 */
public class MainForm extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private static Obfuscator codeObfuscator;

    /**
     * Creates new form MainForm
     *
     */
    public MainForm() {
        initComponents();
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

        jPanel1 = new javax.swing.JPanel();
        optionsPanel = new javax.swing.JPanel();
        obfuscatingOptionsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chooseAllOptionsChBox = new javax.swing.JCheckBox();
        variablesRenamingChBox = new javax.swing.JCheckBox();
        ternaryTransformerChBox = new javax.swing.JCheckBox();
        removeFormattingChBox = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        obfucateBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        saveCodeBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        mangledCodeTextArea = new javax.swing.JTextArea();
        loadCodeBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        sourceCodeTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        optionsPanel.setMinimumSize(new java.awt.Dimension(250, 80));
        optionsPanel.setLayout(new java.awt.GridBagLayout());

        obfuscatingOptionsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel1.setText("Режимы обфускации:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(jLabel1, gridBagConstraints);

        chooseAllOptionsChBox.setText("выбрать все");
        chooseAllOptionsChBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseAllOptionsChBoxMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        obfuscatingOptionsPanel.add(chooseAllOptionsChBox, gridBagConstraints);

        variablesRenamingChBox.setText("Переименование переменных");
        variablesRenamingChBox.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(variablesRenamingChBox, gridBagConstraints);

        ternaryTransformerChBox.setText("Преобразование if-else");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(ternaryTransformerChBox, gridBagConstraints);

        removeFormattingChBox.setSelected(true);
        removeFormattingChBox.setText("Удаление форматирования");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        obfuscatingOptionsPanel.add(removeFormattingChBox, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(220, 25));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(250, 56));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(3);
        jTextArea1.setTabSize(2);
        jTextArea1.setText("Данный режим удаляет всё форматирование кода (пробелы, знаки табуляции и перевода строк).");
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextArea1.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextArea1.setEnabled(false);
        jTextArea1.setMinimumSize(new java.awt.Dimension(20, 18));
        jTextArea1.setPreferredSize(new java.awt.Dimension(220, 50));
        jScrollPane3.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        obfuscatingOptionsPanel.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 0.1;
        optionsPanel.add(obfuscatingOptionsPanel, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

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
        jPanel5.add(obfucateBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 1.0;
        optionsPanel.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(optionsPanel, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        saveCodeBtn.setText("Сохранить файл");
        saveCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCodeBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel3.add(saveCodeBtn, gridBagConstraints);

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
        jPanel3.add(jScrollPane2, gridBagConstraints);

        loadCodeBtn.setText("Загрузить файл");
        loadCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCodeBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(loadCodeBtn, gridBagConstraints);

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
        jPanel3.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.99;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        getContentPane().add(jPanel1, gridBagConstraints);

        setSize(new java.awt.Dimension(588, 543));
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
            obfuscatingOptions.put("ternaryTransformer", "true");
            obfuscatingOptions.put("renaming", "true");
            
            String obfuscatedCode =
                    codeObfuscator.obfuscateCode(sourceCode, obfuscatingOptions);
            
            mangledCodeTextArea.setText(obfuscatedCode);
        } catch (ScriptException | NoSuchMethodException | ParseException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_obfucateBtnActionPerformed

    private void chooseAllOptionsChBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseAllOptionsChBoxMouseClicked
        boolean shouldSelectAll = chooseAllOptionsChBox.isSelected();
        for (Component component : obfuscatingOptionsPanel.getComponents()) {
            if (component instanceof JCheckBox && component != evt.getComponent()) {
                ((AbstractButton) component).setSelected(shouldSelectAll);
            }
        }
    }//GEN-LAST:event_chooseAllOptionsChBoxMouseClicked

    private void saveCodeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCodeBtnActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("JavaScript source files (*.js)",
            "js"));
        
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File javaScriptFile = chooser.getSelectedFile();
                Files.write(javaScriptFile.toPath(), mangledCodeTextArea.getText().getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveCodeBtnActionPerformed

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
            try {
                codeObfuscator = new Obfuscator();
            } catch (FileNotFoundException | ScriptException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                System.exit(1);
            }
            mainForm.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chooseAllOptionsChBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton loadCodeBtn;
    private javax.swing.JTextArea mangledCodeTextArea;
    private javax.swing.JButton obfucateBtn;
    private javax.swing.JPanel obfuscatingOptionsPanel;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JCheckBox removeFormattingChBox;
    private javax.swing.JButton saveCodeBtn;
    private javax.swing.JTextArea sourceCodeTextArea;
    private javax.swing.JCheckBox ternaryTransformerChBox;
    private javax.swing.JCheckBox variablesRenamingChBox;
    // End of variables declaration//GEN-END:variables
}
