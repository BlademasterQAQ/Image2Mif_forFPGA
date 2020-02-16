package com.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mifDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	
	private String RBG_type;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			mifDialog dialog = new mifDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public mifDialog(MainFrame mainframe) {
		setBounds(100, 100, 360, 193);
		setTitle("���mif");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JTextArea txtrRgb = new JTextArea();
			txtrRgb.setEditable(false);
			txtrRgb.setBounds(81, 21, 101, 24);
			txtrRgb.setText("RGB\u7684\u6392\u5217\u65B9\u5F0F\uFF1A");
			txtrRgb.setBackground(SystemColor.menu);
			contentPanel.add(txtrRgb);
		}
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(191, 22, 52, 23);
		contentPanel.add(comboBox);
		
		JProgressBar progressBar = new JProgressBar();		
		progressBar.setBounds(43, 79, 252, 16);
		contentPanel.add(progressBar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u751F\u6210mif\u6587\u4EF6");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {//����mif
						progressBar.setStringPainted(true);//��ʾ���ȣ�Ĭ�ϣ�/�Զ����ַ�
						progressBar.setString("0");
						RBG_type = (String) comboBox.getSelectedItem();
						mainframe.buttonMifDialogOK.doClick();//ͨ�����ⰴ��������������mif�ļ�

						while (mainframe.getTotalMifSize() == 0) {
						}
						progressBar.setMaximum(mainframe.getTotalMifSize());//�ﵽ�ٷ�֮�ٵ���ֵ��ͨ��Ϊ�ļ��Ĵ�С��
						while (mainframe.getCurrentProgress() != mainframe.getTotalMifSize()) {
							progressBar.setValue(mainframe.getCurrentProgress());
						}
						progressBar.setString("ת���ɹ�");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mifDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		comboBox.addItem("RGB");
		comboBox.addItem("BGR");
		comboBox.addItem("RBG");
		comboBox.addItem("BRG");
		comboBox.addItem("GRB");
		comboBox.addItem("GBR");
		
	}
	
	public String getRBG_type() {
		return RBG_type;
	}
}
