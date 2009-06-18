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
 * QuestionGenerator.java
 * 
 */
package gre.utility;

import gre.database.DataBaseTool;
import java.util.ArrayList;
import java.util.Random;

public class QuestionGenerator {

  private DataBaseTool dbTool;
  private ArrayList<String[]> all_analogyData;
  private ArrayList<String[]> all_vocabularyData;

  public QuestionGenerator(DataBaseTool dbTool) {
    this.dbTool = dbTool;
  }

  public ArrayList<Question> generateAntonymQuestions(String unit, boolean isError) {
    all_analogyData = dbTool.getAllAntonym(dbTool.ALL, false);
    ArrayList<Question> antonymQuestions = new ArrayList<Question>();
    ArrayList<String[]> antonymData = dbTool.getAllAntonym(unit, isError);
    for (String[] e : antonymData) {
      Question question = new Question();
      Random random = new Random();
      int index = random.nextInt(5);
      question.setIndex(e[AntonymEnum.INDEX.ordinal()]);
      question.setQuestionEnglish(e[AntonymEnum.ENG_1.ordinal()]);
      question.setQuestionChinese(e[AntonymEnum.CHINESE_1.ordinal()]);
      question.setAnswerIndex(index);
      setAntonymOption(question, e, index);
      for (int i = 0; i < 5; i++) {
        if (i != index) {
          String[] otherOption = getOtherOptionData(e);
          setAntonymOption(question, otherOption, i);
        }
      }
      antonymQuestions.add(question);
    }
    all_analogyData = null;
    return antonymQuestions;
  }

  public ArrayList<Question> generateAntonymQuestionsByAlphabet(String unit, String alph, boolean isError) {
    ArrayList<Question> antonymQuestions = generateAntonymQuestions(unit, isError);
    ArrayList<Question> antonymQuestionsByAlphabet = new ArrayList<Question>();
    for (Question question : antonymQuestions) {
      String questionEnglish = question.getQuestionEnglish();
      if (questionEnglish.substring(0, 1).equalsIgnoreCase(alph)) {
        antonymQuestionsByAlphabet.add(question);
      }
    }
    return antonymQuestionsByAlphabet;
  }

  public ArrayList<Question> generateAntonymQuestionsByRange(String unit, int start, int end, boolean isError) {
    ArrayList<Question> antonymQuestions = generateAntonymQuestions(unit, isError);
    ArrayList<Question> antonymQuestionsByRange = new ArrayList<Question>();
    for (int index = start; index < end; index++) {
      antonymQuestionsByRange.add(antonymQuestions.get(index));
    }
    return antonymQuestionsByRange;
  }

  public ArrayList<Question> generateAnalogyQuestions(String unit, boolean isError) {
    all_analogyData = dbTool.getAllAnalogy(dbTool.ALL, false);
    ArrayList<Question> analogyQuestions = new ArrayList<Question>();
    ArrayList<String[]> analogyData = dbTool.getAllAnalogy(unit, isError);
    for (String[] e : analogyData) {
      Question question = new Question();
      Random random = new Random();
      int index = random.nextInt(5);
      question.setIndex(e[AnalogyEnum.INDEX.ordinal()]);
      question.setQuestionEnglish(e[AnalogyEnum.ENG_1.ordinal()].concat(":").concat(e[AnalogyEnum.ENG_2.ordinal()]));
      question.setQuestionChinese(e[AnalogyEnum.CHINESE_1.ordinal()].concat(":").concat(e[AnalogyEnum.CHINESE_2.ordinal()]));
      question.setAnswerIndex(index);
      setAnalogyOption(question, e, index);
      for (int i = 0; i < 5; i++) {
        if (i != index) {
          String[] otherOption = getOtherOptionData(e);
          setAnalogyOption(question, otherOption, i);
        }
      }
      analogyQuestions.add(question);
    }
    all_analogyData = null;
    return analogyQuestions;
  }

