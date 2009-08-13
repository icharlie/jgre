/*
 * Copyright 2009 digman543
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * GRESearchExaminationPanel.java
 * 
 */

/*
 * GRESearchExaminationPanel.java
 *
 * Created on 2009/5/22, 上午 12:33:53
 */
package gre.view;

import gre.database.AccessDataBaseTool;
import gre.database.DataBaseTool;
import gre.utility.AnalogyEnum;
import gre.utility.AntonymEnum;
import gre.utility.ExamTypesEnum;
import gre.utility.Utility;
import gre.utility.VocabularyEnum;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdesktop.application.Action;

public class GRESearchExaminationPanel extends javax.swing.JPanel {

  ArrayList<String> _index;
  boolean isError = false;
  int examType;

  /** Creates new form GRESearchExaminationPanel */
  public GRESearchExaminationPanel() {
    initComponents();
  }

  @Action
  public void showNewExamUnitBox() {
    JFrame mainFrame = GREApp.getApplication().getMainFrame();
    GRENewExamUnitBox newExamUnitBox = new GRENewExamUnitBox(mainFrame, queryTable, examTypeComboBox.getSelectedIndex(), dbTool);
    newExamUnitBox.setLocationRelativeTo(mainFrame);
    GREApp.getApplication().show(newExamUnitBox);
    upDateExamUnits();
  }

