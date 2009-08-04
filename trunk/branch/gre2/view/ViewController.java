/*
 * 
 * Copyright 2009 digma543
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
 * ViewController.java
 * 
 */
package jgre.view;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.Util;

/**
 *
 * @author digma543
 */
public class ViewController {

  private static ViewController controller;
  private JFrame main;
  private HashMap<String, JPanel> subViews;
  private final String CURRECT = "currect";

  public static ViewController getInstance() {

    return controller;
  }

  public ViewController(JFrame frame) {
    this.main = frame;
    subViews = new HashMap<String, JPanel>();
    subViews.put(CURRECT, null);
  }

  public void registSubView(String viewName, JPanel panel) {
    if (!subViews.containsKey(viewName)) {
      subViews.put(viewName, panel);
    }
  }

  public void setFirstView(String viewName) {
    if (subViews.containsKey(viewName) && subViews.get(CURRECT) == null) {
      JPanel newView = subViews.get(viewName);
      subViews.put(CURRECT, newView);
      main.add(newView);
      main.pack();
    }
  }

  public void changeView(String viewName) {
    if (subViews.containsKey(viewName)) {
      JPanel newView = subViews.get(viewName);
      main.remove(subViews.get(CURRECT));
      subViews.put(CURRECT, newView);
      main.add(newView);
      main.pack();
      Util.setCenter(main);
    }
  }
}
