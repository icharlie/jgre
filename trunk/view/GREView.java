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
 * GREView.java
 */
package gre.view;

import gre.database.AccessDataBaseTool;
import gre.database.DataBaseTool;
import gre.utility.Configure;
import gre.utility.Utility;
import gre.utility.VocabularyEnum;
import gre.utility.Voice;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.swingworker.SwingWorker;

/**
 * The application's main frame.
 */
public class GREView extends FrameView {

	public GREView(SingleFrameApplication app) {
		super(app);
		initComponents();
	}

	@Action
	public void showAboutBox() {
		if (aboutBox == null) {
			JFrame mainFrame = GREApp.getApplication().getMainFrame();
			aboutBox = new GREAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		GREApp.getApplication().show(aboutBox);
	}

	@Action
	public void showConfigurationView() {
		if (configurationBox == null) {
			JFrame mainFrame = GREApp.getApplication().getMainFrame();
			configurationBox = new GREConfigurationBox(mainFrame);
			configurationBox.setLocationRelativeTo(mainFrame);
		}
		GREApp.getApplication().show(configurationBox);
		upDatevocabularyUnit();
		upDateExamUnits();
		Configure configure = Configure.getSingleConfigure();
		if (configure.getElement(Configure.VOICE).equals("")) {
			voiceCheckbox.setEnabled(false);
			voiceCheckbox.setSelected(false);
		} else {
			voiceCheckbox.setEnabled(true);
		}
	}

	@Action
	public void showMaintainUnitBox() {
		if (maintainUnitBox == null) {
			JFrame mainFrame = GREApp.getApplication().getMainFrame();
			maintainUnitBox = new GREMaintainUnitBox(mainFrame);
			maintainUnitBox.setLocationRelativeTo(mainFrame);
		}
		GREApp.getApplication().show(maintainUnitBox);
		upDatevocabularyUnit();
		upDateExamUnits();
	}

	/**
	 * After updating or setting database, other componenets have to call this
	 * method
	 */
	protected void upDatevocabularyUnit() {
		JComboBox reviewUnitComboBox = reviewPanel.getUnitComboBox();
		unitCombobox.removeAllItems();
		reviewUnitComboBox.removeAllItems();
		if (Utility.isDatabaseExist()) {
			for (String unit : databaseTool.getVocabularyUnits()) {
				unitCombobox.addItem(unit);
				reviewUnitComboBox.addItem(unit);
			}
		}
		unitCombobox.repaint();
		reviewUnitComboBox.repaint();
	}

	@Action
	protected void upDateExamUnits() {
		examinationalPanel.upDateExamUnits();
		searchExaminationPanel.upDateExamUnits();
	}

	private void checkDigit(KeyEvent evt) throws NumberFormatException,
			HeadlessException {
		char key = evt.getKeyChar();
		JTextField textField = (JTextField) evt.getSource();
		if ((key >= KeyEvent.VK_A && key <= KeyEvent.VK_Z)
				|| (key >= 97 && key <= 122)) {
			// 檢查是否為數字
			textField.setText("4");
			JOptionPane.showMessageDialog(this.getFrame(), "請輸入1~10的數字", "注意！",
					JOptionPane.WARNING_MESSAGE);
		}
		if (!("".equals(textField.getText()))) {
			int value = Integer.valueOf(textField.getText());
			if (value > 10 || value < 1) {
				textField.setText("4");
				JOptionPane.showMessageDialog(this.getFrame(), "請輸入1~10的數字",
						"注意！", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		bindingGroup = new BindingGroup();

		mainPanel = new JPanel();
		statusPanel = new JPanel();
		statusMessageLabel = new JLabel();
		activeTabPanel = new JTabbedPane();
		memoPanel = new JPanel();
		controlPanel = new JPanel();
		verbalSpeedSlider = new JSlider();
		explainSpeedSlider = new JSlider();
		verbalSpeedText = new JTextField();
		explainSpeedText = new JTextField();
		verbalSpeedLabel = new JLabel();
		explainSpeedLabel = new JLabel();
		unitCombobox = new JComboBox();
		playControlPanel = new JPanel();
		manualCheckbox = new JCheckBox();
		randomCheckBox = new JCheckBox();
		voiceCheckbox = new JCheckBox();
		repeatCheckBox = new JCheckBox();
		explainInTimeCheckBox = new JCheckBox();
		forgetCheckBox = new JCheckBox();
		upperComboBox = new JCheckBox();
		startSpinner = new JSpinner();
		contactLabel = new JLabel();
		endSpinner = new JSpinner();
		buttonPanel = new JPanel();
		startButton = new JButton();
		pauseButton = new JButton();
		nextButton = new JButton();
		stopButton = new JButton();
		forgetButton = new JButton();
		showVerbalPanel = new JPanel();
		showExplainLabel = new JLabel();
		verbalLabel = new JLabel();
		explainLabel = new JLabel();
		showVerbalLabel = new JLabel();
		manualPanel = new JPanel();
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu();
		JMenuItem exitMenuItem = new JMenuItem();
		toolMenu = new JMenu();
		environmentMenuItem = new JMenuItem();
		maintainUnitMenuItem = new JMenuItem();
		showPlayControlPanelMenuItem = new JCheckBoxMenuItem();
		JMenu helpMenu = new JMenu();
		JMenuItem aboutMenuItem = new JMenuItem();

		mainPanel.setMaximumSize(new Dimension(800, 600));
		mainPanel.setMinimumSize(new Dimension(700, 400));
		mainPanel.setName("mainPanel"); // NOI18N
		mainPanel.setPreferredSize(new Dimension(700, 450));
		mainPanel.setLayout(new BorderLayout());

		statusPanel.setName("statusPanel"); // NOI18N
		statusPanel.setPreferredSize(new Dimension(100, 25));

		ResourceMap resourceMap = Application.getInstance(GREApp.class)
				.getContext().getResourceMap(GREView.class);
		statusMessageLabel.setFont(resourceMap
				.getFont("statusMessageLabel.font")); // NOI18N
		statusMessageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusMessageLabel.setName("statusMessageLabel"); // NOI18N

		GroupLayout statusPanelLayout = new GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);
		statusPanelLayout.setHorizontalGroup(statusPanelLayout
				.createParallelGroup(GroupLayout.LEADING).add(
						GroupLayout.TRAILING,
						statusPanelLayout.createSequentialGroup()
								.addContainerGap(667, Short.MAX_VALUE).add(
										statusMessageLabel,
										GroupLayout.PREFERRED_SIZE, 92,
										GroupLayout.PREFERRED_SIZE).add(11, 11,
										11)));
		statusPanelLayout.setVerticalGroup(statusPanelLayout
				.createParallelGroup(GroupLayout.LEADING).add(
						statusPanelLayout.createSequentialGroup().add(
								statusMessageLabel, GroupLayout.PREFERRED_SIZE,
								18, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		mainPanel.add(statusPanel, BorderLayout.SOUTH);

		activeTabPanel.setFont(resourceMap.getFont("activeTabPanel.font")); // NOI18N
		activeTabPanel.setName("activeTabPanel"); // NOI18N
		activeTabPanel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				activeTabPanelStateChanged(evt);
			}
		});

		memoPanel.setFont(resourceMap.getFont("memoPanel.font")); // NOI18N
		memoPanel.setMaximumSize(new Dimension(800, 600));
		memoPanel.setMinimumSize(new Dimension(700, 450));
		memoPanel.setName("memoPanel"); // NOI18N

		controlPanel.setName("controlPanel"); // NOI18N
		controlPanel.setPreferredSize(new Dimension(650, 155));

		verbalSpeedSlider.setMaximum(10);
		verbalSpeedSlider.setMinimum(1);
		verbalSpeedSlider.setValue(4);
		verbalSpeedSlider.setName("verbalSpeedSlider"); // NOI18N

		Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				manualCheckbox, ELProperty.create("${!selected}"),
				verbalSpeedSlider, BeanProperty.create("enabled"));
		bindingGroup.addBinding(binding);

		explainSpeedSlider.setMaximum(10);
		explainSpeedSlider.setMinimum(1);
		explainSpeedSlider.setValue(4);
		explainSpeedSlider.setName("explainSpeedSlider"); // NOI18N

		verbalSpeedText.setName("verbalSpeedText"); // NOI18N
		verbalSpeedText.setSelectionEnd(2);

		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				verbalSpeedSlider, ELProperty.create("${value}"),
				verbalSpeedText, BeanProperty.create("text"));
		bindingGroup.addBinding(binding);
		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				manualCheckbox, ELProperty.create("${!selected}"),
				verbalSpeedText, BeanProperty.create("enabled"));
		bindingGroup.addBinding(binding);

		verbalSpeedText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				verbalSpeedTextKeyReleased(evt);
			}
		});

		explainSpeedText.setName("explainSpeedText"); // NOI18N

		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				explainSpeedSlider, ELProperty.create("${value}"),
				explainSpeedText, BeanProperty.create("text"));
		bindingGroup.addBinding(binding);
		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				manualCheckbox, ELProperty.create("${!selected}"),
				explainSpeedText, BeanProperty.create("enabled"));
		bindingGroup.addBinding(binding);

		explainSpeedText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				explainSpeedTextKeyReleased(evt);
			}
		});

		verbalSpeedLabel.setFont(resourceMap.getFont("verbalSpeedLabel.font")); // NOI18N
		verbalSpeedLabel
				.setText(resourceMap.getString("verbalSpeedLabel.text")); // NOI18N
		verbalSpeedLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		verbalSpeedLabel.setName("verbalSpeedLabel"); // NOI18N

		explainSpeedLabel
				.setFont(resourceMap.getFont("explainSpeedLabel.font")); // NOI18N
		explainSpeedLabel.setText(resourceMap
				.getString("explainSpeedLabel.text")); // NOI18N
		explainSpeedLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		explainSpeedLabel.setName("explainSpeedLabel"); // NOI18N

		unitCombobox.setAutoscrolls(true);
		unitCombobox.setName("unitCombobox"); // NOI18N
		unitCombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				unitComboboxActionPerformed(evt);
			}
		});

		playControlPanel.setBorder(BorderFactory.createTitledBorder(resourceMap
				.getString("playControlPanel.border.title"))); // NOI18N
		playControlPanel.setFont(resourceMap.getFont("playControlPanel.font")); // NOI18N
		playControlPanel.setName("playControlPanel"); // NOI18N

		ActionMap actionMap = Application.getInstance(GREApp.class)
				.getContext().getActionMap(GREView.class, this);
		manualCheckbox.setAction(actionMap.get("setExplainSpeedState")); // NOI18N
		manualCheckbox.setFont(resourceMap.getFont("manualCheckbox.font")); // NOI18N
		manualCheckbox.setText(resourceMap.getString("manualCheckbox.text")); // NOI18N
		manualCheckbox.setHorizontalAlignment(SwingConstants.LEFT);
		manualCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
		manualCheckbox.setName("manualCheckbox"); // NOI18N
		playControlPanel.add(manualCheckbox);
		// manualCheckbox.setAction()

		randomCheckBox.setFont(resourceMap.getFont("randomCheckBox.font")); // NOI18N
		randomCheckBox.setText(resourceMap.getString("randomCheckBox.text")); // NOI18N
		randomCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		randomCheckBox.setHorizontalTextPosition(SwingConstants.RIGHT);
		randomCheckBox.setName("randomCheckBox"); // NOI18N
		playControlPanel.add(randomCheckBox);

		voiceCheckbox.setFont(resourceMap.getFont("voiceCheckbox.font")); // NOI18N
		voiceCheckbox.setText(resourceMap.getString("voiceCheckbox.text")); // NOI18N
		voiceCheckbox.setHorizontalAlignment(SwingConstants.LEFT);
		voiceCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
		voiceCheckbox.setName("voiceCheckbox"); // NOI18N
		Configure configure = Configure.getSingleConfigure();
		if (configure.getElement(Configure.VOICE).equals("")) {
			voiceCheckbox.setEnabled(false);
			voiceCheckbox.setSelected(false);
		}
		playControlPanel.add(voiceCheckbox);

		repeatCheckBox.setFont(resourceMap.getFont("repeatCheckBox.font")); // NOI18N
		repeatCheckBox.setText(resourceMap.getString("repeatCheckBox.text")); // NOI18N
		repeatCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		repeatCheckBox.setHorizontalTextPosition(SwingConstants.RIGHT);
		repeatCheckBox.setName("repeatCheckBox"); // NOI18N
		playControlPanel.add(repeatCheckBox);

		explainInTimeCheckBox.setAction(actionMap.get("setExplainSpeedState")); // NOI18N
		explainInTimeCheckBox.setFont(resourceMap
				.getFont("explainInTimeCheckBox.font")); // NOI18N
		explainInTimeCheckBox.setText(resourceMap
				.getString("explainInTimeCheckBox.text")); // NOI18N
		explainInTimeCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		explainInTimeCheckBox.setHorizontalTextPosition(SwingConstants.RIGHT);
		explainInTimeCheckBox.setName("explainInTimeCheckBox"); // NOI18N
		playControlPanel.add(explainInTimeCheckBox);

		forgetCheckBox.setFont(resourceMap.getFont("forgetfulCheckBox.font")); // NOI18N
		forgetCheckBox.setText(resourceMap.getString("forgetfulCheckBox.text")); // NOI18N
		forgetCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		forgetCheckBox.setHorizontalTextPosition(SwingConstants.RIGHT);
		forgetCheckBox.setName("forgetfulCheckBox"); // NOI18N
		setForgetfulCheckboxState();
		forgetCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				forgetCheckBoxActionPerformed(evt);
			}
		});
		playControlPanel.add(forgetCheckBox);

		upperComboBox.setFont(resourceMap.getFont("upperComboBox.font")); // NOI18N
		upperComboBox.setText(resourceMap.getString("upperComboBox.text")); // NOI18N
		upperComboBox.setHorizontalAlignment(SwingConstants.LEFT);
		upperComboBox.setHorizontalTextPosition(SwingConstants.RIGHT);
		upperComboBox.setName("upperComboBox"); // NOI18N
		playControlPanel.add(upperComboBox);

		startSpinner.setFont(resourceMap.getFont("startSpinner.font")); // NOI18N
		startSpinner.setName("startSpinner"); // NOI18N
		startSpinner.setValue(1);

		contactLabel.setFont(resourceMap.getFont("contactLabel.font")); // NOI18N
		contactLabel.setText(resourceMap.getString("contactLabel.text")); // NOI18N
		contactLabel.setName("contactLabel"); // NOI18N

		endSpinner.setName("endSpinner"); // NOI18N

		GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
		controlPanel.setLayout(controlPanelLayout);
		controlPanelLayout
				.setHorizontalGroup(controlPanelLayout
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								controlPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												controlPanelLayout
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																controlPanelLayout
																		.createSequentialGroup()
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.LEADING)
																						.add(
																								unitCombobox,
																								GroupLayout.PREFERRED_SIZE,
																								160,
																								GroupLayout.PREFERRED_SIZE)
																						.add(
																								controlPanelLayout
																										.createSequentialGroup()
																										.add(
																												startSpinner,
																												GroupLayout.PREFERRED_SIZE,
																												70,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.RELATED)
																										.add(
																												contactLabel)
																										.addPreferredGap(
																												LayoutStyle.RELATED)
																										.add(
																												endSpinner,
																												GroupLayout.PREFERRED_SIZE,
																												70,
																												GroupLayout.PREFERRED_SIZE)))
																		.add(
																				134,
																				134,
																				134)
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.LEADING)
																						.add(
																								explainSpeedLabel)
																						.add(
																								verbalSpeedLabel))
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.LEADING)
																						.add(
																								explainSpeedSlider,
																								GroupLayout.PREFERRED_SIZE,
																								134,
																								GroupLayout.PREFERRED_SIZE)
																						.add(
																								verbalSpeedSlider,
																								GroupLayout.PREFERRED_SIZE,
																								134,
																								GroupLayout.PREFERRED_SIZE))
																		.add(
																				18,
																				18,
																				18)
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.LEADING,
																								false)
																						.add(
																								verbalSpeedText,
																								GroupLayout.PREFERRED_SIZE,
																								30,
																								GroupLayout.PREFERRED_SIZE)
																						.add(
																								explainSpeedText,
																								GroupLayout.PREFERRED_SIZE,
																								30,
																								GroupLayout.PREFERRED_SIZE)))
														.add(
																playControlPanel,
																GroupLayout.PREFERRED_SIZE,
																628,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(81, Short.MAX_VALUE)));
		controlPanelLayout
				.setVerticalGroup(controlPanelLayout
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								GroupLayout.TRAILING,
								controlPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												controlPanelLayout
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																GroupLayout.TRAILING,
																controlPanelLayout
																		.createSequentialGroup()
																		.add(
																				verbalSpeedText,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.add(
																				9,
																				9,
																				9)
																		.add(
																				explainSpeedText,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.add(
																GroupLayout.TRAILING,
																controlPanelLayout
																		.createSequentialGroup()
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.TRAILING)
																						.add(
																								verbalSpeedLabel)
																						.add(
																								verbalSpeedSlider,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.LEADING)
																						.add(
																								explainSpeedSlider,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.add(
																								explainSpeedLabel)))
														.add(
																controlPanelLayout
																		.createSequentialGroup()
																		.add(
																				unitCombobox,
																				GroupLayout.PREFERRED_SIZE,
																				25,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				controlPanelLayout
																						.createParallelGroup(
																								GroupLayout.BASELINE)
																						.add(
																								startSpinner,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.add(
																								contactLabel)
																						.add(
																								endSpinner,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.add(
																				14,
																				14,
																				14)))
										.addPreferredGap(LayoutStyle.RELATED,
												24, Short.MAX_VALUE).add(
												playControlPanel,
												GroupLayout.PREFERRED_SIZE, 73,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				showPlayControlPanelMenuItem, ELProperty.create("${selected}"),
				playControlPanel, BeanProperty.create("visible"));
		bindingGroup.addBinding(binding);

		buttonPanel.setName("buttonPanel"); // NOI18N

		startButton.setText(resourceMap.getString("startButton.text")); // NOI18N
		startButton.setHorizontalAlignment(SwingConstants.LEFT);
		startButton.setName("startButton"); // NOI18N
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});

		pauseButton.setText(resourceMap.getString("pauseButton.text")); // NOI18N
		pauseButton.setActionCommand(resourceMap
				.getString("pauseButton.actionCommand")); // NOI18N
		pauseButton.setEnabled(false);
		pauseButton.setHorizontalAlignment(SwingConstants.LEFT);
		pauseButton.setName("pauseButton"); // NOI18N
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				pauseButtonActionPerformed(evt);
			}
		});

		nextButton.setText(resourceMap.getString("nextButton.text")); // NOI18N
		nextButton.setEnabled(false);
		nextButton.setHorizontalAlignment(SwingConstants.LEFT);
		nextButton.setName("nextButton"); // NOI18N
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nextButtonActionPerformed(evt);
			}
		});

		stopButton.setText(resourceMap.getString("stopButton.text")); // NOI18N
		stopButton.setEnabled(false);
		stopButton.setHorizontalAlignment(SwingConstants.LEFT);
		stopButton.setName("stopButton"); // NOI18N
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				stopButtonActionPerformed(evt);
			}
		});

		forgetButton.setText(resourceMap.getString("forgetButton.text")); // NOI18N
		forgetButton.setEnabled(false);
		forgetButton.setHorizontalAlignment(SwingConstants.LEFT);
		forgetButton.setName("forgetButton"); // NOI18N
		forgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				forgetButtonActionPerformed(evt);
			}
		});

		GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
		buttonPanel.setLayout(buttonPanelLayout);
		buttonPanelLayout.setHorizontalGroup(buttonPanelLayout
				.createParallelGroup(GroupLayout.LEADING).add(
						buttonPanelLayout.createSequentialGroup().add(127, 127,
								127).add(startButton).add(5, 5, 5).add(
								pauseButton).add(5, 5, 5).add(nextButton).add(
								5, 5, 5).add(stopButton).add(5, 5, 5).add(
								forgetButton).addContainerGap(178,
								Short.MAX_VALUE)));
		buttonPanelLayout.setVerticalGroup(buttonPanelLayout
				.createParallelGroup(GroupLayout.LEADING).add(
						buttonPanelLayout.createSequentialGroup().add(5, 5, 5)
								.add(
										buttonPanelLayout.createParallelGroup(
												GroupLayout.LEADING).add(
												startButton).add(pauseButton)
												.add(nextButton)
												.add(stopButton).add(
														forgetButton))));

		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				manualCheckbox, ELProperty.create("${!selected}"), pauseButton,
				BeanProperty.create("visible"));
		bindingGroup.addBinding(binding);
		binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				manualCheckbox, ELProperty.create("${selected}"), nextButton,
				BeanProperty.create("visible"));
		bindingGroup.addBinding(binding);

		showVerbalPanel.setName("showVerbalPanel"); // NOI18N

		showExplainLabel.setFont(resourceMap.getFont("explainLabel.font")); // NOI18N
		showExplainLabel.setHorizontalAlignment(SwingConstants.LEFT);
		showExplainLabel
				.setText(resourceMap.getString("showExplainLabel.text")); // NOI18N
		showExplainLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		showExplainLabel.setMaximumSize(new Dimension(92, 25));
		showExplainLabel.setMinimumSize(new Dimension(92, 25));
		showExplainLabel.setName("showExplainLabel"); // NOI18N
		showExplainLabel.setPreferredSize(new Dimension(92, 25));

		verbalLabel.setFont(resourceMap.getFont("explainLabel.font")); // NOI18N
		verbalLabel.setText(resourceMap.getString("verbalLabel.text")); // NOI18N
		verbalLabel.setMaximumSize(new Dimension(90, 25));
		verbalLabel.setMinimumSize(new Dimension(90, 25));
		verbalLabel.setName("verbalLabel"); // NOI18N
		verbalLabel.setPreferredSize(new Dimension(90, 25));

		explainLabel.setFont(resourceMap.getFont("explainLabel.font")); // NOI18N
		explainLabel.setText(resourceMap.getString("explainLabel.text")); // NOI18N
		explainLabel.setMaximumSize(new Dimension(90, 25));
		explainLabel.setMinimumSize(new Dimension(90, 25));
		explainLabel.setName("explainLabel"); // NOI18N
		explainLabel.setPreferredSize(new Dimension(90, 25));

		showVerbalLabel.setFont(resourceMap.getFont("explainLabel.font")); // NOI18N
		showVerbalLabel.setHorizontalAlignment(SwingConstants.LEFT);
		showVerbalLabel.setText(resourceMap.getString("showVerbalLabel.text")); // NOI18N
		showVerbalLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		showVerbalLabel.setName("showVerbalLabel"); // NOI18N

		GroupLayout showVerbalPanelLayout = new GroupLayout(showVerbalPanel);
		showVerbalPanel.setLayout(showVerbalPanelLayout);
		showVerbalPanelLayout.setHorizontalGroup(showVerbalPanelLayout
				.createParallelGroup(GroupLayout.LEADING).add(
						GroupLayout.TRAILING,
						showVerbalPanelLayout.createSequentialGroup().add(30,
								30, 30).add(
								showVerbalPanelLayout.createParallelGroup(
										GroupLayout.LEADING).add(verbalLabel,
										GroupLayout.PREFERRED_SIZE, 115,
										GroupLayout.PREFERRED_SIZE).add(
										explainLabel, GroupLayout.DEFAULT_SIZE,
										141, Short.MAX_VALUE)).addPreferredGap(
								LayoutStyle.RELATED).add(
								showVerbalPanelLayout.createParallelGroup(
										GroupLayout.TRAILING).add(
										showExplainLabel,
										GroupLayout.DEFAULT_SIZE, 530,
										Short.MAX_VALUE).add(showVerbalLabel,
										GroupLayout.DEFAULT_SIZE, 530,
										Short.MAX_VALUE)).add(20, 20, 20)));
		showVerbalPanelLayout.setVerticalGroup(showVerbalPanelLayout
				.createParallelGroup(GroupLayout.LEADING).add(
						showVerbalPanelLayout.createSequentialGroup().add(
								showVerbalPanelLayout.createParallelGroup(
										GroupLayout.BASELINE).add(
										showVerbalLabel,
										GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE).add(
										verbalLabel,
										GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE)).add(45,
								45, 45).add(
								showVerbalPanelLayout.createParallelGroup(
										GroupLayout.BASELINE).add(explainLabel,
										GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE).add(
										showExplainLabel,
										GroupLayout.PREFERRED_SIZE, 45,
										GroupLayout.PREFERRED_SIZE))
								.addContainerGap(60, Short.MAX_VALUE)));

		manualPanel.setName("manualPanel"); // NOI18N
		manualPanel.setOpaque(false);

		GroupLayout memoPanelLayout = new GroupLayout(memoPanel);
		memoPanel.setLayout(memoPanelLayout);
		memoPanelLayout
				.setHorizontalGroup(memoPanelLayout
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								memoPanelLayout
										.createSequentialGroup()
										.add(
												memoPanelLayout
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																memoPanelLayout
																		.createSequentialGroup()
																		.add(
																				219,
																				219,
																				219)
																		.add(
																				manualPanel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.add(
																controlPanel,
																GroupLayout.DEFAULT_SIZE,
																729,
																Short.MAX_VALUE)
														.add(
																buttonPanel,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																showVerbalPanel,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		memoPanelLayout.setVerticalGroup(memoPanelLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				memoPanelLayout.createSequentialGroup().add(controlPanel,
						GroupLayout.PREFERRED_SIZE, 208,
						GroupLayout.PREFERRED_SIZE).add(18, 18, 18).add(
						showVerbalPanel, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED).add(manualPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.RELATED, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).add(buttonPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).add(30, 30, 30)));

		activeTabPanel.addTab(resourceMap
				.getString("memoPanel.TabConstraints.tabTitle"), memoPanel); // NOI18N
		reviewPanel = new GREReviewPanel();
		activeTabPanel.addTab("單字複習", reviewPanel);
		searchPanel = new GRESearchPanel();
		activeTabPanel.addTab("單字查詢", searchPanel);
		examinationalPanel = new GREExaminationalPanel();
		activeTabPanel.addTab("測驗介面", examinationalPanel);
		searchExaminationPanel = new GRESearchExaminationPanel();
		activeTabPanel.addTab("測驗查詢", searchExaminationPanel);
		// unit
		upDatevocabularyUnit();

		mainPanel.add(activeTabPanel, BorderLayout.CENTER);

		menuBar.setName("menuBar"); // NOI18N

		fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
		fileMenu.setName("fileMenu"); // NOI18N

		exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
		exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
		exitMenuItem.setName("exitMenuItem"); // NOI18N
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		toolMenu.setText(resourceMap.getString("toolMenu.text")); // NOI18N
		toolMenu.setName("toolMenu"); // NOI18N

		environmentMenuItem.setAction(actionMap.get("showConfigurationView")); // NOI18N
		environmentMenuItem.setText(resourceMap
				.getString("environmentMenuItem.text")); // NOI18N
		environmentMenuItem.setName("environmentMenuItem"); // NOI18N
		toolMenu.add(environmentMenuItem);

		maintainUnitMenuItem.setAction(actionMap.get("showMaintainUnitBox")); // NOI18N
		maintainUnitMenuItem.setText(resourceMap
				.getString("maintainUnitMenuItem.text")); // NOI18N
		maintainUnitMenuItem.setName("maintainUnitMenuItem"); // NOI18N
		toolMenu.add(maintainUnitMenuItem);

		showPlayControlPanelMenuItem.setSelected(true);
		showPlayControlPanelMenuItem.setText(resourceMap
				.getString("showPlayControlPanelMenuItem.text")); // NOI18N
		showPlayControlPanelMenuItem.setName("showPlayControlPanelMenuItem"); // NOI18N
		showPlayControlPanelMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				showPlayControlPanelMenuItemActionPerformed(evt);
			}
		});
		toolMenu.add(showPlayControlPanelMenuItem);

		menuBar.add(toolMenu);

		helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
		helpMenu.setName("helpMenu"); // NOI18N

		aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
		aboutMenuItem.setName("aboutMenuItem"); // NOI18N
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		setComponent(mainPanel);
		setMenuBar(menuBar);
		setStatusBar(statusPanel);

		bindingGroup.bind();
	}// </editor-fold>//GEN-END:initComponents

	private void manulShowVerbal(int verbalIndex, int explainIndex) {
		String vocabulary = vocabularyList.get(verbalIndex)[VocabularyEnum.ENG_1
				.ordinal()];
		if (explainInTimeCheckBox.isSelected()) {
			showVerbalLabel.setText(vocabulary);
			showExplainLabel
					.setText(vocabularyList.get(explainIndex)[VocabularyEnum.CHINESE_1
							.ordinal()]);
			if (voiceCheckbox.isSelected()) {
				voice.play(vocabulary);
			}
		} else if (verbalIndex != explainIndex) {
			showVerbalLabel.setText(vocabulary);
			showExplainLabel.setText("");
			if (voiceCheckbox.isSelected()) {
				voice.play(vocabulary);
			}
		} else {
			showVerbalLabel
					.setText(vocabularyList.get(verbalIndex)[VocabularyEnum.ENG_1
							.ordinal()]);
			showExplainLabel
					.setText(vocabularyList.get(explainIndex)[VocabularyEnum.CHINESE_1
							.ordinal()]);
		}
		// 顯示目前已播放字彙數和字彙總量
		statusMessageLabel.setText(String.valueOf((verbalIndex + 1))
				.concat("/").concat(String.valueOf(vocabularyList.size())));
	}

	private void setForgetButtonText() {
		if (forgetCheckBox.isSelected()) {
			forgetButton.setText("記住了！");
		} else {
			forgetButton.setText("忘記了！");
		}
		vocabularyList = databaseTool.getAllVacabulary((String) unitCombobox
				.getSelectedItem(), forgetCheckBox.isSelected());
		endSpinner.setValue(vocabularyList.size());
	}

	private void stopButtonActionPerformed(ActionEvent evt) {// GEN-FIRST:event_stopButtonActionPerformed
		initialState();
	}// GEN-LAST:event_stopButtonActionPerformed

	private void pauseButtonActionPerformed(ActionEvent evt) {// GEN-FIRST:event_pauseButtonActionPerformed
		if ("pause".equals(evt.getActionCommand())) {
			pauseButton.setText("繼續");
			pauseButton.setActionCommand("continue");
			while (!vw.isCancelled()) {
				vw.cancel(true);
			}
			lastIndex = vw.getIndex();
		} else {
			pauseButton.setText("暫停");
			pauseButton.setActionCommand("pause");
			vw = new VerbalWorker(vocabularyList);
			vw.setIndex(lastIndex);
			vw.execute();
		}
	}// GEN-LAST:event_pauseButtonActionPerformed

	private void startButtonActionPerformed(ActionEvent evt) {// GEN-FIRST:event_startButtonActionPerformed
		// 設定控制選項狀態
		startButton.setEnabled(false);
		forgetButton.setEnabled(true);
		stopButton.setEnabled(true);
		unitCombobox.setEnabled(false);
		forgetCheckBox.setEnabled(false);
		randomCheckBox.setEnabled(false);
		manualCheckbox.setEnabled(false);
		explainInTimeCheckBox.setEnabled(false);
		repeatCheckBox.setEnabled(false);
		vocabularyList = databaseTool.getAllVacabulary((String) unitCombobox
				.getSelectedItem(), forgetCheckBox.isSelected());
		if (randomCheckBox.isSelected()) {
			vocabularyList = Utility.randomList(vocabularyList);

			int startIndex = Integer.parseInt(startSpinner.getValue()
					.toString()) - 1;
			if (manualCheckbox.isSelected()) {
				nextButton.setEnabled(true);
				if (explainInTimeCheckBox.isSelected()) {
					manulVerbalIndex = startIndex;
					manulExplainIndex = startIndex;
				} else {
					manulVerbalIndex = startIndex;
					manulExplainIndex = startIndex - 1;
				}
				manulShowVerbal(manulVerbalIndex, manulExplainIndex);
			} else {
				pauseButton.setEnabled(true);
			}
			vw = new VerbalWorker(vocabularyList);
			vw.setIndex(startIndex);
			vw.execute();
		}
	}// GEN-LAST:event_startButtonActionPerformed

	private void verbalSpeedTextKeyReleased(KeyEvent evt) {// GEN-FIRST:event_verbalSpeedTextKeyReleased
		checkDigit(evt);
	}// GEN-LAST:event_verbalSpeedTextKeyReleased

	private void explainSpeedTextKeyReleased(KeyEvent evt) {// GEN-FIRST:event_explainSpeedTextKeyReleased
		checkDigit(evt);
	}// GEN-LAST:event_explainSpeedTextKeyReleased

	private void nextButtonActionPerformed(ActionEvent evt) {// GEN-FIRST:event_nextButtonActionPerformed
		if (manulExplainIndex + 1 == vocabularyList.size()) {
			if (repeatCheckBox.isSelected()) {
				int startIndex = Integer.parseInt(startSpinner.getValue()
						.toString()) - 1;
				manulVerbalIndex = startIndex - 1;
				manulExplainIndex = startIndex - 1;
			} else {
				initialState();
				return;
			}
		}
		if (explainInTimeCheckBox.isSelected()) {
			manulVerbalIndex++;
			manulExplainIndex++;
			manulShowVerbal(manulVerbalIndex, manulExplainIndex);
			return;
		}
		if (manulExplainIndex < manulVerbalIndex) {
			manulExplainIndex++;
			manulShowVerbal(manulVerbalIndex, manulExplainIndex);
			return;
		}
		if (manulExplainIndex == manulVerbalIndex) {
			manulVerbalIndex++;
			manulShowVerbal(manulVerbalIndex, manulExplainIndex);
			return;
		}
	}// GEN-LAST:event_nextButtonActionPerformed

	private void forgetButtonActionPerformed(ActionEvent evt) {// GEN-FIRST:event_forgetButtonActionPerformed
		String memoState = forgetCheckBox.isSelected() ? "0" : "1";
		String word = null;
		for (int index = 0, end = vocabularyList.size(); index < end; index++) {
			String vocabulary = vocabularyList.get(index)[VocabularyEnum.ENG_1
					.ordinal()];
			if (vocabulary.equalsIgnoreCase(showVerbalLabel.getText())) {
				word = vocabulary;
				break;
			}
		}
		if (null != word) {
			databaseTool.updateVocabularyMemoState((String) unitCombobox
					.getSelectedItem(), word, memoState);
		}
	}// GEN-LAST:event_forgetButtonActionPerformed

	private void forgetCheckBoxActionPerformed(ActionEvent evt) {// GEN-FIRST:event_forgetCheckBoxActionPerformed
		setForgetButtonText();

	}// GEN-LAST:event_forgetCheckBoxActionPerformed

	private void unitComboboxActionPerformed(ActionEvent evt) {// GEN-FIRST:event_unitComboboxActionPerformed
		// 字彙播放物件
		vocabularyList = databaseTool.getAllVacabulary((String) unitCombobox
				.getSelectedItem(), forgetCheckBox.isSelected());
		endSpinner.setValue(vocabularyList.size());
		setForgetfulCheckboxState();
	}// GEN-LAST:event_unitComboboxActionPerformed

	private void showPlayControlPanelMenuItemActionPerformed(ActionEvent evt) {// GEN-FIRST:event_showPlayControlPanelMenuItemActionPerformed
	// playControlPanel.setVisible(showPlayControlPanel.isSelected() ? true :
	// false);
	}// GEN-LAST:event_showPlayControlPanelMenuItemActionPerformed

	private void activeTabPanelStateChanged(ChangeEvent evt) {// GEN-FIRST:event_activeTabPanelStateChanged
		if (activeTabPanel.getSelectedIndex() == 3) {
			examinationalPanel.setErrorCheckBoxState();
		}
	}// GEN-LAST:event_activeTabPanelStateChanged

	@Action
	public void setExplainSpeedState() {
		if (manualCheckbox.isSelected() || explainInTimeCheckBox.isSelected()) {
			explainSpeedSlider.setEnabled(false);
			explainSpeedText.setEnabled(false);
		} else {
			explainSpeedSlider.setEnabled(true);
			explainSpeedText.setEnabled(true);
		}
	}

	/**
	 * When user click stop button or it has done a circle of the unit, the
	 * method support to initialize the application state.
	 * 
	 */
	private void initialState() {
		// 設定控制選項狀態
		startButton.setEnabled(true);
		forgetButton.setEnabled(false);
		nextButton.setEnabled(false);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		unitCombobox.setEnabled(true);
		setForgetfulCheckboxState();
		setForgetButtonText();
		randomCheckBox.setEnabled(true);
		explainInTimeCheckBox.setEnabled(true);
		repeatCheckBox.setEnabled(true);
		manualCheckbox.setEnabled(true);
		showVerbalLabel.setText("");
		showExplainLabel.setText("");
		pauseButton.setText("暫停");
		statusMessageLabel.setText("");
		manulVerbalIndex = -1;
		manulExplainIndex = -1;
		lastIndex = 0;
		if (vw != null) {
			vw.cancel(true);
		}
	}

	/**
	 * set forgetbulCheckBox enable or unenable depend by this unit has
	 * forgetful vocabularies.
	 */
	private void setForgetfulCheckboxState() {
		boolean isEnable = databaseTool
				.hasForgetfulVocabulary((String) unitCombobox.getSelectedItem());
		forgetCheckBox.setEnabled(isEnable);
		if (!isEnable) {
			forgetCheckBox.setSelected(false);
		}
	}

	/**
	 * Get the unit combobox at View
	 */
	public JComboBox getUnitComboBox() {
		return unitCombobox;
	}

	/**
	 * 字彙播放物件 依據設定選項來播放字彙的物件
	 */
	class VerbalWorker extends SwingWorker {

		private int index = 0;
		private int total = 0;
		private boolean isFirstTime = true;
		private final ArrayList<String[]> vocabularyList;

		public VerbalWorker(final ArrayList newVocabularyList) {
			this.vocabularyList = newVocabularyList;
			this.total = newVocabularyList.size();
		}

		@Override
		protected Object doInBackground() throws Exception {
			while (!isCancelled()) {
				// First time not delay
				if (isFirstTime) {
					isFirstTime = false;
				} else {
					TimeUnit.SECONDS.sleep(verbalSpeedSlider.getValue());
				}

				// check range and repeat state
				if (index + 1 > this.vocabularyList.size()) {
					if (repeatCheckBox.isSelected()) {
						index = 0;
					} else {
						initialState();
						return null;
					}
				}

				// show the index of the word that was be showed and total
				// number 顯示目前已播放字彙數和字彙總量
				statusMessageLabel.setText(String.valueOf((index + 1)).concat(
						"/").concat(String.valueOf(total)));

				// clean previous display
				showVerbalLabel.setText("");
				showExplainLabel.setText("");

				// determine the word's display case, upper or natural case.
				String vocabulary = vocabularyList.get(index)[VocabularyEnum.ENG_1
						.ordinal()];
				if (upperComboBox.isSelected()) {
					vocabulary = vocabulary.toUpperCase();
				}
				// explain vocabulary
				showVerbalLabel.setText(vocabulary);

				// chechk explainLabel display immediate or not.
				if (explainInTimeCheckBox.isSelected()) {
					showExplainLabel
							.setText(vocabularyList.get(index)[VocabularyEnum.CHINESE_1
									.ordinal()]);
					// Text to speech
					if (voiceCheckbox.isSelected()) {
						voice.play(vocabulary);
					}
					// Move index to next
					index++;
				} else {
					// Text to speech
					if (voiceCheckbox.isSelected()) {
						voice.play(vocabulary);
					}
					// user interrupt after displaying verbal.
					if (!isCancelled()) {
						TimeUnit.SECONDS.sleep(explainSpeedSlider.getValue());
					}
					showExplainLabel
							.setText(vocabularyList.get(index)[VocabularyEnum.CHINESE_1
									.ordinal()]);
					// Move index to next
					index++;
				}
			}
			return null;
		}

		// set starting index
		public void setIndex(int initalIndex) {
			index = initalIndex;
		}

		// get current index
		public int getIndex() {
			return index;
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JTabbedPane activeTabPanel;
	private JPanel buttonPanel;
	private JLabel contactLabel;
	private JPanel controlPanel;
	private JSpinner endSpinner;
	private JMenuItem environmentMenuItem;
	private JCheckBox explainInTimeCheckBox;
	private JLabel explainLabel;
	private JLabel explainSpeedLabel;
	private JSlider explainSpeedSlider;
	private JTextField explainSpeedText;
	private JButton forgetButton;
	private JCheckBox forgetCheckBox;
	private JPanel mainPanel;
	private JMenuItem maintainUnitMenuItem;
	private JCheckBox manualCheckbox;
	private JPanel manualPanel;
	private JPanel memoPanel;
	private JMenuBar menuBar;
	private JButton nextButton;
	private JButton pauseButton;
	private JPanel playControlPanel;
	private JCheckBox randomCheckBox;
	private JCheckBox repeatCheckBox;
	private JLabel showExplainLabel;
	private JCheckBoxMenuItem showPlayControlPanelMenuItem;
	private JLabel showVerbalLabel;
	private JPanel showVerbalPanel;
	private JButton startButton;
	private JSpinner startSpinner;
	private JLabel statusMessageLabel;
	private JPanel statusPanel;
	private JButton stopButton;
	private JMenu toolMenu;
	private JComboBox unitCombobox;
	private JCheckBox upperComboBox;
	private JLabel verbalLabel;
	private JLabel verbalSpeedLabel;
	private JSlider verbalSpeedSlider;
	private JTextField verbalSpeedText;
	private JCheckBox voiceCheckbox;
	private BindingGroup bindingGroup;
	// End of variables declaration//GEN-END:variables
	// review panel
	private GREReviewPanel reviewPanel;
	private GRESearchPanel searchPanel;
	private GREExaminationalPanel examinationalPanel;
	private GRESearchExaminationPanel searchExaminationPanel;
	// Database Object
	private transient final DataBaseTool databaseTool = AccessDataBaseTool
			.getAccessDataBaseTool();
	private ArrayList<String[]> vocabularyList;
	// manual index
	private int manulVerbalIndex = -1;
	private int manulExplainIndex = -1;
	int lastIndex = 0;
	private Voice voice = Voice.getSingleVoice();
	// object about displaying
	private VerbalWorker vw;
	private JDialog aboutBox;
	private JDialog configurationBox;
	private JDialog maintainUnitBox;
}