  public ArrayList<Question> generateAnalogyQuestionsByAlphabet(String unit, String alph, boolean isError) {
    ArrayList<Question> analogyQuestions = generateAnalogyQuestions(unit, isError);
    ArrayList<Question> analogyQuestionsByAlphabet = new ArrayList<Question>();
    for (Question question : analogyQuestions) {
      String questionEnglish = question.getQuestionEnglish();
      if (questionEnglish.substring(0, 1).equalsIgnoreCase(alph)) {
        analogyQuestionsByAlphabet.add(question);
      }
    }
    return analogyQuestionsByAlphabet;
  }

  public ArrayList<Question> generateAnalogyQuestionsByRange(String unit, int start, int end, boolean isError) {
    ArrayList<Question> analogyQuestions = generateAnalogyQuestions(unit, isError);
    ArrayList<Question> analogyQuestionsByRange = new ArrayList<Question>();
    for (int index = start; index < end; index++) {
      analogyQuestionsByRange.add(analogyQuestions.get(index));
    }
    return analogyQuestionsByRange;
  }

  public ArrayList<Question> generateVocabularyQuestions(String unit, boolean isError) {
    all_vocabularyData = dbTool.getAllVacabulary(unit, false);
    ArrayList<String[]> vocabularyData = dbTool.getAllVacabulary(unit, false, isError);
    ArrayList<Question> vocabularyQuestions = new ArrayList<Question>();
    for (String[] e : vocabularyData) {
      Question question = new Question();
      Random random = new Random();
      int index = random.nextInt(5);
      question.setIndex(e[VocabularyEnum.INDEX.ordinal()]);
      question.setQuestionEnglish(e[VocabularyEnum.ENG_1.ordinal()]);
      question.setQuestionChinese("");
      question.setAnswerIndex(index);
      setVocabularyOption(question, e, index);
      for (int i = 0; i < 5; i++) {
        if (i != index) {
          String[] otherOption = getOtherVocabularyOptionData(e);
          setVocabularyOption(question, otherOption, i);
        }
      }
      vocabularyQuestions.add(question);
    }
    all_vocabularyData = null;
    return vocabularyQuestions;
  }

  public ArrayList<Question> generateVocabularyQuestionsByAlphabet(String unit, String alph, boolean isError) {
    ArrayList<Question> vocabularyQuestions = generateVocabularyQuestions(unit, isError);
    ArrayList<Question> vocabularyQuestionsByAlphabet = new ArrayList<Question>();
    for (Question question : vocabularyQuestions) {
      String questionEnglish = question.getQuestionEnglish();
      if (questionEnglish.substring(0, 1).equalsIgnoreCase(alph)) {
        vocabularyQuestionsByAlphabet.add(question);
      }
    }
    return vocabularyQuestionsByAlphabet;
  }

  public ArrayList<Question> generateVocabularyQuestionsByRange(String unit, int start, int end, boolean isError) {
    ArrayList<Question> vocabularyQuestions = generateVocabularyQuestions(unit, isError);
    ArrayList<Question> vocabularyQuestionsByRange = new ArrayList<Question>();
    for (int index = start; index < end; index++) {
      vocabularyQuestionsByRange.add(vocabularyQuestions.get(index));
    }
    return vocabularyQuestionsByRange;
  }

  private String[] getOtherOptionData(String[] questionData) {
    String[] option = null;
    Random random = new Random();
    boolean isSame = true;
    for (; isSame;) {
      int seed = random.nextInt(all_analogyData.size());
      option = all_analogyData.get(seed);
      if (questionData[AntonymEnum.ENG_1.ordinal()].equals(option[AntonymEnum.ENG_1.ordinal()])) {
        continue;
      }
      if (questionData[AntonymEnum.ENG_2.ordinal()].equals(option[AntonymEnum.ENG_2.ordinal()])) {
        continue;
      }
      isSame = false;
    }
    return option;
  }

  private String[] getOtherVocabularyOptionData(String[] questionData) {
    String[] option = null;
    Random random = new Random();
    boolean isSame = true;
    for (; isSame;) {
      int seed = random.nextInt(all_vocabularyData.size());
      option = all_vocabularyData.get(seed);
      if (questionData[VocabularyEnum.ENG_1.ordinal()].equals(option[VocabularyEnum.ENG_1.ordinal()])) {
        continue;
      }
      if (questionData[VocabularyEnum.CHINESE_1.ordinal()].equals(option[VocabularyEnum.CHINESE_1.ordinal()])) {
        continue;
      }
      isSame = false;
    }
    return option;
  }

