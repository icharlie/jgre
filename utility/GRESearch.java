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
 * GRESearch.java
 */
package gre.utility;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author digman543
 */
public class GRESearch {

  private final String ANALOGY = "analogy";
  private final String ANTONYM = "antonym";
  private final String VOCABULARY = "vocabulary";
  private Database db;

  public GRESearch() {
    String dbPath = Configure.getSingleConfigure().getElement(Configure.DATABASE);
    try {
      db = Database.open(new File(dbPath));
    } catch (IOException ex) {
      System.err.println("Can't find Database.Please check path is correct");
      System.err.print(ex.getMessage());
    }

  }

  /**
   * 搜尋字彙
   * @param keyWord 關鍵字
   * @return
   */
  public List<Map<String, Object>> searchVocabulary(String keyWord, boolean isAbsolute) {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    // check Database exist
    if (Utility.isDatabaseExist()) {
      try {
        Table tb = db.getTable(VOCABULARY);
        for (Iterator<Map<String, Object>> it = tb.iterator(); it.hasNext();) {
          Map<String, Object> row = it.next();
          for (Iterator<Entry<String, Object>> rowSet = row.entrySet().iterator(); rowSet.hasNext();) {
            Entry<String, Object> entry = rowSet.next();
            if (entry.getKey().matches("eng_1")) {
              String word = ((String) entry.getValue()).toUpperCase();
              if (isAbsolute) {
                if (word.equalsIgnoreCase(keyWord)) {
                  resultList.add(row);
                }
              } else {
                if (word.matches(keyWord.concat(".*").toUpperCase())) {
                  resultList.add(row);
                }
              }
            }
          }
        }
      } catch (IOException ex) {
        System.err.print(ex.getMessage());
      }
    }
    return resultList;
  }

  /**
   * 搜尋反義
   * @param keyWord 關鍵字
   * @return
   */
  public List<Map<String, Object>> searchAntonym(String keyWord, boolean isAbsolute) {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    // check Database exist
    if (Utility.isDatabaseExist()) {
      try {
        Table tb = db.getTable(ANTONYM);
        for (Iterator<Map<String, Object>> it = tb.iterator(); it.hasNext();) {
          Map<String, Object> row = it.next();
          for (Iterator<Entry<String, Object>> rowSet = row.entrySet().iterator(); rowSet.hasNext();) {
            Entry<String, Object> entry = rowSet.next();
            if (entry.getKey().matches("eng_(1|2)")) {
              String word = ((String) entry.getValue()).toUpperCase();
              if (isAbsolute) {
                if (word.equalsIgnoreCase(keyWord)) {
                  resultList.add(row);
                }
              } else {
                if (word.matches(keyWord.concat(".*").toUpperCase())) {
                  resultList.add(row);
                }
              }
            }
          }
        }
      } catch (IOException ex) {
        System.err.print(ex.getMessage());
      }
    }
    return resultList;
  }

  /**
   * 搜尋類比資料
   * @param keyWord 關鍵字
   * @return
   */
  public List<Map<String, Object>> searchAnalogy(String keyWord, boolean isAbsolute) {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    // check Database exist
    if (Utility.isDatabaseExist()) {
      try {
        // database location
        Table tb = db.getTable(ANALOGY);
        for (Iterator<Map<String, Object>> it = tb.iterator(); it.hasNext();) {
          Map<String, Object> row = it.next();
          for (Iterator<Entry<String, Object>> rowSet = row.entrySet().iterator(); rowSet.hasNext();) {
            Entry<String, Object> entry = rowSet.next();
            if (entry.getKey().matches("eng_[1-4]")) {
              String word = ((String) entry.getValue()).toUpperCase();
              if (isAbsolute) {
                if (word.equalsIgnoreCase(keyWord)) {
                  resultList.add(row);
                }
              } else {
                if (word.matches(keyWord.concat(".*").toUpperCase())) {
                  resultList.add(row);
                }
              }
            }
          }
        }
      } catch (IOException ex) {
        System.err.print(ex.getMessage());
      }
    }
    return resultList;
  }
}
