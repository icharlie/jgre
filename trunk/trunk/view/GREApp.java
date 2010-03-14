/*
 * Copyright 2010 digman543
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
 * GREApp.java
 */
package gre.view;

import gre.utility.Configure;
import gre.utility.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import org.apache.log4j.PropertyConfigurator;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 * 
 * @author digman543
 */
public class GREApp extends SingleFrameApplication {

	/**
	 * At startup create and show the main frame of the application.
	 */
	@Override
	protected void startup() {
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS")) {
			try {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("apple.awt.brushMetalLoo", "true");
			} catch (Exception ex) {
				Logger.getLogger(GREApp.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
		show(new GREView(this));
	}

	@Override
	protected void initialize(String[] args) {
		super.initialize(args);
		String applicationPath = Utility.getApplicationRootPath();
		// 設定檔
		File configurationFile = new File(applicationPath
				.concat(Configure.CONFIGURE_FILE_NAME));
		File databaseFile = new File(applicationPath.concat("DB").concat(
				File.separator).concat("word.mdb"));
		File voiceDir = new File(applicationPath.concat("TTS").concat(
				File.separator));
		File wordListFile = new File(applicationPath.concat("TTS").concat(
				File.separator).concat("wordList999.txt"));

		if (!configurationFile.exists()) {
			Element jGRE = new Element("JGRE");
			// 資料庫資訊
			Element database = new Element(Configure.DATABASE);
			if (databaseFile.exists()) {
				try {
					database.appendChild(databaseFile.getCanonicalPath());
				} catch (IOException ex) {
					Logger.getLogger(GREApp.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
			Element voice = new Element(Configure.VOICE);
			if (voiceDir.exists()) {
				try {
					voice.appendChild(voiceDir.getCanonicalPath());
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
				}
			}
			Element wordList = new Element(Configure.WORDLIST);
			if (wordListFile.exists()) {
				try {
					wordList.appendChild(wordListFile.getCanonicalPath());
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
				}
			}
			jGRE.appendChild(database);
			jGRE.appendChild(voice);
			jGRE.appendChild(wordList);
			Document doc = new Document(jGRE);
			try {
				Serializer serializer = new Serializer(new FileOutputStream(
						configurationFile), "UTF8");
				serializer.setIndent(4);
				serializer.setMaxLength(60);
				serializer.write(doc);
				serializer.flush();
			} catch (Exception ex) {
				Logger.getLogger(GREApp.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	/**
	 * This method is to initialize the specified window by injecting resources.
	 * Windows shown in our application come fully initialized from the GUI
	 * builder, so this additional configuration is not needed.
	 */
	@Override
	protected void configureWindow(java.awt.Window root) {
	}

	/**
	 * A convenient static getter for the application instance.
	 * 
	 * @return the instance of GREApp
	 */
	public static GREApp getApplication() {
		return Application.getInstance(GREApp.class);
	}

	/**
	 * Main method launching the application.
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		launch(GREApp.class, args);
	}
}
