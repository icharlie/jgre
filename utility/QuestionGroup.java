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
 * QuestionGroup.java
 * 
 */
package gre.utility;

import java.math.BigDecimal;
import java.util.ArrayList;

public class QuestionGroup {

  private ArrayList<Question> questions;
  private int totalCount;
  private int finishedCount;
  private int remnantCount;
  private double accurateRate;
  private int rightCount;
  private int wrongCount;

  public QuestionGroup(ArrayList<Question> questions) {
    this.questions = questions;
    this.totalCount = questions.size();
  }

  public void addFinishedCount() {
    finishedCount++;
  }

  /**
   * @return the totalCount
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * @return the finishedCount
   */
  public int getFininshedCount() {
    return finishedCount;
  }

  /**
   * @return the remnantCount
   */
  public int getRemainCount() {
    remnantCount = totalCount - finishedCount;
    return remnantCount;
  }

  /**
   * @return the accurateRate
   */
  public double getAccurateRate() {
    if (finishedCount != 0) {
      BigDecimal bd = new BigDecimal(rightCount);
      bd = bd.divide(new BigDecimal(finishedCount), 4, BigDecimal.ROUND_HALF_UP).movePointRight(2);
      accurateRate = bd.doubleValue();
    }
    return accurateRate;
  }

  /**
   * @return the rightCount
   */
  public int getAccurateCount() {
    return rightCount;
  }

  /**
   * @return the wrongCount
   */
  public int getWrongCount() {
    return wrongCount;
  }

  public boolean checkAnswer(int selectIndex) {
    Question question = questions.get(getFininshedCount());
    boolean isRight = false;
    if (question.getAnswerIndex() == selectIndex) {
      isRight = true;
      rightCount++;
    } else {
      wrongCount++;
    }
    return isRight;
  }

  /**
   * @return
   */
  public Question getNextQuestion() {
    return questions.get(getFininshedCount());
  }
}
