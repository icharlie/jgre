/*
 * Copyright 2010 digman543
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
 * GREAddNewUnitBox.java
 */
package gre.view;

import gre.database.AccessDataBaseTool;
import gre.database.DataBaseTool;
import gre.utility.AnalogyEnum;
import gre.utility.AntonymEnum;
import gre.utility.ExamTypesEnum;
import gre.utility.VocabularyEnum;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.application.Action;

public class GREAddUnitBox extends JDialog {
	private final String symbol = ";";
	private JLabel examTypeLabel;
	private JComboBox examTypeComboBox;
	private JLabel unitLabel;
	private JTextField unitTextField;
	private JScrollPane inputScrollPane;
	private JTextArea inputTextArea;
	private JButton addBtn;
	private JButton exitBtn;
	private JLabel infoLabel;
	private final DataBaseTool dbTool;
	private GREView view;
	
	
	public GREAddUnitBox(Frame parent, GREView view) {
		super(parent);
		this.view = view;
		initComponents();
		dbTool = AccessDataBaseTool.getAccessDataBaseTool();
		getRootPane().setDefaultButton(exitBtn);
	}

	@Action
	public void closeAddUnitBox() {
		infoLabel.setText("");
		unitTextField.setText("");
		inputTextArea.setText("");
		view.upDateExamUnits();
		view.upDatevocabularyUnit();
		dispose();
	}

