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
 * JGREApp.java
 * 
 */
package jgre.view;

import javax.swing.JFrame;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import util.Util;

/**
 * 
 * @author digma543
 */
public class JGREApp extends SingleFrameApplication {

  static Logger log = Logger.getRootLogger();

  @Override
  protected void startup() {
    JFrame view = new JGREView();
    Util.setCenter(view);
    show(view);
  }

  @Override
  protected void initialize(String[] args) {
    super.initialize(args);
    String osName = System.getProperty("os.name");
    if (osName.startsWith("Mac OS")) {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "jGRE");
    }
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    log.setLevel(Level.ALL);
    Application.launch(JGREApp.class, args);
  }
}
