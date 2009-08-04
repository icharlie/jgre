/*
 * 
 * Copyright 2009 digma543
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
 * JGREExaminationalPanel.java
 * 
 */
package jgre.view;

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;

/**
 *
 * @author digma543
 */
public class JGREExaminationalPanel extends JPanel {

  private JSplitPane pane;
  private JPanel examPanel;
  private JPanel optionsPanel;
  // answer state
  private JPanel answerStatePanel;
  private JLabel accurateCountLabel;
  private JLabel accurateLabel;
  private JLabel accurateRateLabel;
  private JLabel accurateRateNumberLabel;
  private JLabel toLabel;
  private JLabel totalCountLabel;
  private JLabel totalLabel;
  private JLabel unitLabel;
  private JLabel wrongCountLabel;
  private JLabel wrongLabel;
  private JLabel rangeLabel;
  private JLabel remainCountLabel;
  private JLabel remainLabel;

  public JGREExaminationalPanel() {
    initComponent();
  }

  private void initComponent() {
    setLayout(new BorderLayout());
    examPanel = new JPanel();
    pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, examPanel, optionsPanel);
    pane.setDividerSize(5);
    pane.setDividerLocation(examPanel.getWidth());
  }
//
// TODO other Component
//private JComboBox alphabetComboBox;
//private JButton confirmButton;
//private JCheckBox errorCheckBox;
//private JComboBox examTypeComboBox;
//private JLabel examTypeLabel;
//private JLabel finishedCountLabel;
//private JLabel finishedLabel;
//private JRadioButton hiddenButton;
//private JPanel jPanel2;
//private JLabel letterLable;
//private JButton nextButton;
//private JRadioButton optionFiveRadioButton;
//private JRadioButton optionFourRadioButton;
//private JRadioButton optionOneRadioButton;
//private JRadioButton optionThreeRadioButton;
//private JRadioButton optionTwoRadioButton;
//private ButtonGroup optionsButtonGroup;
//private JLabel questionChineseLabel;
//private JLabel questionEnglishLable;
//private JCheckBox randomCheckBox;
//private JComboBox rangeComboBox;
//private JSpinner serialEndSpinner;
//private JLabel serialLabel;
//private JSpinner serialStartSpinner;
//private JButton startButton;
//private JButton stopButton;
//private JComboBox unitComboBox;
}
