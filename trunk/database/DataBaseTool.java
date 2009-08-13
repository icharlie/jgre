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
 * DataBaseTool.java
 */
package gre.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Define the methods that databasetoll should support.
 * @author digman543
 */
public interface DataBaseTool {

  public static String ALL = "All";

  public ArrayList<String[]> getAllAntonym(String unit, boolean isError);

  public ArrayList<String[]> getAllAnalogy(String unit, boolean isError);

  public ArrayList<String[]> getAllVacabulary(String unit, boolean isForgetful);

  public ArrayList<String[]> getAllVacabulary(String unit, boolean isForgetful, boolean isError);

  public Set<String> getAntonymUnits();

  public Set<String> getAnalogyUnits();

  public Set<String> getVocabularyUnits();

  public void updateVocabularyMemoState(String unit, String word, String memoChar);

  public void updateAntonymErrorSate(String index, java.lang.String errorChar);

  public void updateAnalogyErrorSate(String index, java.lang.String errorChar);

  public void updateVocabularyErrorState(String index, String errorChar);

  public boolean hasForgetfulVocabulary(String unit);

  public boolean hasErrorVocabulary(String unit);

  public boolean hasErrorAnatonym(String unit);

  public boolean hasErrorAnalogy(String unit);

  public boolean addNewUnit(String tableName, List<Object[]> data);

  public boolean deleteNewUnit(String tableName, String unit);
}
