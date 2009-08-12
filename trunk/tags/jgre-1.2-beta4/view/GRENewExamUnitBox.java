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
 * GRENewExamUnitBox.java
 * 
 */

/*
 * GRENewExamUnitBox.java
 *
 * Created on 2009/5/27, 上午 01:14:25
 */
package gre.view;

import gre.database.AccessDataBaseTool;
import gre.database.DataBaseTool;
import gre.utility.AnalogyEnum;
import gre.utility.AntonymEnum;
import gre.utility.ExamTypesEnum;
import gre.utility.VocabularyEnum;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.application.Action;

/**
 *
 * @author digman543
 */
public class GRENewExamUnitBox extends javax.swing.JDialog {

  private ExamTypesEnum examType;
  private DataBaseTool dbTool;
  private JTable table;

  /** Creates new form GRENewExamUnitBox */
  public GRENewExamUnitBox(java.awt.Frame parent, JTable table, int type, DataBaseTool dbTool) {
    super(parent);
    this.examType = ExamTypesEnum.values()[type];
    this.table = table;
    this.dbTool = dbTool;
    this.setResizable(false);
    initComponents();
  }

  @Action
  public void closeAddNewUnit() {
    dispose();
  }

  @Action
  public void confirmAddNewUnit() {
    if (isDuplexUnit()) {
      return;
    } else {
      switch (examType) {
        case ANTONYM:
          addAntonymUnitData();
          break;
        case ANALOGY:
          addAnalogyUnitData();
          break;
        case VOCABULARY:
          break;
      }
    }
  }

  private void addAntonymUnitData() {
    String unit = unitTextField.getText();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    List<Object[]> data = new ArrayList<Object[]>();
    int rowCount = model.getRowCount();
    for (int i = 0; i < rowCount; i++) {
      Object[] element = new Object[AntonymEnum.values().length];
      element[AntonymEnum.INDEX.ordinal()] = null;
      element[AntonymEnum.UNIT.ordinal()] = unit;
      element[AntonymEnum.ENG_1.ordinal()] = model.getValueAt(i, 0);
      element[AntonymEnum.ENG_2.ordinal()] = model.getValueAt(i, 1);
      element[AntonymEnum.CHINESE_1.ordinal()] = model.getValueAt(i, 2);
      element[AntonymEnum.CHINESE_2.ordinal()] = model.getValueAt(i, 3);
      element[AntonymEnum.NOTE.ordinal()] = null;
      element[AntonymEnum.ERROR.ordinal()] = null;
      data.add(element);
    }
    dbTool.addNewUnit(AccessDataBaseTool.ANTONYM_TABLE_NAME, data);
  }

  private void addAnalogyUnitData() {
    String unit = unitTextField.getText();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    List<Object[]> data = new ArrayList<Object[]>();
    int rowCount = model.getRowCount();
    for (int i = 0; i < rowCount; i++) {
      Object[] element = new Object[AnalogyEnum.values().length];
      element[AnalogyEnum.INDEX.ordinal()] = null;
      element[AnalogyEnum.UNIT.ordinal()] = unit;
      String[] eng_question = model.getValueAt(i, 0).toString().split(":");
      String[] eng_answer = model.getValueAt(i, 1).toString().split(":");
      String[] chinese_question = model.getValueAt(i, 2).toString().split(":");
      String[] chinese_answer = model.getValueAt(i, 3).toString().split(":");
      element[AnalogyEnum.ENG_1.ordinal()] = eng_question[0];
      element[AnalogyEnum.ENG_2.ordinal()] = eng_question[1];
      element[AnalogyEnum.ENG_3.ordinal()] = eng_answer[0];
      element[AnalogyEnum.ENG_4.ordinal()] = eng_answer[1];
      element[AnalogyEnum.CHINESE_1.ordinal()] = chinese_question[0];
      element[AnalogyEnum.CHINESE_2.ordinal()] = chinese_question[1];
      element[AnalogyEnum.CHINESE_3.ordinal()] = chinese_answer[0];
      element[AnalogyEnum.CHINESE_4.ordinal()] = chinese_answer[1];
      element[AnalogyEnum.NOTE.ordinal()] = null;
      element[AnalogyEnum.ERROR.ordinal()] = null;
      data.add(element);
    }
    dbTool.addNewUnit(AccessDataBaseTool.ANALOGY_TABLE_NAME, data);
  }