	private void initComponents() {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
		    .getInstance(gre.view.GREApp.class).getContext().getResourceMap(
		        GREAddUnitBox.class);
		javax.swing.ActionMap actionMap = org.jdesktop.application.Application
		    .getInstance(gre.view.GREApp.class).getContext().getActionMap(
		        GREAddUnitBox.class, this);

		EnumSet<ExamTypesEnum> types = EnumSet.range(ExamTypesEnum.ANTONYM,
		    ExamTypesEnum.VOCABULARY);
		examTypeLabel = new javax.swing.JLabel();
		examTypeComboBox = new javax.swing.JComboBox(types.toArray());
		examTypeLabel.setText(resourceMap.getString("examTypeLabel.text")); // NOI18N
		examTypeLabel.setName("examTypeLabel"); // NOI18N
		examTypeComboBox.setName("examTypeComboBox"); // NOI18N

		Container contentPane = this.getContentPane();

		unitLabel = new JLabel();
		unitLabel.setText(resourceMap.getString("unitLabel.text"));
		unitTextField = new JTextField();

		inputTextArea = new JTextArea(40, 30);
		inputTextArea.setDragEnabled(true);
		inputScrollPane = new JScrollPane(inputTextArea);

		addBtn = new JButton(resourceMap.getString("addBtn.text"));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addNewUnit(evt);
			}
		});

		exitBtn = new JButton();
		exitBtn.setAction(actionMap.get("closeAddUnitBox")); // NOI18N
		exitBtn.setText(resourceMap.getString("exitBtn.text"));
		exitBtn.setName("exitBtn"); // NOI18N

		infoLabel = new JLabel();

		// layout
		GroupLayout layout = new GroupLayout(contentPane);

		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
		    layout.createParallelGroup().addGroup(
		        layout.createSequentialGroup().addComponent(examTypeLabel)
		            .addComponent(examTypeComboBox)).addGroup(
		        layout.createSequentialGroup().addComponent(unitLabel)
		            .addComponent(unitTextField)).addComponent(inputScrollPane)
		        .addGroup(
		            layout.createSequentialGroup().addComponent(addBtn)
		                .addComponent(exitBtn).addComponent(infoLabel))));

		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
		    layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(examTypeLabel).addComponent(examTypeComboBox))
		    .addGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		            .addComponent(unitLabel).addComponent(unitTextField))
		    .addComponent(inputScrollPane).addGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		            .addComponent(addBtn).addComponent(exitBtn).addComponent(
		                infoLabel)));
		pack();
	}

	private void addNewUnit(ActionEvent evt) {
		String unit = unitTextField.getText();
		String[] lines = null;
		String content = inputTextArea.getText();
		if (parseContent(content) && checkUnit(unit)) {
			lines = content.split("\n");
		} else {
			return;
		}
		List<Object[]> vocabularyList = new ArrayList<Object[]>();
		boolean isSuccess = true;
		if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
			for (String line : lines) {
				Object[] element = new Object[AntonymEnum.values().length];
				element[AntonymEnum.INDEX.ordinal()] = null;
				element[AntonymEnum.UNIT.ordinal()] = unit;
				element[AntonymEnum.ENG_1.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AntonymEnum.ENG_2.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AntonymEnum.CHINESE_1.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AntonymEnum.CHINESE_2.ordinal()] = line.substring(0,
				    line.length()).trim();
				element[AntonymEnum.NOTE.ordinal()] = null;
				element[AntonymEnum.ERROR.ordinal()] = null;
				vocabularyList.add(element);
			}
			isSuccess = dbTool.addNewUnit(AccessDataBaseTool.ANTONYM_TABLE_NAME,
			    vocabularyList);
		} else if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANALOGY
		    .ordinal()) {
			for (String line : lines) {
				Object[] element = new Object[AnalogyEnum.values().length];
				element[AnalogyEnum.INDEX.ordinal()] = null;
				element[AnalogyEnum.UNIT.ordinal()] = unit;
				element[AnalogyEnum.ENG_1.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.ENG_2.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.ENG_3.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.ENG_4.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.CHINESE_1.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.CHINESE_2.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.CHINESE_3.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				line = line.substring(line.indexOf(symbol) + 1, line.length()).trim();
				element[AnalogyEnum.CHINESE_4.ordinal()] = line.substring(0,
				    line.length()).trim();
				element[AnalogyEnum.NOTE.ordinal()] = null;
				element[AnalogyEnum.ERROR.ordinal()] = null;
				vocabularyList.add(element);
			}
			isSuccess = dbTool.addNewUnit(AccessDataBaseTool.ANALOGY_TABLE_NAME,
			    vocabularyList);
		} else {
			for (String line : lines) {
				Object[] element = new Object[VocabularyEnum.values().length];
				element[VocabularyEnum.INDEX.ordinal()] = null;
				element[VocabularyEnum.UNIT.ordinal()] = unit;
				element[VocabularyEnum.ENG_1.ordinal()] = line.substring(0,
				    line.indexOf(symbol)).trim();
				element[VocabularyEnum.CHINESE_1.ordinal()] = line.substring(
				    line.indexOf(symbol) + 1, line.length()).trim();
				element[VocabularyEnum.MEMO.ordinal()] = null;
				element[VocabularyEnum.ERROR.ordinal()] = null;
				vocabularyList.add(element);
			}
			isSuccess = dbTool.addNewUnit(AccessDataBaseTool.VOCABULARY_TABLE_NAME,
			    vocabularyList);
		}

		if (isSuccess) {
			infoLabel.setForeground(new Color(0, 0, 255));
			infoLabel.setText(unit.concat("單元新增成功"));
		} else {
			infoLabel.setForeground(new Color(255, 0, 0));
			infoLabel.setText(unit.concat("單元新增失敗"));
		}
	}

	private boolean checkUnit(String unit) {
		if	(unit.equals("")){
			infoLabel.setForeground(new Color(255, 0, 0));
			infoLabel.setText(unit.concat("沒有單元名稱"));
			return false;
		}
		Set<String> units = null;
		if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
			units = dbTool.getAntonymUnits();
		} else if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANALOGY.ordinal()) {
			units = dbTool.getAnalogyUnits();
		} else {
			units = dbTool.getVocabularyUnits();
		}
		if	(units.contains(unit)){
			infoLabel.setForeground(new Color(255, 0, 0));
			infoLabel.setText(unit.concat("單元名稱重複"));
			return false;
		}
		return true;
	}

	private boolean parseContent(String content) {
		boolean isSuccess = true;
		if (content.equals("")) {
			infoLabel.setForeground(new Color(255, 0, 0));
			infoLabel.setText("沒有資料");
			isSuccess = false;
		}
		return isSuccess;
	}
}