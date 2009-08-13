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
 * AccessDataBaseTool.java
 */
package gre.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import gre.utility.AnalogyEnum;
import gre.utility.AntonymEnum;
import gre.utility.Configure;
import gre.utility.Utility;
import gre.utility.VocabularyEnum;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class implement DataBase interface, and it support Microsoft Access.
 * @author digman543
 */
public class AccessDataBaseTool implements DataBaseTool {

  private static AccessDataBaseTool accessDBTool = null;
  public static final String UPDATE_MEMO_STATE = "update Vocabulary set memo = ? where unit = ? and eng_1 = ?";
  public static final String UPDATE_ERROR_STATE = "update Vocabulary set error = ? where index = ?";
  public static final String UPDATE_ANTONYM_ERROR_STATE = "update Antonym set error = ? where index = ?";
  public static final String UPDATE_ANALOGY_ERROR_STATE = "update Analogy set error = ? where index = ?";
  public static final String ANTONYM_TABLE_NAME = "Antonym";
  public static final String ANALOGY_TABLE_NAME = "Analogy";
  public static final String VOCABULARY_TABLE_NAME = "Vocabulary";
  private Database database;

  private AccessDataBaseTool() {
    try {
      Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }

  public static AccessDataBaseTool getAccessDataBaseTool() {
    if (accessDBTool == null) {
      accessDBTool = new AccessDataBaseTool();
    }
    return accessDBTool;
  }

  /**
   * Find all words by condition of parameters
   * @param unit
   * @param isRandom true:random false:sort by the index of database
   * @param isForgetful true:find the vocabularies that you mark forgetful; false: all vocabularies of the unit
   * @return
   */
  public ArrayList<String[]> getAllVacabulary(String unit, boolean isForgetful) {
    return getAllVacabulary(unit, isForgetful, false);
  }

  /**
   * Find all words by condition of parameters
   * @param unit
   * @param isForgetful
   * @param isError
   * @return
   */
  public ArrayList<String[]> getAllVacabulary(String unit, boolean isForgetful, boolean isError) {
    ArrayList<String[]> wordsList = new ArrayList<String[]>();
    if (!Utility.isDatabaseExist()) {
      return wordsList;
    }
    Table table = getVocabularyTable();
    if (table == null) {
      return wordsList;
    }
    String[] eachRowData = null;
    if (isForgetful) {
      for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
        Map<String, Object> rMap = rIt.next();
        if (rMap.get(VocabularyEnum.UNIT.getDescription()).equals(unit) && rMap.get(VocabularyEnum.MEMO.getDescription()) != null && rMap.get(VocabularyEnum.MEMO.getDescription()).equals("1")) {
          eachRowData = new String[]{rMap.get(VocabularyEnum.INDEX.getDescription()).toString(), unit, (String) rMap.get(VocabularyEnum.ENG_1.getDescription()), (String) rMap.get(VocabularyEnum.CHINESE_1.getDescription())};
          wordsList.add(eachRowData);
        }
      }
    } else {
      if (isError) {
        for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
          Map<String, Object> rMap = rIt.next();
          if (rMap.get(VocabularyEnum.UNIT.getDescription()).equals(unit) && rMap.get(VocabularyEnum.ERROR.getDescription()) != null && rMap.get(VocabularyEnum.ERROR.getDescription()).equals("1")) {
            eachRowData = new String[]{rMap.get(VocabularyEnum.INDEX.getDescription()).toString(), unit, (String) rMap.get(VocabularyEnum.ENG_1.getDescription()), (String) rMap.get(VocabularyEnum.CHINESE_1.getDescription())};
            wordsList.add(eachRowData);
          }
        }
      } else {
        for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
          Map<String, Object> rMap = rIt.next();
          if (rMap.get(VocabularyEnum.UNIT.getDescription()).equals(unit)) {
            eachRowData = new String[]{rMap.get(VocabularyEnum.INDEX.getDescription()).toString(), unit, (String) rMap.get(VocabularyEnum.ENG_1.getDescription()), (String) rMap.get(VocabularyEnum.CHINESE_1.getDescription())};
            wordsList.add(eachRowData);
          }
        }
      }
    }
    // resort
    Collections.sort(wordsList, new Comparator() {

      public int compare(Object o1, Object o2) {
        String[] f = (String[]) o1;
        String[] b = (String[]) o2;
        return Integer.parseInt(f[0]) - Integer.parseInt(b[0]);
      }
    });
    return wordsList;
  }

  /**
   * This method is used to check the unit that has any vocabularies be marked forgetful
   * @param unit
   * @return true: the unit has forgetful vocabularies; false: all you remember!
   */
  public boolean hasForgetfulVocabulary(String unit) {
    boolean hasForgetful = false;
    if (Utility.isDatabaseExist()) {
      Table table = getVocabularyTable();
      for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
        Map<String, Object> rMap = rIt.next();
        if (rMap.get("unit").equals(unit) && rMap.get("memo") != null && rMap.get("memo").equals("1")) {
          hasForgetful = true;
          break;
        }
      }
    }
    return hasForgetful;
  }

  public boolean hasErrorVocabulary(String unit) {
    boolean hasError = false;
    if (Utility.isDatabaseExist()) {
      Table table = getVocabularyTable();
      for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
        Map<String, Object> rMap = rIt.next();
        if (rMap.get("unit").equals(unit) && rMap.get("error") != null && rMap.get("error").equals("1")) {
          hasError = true;
          break;
        }
      }
    }
    return hasError;
  }

  public boolean hasErrorAnatonym(String unit) {
    boolean hasError = false;
    if (Utility.isDatabaseExist()) {
      Table table = getAntonymTable();
      for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
        Map<String, Object> rMap = rIt.next();
        if (rMap.get("unit").equals(unit) && rMap.get("error") != null && rMap.get("error").equals("1")) {
          hasError = true;
          break;
        }
      }
    }
    return hasError;
  }

  public boolean hasErrorAnalogy(String unit) {
    boolean hasError = false;
    if (Utility.isDatabaseExist()) {
      Table table = getAnalogyTable();
      for (Iterator<Map<String, Object>> rIt = table.iterator(); rIt.hasNext();) {
        Map<String, Object> rMap = rIt.next();
        if (rMap.get("unit").equals(unit) && rMap.get("error") != null && rMap.get("error").equals("1")) {
          hasError = true;
          break;
        }
      }
    }
    return hasError;
  }
  /**
   * Add the data of the new unit into vocabulary table
   */
  public boolean addNewUnit(String tableName, List<Object[]> data) {
    boolean isCorrect = true;
    // start inert each row data
    if (isCorrect) {
      Table table = null;
      Database db = getDatabase();
      try {
        table = db.getTable(tableName, true);
        List<Object[]> newData = new ArrayList<Object[]>();
        // index unit eng_1 chinese_1 memo error
        for (Object[] e : data) {
          newData.add(e);
        }
        table.addRows(newData);
        db.flush();
      } catch (IOException ex) {
        System.err.println(ex);
        isCorrect = false;
      } finally {
        table.reset();
      }
    }
    return isCorrect;
  }

  /**
   * Delete all words of this unit in vocaublary table.
   */
  public boolean deleteNewUnit(String tableName, String unit) {
    // start inert each row data
    Database db = getDatabase();
    Table table = null;
    try {
      table = db.getTable(tableName, true);
      table.reset();
      Map<String, Object> row = null;
      while ((row = table.getNextRow()) != null) {
        if (row.get("unit").equals(unit)) {
          table.deleteCurrentRow();
        }
      }
      db.flush();
    } catch (IOException ex) {
      System.err.println(ex);
      return false;
    } finally {
      table.reset();
    }
    return true;
  }

  /**
   * update memo column state about this word of this unit
   * @param unit
   * @param word
   * @param memoChar
   */
  public void updateVocabularyMemoState(String unit, String word, String memoChar) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      Properties info = new Properties();
      info.setProperty("versionNumber", "3");
      con = DriverManager.getConnection("jdbc:access:///".concat(Configure.getSingleConfigure().getElement(Configure.DATABASE)), info);
      ps = con.prepareCall(UPDATE_MEMO_STATE);
      ps.setString(1, memoChar);
      ps.setString(2, unit);
      ps.setString(3, word);
      ps.execute();
    } catch (Exception ex) {
      Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        ps.close();
        con.close();
      } catch (SQLException ex) {
        Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * update error column state about this word of this unit
   * @param unit
   * @param errorChar
   */
  public void updateVocabularyErrorState(String index, String errorChar) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      Properties info = new Properties();
      info.setProperty("versionNumber", "3");
      con = DriverManager.getConnection("jdbc:access:///".concat(Configure.getSingleConfigure().getElement(Configure.DATABASE)), info);
      ps = con.prepareCall(UPDATE_ERROR_STATE);
      ps.setString(1, errorChar);
      ps.setString(2, index);
      ps.execute();
    } catch (Exception ex) {
      Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        ps.close();
        con.close();
      } catch (SQLException ex) {
        Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Find all antonym question of this unit
   * @param unit
   * @param isRandom
   * @param isForgetful
   * @return
   */
  public ArrayList<String[]> getAllAntonym(String unit, boolean isError) {
    Table antonymTable = getAntonymTable();
    ArrayList<String[]> antonymData = new ArrayList<String[]>();
    if (unit.equals(ALL)) {
      if (isError) {
        for (Iterator<Map<String, Object>> rit = antonymTable.iterator(); rit.hasNext();) {
          Map<String, Object> rMap = rit.next();
          if (rMap.get(AntonymEnum.ERROR.getDescroption()) != null &&
              rMap.get(AntonymEnum.ERROR.getDescroption()).equals("1")) {
            Set<AntonymEnum> s = EnumSet.range(AntonymEnum.INDEX, AntonymEnum.CHINESE_2);
            String[] eachRowData = new String[s.size()];
            for (AntonymEnum e : s) {
              eachRowData[e.ordinal()] = rMap.get(e.getDescroption()).toString();
            }
            antonymData.add(eachRowData);
          }
        }
      } else {
        for (Iterator<Map<String, Object>> rit = antonymTable.iterator(); rit.hasNext();) {
          Map<String, Object> rMap = rit.next();
          Set<AntonymEnum> s = EnumSet.range(AntonymEnum.INDEX, AntonymEnum.CHINESE_2);
          String[] eachRowData = new String[s.size()];
          for (AntonymEnum e : s) {
            eachRowData[e.ordinal()] = rMap.get(e.getDescroption()).toString();
          }
          antonymData.add(eachRowData);
        }
      }
    } else {
      if (isError) {
        for (Iterator<Map<String, Object>> rit = antonymTable.iterator(); rit.hasNext();) {
          Map<String, Object> rMap = rit.next();
          if (rMap.get(AntonymEnum.UNIT.getDescroption()).equals(unit) &&
              rMap.get(AntonymEnum.ERROR.getDescroption()) != null &&
              rMap.get(AntonymEnum.ERROR.getDescroption()).equals("1")) {
            Set<AntonymEnum> s = EnumSet.range(AntonymEnum.INDEX, AntonymEnum.CHINESE_2);
            String[] eachRowData = new String[s.size()];
            for (AntonymEnum e : s) {
              eachRowData[e.ordinal()] = rMap.get(e.getDescroption()).toString();
            }
            antonymData.add(eachRowData);
          }
        }
      } else {
        for (Iterator<Map<String, Object>> rit = antonymTable.iterator(); rit.hasNext();) {
          Map<String, Object> rMap = rit.next();
          if (rMap.get(AntonymEnum.UNIT.getDescroption()).equals(unit)) {
            Set<AntonymEnum> s = EnumSet.range(AntonymEnum.INDEX, AntonymEnum.CHINESE_2);
            String[] eachRowData = new String[s.size()];
            for (AntonymEnum e : s) {
              eachRowData[e.ordinal()] = rMap.get(e.getDescroption()).toString();
            }
            antonymData.add(eachRowData);
          }
        }
      }
    }
    Collections.sort(antonymData, new Comparator() {

      public int compare(Object o1, Object o2) {
        String[] f = (String[]) o1;
        String[] b = (String[]) o2;
        return Integer.parseInt(f[0]) - Integer.parseInt(b[0]);
      }
    });
    return antonymData;
  }

  public void updateAntonymErrorSate(String index, String errorChar) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      Properties info = new Properties();
      info.setProperty("versionNumber", "3");
      con = DriverManager.getConnection("jdbc:access:///".concat(Configure.getSingleConfigure().getElement(Configure.DATABASE)), info);
      ps = con.prepareCall(UPDATE_ANTONYM_ERROR_STATE);
      ps.setString(1, errorChar);
      ps.setString(2, index);
      ps.execute();
    } catch (Exception ex) {
      Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        ps.close();
        con.close();
      } catch (SQLException ex) {
        Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public void updateAnalogyErrorSate(String index, String errorChar) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      Properties info = new Properties();
      info.setProperty("versionNumber", "3");
      con = DriverManager.getConnection("jdbc:access:///".concat(Configure.getSingleConfigure().getElement(Configure.DATABASE)), info);
      ps = con.prepareCall(UPDATE_ANALOGY_ERROR_STATE);
      ps.setString(1, errorChar);
      ps.setString(2, index);
      ps.execute();
    } catch (Exception ex) {
      Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        ps.close();
        con.close();
      } catch (SQLException ex) {
        Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public ArrayList<String[]> getAllAnalogy(String unit, boolean isError) {
    ArrayList<String[]> all_analogyData = new ArrayList<String[]>();
    Table _table = getAnalogyTable();
    if (ALL.equals(unit)) {
      if (isError) {
        for (Iterator<Map<String, Object>> it = _table.iterator(); it.hasNext();) {
          Map<String, Object> rMap = it.next();
          if (rMap.get(AntonymEnum.ERROR.getDescroption()) != null &&
              rMap.get(AntonymEnum.ERROR.getDescroption()).equals("1")) {
            Set<AnalogyEnum> _set = EnumSet.range(AnalogyEnum.INDEX, AnalogyEnum.CHINESE_4);
            String[] eachRowDat = new String[_set.size()];
            for (AnalogyEnum e : _set) {
              eachRowDat[e.ordinal()] = rMap.get(e.getDescription()).toString();
            }
            all_analogyData.add(eachRowDat);
          }
        }
      } else {
        for (Iterator<Map<String, Object>> it = _table.iterator(); it.hasNext();) {
          Map<String, Object> rMap = it.next();
          Set<AnalogyEnum> _set = EnumSet.range(AnalogyEnum.INDEX, AnalogyEnum.CHINESE_4);
          String[] eachRowDat = new String[_set.size()];
          for (AnalogyEnum e : _set) {
            eachRowDat[e.ordinal()] = rMap.get(e.getDescription()).toString();
          }
          all_analogyData.add(eachRowDat);
        }
      }
    } else {
      if (isError) {
        for (Iterator<Map<String, Object>> it = _table.iterator(); it.hasNext();) {
          Map<String, Object> rMap = it.next();
          if (rMap.get(AntonymEnum.UNIT.getDescroption()).equals(unit) &&
              rMap.get(AntonymEnum.ERROR.getDescroption()) != null &&
              rMap.get(AntonymEnum.ERROR.getDescroption()).equals("1")) {
            Set<AnalogyEnum> _set = EnumSet.range(AnalogyEnum.INDEX, AnalogyEnum.CHINESE_4);
            String[] eachRowDat = new String[_set.size()];
            for (AnalogyEnum e : _set) {
              eachRowDat[e.ordinal()] = rMap.get(e.getDescription()).toString();
            }
            all_analogyData.add(eachRowDat);
          }
        }
      } else {
        for (Iterator<Map<String, Object>> it = _table.iterator(); it.hasNext();) {
          Map<String, Object> rMap = it.next();
          if (rMap.get(AntonymEnum.UNIT.getDescroption()).equals(unit)) {
            Set<AnalogyEnum> _set = EnumSet.range(AnalogyEnum.INDEX, AnalogyEnum.CHINESE_4);
            String[] eachRowDat = new String[_set.size()];
            for (AnalogyEnum e : _set) {
              eachRowDat[e.ordinal()] = rMap.get(e.getDescription()).toString();
            }
            all_analogyData.add(eachRowDat);
          }
        }
      }
    }
    return all_analogyData;
  }

  private Table getVocabularyTable() {
    return getTable(VOCABULARY_TABLE_NAME);
  }

  public Set<String> getAntonymUnits() {
    return getUnits(getAntonymTable());
  }

  public Set<String> getAnalogyUnits() {
    return getUnits(getAnalogyTable());
  }

// Find Units
  public Set<String> getVocabularyUnits() {
    return getUnits(getVocabularyTable());
  }

  private Set<String> getUnits(Table _table) {
    Set<String> _set = new TreeSet<String>();
    if (Utility.isDatabaseExist()) {
      _table.reset();
      if (_table == null) {
        return _set;
      }

      for (Iterator<Map<String, Object>> it = _table.iterator(); it.hasNext();) {
        Map<String, Object> row = it.next();
        _set.add((String) row.get("unit"));
      }

    }
    return _set;
  }

  private Table getAntonymTable() {
    return getTable(ANTONYM_TABLE_NAME);
  }

  private Table getAnalogyTable() {
    return getTable(ANALOGY_TABLE_NAME);
  }

  private Table getTable(String table_name) {
    Table _table = null;
    if (Utility.isDatabaseExist()) {
      try {
        _table = getDatabase().getTable(table_name);
        _table.reset();
      } catch (IOException ex) {
        Logger.getLogger(AccessDataBaseTool.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return _table;
  }

// get database object
  private Database getDatabase() {
    if (database == null) {
      try {
        String dbPath = Configure.getSingleConfigure().getElement(Configure.DATABASE);
        database =
            Database.open(new File(dbPath), false);
      } catch (IOException ex) {
        System.err.println("Can't find Database.Please check path is correct");
        System.out.println(ex.getMessage());
      }

    }
    return database;
  }
}