  private void addVocabularyUnitData() {
    String unit = unitTextField.getText();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    List<Object[]> data = new ArrayList<Object[]>();
    int rowCount = model.getRowCount();
    for (int i = 0; i < rowCount; i++) {
      Object[] element = new Object[VocabularyEnum.values().length];
      element[VocabularyEnum.INDEX.ordinal()] = null;
      element[VocabularyEnum.UNIT.ordinal()] = unit;
      element[VocabularyEnum.ENG_1.ordinal()] = model.getValueAt(i, 0);
      element[VocabularyEnum.CHINESE_1.ordinal()] = model.getValueAt(i, 1);
      element[VocabularyEnum.MEMO.ordinal()] = null;
      element[VocabularyEnum.ERROR.ordinal()] = null;
      data.add(element);
    }
    dbTool.addNewUnit(AccessDataBaseTool.ANTONYM_TABLE_NAME, data);
  }

  private boolean checkIsDuplexUnit(Set<String> units) {
    boolean isDupe = units.contains(unitTextField.getText());
    if (isDupe) {
      stateLabel.setForeground(new Color(255, 0, 0));
      stateLabel.setText("單元重複");
    } else {
      stateLabel.setText("");
    }
    return isDupe;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    unitLabel = new javax.swing.JLabel();
    unitTextField = new javax.swing.JTextField();
    confirmButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();
    stateLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setModal(true);
    setName("Form"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getResourceMap(GRENewExamUnitBox.class);
    unitLabel.setFont(resourceMap.getFont("unitLabel.font")); // NOI18N
    unitLabel.setText(resourceMap.getString("unitLabel.text")); // NOI18N
    unitLabel.setName("unitLabel"); // NOI18N

    unitTextField.setFont(resourceMap.getFont("unitLabel.font")); // NOI18N
    unitTextField.setText(resourceMap.getString("unitTextField.text")); // NOI18N
    unitTextField.setName("unitTextField"); // NOI18N
    unitTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        unitTextFieldActionPerformed(evt);
      }
    });
    unitTextField.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        unitTextFieldFocusLost(evt);
      }
    });

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getActionMap(GRENewExamUnitBox.class, this);
    confirmButton.setAction(actionMap.get("confirmAddNewUnit")); // NOI18N
    confirmButton.setFont(resourceMap.getFont("confirmButton.font")); // NOI18N
    confirmButton.setText(resourceMap.getString("confirmButton.text")); // NOI18N
    confirmButton.setName("confirmButton"); // NOI18N

    cancelButton.setAction(actionMap.get("closeAddNewUnit")); // NOI18N
    cancelButton.setFont(resourceMap.getFont("cancelButton.font")); // NOI18N
    cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
    cancelButton.setName("cancelButton"); // NOI18N

    stateLabel.setFont(resourceMap.getFont("stateLabel.font")); // NOI18N
    stateLabel.setText(resourceMap.getString("stateLabel.text")); // NOI18N
    stateLabel.setName("stateLabel"); // NOI18N

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(unitLabel)
            .add(1, 1, 1)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
              .add(org.jdesktop.layout.GroupLayout.LEADING, stateLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
              .add(org.jdesktop.layout.GroupLayout.LEADING, unitTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
          .add(layout.createSequentialGroup()
            .add(57, 57, 57)
            .add(confirmButton)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(cancelButton)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
        .addContainerGap(23, Short.MAX_VALUE)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(unitLabel)
          .add(unitTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .add(9, 9, 9)
        .add(stateLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(confirmButton)
          .add(cancelButton)))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private boolean isDuplexUnit() {
    boolean isDupe = false;
    if (unitTextField.getText().length() > 0) {
      switch (examType) {
        case ANTONYM:
          isDupe = checkIsDuplexUnit(dbTool.getAntonymUnits());
          break;
        case ANALOGY:
          isDupe = checkIsDuplexUnit(dbTool.getAnalogyUnits());
          break;
        case VOCABULARY:
          isDupe = checkIsDuplexUnit(dbTool.getVocabularyUnits());
          break;
      }
    }
    return isDupe;
  }

  private void unitTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitTextFieldActionPerformed
    if (unitTextField.getText().length() > 0) {
      confirmButton.setEnabled(true);
    } else {
      confirmButton.setEnabled(false);
    }
  }//GEN-LAST:event_unitTextFieldActionPerformed

  private void unitTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_unitTextFieldFocusLost
    isDuplexUnit();
  }//GEN-LAST:event_unitTextFieldFocusLost

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancelButton;
  private javax.swing.JButton confirmButton;
  private javax.swing.JLabel stateLabel;
  private javax.swing.JLabel unitLabel;
  private javax.swing.JTextField unitTextField;
  // End of variables declaration//GEN-END:variables
}
