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
 * Configure.java
 */
package gre.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Serializer;

/**
 * Use to write and read the configure xml file that will be created at first running application
 * @author digman543
 */
public class Configure {

  private static Configure singleConfigure = null;
  private String configurePath = "";
  public static final String CONFIGURE_FILE_NAME = "configure.xml";
  public static final String DATABASE = "database";
  public static final String VOICE = "voice";
  public static final String WORDLIST = "wordList";

  private Configure() {
    configurePath = Utility.getApplicationRootPath().concat(CONFIGURE_FILE_NAME);
  }

  // 取的Configure物件
  public static Configure getSingleConfigure() {
    if (singleConfigure == null) {
      singleConfigure = new Configure();
    }
    return singleConfigure;
  }

  // 取得設定檔資料
  public String getElement(String tagName) {
    try {
      Document doc = getConfigureDocument();
      Element root = doc.getRootElement();
      return root.getChildElements(tagName).get(0).getValue();

    } catch (Exception ex) {
      Logger.getLogger(Configure.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  // 取得全部設定檔資料
  public HashMap getElements() {
    HashMap<String, String> reply = new HashMap<String, String>();
    try {
      Document doc = getConfigureDocument();
      Element root = doc.getRootElement();
      Elements elements = root.getChildElements();

      for (int i = 0; i < elements.size(); i++) {
        Element e = elements.get(i);
        reply.put(e.getLocalName(), e.getValue());
      }

      return reply;

    } catch (Exception ex) {
      Logger.getLogger(Configure.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  // 設定多個資訊
  public void setElements(HashMap<String, String> elements) {
    try {
      Document doc = getConfigureDocument();
      Element root = doc.getRootElement();

      for (Iterator<Entry<String, String>> it = elements.entrySet().iterator(); it.hasNext();) {
        Entry<String, String> entry = it.next();
        Element child = new Element(entry.getKey());
        child.appendChild(entry.getValue());
        root.replaceChild(root.getChildElements(entry.getKey()).get(0), child);
      }
      Serializer serializer = new Serializer(new FileOutputStream(configurePath));
      serializer.write(doc);
      serializer.flush();
    } catch (Exception ex) {
      Logger.getLogger(Configure.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  // 設定單一資訊
  public void setSingleElement(String key, String value) {
    try {
      Document doc = getConfigureDocument();
      Element root = doc.getRootElement();
      Element child = new Element(key);
      child.appendChild(value);
      root.replaceChild(root.getChildElements(key).get(0), child);
      Serializer serializer = new Serializer(new FileOutputStream(configurePath));
      serializer.write(doc);
      serializer.flush();
    } catch (Exception ex) {
      Logger.getLogger(Configure.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private Document getConfigureDocument() throws Exception {
    return new Builder().build(new File(configurePath));
  }
}