  private void setAntonymOption(Question question, String[] antonymData, int index) {
    switch (index) {
      case 0:
        question.setOptionOneEnglish(antonymData[AntonymEnum.ENG_2.ordinal()]);
        question.setOptionOneChinese(antonymData[AntonymEnum.CHINESE_2.ordinal()]);
        break;
      case 1:
        question.setOptionTwoEnglish(antonymData[AntonymEnum.ENG_2.ordinal()]);
        question.setOptionTwoChinese(antonymData[AntonymEnum.CHINESE_2.ordinal()]);
        break;
      case 2:
        question.setOptionThreeEnglish(antonymData[AntonymEnum.ENG_2.ordinal()]);
        question.setOptionThreeChinese(antonymData[AntonymEnum.CHINESE_2.ordinal()]);
        break;
      case 3:
        question.setOptionFourEnglish(antonymData[AntonymEnum.ENG_2.ordinal()]);
        question.setOptionFourChinese(antonymData[AntonymEnum.CHINESE_2.ordinal()]);
        break;
      case 4:
        question.setOptionFiveEnglish(antonymData[AntonymEnum.ENG_2.ordinal()]);
        question.setOptionFiveChinese(antonymData[AntonymEnum.CHINESE_2.ordinal()]);
        break;
    }
  }

  private void setAnalogyOption(Question question, String[] analogyData, int index) {
    switch (index) {
      case 0:
        question.setOptionOneEnglish(analogyData[AnalogyEnum.ENG_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.ENG_4.ordinal()]).toLowerCase());
        question.setOptionOneChinese(analogyData[AnalogyEnum.CHINESE_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.CHINESE_4.ordinal()]));
        break;
      case 1:
        question.setOptionTwoEnglish(analogyData[AnalogyEnum.ENG_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.ENG_4.ordinal()]).toLowerCase());
        question.setOptionTwoChinese(analogyData[AnalogyEnum.CHINESE_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.CHINESE_4.ordinal()]));
        break;
      case 2:
        question.setOptionThreeEnglish(analogyData[AnalogyEnum.ENG_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.ENG_4.ordinal()]).toLowerCase());
        question.setOptionThreeChinese(analogyData[AnalogyEnum.CHINESE_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.CHINESE_4.ordinal()]));
        break;
      case 3:
        question.setOptionFourEnglish(analogyData[AnalogyEnum.ENG_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.ENG_4.ordinal()]).toLowerCase());
        question.setOptionFourChinese(analogyData[AnalogyEnum.CHINESE_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.CHINESE_4.ordinal()]));
        break;
      case 4:
        question.setOptionFiveEnglish(analogyData[AnalogyEnum.ENG_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.ENG_4.ordinal()]).toLowerCase());
        question.setOptionFiveChinese(analogyData[AnalogyEnum.CHINESE_3.ordinal()].concat(":").concat(analogyData[AnalogyEnum.CHINESE_4.ordinal()]));
        break;
    }
  }

  private void setVocabularyOption(Question question, String[] vocabularyData, int index) {
    switch (index) {
      case 0:
        question.setOptionOneChinese(vocabularyData[VocabularyEnum.CHINESE_1.ordinal()]);
        break;
      case 1:
        question.setOptionTwoChinese(vocabularyData[VocabularyEnum.CHINESE_1.ordinal()]);
        break;
      case 2:
        question.setOptionThreeChinese(vocabularyData[VocabularyEnum.CHINESE_1.ordinal()]);
        break;
      case 3:
        question.setOptionFourChinese(vocabularyData[VocabularyEnum.CHINESE_1.ordinal()]);
        break;
      case 4:
        question.setOptionFiveChinese(vocabularyData[VocabularyEnum.CHINESE_1.ordinal()]);
        break;
    }
  }
}