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
 * Question.java
 * 
 */
package gre.utility;

public class Question {

  private String index;
  private String questionEnglish;
  private String questionChinese;
  private String optionOneEnglish;
  private String optionOneChinese;
  private String optionTwoEnglish;
  private String optionTwoChinese;
  private String optionThreeEnglish;
  private String optionThreeChinese;
  private String optionFourEnglish;
  private String optionFourChinese;
  private String optionFiveEnglish;
  private String optionFiveChinese;
  private int answerIndex;

  /**
   * @return the questionEnglish
   */
  public String getQuestionEnglish() {
    return questionEnglish;
  }

  /**
   * @return the questionChinese
   */
  public String getQuestionChinese() {
    return questionChinese;
  }

  /**
   * @return the optionOneEnglish
   */
  public String getOptionOneEnglish() {
    return optionOneEnglish;
  }

  /**
   * @return the optionOneChinese
   */
  public String getOptionOneChinese() {
    return optionOneChinese;
  }

  /**
   * @return the optionTwoEnglish
   */
  public String getOptionTwoEnglish() {
    return optionTwoEnglish;
  }

  /**
   * @return the optionTwoChinese
   */
  public String getOptionTwoChinese() {
    return optionTwoChinese;
  }

  /**
   * @return the optionThreeEnglish
   */
  public String getOptionThreeEnglish() {
    return optionThreeEnglish;
  }

  /**
   * @return the optionThreeChinese
   */
  public String getOptionThreeChinese() {
    return optionThreeChinese;
  }

  /**
   * @return the optionFourEnglish
   */
  public String getOptionFourEnglish() {
    return optionFourEnglish;
  }

  /**
   * @return the optionFourChinese
   */
  public String getOptionFourChinese() {
    return optionFourChinese;
  }

  /**
   * @return the optionFiveEnglish
   */
  public String getOptionFiveEnglish() {
    return optionFiveEnglish;
  }

  /**
   * @return the optionFiveChinese
   */
  public String getOptionFiveChinese() {
    return optionFiveChinese;
  }

  /**
   * @return the answerIndex
   */
  public int getAnswerIndex() {
    return answerIndex;
  }

  /**
   * @param answerIndex the answerIndex to set
   */
  public void setAnswerIndex(int answerIndex) {
    this.answerIndex = answerIndex;
  }

  /**
   * @param questionEnglish the questionEnglish to set
   */
  public void setQuestionEnglish(String questionEnglish) {
    this.questionEnglish = questionEnglish;
  }

  /**
   * @param questionChinese the questionChinese to set
   */
  public void setQuestionChinese(String questionChinese) {
    this.questionChinese = questionChinese;
  }

  /**
   * @param optionOneEnglish the optionOneEnglish to set
   */
  public void setOptionOneEnglish(String optionOneEnglish) {
    this.optionOneEnglish = optionOneEnglish;
  }

  /**
   * @param optionOneChinese the optionOneChinese to set
   */
  public void setOptionOneChinese(String optionOneChinese) {
    this.optionOneChinese = optionOneChinese;
  }

  /**
   * @param optionTwoEnglish the optionTwoEnglish to set
   */
  public void setOptionTwoEnglish(String optionTwoEnglish) {
    this.optionTwoEnglish = optionTwoEnglish;
  }

  /**
   * @param optionTwoChinese the optionTwoChinese to set
   */
  public void setOptionTwoChinese(String optionTwoChinese) {
    this.optionTwoChinese = optionTwoChinese;
  }

  /**
   * @param optionThreeEnglish the optionThreeEnglish to set
   */
  public void setOptionThreeEnglish(String optionThreeEnglish) {
    this.optionThreeEnglish = optionThreeEnglish;
  }

  /**
   * @param optionThreeChinese the optionThreeChinese to set
   */
  public void setOptionThreeChinese(String optionThreeChinese) {
    this.optionThreeChinese = optionThreeChinese;
  }

  /**
   * @param optionFourEnglish the optionFourEnglish to set
   */
  public void setOptionFourEnglish(String optionFourEnglish) {
    this.optionFourEnglish = optionFourEnglish;
  }

  /**
   * @param optionFourChinese the optionFourChinese to set
   */
  public void setOptionFourChinese(String optionFourChinese) {
    this.optionFourChinese = optionFourChinese;
  }

  /**
   * @param optionFiveEnglish the optionFiveEnglish to set
   */
  public void setOptionFiveEnglish(String optionFiveEnglish) {
    this.optionFiveEnglish = optionFiveEnglish;
  }

  /**
   * @param optionFiveChinese the optionFiveChinese to set
   */
  public void setOptionFiveChinese(String optionFiveChinese) {
    this.optionFiveChinese = optionFiveChinese;
  }

  /**
   *
   * @return answer
   */
  public String getAnswer() {
    String answer = "";
    switch (this.answerIndex) {
      case 0:
        answer = getOptionOneEnglish();
        break;
      case 1:
        answer = getOptionTwoEnglish();
        break;
      case 2:
        answer = getOptionThreeEnglish();
        break;
      case 3:
        answer = getOptionFourEnglish();
        break;
      case 4:
        answer = getOptionFiveEnglish();
        break;
    }
    return answer;
  }

  /**
   * @return the index
   */
  public String getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public void setIndex(String index) {
    this.index = index;
  }
}
