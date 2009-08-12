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
 * Voice.java
 */
package gre.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class is designed to play the wav file
 * @author digman543
 */
public class Voice {

  private static Voice singleVoice = null;
  private Configure configure;

  public static Voice getSingleVoice() {
    if (singleVoice == null) {
      singleVoice = new Voice();
    }
    return singleVoice;
  }

  private Voice() {
    this.configure = Configure.getSingleConfigure();
  }

  public void play(String word) {
    String fileName = null;
    Properties properties = new Properties();
    FileInputStream fi = null;
    try {
      fi = new FileInputStream(new File(configure.getElement(Configure.WORDLIST)));
      properties.load(fi);
      fileName = properties.getProperty(word.toLowerCase());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fi.close();
      } catch (IOException ex) {
        Logger.getLogger(Voice.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    if (fileName == null) {
      // TODO 找不到聲音檔時，使用freeTTS
      return;
    } else {
      String fistLetter = word.substring(0, 1);
      String[] elements = {configure.getElement(Configure.VOICE), fistLetter, fileName};
      String path = "";
      for (String element : elements) {
        path = path.concat(element).concat(File.separator);
      }
      File wavFile = new File(path);
      if (wavFile.exists()) {
        playSound(wavFile);
      }
    }
  }

  // manipulate the WAV file of the word
  private void playSound(File wavFile) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception ex) {
      Logger.getLogger(Voice.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
