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
 * GREMaintainUnitBox.java
 */
package gre.view;

import gre.database.AccessDataBaseTool;
import gre.database.DataBaseTool;
import gre.utility.ExamTypesEnum;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import org.jdesktop.application.Action;

/**
 * Use to add or delete units.
 * 
 * @author digman543
 */
public class GREDeleteUnitBox extends javax.swing.JDialog {

	/** Creates new form GREMaintainUnitBox */
	public GREDeleteUnitBox(java.awt.Frame parent) {
		super(parent);
		dbTool = AccessDataBaseTool.getAccessDataBaseTool();
		initComponents();
		getRootPane().setDefaultButton(exitButton);
	}

	@Action
	public void closeDeleteUnit() {
		deleteInfoLable.setText("");
		deleteStateLable.setText("");
		dispose();
	}

	private void initComponents() {

		EnumSet<ExamTypesEnum> types = EnumSet.range(ExamTypesEnum.ANTONYM,
		    ExamTypesEnum.VOCABULARY);
		deletePanel = new javax.swing.JPanel();
		unitComboBox = new javax.swing.JComboBox();
		deleteButton = new javax.swing.JButton();
		exitButton = new javax.swing.JButton();
		deleteInfoLable = new javax.swing.JLabel();
		deleteStateLable = new javax.swing.JLabel();
		examTypeComboBox = new javax.swing.JComboBox(types.toArray());
		typeLabel = new javax.swing.JLabel();
		deleteUnitLabel = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
		setName("maintainUnitBox"); // NOI18N
		setResizable(false);

		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
		    .getInstance(gre.view.GREApp.class).getContext().getResourceMap(
		        GREDeleteUnitBox.class);

		deletePanel.setName("deletePanel"); // NOI18N

		unitComboBox.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		unitComboBox.setName("unitComboBox"); // NOI18N

		deleteButton.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		deleteButton.setText(resourceMap.getString("deleteButton.text")); // NOI18N
		deleteButton.setName("deleteButton"); // NOI18N
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteButtonActionPerformed(evt);
			}
		});

		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
		    .getInstance(gre.view.GREApp.class).getContext().getActionMap(
		        GREDeleteUnitBox.class, this);
		exitButton.setAction(actionMap.get("closeDeleteUnit")); // NOI18N
		exitButton.setFont(resourceMap.getFont("unitLabel.font")); // NOI18N
		exitButton.setText(resourceMap.getString("exitButton.text")); // NOI18N
		exitButton.setName("exitButton"); // NOI18N

		deleteInfoLable.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		deleteInfoLable.setText(resourceMap.getString("deleteInfoLable.text")); // NOI18N
		deleteInfoLable.setName("deleteInfoLable"); // NOI18N

		deleteStateLable.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		deleteStateLable.setText(resourceMap.getString("deleteStateLable.text")); // NOI18N
		deleteStateLable.setName("deleteStateLable"); // NOI18N

		examTypeComboBox.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		examTypeComboBox.setName("examTypeComboBox"); // NOI18N
		examTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				examTypeComboBoxActionPerformed(evt);
			}
		});

		typeLabel.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		typeLabel.setText(resourceMap.getString("typeLabel.text")); // NOI18N
		typeLabel.setName("typeLabel"); // NOI18N

		deleteUnitLabel.setFont(resourceMap.getFont("unitComboBox.font")); // NOI18N
		deleteUnitLabel.setText(resourceMap.getString("deleteunitLabel.text")); // NOI18N
		deleteUnitLabel.setName("deleteunitLabel"); // NOI18N

		org.jdesktop.layout.GroupLayout deletePanelLayout = new org.jdesktop.layout.GroupLayout(
		    deletePanel);
		deletePanel.setLayout(deletePanelLayout);
		deletePanelLayout.setHorizontalGroup(deletePanelLayout.createParallelGroup(
		    org.jdesktop.layout.GroupLayout.LEADING).add(
		    deletePanelLayout.createSequentialGroup().addContainerGap().add(
		        deletePanelLayout.createParallelGroup(
		            org.jdesktop.layout.GroupLayout.LEADING).add(
		            deletePanelLayout.createSequentialGroup().add(
		                deletePanelLayout.createParallelGroup(
		                    org.jdesktop.layout.GroupLayout.LEADING, false).add(
		                    deleteUnitLabel,
		                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                    Short.MAX_VALUE).add(typeLabel,
		                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                    Short.MAX_VALUE)).addPreferredGap(
		                org.jdesktop.layout.LayoutStyle.RELATED).add(
		                deletePanelLayout.createParallelGroup(
		                    org.jdesktop.layout.GroupLayout.TRAILING).add(
		                    org.jdesktop.layout.GroupLayout.LEADING,
		                    examTypeComboBox, 0, 181, Short.MAX_VALUE).add(
		                    org.jdesktop.layout.GroupLayout.LEADING, unitComboBox,
		                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181,
		                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).add(
		            deletePanelLayout.createSequentialGroup().add(deleteInfoLable)
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                .add(
		                    deletePanelLayout.createParallelGroup(
		                        org.jdesktop.layout.GroupLayout.LEADING).add(
		                        deleteStateLable,
		                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177,
		                        Short.MAX_VALUE).add(
		                        deletePanelLayout.createSequentialGroup().add(
		                            deleteButton).add(exitButton)))))));
		deletePanelLayout.setVerticalGroup(deletePanelLayout.createParallelGroup(
		    org.jdesktop.layout.GroupLayout.LEADING).add(
		    deletePanelLayout.createSequentialGroup().add(16, 16, 16).add(
		        deletePanelLayout.createParallelGroup(
		            org.jdesktop.layout.GroupLayout.BASELINE).add(examTypeComboBox,
		            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
		            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(typeLabel))
		        .add(16, 16, 16).add(
		            deletePanelLayout.createParallelGroup(
		                org.jdesktop.layout.GroupLayout.BASELINE).add(unitComboBox,
		                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
		                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
		                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(
		                deleteUnitLabel)).add(20, 20, 20).add(
		            deletePanelLayout.createParallelGroup(
		                org.jdesktop.layout.GroupLayout.BASELINE).add(
		                deleteInfoLable).add(deleteStateLable,
		                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20,
		                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(
		            deletePanelLayout.createParallelGroup(
		                org.jdesktop.layout.GroupLayout.BASELINE).add(deleteButton)
		                .add(exitButton))));

		examTypeComboBox.setSelectedIndex(0);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
		    getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
		    org.jdesktop.layout.GroupLayout.LEADING)
		    .add(
		        layout.createSequentialGroup().addContainerGap().add(deletePanel,
		            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200,
		            Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
		    org.jdesktop.layout.GroupLayout.LEADING).add(
		    layout.createSequentialGroup().addContainerGap().add(deletePanel,
		        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
		        .addContainerGap()));

		pack();
	}

	private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteButtonActionPerformed
		deleteStateLable.setText("");
		int index = examTypeComboBox.getSelectedIndex();
		String tableName = null;
		if (index == ExamTypesEnum.ANTONYM.ordinal()) {
			tableName = AccessDataBaseTool.ANTONYM_TABLE_NAME;
		} else if (index == ExamTypesEnum.ANALOGY.ordinal()) {
			tableName = AccessDataBaseTool.ANALOGY_TABLE_NAME;
		} else {
			tableName = AccessDataBaseTool.VOCABULARY_TABLE_NAME;
		}
		String unitName = (String) unitComboBox.getSelectedItem();
		if (dbTool.deleteNewUnit(tableName, unitName)) {
			deleteStateLable.setForeground(new Color(0, 0, 255));
			deleteStateLable.setText(unitName.concat("刪除成功!"));
			resetUnits();
		} else {
			deleteStateLable.setForeground(new Color(255, 0, 0));
			deleteStateLable.setText(unitName.concat("刪除失敗!"));
		}

	}

	private void examTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_examTypeComboBoxActionPerformed
		resetUnits();
	}

	private void resetUnits() {
		unitComboBox.removeAllItems();
		int index = examTypeComboBox.getSelectedIndex();
		Set<String> units = null;
		if (index == ExamTypesEnum.ANTONYM.ordinal()) {
			units = dbTool.getAntonymUnits();
		} else if (index == ExamTypesEnum.ANALOGY.ordinal()) {
			units = dbTool.getAnalogyUnits();
		} else {
			units = dbTool.getVocabularyUnits();
		}
		for (Iterator<String> it = units.iterator(); it.hasNext();) {
			unitComboBox.addItem(it.next());
		}
	}

	private javax.swing.JComboBox examTypeComboBox;
	private javax.swing.JLabel typeLabel;
	private javax.swing.JButton deleteButton;
	private javax.swing.JLabel deleteInfoLable;
	private javax.swing.JPanel deletePanel;
	private javax.swing.JLabel deleteStateLable;
	private javax.swing.JLabel deleteUnitLabel;
	private javax.swing.JButton exitButton;
	private javax.swing.JComboBox unitComboBox;
	private final DataBaseTool dbTool;
}
