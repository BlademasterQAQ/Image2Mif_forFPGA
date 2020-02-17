package com.blademaster;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * һ��ͼƬתmif��д��ROM�����ļ���ת������
 * @date 2020.2.15
 * @author blademaster
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private List<File> files;
	private List<Integer> images_width = new LinkedList<>();//��ʼ��java.util.List����
	private List<Integer> images_height = new LinkedList<>();
	//��ǰ�ļ�����
	private BufferedImage currentBufferedImage;
	private BufferedImage compressed_bufferedImage;
	private int currentImageCount;//��ǰ��ͼƬ�ǵڼ���
	
	//��ӦDialog�Ķ���
	public JButton buttonMifDialogOK;//ֻ��JButton��doClick����
	private int currentProgress;
	private int totalMifByte;
	
	//����Dialog����Ϣ
	private String mifInformation;
	
	private mifDialog dialog;
	
	private JTextField txtProductedByBlademaster;
	private JTextField textField_photoCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 465);
		setTitle("ͼƬ���ŵ�ɫmifת����");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button_mif = new JButton("\u8F93\u51FAmif\u6587\u4EF6");
		button_mif.setBounds(571, 387, 105, 29);
		contentPane.add(button_mif);
		
		JButton button_photo = new JButton("\u8F93\u51FA\u9884\u89C8\u56FE");
		button_photo.setBounds(571, 348, 105, 29);
		contentPane.add(button_photo);
		
		JPanel panel = new JPanel();
		panel.setBounds(573, 77, 113, 80);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSpinner spinner_width = new JSpinner();
		spinner_width.setBounds(51, 0, 62, 22);
		panel.add(spinner_width);
		
		JSpinner spinner_height = new JSpinner();
		spinner_height.setBounds(51, 32, 62, 22);
		panel.add(spinner_height);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(10, -2, 41, 24);
		panel.add(textArea_1);
		textArea_1.setText("\u5BBD\u5EA6\uFF1A");
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setText("\u9AD8\u5EA6\uFF1A");
		textArea_2.setBounds(10, 30, 41, 24);
		panel.add(textArea_2);
		
		JCheckBox checkBox_sameScale = new JCheckBox("\u7B49\u6BD4\u4F8B\u7F29\u653E");
		checkBox_sameScale.setSelected(true);
		checkBox_sameScale.setBounds(10, 60, 92, 23);
		panel.add(checkBox_sameScale);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(43, 21, 616, 35);
		contentPane.add(textArea);
		textArea.setEditable(false);
		textArea.setText("\u8BF7\u62D6\u5165\u9700\u8981\u8F6C\u4E3Amif\u6587\u4EF6\u7684\u56FE\u7247\u6587\u4EF6");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(25, 94, 499, 248);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton button_preview = new JButton("\u9884\u89C8");
		button_preview.setBounds(585, 296, 79, 29);
		contentPane.add(button_preview);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(553, 198, 155, 89);
		contentPane.add(panel_2);
		
		JSpinner spinner_redBit = new JSpinner();
		spinner_redBit.setBounds(35, 0, 42, 22);
		panel_2.add(spinner_redBit);
		
		JSpinner spinner_greenBit = new JSpinner();
		spinner_greenBit.setBounds(35, 32, 42, 22);
		panel_2.add(spinner_greenBit);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setText("\u7EA2\uFF1A");
		textArea_3.setEditable(false);
		textArea_3.setBounds(0, 0, 31, 22);
		panel_2.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setText("\u7EFF\uFF1A");
		textArea_4.setEditable(false);
		textArea_4.setBounds(0, 32, 31, 22);
		panel_2.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setText("\u84DD\uFF1A");
		textArea_5.setEditable(false);
		textArea_5.setBounds(0, 64, 31, 24);
		panel_2.add(textArea_5);
		
		JSpinner spinner_blueBit = new JSpinner();
		spinner_blueBit.setBounds(35, 66, 42, 22);
		panel_2.add(spinner_blueBit);
		
		JTextArea textArea_redNum = new JTextArea();
		textArea_redNum.setEditable(false);
		textArea_redNum.setText("0");
		textArea_redNum.setBounds(103, 0, 31, 22);
		panel_2.add(textArea_redNum);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setEditable(false);
		textArea_8.setText("\u8272");
		textArea_8.setBounds(137, 0, 18, 22);
		panel_2.add(textArea_8);
		
		JTextArea textArea_greenNum = new JTextArea();
		textArea_greenNum.setText("0");
		textArea_greenNum.setEditable(false);
		textArea_greenNum.setBounds(103, 32, 31, 22);
		panel_2.add(textArea_greenNum);
		
		JTextArea textArea_9 = new JTextArea();
		textArea_9.setEditable(false);
		textArea_9.setText("\u8272");
		textArea_9.setBounds(137, 32, 18, 22);
		panel_2.add(textArea_9);
		
		JTextArea textArea_blueNum = new JTextArea();
		textArea_blueNum.setText("0");
		textArea_blueNum.setEditable(false);
		textArea_blueNum.setBounds(103, 66, 31, 22);
		panel_2.add(textArea_blueNum);
		
		JTextArea textArea_11 = new JTextArea();
		textArea_11.setEditable(false);
		textArea_11.setText("\u8272");
		textArea_11.setBounds(137, 66, 18, 22);
		panel_2.add(textArea_11);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setText("\u4F4D");
		textArea_7.setEditable(false);
		textArea_7.setBounds(78, 0, 18, 22);
		panel_2.add(textArea_7);
		
		JTextArea textArea_10 = new JTextArea();
		textArea_10.setText("\u4F4D");
		textArea_10.setEditable(false);
		textArea_10.setBounds(78, 32, 18, 22);
		panel_2.add(textArea_10);
		
		JTextArea textArea_12 = new JTextArea();
		textArea_12.setText("\u4F4D");
		textArea_12.setEditable(false);
		textArea_12.setBounds(78, 66, 18, 22);
		panel_2.add(textArea_12);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setText("\u989C\u8272\u4F4D\u6570");
		textArea_6.setEditable(false);
		textArea_6.setBounds(603, 167, 56, 21);
		contentPane.add(textArea_6);
		
		txtProductedByBlademaster = new JTextField();
		txtProductedByBlademaster.setEditable(false);
		txtProductedByBlademaster.setBackground(SystemColor.control);
		txtProductedByBlademaster.setHorizontalAlignment(SwingConstants.CENTER);
		txtProductedByBlademaster.setText("Producted by blademaster");
		txtProductedByBlademaster.setBounds(194, 394, 164, 24);
		contentPane.add(txtProductedByBlademaster);
		txtProductedByBlademaster.setColumns(10);
		
		JTextArea textArea_13 = new JTextArea();
		textArea_13.setEditable(false);
		textArea_13.setBackground(SystemColor.control);
		textArea_13.setText("\u9884\u89C8");
		textArea_13.setBounds(383, 73, 31, 24);
		contentPane.add(textArea_13);
		
		JTextArea textArea_14 = new JTextArea();
		textArea_14.setText("\u539F\u56FE");
		textArea_14.setEditable(false);
		textArea_14.setBackground(SystemColor.menu);
		textArea_14.setBounds(132, 73, 31, 24);
		contentPane.add(textArea_14);
		
		textField_photoCount = new JTextField();
		textField_photoCount.setText("0/0");
		textField_photoCount.setBackground(Color.WHITE);
		textField_photoCount.setEditable(false);
		textField_photoCount.setHorizontalAlignment(SwingConstants.CENTER);
		textField_photoCount.setBounds(255, 66, 46, 21);
		contentPane.add(textField_photoCount);
		textField_photoCount.setColumns(10);
		
		JButton button_front = new JButton("<<\u4E0A\u4E00\u5F20");
		button_front.setBounds(25, 62, 89, 29);
		button_front.setVisible(false);
		contentPane.add(button_front);
		
		JButton button_next = new JButton("\u4E0B\u4E00\u5F20>>");
		button_next.setBounds(435, 63, 89, 29);
		button_next.setVisible(false);
		contentPane.add(button_next);
		
		//ͼ���
		JLabel label_before = new JLabel("");
		label_before.setBounds(10, 10, 229, 229);
		panel_1.add(label_before);
		label_before.setForeground(Color.BLACK);
		label_before.setBackground(Color.WHITE);
		
		JLabel label_after = new JLabel("");
		label_after.setBounds(260, 10, 229, 229);
		panel_1.add(label_after);
		label_after.setForeground(Color.BLACK);
		label_after.setBackground(Color.WHITE);
		
		//label_before.setIcon(imageIcon_before);//��Ч
		//label_after.setIcon(imageIcon_after);//��Ч
		
		textArea.setTransferHandler(new TransferHandler() {//�����ļ��϶���ȡ·��
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			@Override
			public boolean importData(JComponent comp, Transferable t) {
				// TODO �Զ����ɵķ������
				try {
					files  = (java.util.List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);//ֻ��List���Ϳ��Գɹ�ǿ��ת���ļ���
					button_front.setVisible(false);
                	button_next.setVisible(false);
					if(files.size() > 1) {
                    	button_next.setVisible(true);
                    }
					textField_photoCount.setText(1 + "/" + files.size());
					
                    String string = "";
                    images_width.clear();//���List
                    images_height.clear();
                    for(File f:files) {
                    	string = string + f.getPath() + "\n";
                    	Image image = ImageIO.read(f);
                    	images_width.add(image.getWidth(null));
                    	images_height.add(image.getHeight(null));
                    }
                    textArea.setText(string);
                    currentBufferedImage = ImageIO.read(files.get(0));
                    label_after.setIcon(null);
                    setSameScaleFullImageForJLabel(label_before, currentBufferedImage);//��ʾͼƬ
                    currentImageCount = 0;//��ʾ��һ��ͼ
                    spinner_width.setValue(images_width.get(currentImageCount));
                    spinner_height.setValue(images_height.get(currentImageCount));
                    return true;
                }
                catch (Exception e) {
                	
                }
                return false;
			}
			@Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                        return true;
                    }
                }
                return false;
            }
		});
		
		//���UI
		
		spinner_width.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {//��spinner�ڵ���ֵ���ͱ仯ʱִ��
				// TODO �Զ����ɵķ������
				if((int)spinner_width.getValue() > images_width.get(currentImageCount) || images_width.get(currentImageCount) == 0) {//��ֹ����ͼƬԭʼ��С
					spinner_width.setValue(images_width.get(currentImageCount));
				}else if ((int)spinner_width.getValue() < 1) {
					spinner_width.setValue(1);
				}else if(checkBox_sameScale.isSelected()) {
					spinner_height.setValue(Math.round((int)spinner_width.getValue() * (float)images_height.get(currentImageCount) / images_width.get(currentImageCount)));
				}
				//�����޸ĵĿ����޸�ʱ���Զ�����spinner_height��ChangeListemer��
				images_width.set(currentImageCount, (Integer)spinner_width.getValue());
			}
		});
		
		spinner_height.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {//��spinner�ڵ���ֵ���ͱ仯ʱִ��
				// TODO �Զ����ɵķ������
				if((int)spinner_height.getValue() > images_height.get(currentImageCount) || images_height.get(currentImageCount) == 0) {//��ֹ����ͼƬԭʼ��С
					spinner_height.setValue(images_height.get(currentImageCount));
				}else if ((int)spinner_height.getValue() < 1) {
					spinner_height.setValue(1);
				}else if(checkBox_sameScale.isSelected()) {
					spinner_width.setValue(Math.round((int)spinner_height.getValue() * (float)images_width.get(currentImageCount) / images_height.get(currentImageCount)));
				}
				//�����޸ĵĿ��
				images_height.set(currentImageCount, (Integer)spinner_height.getValue());
			}
		});
		
		//��ɫUI
		
		spinner_redBit.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				textArea_redNum.setText("" + Math.round((int)Math.pow(2, (int)spinner_redBit.getValue())));
				if((int)spinner_redBit.getValue() < 0) {//��ֹ����ͼƬԭʼ��С
					spinner_redBit.setValue(0);
				}
			}
		});
		
		spinner_greenBit.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				textArea_greenNum.setText("" + Math.round((int)Math.pow(2, (int)spinner_greenBit.getValue())));
			}
		});
		
		spinner_blueBit.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				textArea_blueNum.setText("" + Math.round((int)Math.pow(2, (int)spinner_blueBit.getValue())));
			}
		});

		spinner_redBit.setValue(2);
		spinner_greenBit.setValue(3);
		spinner_blueBit.setValue(3);
		
		txtProductedByBlademaster.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 1) {//Ϊ���ʱ
					try {
						Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler www.github.com/BlademasterQAQ");
					} catch (IOException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}
			}
		});
		
		//ǰ��ͼ�л�
		button_front.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				currentImageCount--;
				button_next.setVisible(true);//��ǰһ��˵������ض���ͼ
				if(currentImageCount == 0) {
					button_front.setVisible(false);
				}
				//���õ�ǰͼ����
                spinner_width.setValue(images_width.get(currentImageCount));
                spinner_height.setValue(images_height.get(currentImageCount));
                
                textField_photoCount.setText((currentImageCount + 1) + "/" + files.size());
                //���Ԥ��ͼ�����õ�ǰͼ��
                label_after.setIcon(null);
                try {
                	currentBufferedImage = ImageIO.read(files.get(currentImageCount));
					setSameScaleFullImageForJLabel(label_before, currentBufferedImage);
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		
		button_next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				currentImageCount++;
				button_front.setVisible(true);//�ܺ�һ��˵��ǰ��ض���ͼ
				if(currentImageCount == files.size() - 1) {
					button_next.setVisible(false);
				}
				//���õ�ǰͼ����
                spinner_width.setValue(images_width.get(currentImageCount));
                spinner_height.setValue(images_height.get(currentImageCount));
                
                textField_photoCount.setText((currentImageCount + 1) + "/" + files.size());
                //���Ԥ��ͼ�����õ�ǰͼ��
                label_after.setIcon(null);
                try {
                	currentBufferedImage = ImageIO.read(files.get(currentImageCount));
					setSameScaleFullImageForJLabel(label_before, currentBufferedImage);
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		
		//Ԥ��ͼ
		
		button_preview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				compressed_bufferedImage = getCompressedThumbnail(currentBufferedImage, (int)spinner_width.getValue(), (int)spinner_height.getValue(), Image.SCALE_SMOOTH);//ѹ����С10X10����UIָ��
				for(int i=0;i < compressed_bufferedImage.getHeight();i++) {//����
					for(int j=0;j < compressed_bufferedImage.getWidth();j++) {//����
						Color color = new Color(compressed_bufferedImage.getRGB(j, i));//������Ļɨ�裬��ɨһ�к�ɨһ�У�iΪ����
						int compressed_red = colorCompressReturnInt(color.getRed(), (int)spinner_redBit.getValue());
						int compressed_green = colorCompressReturnInt(color.getGreen(), (int)spinner_greenBit.getValue());
						int compressed_blue = colorCompressReturnInt(color.getBlue(), (int)spinner_blueBit.getValue());
						color = new Color(compressed_red, compressed_green, compressed_blue);
						compressed_bufferedImage.setRGB(j, i, color.getRGB());//����Ϊѹ�������ɫ
					}
				}
				setSameScaleFullImageForJLabel(label_after, compressed_bufferedImage);
			}
		});
		
		//���Ԥ��ͼ
		button_photo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				button_preview.doClick();
				try {
					ImageIO.write(compressed_bufferedImage, "png", new File(files.get(0).getPath().split("\\.")[0] + "_thumbnail.png"));
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		
		button_mif.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if(files!=null && !files.isEmpty() && currentBufferedImage != null) {
					try {
						dialog = new mifDialog(MainFrame.this);
						dialog.setLocationRelativeTo(MainFrame.this);//�ڸ���Main.this��������ʾ
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		buttonMifDialogOK = new JButton();
		buttonMifDialogOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				try {
					int color_bytesCount = ((int)spinner_redBit.getValue() + (int)spinner_greenBit.getValue() + (int)spinner_blueBit.getValue()) / 8;//ÿ����ɫ��ռ�ı�����
					totalMifByte = 0;
					currentProgress = 0;
					int currentByte = 0;
					mifInformation = "";
					for(int f=0;f<files.size();f++) {
						totalMifByte += images_width.get(f) * images_height.get(f);
					}
					//��������ʼ��mif�ļ�
					File file = new File(files.get(0).getPath().split("\\.")[0] + ".mif");
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
					BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
					bufferedWriter.write("WIDTH=8;\n");
					bufferedWriter.write("DEPTH=" + (totalMifByte * color_bytesCount) + ";\n");//���Դ洢��bitλ��
					bufferedWriter.newLine();
					bufferedWriter.write("ADDRESS_RADIX=UNS;\n");
					bufferedWriter.write("DATA_RADIX=BIN;\n");
					bufferedWriter.newLine();
					bufferedWriter.write("CONTENT BEGIN\n");
					
					//����ͼƬ����
					for(int f=0;f<files.size();f++) {
						//ͼƬѹ��
						BufferedImage compressed_bufferedImage = getCompressedThumbnail(currentBufferedImage, images_width.get(f), images_height.get(f), Image.SCALE_SMOOTH);//ѹ����С10X10����UIָ��
						
						while(dialog.getRBG_type() == null || dialog.getRBG_type() == "") {
						}
						//���к��ж�ȡ��ɫ
						for(int i=0;i < compressed_bufferedImage.getHeight();i++) {//����
							for(int j=0;j < compressed_bufferedImage.getWidth();j++) {//����
								Color color = new Color(compressed_bufferedImage.getRGB(j, i));//������Ļɨ�裬��ɨһ�к�ɨһ�У�iΪ����
								String compressed_color = "";
								for(int k=0;k<3;k++) {
									if(dialog.getRBG_type().charAt(k) == 'R') {
										compressed_color = compressed_color + colorCompressReturnBit(color.getRed(), (int)spinner_redBit.getValue());
									}else if (dialog.getRBG_type().charAt(k) == 'G') {
										compressed_color = compressed_color + colorCompressReturnBit(color.getGreen(), (int)spinner_greenBit.getValue());
									}else {
										compressed_color = compressed_color + colorCompressReturnBit(color.getBlue(), (int)spinner_blueBit.getValue());
									}
								}
								bufferedWriter.write("\t" + (i * compressed_bufferedImage.getWidth() + j + currentByte) + "\t:\t" + compressed_color + ";\n");
							}
							currentProgress += compressed_bufferedImage.getWidth();
						}
						mifInformation = mifInformation + currentByte;
						currentByte += compressed_bufferedImage.getWidth() * compressed_bufferedImage.getHeight();
						mifInformation = mifInformation + " ~ " + (currentByte - 1) + " : " + files.get(f) + "\n";
					}
					
					bufferedWriter.write("END;\n");
					bufferedWriter.close();
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		
		
	}
	/**
	 * {@code color}Ϊ��/��/����ɫ��intֵ��0~255����
	 * {@code compressed_length}Ϊѹ�������ɫ��λ������Ϊ2ʱ��ɫ��2λ
	 * ������ɣ�һ��ֻ��2^2=4����ɫ
	 * 
	 * @param color
	 * @param compressed_length
	 * @return ��ɫ��bitֵ
	 */
	static private String colorCompressReturnBit(int color, int compressed_length) {
		double interval = 255/(Math.pow(2, compressed_length) - 1);//���=�ܾ���/2^compressed_length
		double area = color/interval;
		int compressed_color = (int)Math.round(area);//area���������Ϊѹ�����ʮ����
		return "0".repeat(compressed_length - Integer.toBinaryString(compressed_color).length()) + Integer.toBinaryString(compressed_color);
	}
	
	/**
	 * {@code color}Ϊ��/��/����ɫ��intֵ��0~255����
	 * {@code compressed_length}Ϊѹ�������ɫ��λ������Ϊ2ʱ��ɫ��2λ
	 * ������ɣ�һ��ֻ��2^2=4����ɫ
	 * 
	 * @param color
	 * @param compressed_length
	 * @return ��ɫ��0~255��Χ��һ���ֽڣ���intֵ
	 */
	static private int colorCompressReturnInt(int color, int compressed_length) {
		double interval = 255/(Math.pow(2, compressed_length) - 1);//���=�ܾ���/2^compressed_length
		double area = color/interval;
		int compressed_color = (int)Math.round(area);//area���������Ϊѹ�����ʮ����
		return (int) (compressed_color * interval);
	}
	
	/**
	 * ����ѹ��Ϊ���Ϊ{@code compressed_width}���߶�Ϊ
	 * {@code compressed_height}ѹ�����BufferedImage����
	 * @param bufferedImage :ѹ��ǰ��ͼƬ
	 * @param compressed_width
	 * @param compressed_height
	 * @param hints :BufferedImage.getScaledInstance�Ĳ�����ͨ��Image.SCALE_ѡ��ѹ����ʽ
	 * @return
	 */
	static private BufferedImage getCompressedThumbnail(BufferedImage bufferedImage, int compressed_width, int compressed_height, int hints) {
		if(hints == 0) {
			hints = Image.SCALE_DEFAULT;
		}
		Image compressed_image = bufferedImage.getScaledInstance(compressed_width, compressed_height, hints);
		BufferedImage compressed_bufferedImage = new BufferedImage(compressed_width, compressed_height, bufferedImage.getType());
		compressed_bufferedImage.createGraphics().drawImage(compressed_image, 0, 0, null);//BufferedImage��Ҫͨ��Graphics�������Image���������
		
		return compressed_bufferedImage;
	}
	/**
	 * ΪjLabel���õȱ�����С��image
	 * @param jLabel
	 * @param image
	 */
	static private void setSameScaleFullImageForJLabel(JLabel jLabel, Image image) {
		float rate = image.getWidth(null) / (float)image.getHeight(null);//����int�ĳ����ڼ����ϵõ��Ļ���int�����۸�ֵ����ʲô����
		if(rate > jLabel.getWidth() / jLabel.getHeight()) {//��ϴ�Ӧ��image.getWidth(null)����jLabel.getWidth()���߶Ȱ���������
			image = image.getScaledInstance(jLabel.getWidth(), Math.round(jLabel.getWidth() / rate), Image.SCALE_SMOOTH);
		}else {
			image = image.getScaledInstance(Math.round(jLabel.getHeight() * rate), jLabel.getHeight(), Image.SCALE_SMOOTH);
		}
		jLabel.setIcon(new ImageIcon(image));
		jLabel.setHorizontalAlignment(JLabel.CENTER);//ˮƽ����
		jLabel.setVerticalAlignment(JLabel.CENTER);//��ֱ����
	}
	
	//����Ϣ��Dialog
	public int getCurrentProgress() {
		return currentProgress;
	}
	public int getTotalMifByte() {
		return totalMifByte;
	}
	public String getMifInformation() {
		return mifInformation;
	}
}
