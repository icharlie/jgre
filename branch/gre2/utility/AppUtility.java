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
package jgre.utility;

import java.lang.reflect.Method;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * The utility used to support the common method in each classes for basic needs.
 * @author digman543
 */
public class AppUtility {

  static final String[] browsers = {"firefox", "opera", "konqueror", "epiphany",
    "seamonkey", "galeon", "kazehakase", "mozilla", "netscape"};

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