  private ArrayList<String[]> getUnitData() {
    ArrayList<String[]> unitData = new ArrayList<String[]>();
    ArrayList<String[]> temp = null;
    String unit = unitComboBox.getSelectedItem().toString();
    isError = errorCheckBox.isSelected();
    examType = examTypeComboBox.getSelectedIndex();
    _index = new ArrayList<String>();
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
      temp = dbTool.getAllAntonym(unit, isError);
      for (String[] row : temp) {
        unitData.add(new String[]{
              row[AntonymEnum.ENG_1.ordinal()],
              row[AntonymEnum.ENG_2.ordinal()],
              row[AntonymEnum.CHINESE_1.ordinal()],
              row[AntonymEnum.CHINESE_2.ordinal()]
            });
        _index.add(row[AntonymEnum.INDEX.ordinal()]);
      }
    } else if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANALOGY.ordinal()) {
      temp = dbTool.getAllAnalogy(unit, isError);
      for (String[] row : temp) {
        unitData.add(new String[]{
              row[AnalogyEnum.ENG_1.ordinal()].concat(":").concat(row[AnalogyEnum.ENG_2.ordinal()]),
              row[AnalogyEnum.ENG_3.ordinal()].concat(":").concat(row[AnalogyEnum.ENG_4.ordinal()]),
              row[AnalogyEnum.CHINESE_1.ordinal()].concat(":").concat(row[AnalogyEnum.CHINESE_2.ordinal()]),
              row[AnalogyEnum.CHINESE_3.ordinal()].concat(":").concat(row[AnalogyEnum.CHINESE_4.ordinal()])
            });
        _index.add(row[AnalogyEnum.INDEX.ordinal()]);
      }
    } else {
      temp = dbTool.getAllVacabulary(unit, false,isError);
      for (String[] row : temp) {
        unitData.add(new String[]{
              row[VocabularyEnum.ENG_1.ordinal()],
              row[VocabularyEnum.CHINESE_1.ordinal()],
              "",
              ""
            });
        _index.add(row[VocabularyEnum.INDEX.ordinal()]);
      }
    }
    return unitData;
  }

  /**
   * remove the previous data in the table
   */
  private void removeTableData() {
    DefaultTableModel model = (DefaultTableModel) queryTable.getModel();
    for (int row = model.getRowCount() - 1; row > -1; row--) {
      model.removeRow(row);
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    queryScrollPane = new javax.swing.JScrollPane();
    queryTable = new javax.swing.JTable();
    controlPanel = new javax.swing.JPanel();
    examTypeLabel = new javax.swing.JLabel();
    String[] examTypes = new String[]{ExamTypesEnum.ANTONYM.getTypeName(), ExamTypesEnum.ANALOGY.getTypeName(), ExamTypesEnum.VOCABULARY.getTypeName()};
    examTypeComboBox = new javax.swing.JComboBox(examTypes);
    unitLabel = new javax.swing.JLabel();
    unitComboBox = new javax.swing.JComboBox();
    errorCheckBox = new javax.swing.JCheckBox();
    searchButton = new javax.swing.JButton();
    cleanButton = new javax.swing.JButton();
    exportButton = new javax.swing.JButton();
    addNewUnitButton = new javax.swing.JButton();

    setName("Form"); // NOI18N
    setOpaque(false);
    setPreferredSize(new java.awt.Dimension(750, 700));

    queryScrollPane.setName("queryScrollPane"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getResourceMap(GRESearchExaminationPanel.class);
    queryTable.setFont(resourceMap.getFont("queryTable.font")); // NOI18N
    queryTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "題目", "答案", "題目(中)", "答案(中)"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
      };
      boolean[] canEdit = new boolean [] {
        false, false, false, false
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    queryTable.setName("queryTable"); // NOI18N
    queryTable.setRowHeight(20);
    queryScrollPane.setViewportView(queryTable);

    controlPanel.setName("controlPanel"); // NOI18N

    examTypeLabel.setFont(resourceMap.getFont("examTypeLabel.font")); // NOI18N
    examTypeLabel.setText(resourceMap.getString("examTypeLabel.text")); // NOI18N
    examTypeLabel.setName("examTypeLabel"); // NOI18N

    examTypeComboBox.setFont(resourceMap.getFont("examTypeComboBox.font")); // NOI18N
    examTypeComboBox.setName("examTypeComboBox"); // NOI18N
    examTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        examTypeComboBoxActionPerformed(evt);
      }
    });

    unitLabel.setFont(resourceMap.getFont("unitLabel.font")); // NOI18N
    unitLabel.setText(resourceMap.getString("unitLabel.text")); // NOI18N
    unitLabel.setName("unitLabel"); // NOI18N

    unitComboBox.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
    unitComboBox.setName("unitComboBox"); // NOI18N

    errorCheckBox.setFont(resourceMap.getFont("errorCheckBox.font")); // NOI18N
    errorCheckBox.setText(resourceMap.getString("errorCheckBox.text")); // NOI18N
    errorCheckBox.setName("errorCheckBox"); // NOI18N

    searchButton.setFont(resourceMap.getFont("jButton1.font")); // NOI18N
    searchButton.setText(resourceMap.getString("searchButton.text")); // NOI18N
    searchButton.setName("searchButton"); // NOI18N
    searchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        searchButtonActionPerformed(evt);
      }
    });

    cleanButton.setFont(resourceMap.getFont("jButton1.font")); // NOI18N
    cleanButton.setText(resourceMap.getString("cleanButton.text")); // NOI18N
    cleanButton.setName("cleanButton"); // NOI18N
    cleanButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cleanButtonActionPerformed(evt);
      }
    });

    exportButton.setFont(resourceMap.getFont("jButton1.font")); // NOI18N
    exportButton.setText(resourceMap.getString("exportButton.text")); // NOI18N
    exportButton.setEnabled(false);
    exportButton.setName("exportButton"); // NOI18N
    exportButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exportButtonActionPerformed(evt);
      }
    });

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getActionMap(GRESearchExaminationPanel.class, this);
    addNewUnitButton.setAction(actionMap.get("showNewExamUnitBox")); // NOI18N
    addNewUnitButton.setFont(resourceMap.getFont("addNewUnitButton.font")); // NOI18N
    addNewUnitButton.setText(resourceMap.getString("addNewUnitButton.text")); // NOI18N
    addNewUnitButton.setToolTipText(resourceMap.getString("addNewUnitButton.toolTipText")); // NOI18N
    addNewUnitButton.setName("addNewUnitButton"); // NOI18N

    org.jdesktop.layout.GroupLayout controlPanelLayout = new org.jdesktop.layout.GroupLayout(controlPanel);
    controlPanel.setLayout(controlPanelLayout);
    controlPanelLayout.setHorizontalGroup(
      controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(controlPanelLayout.createSequentialGroup()
        .add(43, 43, 43)
        .add(controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(controlPanelLayout.createSequentialGroup()
            .add(searchButton)
            .add(cleanButton)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(exportButton)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(addNewUnitButton)
            .add(11, 11, 11))
          .add(controlPanelLayout.createSequentialGroup()
            .add(examTypeLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(5, 5, 5)
            .add(examTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(5, 5, 5)
            .add(unitLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(unitComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(errorCheckBox)
        .addContainerGap(164, Short.MAX_VALUE))
    );
    controlPanelLayout.setVerticalGroup(
      controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(controlPanelLayout.createSequentialGroup()
        .add(controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(examTypeLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(controlPanelLayout.createSequentialGroup()
            .add(3, 3, 3)
            .add(examTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(unitLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(controlPanelLayout.createSequentialGroup()
            .add(3, 3, 3)
            .add(unitComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
        .add(controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(searchButton)
          .add(controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(cleanButton)
            .add(exportButton)
            .add(addNewUnitButton))))
      .add(controlPanelLayout.createSequentialGroup()
        .add(3, 3, 3)
        .add(errorCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    );

    controlPanelLayout.linkSize(new java.awt.Component[] {examTypeLabel, unitLabel}, org.jdesktop.layout.GroupLayout.VERTICAL);

    controlPanelLayout.linkSize(new java.awt.Component[] {cleanButton, exportButton, searchButton}, org.jdesktop.layout.GroupLayout.VERTICAL);

    examTypeComboBox.setSelectedIndex(0);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(queryScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
      .add(controlPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(controlPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(queryScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void cleanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanButtonActionPerformed
    addNewUnitButton.setEnabled(false);
    exportButton.setEnabled(false);
    removeTableData();
    if (isError) {
      if (examType == ExamTypesEnum.ANTONYM.ordinal()) {
        for (String index : _index) {
          dbTool.updateAntonymErrorSate(index, "");
        }
      } else if (examType == ExamTypesEnum.ANALOGY.ordinal()) {
        for (String index : _index) {
          dbTool.updateAntonymErrorSate(index, "");
        }
      } else {
        for (String index : _index) {
          dbTool.updateAntonymErrorSate(index, "");
        }
      }
    }
}//GEN-LAST:event_cleanButtonActionPerformed

  private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
    removeTableData();
    DefaultTableModel model = (DefaultTableModel) queryTable.getModel();
    ArrayList<String[]> unitData = null;
    unitData = getUnitData();
    for (String[] element : unitData) {
      model.addRow(element);
    }
    if (model.getRowCount() > 0) {
      addNewUnitButton.setEnabled(true);
      exportButton.setEnabled(true);
    } else {
      addNewUnitButton.setEnabled(false);
      exportButton.setEnabled(false);
    }
}//GEN-LAST:event_searchButtonActionPerformed

  private void examTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examTypeComboBoxActionPerformed
    Set<String> units = null;
    unitComboBox.removeAllItems();
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
      units = dbTool.getAntonymUnits();
      for (Iterator<String> it = units.iterator(); it.hasNext();) {
        unitComboBox.addItem(it.next());
      }
      return;
    }
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANALOGY.ordinal()) {
      units = dbTool.getAnalogyUnits();
      for (Iterator<String> it = units.iterator(); it.hasNext();) {
        unitComboBox.addItem(it.next());
      }
      return;
    }
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.VOCABULARY.ordinal()) {
      units = dbTool.getVocabularyUnits();
      for (Iterator<String> it = units.iterator(); it.hasNext();) {
        unitComboBox.addItem(it.next());
      }
      return;
    }
}//GEN-LAST:event_examTypeComboBoxActionPerformed

  private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
    try {
      Date date = Calendar.getInstance().getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-");
      String fileName = dateFormat.format(date).concat(String.valueOf(date.getTime())).concat(".xls");
      String sheetName = examTypeComboBox.getSelectedItem().toString().concat("-").concat(unitComboBox.getSelectedItem().toString());
      JFileChooser fileChooser = new JFileChooser(Utility.getApplicationRootPath());
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setSelectedFile(new File(fileName));
      int returnVal = fileChooser.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        DefaultTableModel model = (DefaultTableModel) queryTable.getModel();
        FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile());
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet(sheetName);
        int rowCount = model.getRowCount();
        int columCount = model.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
          HSSFRow row = sheet.createRow(i);
          for (int c = 0; c < columCount; c++) {
            HSSFCell cell = row.createCell(c, HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(model.getValueAt(i, c).toString()));
          }
        }
        for (int c = 0; c < columCount; c++) {
          sheet.autoSizeColumn((short) c);
        }
        workBook.write(fileOut);
        fileOut.close();
      }
    } catch (IOException ex) {
      Logger.getLogger(GRESearchExaminationPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_exportButtonActionPerformed
  protected void upDateExamUnits() {
    examTypeComboBox.setSelectedIndex(examTypeComboBox.getSelectedIndex());
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addNewUnitButton;
  private javax.swing.JButton cleanButton;
  private javax.swing.JPanel controlPanel;
  private javax.swing.JCheckBox errorCheckBox;
  private javax.swing.JComboBox examTypeComboBox;
  private javax.swing.JLabel examTypeLabel;
  private javax.swing.JButton exportButton;
  private javax.swing.JScrollPane queryScrollPane;
  private javax.swing.JTable queryTable;
  private javax.swing.JButton searchButton;
  private javax.swing.JComboBox unitComboBox;
  private javax.swing.JLabel unitLabel;
  // End of variables declaration//GEN-END:variables
  private DataBaseTool dbTool = AccessDataBaseTool.getAccessDataBaseTool();
}
