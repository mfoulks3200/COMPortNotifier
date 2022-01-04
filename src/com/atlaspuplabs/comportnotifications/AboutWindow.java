package com.atlaspuplabs.comportnotifications;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class AboutWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public AboutWindow() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Image putty = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("puttyIcon.png"));
		ImageIcon ico = new ImageIcon(putty.getScaledInstance(75, 75, Image.SCALE_SMOOTH));
		setTitle("About COM Port Notifier");
		setType(Type.POPUP);
		setResizable(false);
		setSize(450, 307);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new MigLayout("fill, insets 0", "[grow]", "[150][::50]"));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "cell 0 0,grow");
		contentPanel.setLayout(new MigLayout("", "[10:n][grow][10:n]", "[5:n][][][grow]"));
		{
			JLabel lblNewLabel_1 = new JLabel("About COM Port Notifier");
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 24));
			contentPanel.add(lblNewLabel_1, "cell 1 1");
		}
		{
			JTextPane txtpnNoCurrentPutty = new JTextPane();
			txtpnNoCurrentPutty.setContentType("text/html");
			txtpnNoCurrentPutty.setSelectionColor(Color.WHITE);
			txtpnNoCurrentPutty.setSelectedTextColor(Color.BLACK);
			txtpnNoCurrentPutty.setForeground(Color.BLACK);
			txtpnNoCurrentPutty.setEditable(false);
			txtpnNoCurrentPutty.setBorder(null);
			txtpnNoCurrentPutty.setBackground(Color.WHITE);
			txtpnNoCurrentPutty.setText("<p style=\"font-family:'tahoma';font-size:11pt;\">COM Port Notifier v1.1.0.22.01.01<br />\r\nMatt Foulks Early 2022<br />\r\n<br />\r\n<br />\r\nOpen Source Project(s):<br />\r\n<br />\r\nJSerialComm <a href=\"https://github.com/Fazecast/jSerialComm\">https://github.com/Fazecast/jSerialComm</a><br />\r\n<a href=\"https://www.gnu.org/licenses/gpl-3.0.en.html\">GNU GENERAL PUBLIC LICENSE V3</a></p>");
			contentPanel.add(txtpnNoCurrentPutty, "cell 1 2,growx,aligny top");
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, "cell 0 1,growx,aligny top");
			buttonPane.setLayout(new MigLayout("", "[grow][109px][47px][1:n]", "[][29px][]"));
			{
				JButton okButton = new JButton("OK");
				okButton.setMargin(new Insets(5, 14, 5, 14));
				okButton.setActionCommand("Cancel");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
					
				});
				buttonPane.add(okButton, "cell 2 1,alignx left,aligny top");
				getRootPane().setDefaultButton(okButton);
			}
		}
		setVisible(true);
	}

}
