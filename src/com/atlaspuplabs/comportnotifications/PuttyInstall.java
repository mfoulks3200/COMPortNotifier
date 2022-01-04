package com.atlaspuplabs.comportnotifications;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Insets;

public class PuttyInstall extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public PuttyInstall() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Image putty = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("puttyIcon.png"));
		ImageIcon ico = new ImageIcon(putty.getScaledInstance(75, 75, Image.SCALE_SMOOTH));
		setTitle("Putty Detection");
		setType(Type.POPUP);
		setResizable(false);
		setSize(450, 231);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new MigLayout("fill, insets 0", "[434px]", "[150][33px]"));
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "cell 0 0,grow");
		contentPanel.setLayout(new MigLayout("", "[10:n][][25:n][grow][10:n]", "[][][grow][]"));
		{
			JLabel puttyIconLabel = new JLabel("");
			puttyIconLabel.setIcon(ico);
			contentPanel.add(puttyIconLabel, "cell 1 1 1 2");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Putty Not Detected");
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 24));
			contentPanel.add(lblNewLabel_1, "cell 3 1");
		}
		{
			JTextPane txtpnNoCurrentPutty = new JTextPane();
			txtpnNoCurrentPutty.setSelectionColor(Color.WHITE);
			txtpnNoCurrentPutty.setSelectedTextColor(Color.BLACK);
			txtpnNoCurrentPutty.setForeground(Color.BLACK);
			txtpnNoCurrentPutty.setEditable(false);
			txtpnNoCurrentPutty.setBorder(null);
			txtpnNoCurrentPutty.setBackground(Color.WHITE);
			txtpnNoCurrentPutty.setText("No current Putty installation was detected. Click the button below to open the Putty download site.\r\n\r\nIf Putty is already installed, ensure it has been added to the PATH variable.");
			contentPanel.add(txtpnNoCurrentPutty, "cell 3 2,grow");
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, "cell 0 1,growx,aligny top");
			buttonPane.setLayout(new MigLayout("", "[grow][109px][47px][1:n]", "[][29px][]"));
			{
				JButton downloadButton = new JButton("Download Putty");
				downloadButton.setMargin(new Insets(5, 14, 5, 14));
				downloadButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							Desktop.getDesktop().browse(new URI("https://www.putty.org/"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						dispose();
					}
					
				});
				buttonPane.add(downloadButton, "cell 1 1");
			}
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
