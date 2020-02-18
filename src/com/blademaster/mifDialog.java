package com.blademaster;

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
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class mifDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea_information;
	private JCheckBox checkBox_useFirstInformation;
	
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
		setBounds(100, 100, 363, 261);
		setTitle("输出mif");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JTextArea txtrRgb = new JTextArea();
			txtrRgb.setEditable(false);
			txtrRgb.setBounds(34, 23, 101, 24);
			txtrRgb.setText("RGB\u7684\u6392\u5217\u65B9\u5F0F\uFF1A");
			txtrRgb.setBackground(SystemColor.menu);
			contentPanel.add(txtrRgb);
		}
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(135, 23, 52, 23);
		contentPanel.add(comboBox);
		
		JProgressBar progressBar = new JProgressBar();		
		progressBar.setBounds(47, 165, 252, 16);
		contentPanel.add(progressBar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 82, 252, 73);
		contentPanel.add(scrollPane);
		{
			textArea_information = new JTextArea();
			textArea_information.setEditable(false);
			scrollPane.setViewportView(textArea_information);
		}
		{
			JTextArea txtrMif = new JTextArea();
			txtrMif.setText("mif\u6587\u4EF6\u4FE1\u606F");
			txtrMif.setEditable(false);
			txtrMif.setBackground(SystemColor.menu);
			txtrMif.setBounds(137, 56, 75, 24);
			contentPanel.add(txtrMif);
		}
		
		checkBox_useFirstInformation = new JCheckBox("");
		checkBox_useFirstInformation.setBounds(208, 26, 21, 21);
		contentPanel.add(checkBox_useFirstInformation);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.control);
		textArea.setText("\u7EDF\u4E00\u4F7F\u7528\u7B2C\u4E00\n \u5F20\u56FE\u7684\u5BBD\u9AD8");
		textArea.setBounds(229, 15, 82, 44);
		contentPanel.add(textArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u751F\u6210mif\u6587\u4EF6");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {//生成mif
						progressBar.setStringPainted(true);//显示进度（默认）/自定义字符
						progressBar.setString(null);//清空文字
						RBG_type = (String) comboBox.getSelectedItem();

						okButton.setEnabled(false);
						synchronized (mainframe.lock_MifDialogOK) {
							mainframe.lock_MifDialogOK.notify();//解锁
						}

//						while (mainframe.getTotalMifByte() == 0 || mainframe.getCurrentProgress() == 0) {
//							if (progressBar.getValue() < 99) {
//								progressBar.setValue(1 + progressBar.getValue());
//								try {
//									Thread.sleep(50);
//								} catch (InterruptedException e1) {
//									// TODO 自动生成的 catch 块
//									e1.printStackTrace();
//								}
//							}
//						}
						// progressBar.setMaximum(mainframe.getTotalMifByte());//达到百分之百的数值，通常为文件的大小等
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO 自动生成的方法存根
								while (!mainframe.isMifComplete()) {
									int getCurrentProgress = mainframe.getCurrentProgress();
									int getTotalMifByte = mainframe.getTotalMifByte();
									if(getTotalMifByte == 0) break;//防止分母为0抛出ArithmeticException
									//TODO 删除System.err.println会出现死循环的问题
									System.err.println("");
									progressBar.setValue(getCurrentProgress * 100 / getTotalMifByte);
								}
								progressBar.setValue(progressBar.getMaximum());
								progressBar.setString("转换成功");
								textArea_information.setText(mainframe.getMifInformation());
								textArea_information.setCaretPosition(0);// 滚动条置顶，即让光标置于textArea的开头
								mainframe.setMifComplete(false);
								okButton.setEnabled(true);
							}
						}).start();

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

	public boolean is_checkBox_useFirstInformation_Selected() {
		return checkBox_useFirstInformation.isSelected();
	}
}
