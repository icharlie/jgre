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
 * Utility.java
 */
package gre.utility;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * The utility used to support the common method in each classes for basic needs.
 * @author digman543
 */
public class Utility {

  static final String[] browsers = {"firefox", "opera", "konqueror", "epiphany",
    "seamonkey", "galeon", "kazehakase", "mozilla", "netscape"};

  public static String getApplicationRootPath() {
    String appRootPath = ".";
    try {
      String partialPath = Utility.class.getName().replace('.', File.separatorChar).concat(".class");
      URL url = Utility.class.getClassLoader().getResource(partialPath);
      if (url != null && "jar".equals(url.getProtocol())) {
        String path = url.toString();
        String jarPath = path.substring(path.indexOf("file:"),
            path.lastIndexOf('!'));
        String dirPath = jarPath.substring(0, jarPath.lastIndexOf('/') + 1);
        File installDirFile = new File(URI.create(dirPath));
        appRootPath = installDirFile.getCanonicalPath().replace(
            File.separatorChar, '/');
      }
    } catch (Exception ex) {
      Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
    }
    return appRootPath.concat(File.separator);
  }

  /**
   * 檢查Database是否存在
   * @return true:存在，false:不存在
   */
  public static boolean isDatabaseExist() {
    Configure configure = Configure.getSingleConfigure();
    String databasePath = configure.getElement(Configure.DATABASE);
    if (databasePath.equals("")) {
      return false;
    }
    File databaseFile = new File(databasePath);
    if (!databaseFile.exists()) {
      return false;
    }
    return true;
  }

  public static ArrayList randomList(ArrayList sourceList) {
    ArrayList newList = new ArrayList();
    int size;
    while ((size = sourceList.size()) > 0) {
      Random random = new Random();
      int index = random.nextInt(size);
      newList.add(sourceList.remove(index));
    }
    return newList;
  }

  public static void openURL(String url) {
    String osName = System.getProperty("os.name");
    try {
      if (osName.startsWith("Mac OS")) {
        Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
        Method openURL = fileMgr.getDeclaredMethod("openURL",
            new Class[]{String.class});
        openURL.invoke(null, new Object[]{url});
      } else if (osName.startsWith("Windows")) {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
      } else { //assume Unix or Linux
        boolean found = false;
        for (String browser : browsers) {
          if (!found) {
            found = Runtime.getRuntime().exec(
                new String[]{"which", browser}).waitFor() == 0;
            if (found) {
              Runtime.getRuntime().exec(new String[]{browser, url});
            }
          }
        }
        if (!found) {
          throw new Exception(Arrays.toString(browsers));
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null,
          "Error attempting to launch web browser\n" + e.toString());
    }
  }
}
