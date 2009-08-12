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
 * GREExaminationalPanel.java
 */
package gre.view;

import gre.database.AccessDataBaseTool;
import gre.database.DataBaseTool;
import gre.utility.ExamTypesEnum;
import gre.utility.Question;
import gre.utility.QuestionGenerator;
import gre.utility.QuestionGroup;
import gre.utility.RangeTypeEnum;
import gre.utility.Utility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;

public class GREExaminationalPanel extends javax.swing.JPanel {

  /** Creates new form GREExaminationalPanel */
  public GREExaminationalPanel() {
    initComponents();
  }

  protected void setErrorCheckBoxState() {
    if(unitComboBox.getSelectedItem() == null){
      return;
    }
    String unit = unitComboBox.getSelectedItem().toString();
    boolean hasError = false;
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
      hasError = dbTool.hasErrorAnatonym(unit);
    } else if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
      hasError = dbTool.hasErrorAnalogy(unit);
    } else {
      hasError = dbTool.hasErrorVocabulary(unit);
    }
    if (!hasError) {
      errorCheckBox.setSelected(false);
    }
    errorCheckBox.setEnabled(hasError);
  }

  private int getEnd(String unit) {
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
      return dbTool.getAllAntonym(unit.toString(), errorCheckBox.isSelected()).size();
    } else if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANALOGY.ordinal()) {
      return dbTool.getAllAnalogy(unit.toString(), errorCheckBox.isSelected()).size();
    } else {
      return dbTool.getAllVacabulary(unit.toString(), false, errorCheckBox.isSelected()).size();
    }
  }

  private void getNextQuestion() {
    nowQuestion = questionGroup.getNextQuestion();
    if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.VOCABULARY.ordinal()) {
      questionEnglishLable.setText(nowQuestion.getQuestionEnglish());
      questionChineseLabel.setText("");
      optionOneRadioButton.setText(nowQuestion.getOptionOneChinese());
      optionTwoRadioButton.setText(nowQuestion.getOptionTwoChinese());
      optionThreeRadioButton.setText(nowQuestion.getOptionThreeChinese());
      optionFourRadioButton.setText(nowQuestion.getOptionFourChinese());
      optionFiveRadioButton.setText(nowQuestion.getOptionFiveChinese());
    } else {
      questionEnglishLable.setText(nowQuestion.getQuestionEnglish());
      questionChineseLabel.setText("");
      optionOneRadioButton.setText(nowQuestion.getOptionOneEnglish());
      optionTwoRadioButton.setText(nowQuestion.getOptionTwoEnglish());
      optionThreeRadioButton.setText(nowQuestion.getOptionThreeEnglish());
      optionFourRadioButton.setText(nowQuestion.getOptionFourEnglish());
      optionFiveRadioButton.setText(nowQuestion.getOptionFiveEnglish());
    }
    Enumeration<AbstractButton> buttonEnum = optionsButtonGroup.getElements();
    for (; buttonEnum.hasMoreElements();) {
      AbstractButton button = buttonEnum.nextElement();
      button.setForeground(Color.BLACK);
    }
    setAnswerState();
  }

  private void initialState() {
    startButton.setVisible(true);
    stopButton.setVisible(false);
    startButton.setEnabled(true);
    confirmButton.setEnabled(false);
    confirmButton.setVisible(true);
    nextButton.setVisible(false);
    questionGroup = null;
  }

  private boolean isLastQuestion() {
    return Integer.parseInt(totalCountLabel.getText()) == Integer.parseInt(finishedCountLabel.getText());
  }

  private void prepareAntonymQuestions(QuestionGenerator questionGenerator) {
    ArrayList<Question> questions = null;
    String unit = unitComboBox.getSelectedItem().toString();
    if (rangeComboBox.getSelectedIndex() == 0) {
      questions = questionGenerator.generateAntonymQuestions(unit, errorCheckBox.isSelected());
    } else if (rangeComboBox.getSelectedIndex() == 1) {
      String alph = alphabetComboBox.getSelectedItem().toString();
      questions = questionGenerator.generateAntonymQuestionsByAlphabet(unit, alph, errorCheckBox.isSelected());
    } else {
      int start = Integer.parseInt(serialStartSpinner.getValue().toString());
      int end = Integer.parseInt(serialEndSpinner.getValue().toString());
      questions = questionGenerator.generateAntonymQuestionsByRange(unit, start - 1, end, errorCheckBox.isSelected());
    }
    if (randomCheckBox.isSelected()) {
      questions = Utility.randomList(questions);
    }
    questionGroup = new QuestionGroup(questions);
  }

  private void prepareAnalogyQuestions(QuestionGenerator questionGenerator) {
    ArrayList<Question> questions = null;
    String unit = unitComboBox.getSelectedItem().toString();
    if (rangeComboBox.getSelectedIndex() == 0) {
      questions = questionGenerator.generateAnalogyQuestions(unit, errorCheckBox.isSelected());
    } else if (rangeComboBox.getSelectedIndex() == 1) {
      String alph = alphabetComboBox.getSelectedItem().toString();
      questions = questionGenerator.generateAnalogyQuestionsByAlphabet(unit, alph, errorCheckBox.isSelected());
    } else {
      int start = Integer.parseInt(serialStartSpinner.getValue().toString());
      int end = Integer.parseInt(serialEndSpinner.getValue().toString());
      questions = questionGenerator.generateAnalogyQuestionsByRange(unit, start - 1, end, errorCheckBox.isSelected());
    }
    if (randomCheckBox.isSelected()) {
      questions = Utility.randomList(questions);
    }
    questionGroup = new QuestionGroup(questions);
  }

  private void prepareVocabularyQuestions(QuestionGenerator qGenerator) throws NumberFormatException {
    ArrayList<Question> questions = null;
    String unit = unitComboBox.getSelectedItem().toString();
    boolean isError = errorCheckBox.isSelected();
    if (rangeComboBox.getSelectedIndex() == 0) {
      questions = qGenerator.generateVocabularyQuestions(unit, isError);
    } else if (rangeComboBox.getSelectedIndex() == 1) {
      questions = qGenerator.generateVocabularyQuestionsByAlphabet(unit, alphabetComboBox.getSelectedItem().toString(), isError);
    } else {
      int start = Integer.parseInt(serialStartSpinner.getValue().toString());
      int end = Integer.parseInt(serialEndSpinner.getValue().toString());
      questions = qGenerator.generateVocabularyQuestionsByRange(unit, start - 1, end, isError);
    }
    questionGroup = new QuestionGroup(questions);
  }

  private void setAnswerState() {
    totalCountLabel.setText(String.valueOf(questionGroup.getTotalCount()));
    finishedCountLabel.setText(String.valueOf(questionGroup.getFininshedCount()));
    remainCountLabel.setText(String.valueOf(questionGroup.getRemainCount()));
    accurateCountLabel.setText(String.valueOf(questionGroup.getAccurateCount()));
    wrongCountLabel.setText(String.valueOf(questionGroup.getWrongCount()));
    accurateRateNumberLabel.setText(String.valueOf(questionGroup.getAccurateRate()).concat("%"));
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

    optionsButtonGroup = new javax.swing.ButtonGroup()
    ;
    examTypeLabel = new javax.swing.JLabel();
    String[] examTypes = new String[]{ExamTypesEnum.ANTONYM.getTypeName(), ExamTypesEnum.ANALOGY.getTypeName(), ExamTypesEnum.VOCABULARY.getTypeName()};
    examTypeComboBox = new javax.swing.JComboBox();
    unitLabel = new javax.swing.JLabel();
    unitComboBox = new javax.swing.JComboBox();
    errorCheckBox = new javax.swing.JCheckBox();
    randomCheckBox = new javax.swing.JCheckBox();
    rangeLabel = new javax.swing.JLabel();
    rangeComboBox = new javax.swing.JComboBox();
    letterLable = new javax.swing.JLabel();
    alphabetComboBox = new javax.swing.JComboBox();
    serialLabel = new javax.swing.JLabel();
    serialStartSpinner = new javax.swing.JSpinner();
    toLabel = new javax.swing.JLabel();
    serialEndSpinner = new javax.swing.JSpinner();
    startButton = new javax.swing.JButton();
    stopButton = new javax.swing.JButton();
    examPanel = new javax.swing.JPanel();
    answerStatePanel = new javax.swing.JPanel();
    totalLabel = new javax.swing.JLabel();
    totalCountLabel = new javax.swing.JLabel();
    finishedLabel = new javax.swing.JLabel();
    finishedCountLabel = new javax.swing.JLabel();
    remainLabel = new javax.swing.JLabel();
    remainCountLabel = new javax.swing.JLabel();
    accurateLabel = new javax.swing.JLabel();
    accurateCountLabel = new javax.swing.JLabel();
    wrongLabel = new javax.swing.JLabel();
    wrongCountLabel = new javax.swing.JLabel();
    accurateRateLabel = new javax.swing.JLabel();
    accurateRateNumberLabel = new javax.swing.JLabel();
    questionPanel = new javax.swing.JPanel();
    questionEnglishLable = new javax.swing.JLabel();
    questionChineseLabel = new javax.swing.JLabel();
    optionOneRadioButton = new javax.swing.JRadioButton();
    optionTwoRadioButton = new javax.swing.JRadioButton();
    optionThreeRadioButton = new javax.swing.JRadioButton();
    optionFourRadioButton = new javax.swing.JRadioButton();
    optionFiveRadioButton = new javax.swing.JRadioButton();
    confirmButton = new javax.swing.JButton();
    nextButton = new javax.swing.JButton();
    hiddenButton = new javax.swing.JRadioButton();

    setName("Form"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getResourceMap(GREExaminationalPanel.class);
    examTypeLabel.setFont(resourceMap.getFont("examTypeLabel.font")); // NOI18N
    examTypeLabel.setText(resourceMap.getString("examTypeLabel.text")); // NOI18N
    examTypeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
    examTypeLabel.setName("examTypeLabel"); // NOI18N

    examTypeComboBox.setFont(resourceMap.getFont("examTypeComboBox.font")); // NOI18N
    examTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(examTypes));
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
    unitComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        unitComboBoxActionPerformed(evt);
      }
    });

    errorCheckBox.setFont(resourceMap.getFont("errorCheckBox.font")); // NOI18N
    errorCheckBox.setText(resourceMap.getString("errorCheckBox.text")); // NOI18N
    errorCheckBox.setEnabled(false);
    errorCheckBox.setName("errorCheckBox"); // NOI18N
    errorCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        errorCheckBoxActionPerformed(evt);
      }
    });

    randomCheckBox.setFont(resourceMap.getFont("randomCheckBox.font")); // NOI18N
    randomCheckBox.setText(resourceMap.getString("randomCheckBox.text")); // NOI18N
    randomCheckBox.setName("randomCheckBox"); // NOI18N

    rangeLabel.setFont(resourceMap.getFont("rangeLabel.font")); // NOI18N
    rangeLabel.setText(resourceMap.getString("rangeLabel.text")); // NOI18N
    rangeLabel.setName("rangeLabel"); // NOI18N

    rangeComboBox.setFont(resourceMap.getFont("rangeComboBox.font")); // NOI18N
    rangeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "全部", "依字母", "依編號" }));
    rangeComboBox.setName("rangeComboBox"); // NOI18N
    rangeComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        rangeComboBoxActionPerformed(evt);
      }
    });

    letterLable.setFont(resourceMap.getFont("letterLable.font")); // NOI18N
    letterLable.setText(resourceMap.getString("letterLable.text")); // NOI18N
    letterLable.setName("letterLable"); // NOI18N

    alphabetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
    alphabetComboBox.setEnabled(false);
    alphabetComboBox.setName("alphabetComboBox"); // NOI18N

    serialLabel.setFont(resourceMap.getFont("serialLabel.font")); // NOI18N
    serialLabel.setText(resourceMap.getString("serialLabel.text")); // NOI18N
    serialLabel.setName("serialLabel"); // NOI18N

    serialStartSpinner.setEnabled(false);
    serialStartSpinner.setName("serialStartSpinner"); // NOI18N

    toLabel.setFont(resourceMap.getFont("toLabel.font")); // NOI18N
    toLabel.setText(resourceMap.getString("toLabel.text")); // NOI18N
    toLabel.setName("toLabel"); // NOI18N

    serialEndSpinner.setEnabled(false);
    serialEndSpinner.setName("serialEndSpinner"); // NOI18N

    startButton.setFont(resourceMap.getFont("startButton.font")); // NOI18N
    startButton.setText(resourceMap.getString("startButton.text")); // NOI18N
    startButton.setName("startButton"); // NOI18N
    startButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        startButtonActionPerformed(evt);
      }
    });

    stopButton.setFont(resourceMap.getFont("stopButton.font")); // NOI18N
    stopButton.setText(resourceMap.getString("stopButton.text")); // NOI18N
    stopButton.setName("stopButton"); // NOI18N
    stopButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopButtonActionPerformed(evt);
      }
    });

    examPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("examPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("examPanel.border.titleFont"), resourceMap.getColor("examPanel.border.titleColor"))); // NOI18N
    examPanel.setName("examPanel"); // NOI18N

    answerStatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("answerStatePanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("answerStatePanel.border.titleFont"), resourceMap.getColor("answerStatePanel.border.titleColor"))); // NOI18N
    answerStatePanel.setName("answerStatePanel"); // NOI18N

    totalLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    totalLabel.setText(resourceMap.getString("totalLabel.text")); // NOI18N
    totalLabel.setName("totalLabel"); // NOI18N

    totalCountLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    totalCountLabel.setText(resourceMap.getString("totalCountLabel.text")); // NOI18N
    totalCountLabel.setName("totalCountLabel"); // NOI18N

    finishedLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    finishedLabel.setText(resourceMap.getString("finishedLabel.text")); // NOI18N
    finishedLabel.setName("finishedLabel"); // NOI18N

    finishedCountLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    finishedCountLabel.setText(resourceMap.getString("finishedCountLabel.text")); // NOI18N
    finishedCountLabel.setName("finishedCountLabel"); // NOI18N

    remainLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    remainLabel.setText(resourceMap.getString("remainLabel.text")); // NOI18N
    remainLabel.setName("remainLabel"); // NOI18N

    remainCountLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    remainCountLabel.setText(resourceMap.getString("remainCountLabel.text")); // NOI18N
    remainCountLabel.setName("remainCountLabel"); // NOI18N

    accurateLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    accurateLabel.setText(resourceMap.getString("accurateLabel.text")); // NOI18N
    accurateLabel.setName("accurateLabel"); // NOI18N

    accurateCountLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    accurateCountLabel.setText(resourceMap.getString("accurateCountLabel.text")); // NOI18N
    accurateCountLabel.setName("accurateCountLabel"); // NOI18N

    wrongLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    wrongLabel.setText(resourceMap.getString("wrongLabel.text")); // NOI18N
    wrongLabel.setName("wrongLabel"); // NOI18N

    wrongCountLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    wrongCountLabel.setText(resourceMap.getString("wrongCountLabel.text")); // NOI18N
    wrongCountLabel.setName("wrongCountLabel"); // NOI18N

    accurateRateLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    accurateRateLabel.setText(resourceMap.getString("accurateRateLabel.text")); // NOI18N
    accurateRateLabel.setName("accurateRateLabel"); // NOI18N

    accurateRateNumberLabel.setFont(resourceMap.getFont("totalLabel.font")); // NOI18N
    accurateRateNumberLabel.setText(resourceMap.getString("accurateRateNumberLabel.text")); // NOI18N
    accurateRateNumberLabel.setName("accurateRateNumberLabel"); // NOI18N

    org.jdesktop.layout.GroupLayout answerStatePanelLayout = new org.jdesktop.layout.GroupLayout(answerStatePanel);
    answerStatePanel.setLayout(answerStatePanelLayout);
    answerStatePanelLayout.setHorizontalGroup(
      answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(answerStatePanelLayout.createSequentialGroup()
        .addContainerGap()
        .add(answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(answerStatePanelLayout.createSequentialGroup()
            .add(totalLabel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(totalCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(answerStatePanelLayout.createSequentialGroup()
            .add(accurateLabel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(accurateCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(answerStatePanelLayout.createSequentialGroup()
            .add(finishedLabel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(finishedCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(answerStatePanelLayout.createSequentialGroup()
            .add(wrongLabel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(wrongCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(answerStatePanelLayout.createSequentialGroup()
            .add(remainLabel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(remainCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(answerStatePanelLayout.createSequentialGroup()
            .add(accurateRateLabel)
            .add(24, 24, 24)
            .add(accurateRateNumberLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    answerStatePanelLayout.setVerticalGroup(
      answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(answerStatePanelLayout.createSequentialGroup()
        .add(answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(totalLabel)
          .add(totalCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(finishedLabel)
          .add(finishedCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(remainLabel)
          .add(remainCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .add(14, 14, 14)
        .add(answerStatePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(wrongLabel)
          .add(wrongCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(accurateLabel)
          .add(accurateCountLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(accurateRateLabel)
          .add(accurateRateNumberLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
    );

    answerStatePanelLayout.linkSize(new java.awt.Component[] {accurateCountLabel, accurateLabel, accurateRateLabel, accurateRateNumberLabel, finishedCountLabel, finishedLabel, remainCountLabel, remainLabel, totalCountLabel, totalLabel, wrongCountLabel, wrongLabel}, org.jdesktop.layout.GroupLayout.VERTICAL);

    questionPanel.setName("questionPanel"); // NOI18N

    questionEnglishLable.setFont(resourceMap.getFont("questionEnglishLable.font")); // NOI18N
    questionEnglishLable.setText(resourceMap.getString("questionEnglishLable.text")); // NOI18N
    questionEnglishLable.setName("questionEnglishLable"); // NOI18N

    questionChineseLabel.setFont(resourceMap.getFont("questionChineseLabel.font")); // NOI18N
    questionChineseLabel.setText(resourceMap.getString("questionChineseLabel.text")); // NOI18N
    questionChineseLabel.setName("questionChineseLabel"); // NOI18N

    optionsButtonGroup.add(optionOneRadioButton);
    optionOneRadioButton.setFont(resourceMap.getFont("optionOneRadioButton.font")); // NOI18N
    optionOneRadioButton.setText(resourceMap.getString("optionOneRadioButton.text")); // NOI18N
    optionOneRadioButton.setActionCommand(resourceMap.getString("optionOneRadioButton.actionCommand")); // NOI18N
    optionOneRadioButton.setEnabled(false);
    optionOneRadioButton.setName("optionOneRadioButton"); // NOI18N

    optionsButtonGroup.add(optionTwoRadioButton);
    optionTwoRadioButton.setFont(resourceMap.getFont("optionTwoRadioButton.font")); // NOI18N
    optionTwoRadioButton.setText(resourceMap.getString("optionTwoRadioButton.text")); // NOI18N
    optionTwoRadioButton.setActionCommand(resourceMap.getString("optionTwoRadioButton.actionCommand")); // NOI18N
    optionTwoRadioButton.setEnabled(false);
    optionTwoRadioButton.setName("optionTwoRadioButton"); // NOI18N

    optionsButtonGroup.add(optionThreeRadioButton);
    optionThreeRadioButton.setFont(resourceMap.getFont("optionTwoRadioButton.font")); // NOI18N
    optionThreeRadioButton.setText(resourceMap.getString("optionThreeRadioButton.text")); // NOI18N
    optionThreeRadioButton.setActionCommand(resourceMap.getString("optionThreeRadioButton.actionCommand")); // NOI18N
    optionThreeRadioButton.setEnabled(false);
    optionThreeRadioButton.setName("optionThreeRadioButton"); // NOI18N

    optionsButtonGroup.add(optionFourRadioButton);
    optionFourRadioButton.setFont(resourceMap.getFont("optionTwoRadioButton.font")); // NOI18N
    optionFourRadioButton.setText(resourceMap.getString("optionFourRadioButton.text")); // NOI18N
    optionFourRadioButton.setActionCommand(resourceMap.getString("optionFourRadioButton.actionCommand")); // NOI18N
    optionFourRadioButton.setEnabled(false);
    optionFourRadioButton.setName("optionFourRadioButton"); // NOI18N

    optionsButtonGroup.add(optionFiveRadioButton);
    optionFiveRadioButton.setFont(resourceMap.getFont("optionTwoRadioButton.font")); // NOI18N
    optionFiveRadioButton.setText(resourceMap.getString("optionFiveRadioButton.text")); // NOI18N
    optionFiveRadioButton.setActionCommand(resourceMap.getString("optionFiveRadioButton.actionCommand")); // NOI18N
    optionFiveRadioButton.setEnabled(false);
    optionFiveRadioButton.setName("optionFiveRadioButton"); // NOI18N

    confirmButton.setFont(resourceMap.getFont("confirmButton.font")); // NOI18N
    confirmButton.setText(resourceMap.getString("confirmButton.text")); // NOI18N
    confirmButton.setName("confirmButton"); // NOI18N

    org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, hiddenButton, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), confirmButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
    bindingGroup.addBinding(binding);

    confirmButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        confirmButtonActionPerformed(evt);
      }
    });

    nextButton.setFont(resourceMap.getFont("nextButton.font")); // NOI18N
    nextButton.setText(resourceMap.getString("nextButton.text")); // NOI18N
    nextButton.setName("nextButton"); // NOI18N
    nextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextButtonActionPerformed(evt);
      }
    });

    optionsButtonGroup.add(hiddenButton);
    hiddenButton.setSelected(true);
    hiddenButton.setText(resourceMap.getString("hiddenButton.text")); // NOI18N
    hiddenButton.setName("hiddenButton"); // NOI18N

    org.jdesktop.layout.GroupLayout questionPanelLayout = new org.jdesktop.layout.GroupLayout(questionPanel);
    questionPanel.setLayout(questionPanelLayout);
    questionPanelLayout.setHorizontalGroup(
      questionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(questionPanelLayout.createSequentialGroup()
        .add(questionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(questionEnglishLable, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 400, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(questionChineseLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 400, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(optionOneRadioButton))
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(optionTwoRadioButton))
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(optionThreeRadioButton))
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(optionFourRadioButton))
          .add(questionPanelLayout.createSequentialGroup()
            .add(10, 10, 10)
            .add(optionFiveRadioButton))
          .add(questionPanelLayout.createSequentialGroup()
            .add(hiddenButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(44, 44, 44)
            .add(questionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(nextButton)
              .add(confirmButton))))
        .addContainerGap(303, Short.MAX_VALUE))
    );
    questionPanelLayout.setVerticalGroup(
      questionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(questionPanelLayout.createSequentialGroup()
        .add(questionEnglishLable, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(5, 5, 5)
        .add(questionChineseLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(15, 15, 15)
        .add(optionOneRadioButton)
        .add(10, 10, 10)
        .add(optionTwoRadioButton)
        .add(10, 10, 10)
        .add(optionThreeRadioButton)
        .add(10, 10, 10)
        .add(optionFourRadioButton)
        .add(10, 10, 10)
        .add(optionFiveRadioButton)
        .add(10, 10, 10)
        .add(questionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(hiddenButton)
          .add(nextButton)
          .add(confirmButton))
        .addContainerGap(20, Short.MAX_VALUE))
    );

    nextButton.setVisible(false);
    hiddenButton.setVisible(false);

    org.jdesktop.layout.GroupLayout examPanelLayout = new org.jdesktop.layout.GroupLayout(examPanel);
    examPanel.setLayout(examPanelLayout);
    examPanelLayout.setHorizontalGroup(
      examPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(org.jdesktop.layout.GroupLayout.TRAILING, questionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .add(org.jdesktop.layout.GroupLayout.TRAILING, examPanelLayout.createSequentialGroup()
        .addContainerGap()
        .add(answerStatePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    );
    examPanelLayout.setVerticalGroup(
      examPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(examPanelLayout.createSequentialGroup()
        .add(answerStatePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(questionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
              .add(layout.createSequentialGroup()
                .add(examTypeLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(examTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
              .add(layout.createSequentialGroup()
                .add(rangeLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(rangeComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .add(18, 18, 18)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(letterLable)
              .add(unitLabel))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(alphabetComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(unitComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(layout.createSequentialGroup()
                .add(errorCheckBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(randomCheckBox))
              .add(layout.createSequentialGroup()
                .add(serialLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(serialStartSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(toLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(serialEndSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
          .add(layout.createSequentialGroup()
            .add(259, 259, 259)
            .add(startButton)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(stopButton)))
        .addContainerGap())
      .add(examPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(21, 21, 21)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(examTypeLabel)
              .add(examTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(18, 18, 18)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(rangeLabel)
              .add(rangeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
          .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(unitLabel)
              .add(unitComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(errorCheckBox)
              .add(randomCheckBox))
            .add(18, 18, 18)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(letterLable)
              .add(alphabetComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(serialLabel)
              .add(serialStartSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(toLabel)
              .add(serialEndSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(6, 6, 6)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(startButton)
          .add(stopButton))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(examPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(0, 1, Short.MAX_VALUE))
    );

    layout.linkSize(new java.awt.Component[] {errorCheckBox, examTypeComboBox, examTypeLabel, letterLable, rangeComboBox, rangeLabel, serialEndSpinner, serialLabel, serialStartSpinner, startButton, stopButton, toLabel, unitComboBox, unitLabel}, org.jdesktop.layout.GroupLayout.VERTICAL);

    examTypeComboBox.setSelectedIndex(0);
    stopButton.setVisible(false);

    bindingGroup.bind();
  }// </editor-fold>//GEN-END:initComponents

    private void rangeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rangeComboBoxActionPerformed
      if (rangeComboBox.getSelectedIndex() == RangeTypeEnum.ALL.ordinal()) {
        alphabetComboBox.setEnabled(false);
        serialStartSpinner.setEnabled(false);
        serialEndSpinner.setEnabled(false);
      } else if (rangeComboBox.getSelectedIndex() == RangeTypeEnum.ALPHABET.ordinal()) {
        alphabetComboBox.setEnabled(true);
        serialStartSpinner.setEnabled(false);
        serialEndSpinner.setEnabled(false);
      }
      setSerialState();
    }//GEN-LAST:event_rangeComboBoxActionPerformed

  private void setSerialState() {
    if (rangeComboBox.getSelectedIndex() == RangeTypeEnum.SERAIL_NUMBER.ordinal()) {
      Object item = unitComboBox.getSelectedItem();
      if (item != null) {
        alphabetComboBox.setEnabled(false);
        serialStartSpinner.setEnabled(true);
        serialEndSpinner.setEnabled(true);
        int end = getEnd(item.toString());
        serialStartSpinner.setValue(1);
        serialEndSpinner.setValue(end);
      } else {
        rangeComboBox.setSelectedIndex(0);
      }
    }
  }

  private void setUnits(Set<String> _units) {
    for (Iterator<String> it = _units.iterator(); it.hasNext();) {
      String unit = it.next();
      unitComboBox.addItem(unit);
    }
    _units = null;
  }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
      QuestionGenerator qGenerator = new QuestionGenerator(dbTool);
      if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANTONYM.ordinal()) {
        prepareAntonymQuestions(qGenerator);
      } else if (examTypeComboBox.getSelectedIndex() == ExamTypesEnum.ANALOGY.ordinal()) {
        prepareAnalogyQuestions(qGenerator);
      } else {
        prepareVocabularyQuestions(qGenerator);
      }
      setAnswerState();
      hiddenButton.setSelected(true);
      startButton.setVisible(false);
      optionOneRadioButton.setEnabled(true);
      optionTwoRadioButton.setEnabled(true);
      optionThreeRadioButton.setEnabled(true);
      optionFourRadioButton.setEnabled(true);
      optionFiveRadioButton.setEnabled(true);
      stopButton.setVisible(true);
      getNextQuestion();
    }//GEN-LAST:event_startButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
      int selectedIndex = getOptionsSelectedIndex();
      int examTypeIndex = examTypeComboBox.getSelectedIndex();
      if (examTypeIndex != ExamTypesEnum.VOCABULARY.ordinal()) {
        questionChineseLabel.setText(nowQuestion.getQuestionChinese());
        optionOneRadioButton.setText(nowQuestion.getOptionOneEnglish().concat("(").concat(nowQuestion.getOptionOneChinese()).concat(")"));
        optionTwoRadioButton.setText(nowQuestion.getOptionTwoEnglish().concat("(").concat(nowQuestion.getOptionTwoChinese()).concat(")"));
        optionThreeRadioButton.setText(nowQuestion.getOptionThreeEnglish().concat("(").concat(nowQuestion.getOptionThreeChinese()).concat(")"));
        optionFourRadioButton.setText(nowQuestion.getOptionFourEnglish().concat("(").concat(nowQuestion.getOptionFourChinese()).concat(")"));
        optionFiveRadioButton.setText(nowQuestion.getOptionFiveEnglish().concat("(").concat(nowQuestion.getOptionFiveChinese()).concat(")"));
      }
      if (questionGroup.checkAnswer(selectedIndex)) {
        showOptionsColor(nowQuestion.getAnswerIndex(), Color.BLUE);
      } else {
        showOptionsColor(nowQuestion.getAnswerIndex(), Color.BLUE);
        showOptionsColor(selectedIndex, Color.RED);
        if (examTypeIndex == ExamTypesEnum.ANTONYM.ordinal()) {
          dbTool.updateAntonymErrorSate(nowQuestion.getIndex(), "1");
        } else if (examTypeIndex == ExamTypesEnum.ANALOGY.ordinal()) {
          dbTool.updateAnalogyErrorSate(nowQuestion.getIndex(), "1");
        } else {
          dbTool.updateVocabularyErrorState(nowQuestion.getIndex(), "1");
        }
      }
      questionGroup.addFinishedCount();
      setAnswerState();
      if (isLastQuestion()) {
        initialState();
      } else {
        confirmButton.setVisible(false);
        nextButton.setVisible(true);
      }
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void examTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examTypeComboBoxActionPerformed
      int examTypeIndex = examTypeComboBox.getSelectedIndex();
      unitComboBox.removeAllItems();
      if (examTypeIndex == ExamTypesEnum.ANTONYM.ordinal()) {
        setUnits(dbTool.getAntonymUnits());
      } else if (examTypeIndex == ExamTypesEnum.ANALOGY.ordinal()) {
        setUnits(dbTool.getAnalogyUnits());
      } else if (examTypeIndex == ExamTypesEnum.VOCABULARY.ordinal()) {
        setUnits(dbTool.getVocabularyUnits());
      }
}//GEN-LAST:event_examTypeComboBoxActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
      getNextQuestion();
      nextButton.setVisible(false);
      hiddenButton.setSelected(true);
      confirmButton.setVisible(true);
    }//GEN-LAST:event_nextButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
      initialState();
      setErrorCheckBoxState();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void unitComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitComboBoxActionPerformed
      setSerialState();
      setErrorCheckBoxState();
    }//GEN-LAST:event_unitComboBoxActionPerformed

    private void errorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_errorCheckBoxActionPerformed
      setSerialState();
    }//GEN-LAST:event_errorCheckBoxActionPerformed

  private int getOptionsSelectedIndex() {
    int index = -1;
    Enumeration<AbstractButton> buttonsEnum = optionsButtonGroup.getElements();
    for (; buttonsEnum.hasMoreElements();) {
      AbstractButton selectButton = buttonsEnum.nextElement();
      if (selectButton.isSelected()) {
        index = Integer.parseInt(selectButton.getActionCommand());
      }
    }
    return index;
  }

  private void showOptionsColor(int index, Color color) {
    switch (index) {
      case 0:
        optionOneRadioButton.setForeground(color);
        break;
      case 1:
        optionTwoRadioButton.setForeground(color);
        break;
      case 2:
        optionThreeRadioButton.setForeground(color);
        break;
      case 3:
        optionFourRadioButton.setForeground(color);
        break;
      case 4:
        optionFiveRadioButton.setForeground(color);
        break;
    }
  }

  protected void upDateExamUnits() {
    examTypeComboBox.setSelectedIndex(examTypeComboBox.getSelectedIndex());
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel accurateCountLabel;
  private javax.swing.JLabel accurateLabel;
  private javax.swing.JLabel accurateRateLabel;
  private javax.swing.JLabel accurateRateNumberLabel;
  private javax.swing.JComboBox alphabetComboBox;
  private javax.swing.JPanel answerStatePanel;
  private javax.swing.JButton confirmButton;
  private javax.swing.JCheckBox errorCheckBox;
  private javax.swing.JPanel examPanel;
  private javax.swing.JComboBox examTypeComboBox;
  private javax.swing.JLabel examTypeLabel;
  private javax.swing.JLabel finishedCountLabel;
  private javax.swing.JLabel finishedLabel;
  private javax.swing.JRadioButton hiddenButton;
  private javax.swing.JLabel letterLable;
  private javax.swing.JButton nextButton;
  private javax.swing.JRadioButton optionFiveRadioButton;
  private javax.swing.JRadioButton optionFourRadioButton;
  private javax.swing.JRadioButton optionOneRadioButton;
  private javax.swing.JRadioButton optionThreeRadioButton;
  private javax.swing.JRadioButton optionTwoRadioButton;
  private javax.swing.ButtonGroup optionsButtonGroup;
  private javax.swing.JLabel questionChineseLabel;
  private javax.swing.JLabel questionEnglishLable;
  private javax.swing.JPanel questionPanel;
  private javax.swing.JCheckBox randomCheckBox;
  private javax.swing.JComboBox rangeComboBox;
  private javax.swing.JLabel rangeLabel;
  private javax.swing.JLabel remainCountLabel;
  private javax.swing.JLabel remainLabel;
  private javax.swing.JSpinner serialEndSpinner;
  private javax.swing.JLabel serialLabel;
  private javax.swing.JSpinner serialStartSpinner;
  private javax.swing.JButton startButton;
  private javax.swing.JButton stopButton;
  private javax.swing.JLabel toLabel;
  private javax.swing.JLabel totalCountLabel;
  private javax.swing.JLabel totalLabel;
  private javax.swing.JComboBox unitComboBox;
  private javax.swing.JLabel unitLabel;
  private javax.swing.JLabel wrongCountLabel;
  private javax.swing.JLabel wrongLabel;
  private org.jdesktop.beansbinding.BindingGroup bindingGroup;
  // End of variables declaration//GEN-END:variables
  private QuestionGroup questionGroup;
  private Question nowQuestion;
  private transient DataBaseTool dbTool = AccessDataBaseTool.getAccessDataBaseTool();
}
