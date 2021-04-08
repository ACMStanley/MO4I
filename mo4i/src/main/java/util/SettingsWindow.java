package util;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	Dimension size = new Dimension(480,360);
	
	public SettingsWindow(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		JLabel label = new JLabel("DSE json: ");
		panel.add(label);
		
		JTextField pathField = new JTextField();
		panel.add(pathField);
		
		JButton browseButton = new JButton("Browse");
		panel.add(browseButton);
		
		JFileChooser fileChooser = new JFileChooser();
		
		browseButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int fcResult = fileChooser.showOpenDialog(fileChooser);
					
					if(fcResult == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						pathField.setText(file.getAbsolutePath());
					}
				}
			}
		);
		
		add(panel);
		setSize(size);
		
		this.setVisible(true);
	}

	
}
