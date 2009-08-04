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
 * GREConfigurationBox.java
 */
package gre.view;

import gre.utility.Configure;
import gre.utility.Utility;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.application.Action;

/**
 * Use to set basic information that the application needs, like
 * the path of database, and the location of TTS directory, etc...
 * @author digman543
 */
public class GREConfigurationBox extends javax.swing.JDialog {

  private HashMap<String, String> elements;

  /** Creates new form GREConfigurationBox */
  public GREConfigurationBox(java.awt.Frame parent) {
    super(parent);
    elements = Configure.getSingleConfigure().getElements();
    initComponents();
    getRootPane().setDefaultButton(exitButton);
  }

  @Action
  public void closeConfiguration() {
    dispose();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    databaseLabel = new javax.swing.JLabel();
    databaseText = new javax.swing.JTextField();
    databaseButton = new javax.swing.JButton();
    voiceButton = new javax.swing.JButton();
    voiceText = new javax.swing.JTextField();
    voiceLabel = new javax.swing.JLabel();
    wordListButton = new javax.swing.JButton();
    wordListText = new javax.swing.JTextField();
    wordListLabel = new javax.swing.JLabel();
    saveButton = new javax.swing.JButton();
    exitButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getResourceMap(GREConfigurationBox.class);
    setTitle(resourceMap.getString("configuretionBox.title")); // NOI18N
    setModal(true);
    setName("configuretionBox"); // NOI18N
    setResizable(false);

    databaseLabel.setFont(resourceMap.getFont("databaseLabel.font")); // NOI18N
    databaseLabel.setText(resourceMap.getString("databaseLabel.text")); // NOI18N
    databaseLabel.setName("databaseLabel"); // NOI18N

    databaseText.setText(elements.get(Configure.DATABASE));
    databaseText.setName("databaseText"); // NOI18N

    databaseButton.setText(resourceMap.getString("databaseButton.text")); // NOI18N
    databaseButton.setName("databaseButton"); // NOI18N
    databaseButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        databaseButtonActionPerformed(evt);
      }
    });

    voiceButton.setText(resourceMap.getString("voiceButton.text")); // NOI18N
    voiceButton.setName("voiceButton"); // NOI18N
    voiceButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        voiceButtonActionPerformed(evt);
      }
    });

    voiceText.setText(elements.get(Configure.VOICE));
    voiceText.setName("voiceText"); // NOI18N

    voiceLabel.setFont(resourceMap.getFont("wordListLabel.font")); // NOI18N
    voiceLabel.setText(resourceMap.getString("voiceLabel.text")); // NOI18N
    voiceLabel.setName("voiceLabel"); // NOI18N

    wordListButton.setText(resourceMap.getString("wordListButton.text")); // NOI18N
    wordListButton.setName("wordListButton"); // NOI18N
    wordListButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        wordListButtonActionPerformed(evt);
      }
    });

    wordListText.setText(elements.get(Configure.WORDLIST));
    wordListText.setName("wordListText"); // NOI18N

    wordListLabel.setFont(resourceMap.getFont("wordListLabel.font")); // NOI18N
    wordListLabel.setText(resourceMap.getString("wordListLabel.text")); // NOI18N
    wordListLabel.setName("wordListLabel"); // NOI18N

    saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
    saveButton.setName("saveButton"); // NOI18N
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveButtonActionPerformed(evt);
      }
    });

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gre.view.GREApp.class).getContext().getActionMap(GREConfigurationBox.class, this);
    exitButton.setAction(actionMap.get("closeConfiguration")); // NOI18N
    exitButton.setText(resourceMap.getString("exitButton.text")); // NOI18N
    exitButton.setName("exitButton"); // NOI18N

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(wordListLabel)
              .add(voiceLabel)
              .add(databaseLabel))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(org.jdesktop.layout.GroupLayout.TRAILING, wordListText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
              .add(voiceText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
              .add(org.jdesktop.layout.GroupLayout.TRAILING, databaseText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(org.jdesktop.layout.GroupLayout.TRAILING, voiceButton)
              .add(org.jdesktop.layout.GroupLayout.TRAILING, databaseButton)
              .add(org.jdesktop.layout.GroupLayout.TRAILING, wordListButton)))
          .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
            .add(saveButton)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(exitButton)
            .add(162, 162, 162)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(23, 23, 23)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(databaseLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(databaseText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(databaseButton)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(voiceLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(voiceText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(voiceButton)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(wordListLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(wordListText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(wordListButton)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(saveButton)
          .add(exitButton))
        .addContainerGap(45, Short.MAX_VALUE))
    );

    databaseText.getAccessibleContext().setAccessibleName(resourceMap.getString("databaseText.AccessibleContext.accessibleName")); // NOI18N

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
      // 儲存設定檔
      elements = new HashMap<String, String>();
      elements.put(Configure.DATABASE, databaseText.getText());
      elements.put(Configure.VOICE, voiceText.getText());
      elements.put(Configure.WORDLIST, wordListText.getText());
      Configure.getSingleConfigure().setElements(elements);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void databaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseButtonActionPerformed
      JFileChooser fileChooser = new JFileChooser(Utility.getApplicationRootPath());
      fileChooser.setFileFilter(new FileFilter() {

        @Override
        public boolean accept(File file) {
          if (file.getName().matches(".*\\.mdb")) {
            return true;
          }
          return false;
        }

        @Override
        public String getDescription() {
          return "mdb";
        }
      });
      int returnVal = fileChooser.showOpenDialog(GREConfigurationBox.this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        try {
          databaseText.setText(fileChooser.getSelectedFile().getCanonicalPath());
        } catch (IOException ex) {
          System.err.println(ex.getMessage());
        }
      }

    }//GEN-LAST:event_databaseButtonActionPerformed

    private void voiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voiceButtonActionPerformed
      // 聲音檔路徑選取視窗
      JFileChooser fileChooser = new JFileChooser(Utility.getApplicationRootPath());
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = fileChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        voiceText.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
    }//GEN-LAST:event_voiceButtonActionPerformed

    private void wordListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordListButtonActionPerformed
      // wordList999檔案選取視窗
      JFileChooser fileChooser = new JFileChooser(Utility.getApplicationRootPath());
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnVal = fileChooser.showOpenDialog(GREConfigurationBox.this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        wordListText.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
    }//GEN-LAST:event_wordListButtonActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton databaseButton;
  private javax.swing.JLabel databaseLabel;
  private javax.swing.JTextField databaseText;
  private javax.swing.JButton exitButton;
  private javax.swing.JButton saveButton;
  private javax.swing.JButton voiceButton;
  private javax.swing.JLabel voiceLabel;
  private javax.swing.JTextField voiceText;
  private javax.swing.JButton wordListButton;
  private javax.swing.JLabel wordListLabel;
  private javax.swing.JTextField wordListText;
  // End of variables declaration//GEN-END:variables
}