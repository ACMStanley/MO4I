package util;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import algorithms.AlgorithmVariant;
import net.miginfocom.swing.MigLayout;
import smile.swing.FileChooser;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class ConfigWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public ConfigWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][grow][grow][grow][][][][][][][][][]", "[][][][][][][][][]"));
		
		lblNewLabel = new JLabel("DSE JSON Path:");
		contentPane.add(lblNewLabel, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		contentPane.add(textField, "cell 1 0 12 1,growx");
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse...");
		contentPane.add(btnNewButton, "cell 13 0");
		
		JLabel lblNewLabel_1 = new JLabel("Algorithm:");
		contentPane.add(lblNewLabel_1, "cell 0 1");
		
		comboBox= new JComboBox();
		contentPane.add(comboBox, "cell 1 1 8 1,growx");
		
		lblNewLabel_2 = new JLabel("");
		contentPane.add(lblNewLabel_2, "cell 0 2");
		
		btnNewButton_1 = new JButton("Run");
		contentPane.add(btnNewButton_1, "cell 0 8");
		
		for(AlgorithmVariant a:AlgorithmVariant.values()) {
			comboBox.addItem(a);
		}
		
		FileChooser fc = new FileChooser();
		
		btnNewButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int result = fc.showOpenDialog(fc);
					if(result == FileChooser.APPROVE_OPTION) {
						textField.setText(fc.getSelectedFile().getPath());
					}
				}
			}
		);
		
		btnNewButton_1.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(formFilled()) {
						DirectorySettings.MMJsonPath = textField.getText();
					}
				}
			}
		);
	}
	
	private boolean formFilled() {
		return textField.getText().trim() != "";
	}
	
	public JButton getRunButton() {
		return btnNewButton_1;
	}
	
	public RunConfig getConfig() {
		RunConfig out = new RunConfig();
		
		out.setMMJsonPath(textField.getText().trim());
		out.setAlgorithm((AlgorithmVariant) comboBox.getSelectedItem());
		
		return out;
	}

}
