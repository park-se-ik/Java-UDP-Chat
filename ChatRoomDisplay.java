import java.awt.*;
import java.io.*;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

class ChatRoomDisplay extends JFrame implements ActionListener, KeyListener, ListSelectionListener, ChangeListener {
	private ClientThread chat_room_thread;
	private String idTo;
	private boolean isSelected;
	public boolean isAdmin;
	private ObjectOutputStream emoticon_out;
	private ObjectInputStream inputstream;
	private JLabel roomer;
	public JList roomerInfo;
	private JButton forceOut, sendWord, sendFile, quitRoom,emoticon;
	private Font font;
	private JViewport view;
	private JScrollPane jspane;
	public JTextArea messages;
	public JTextField message;
	Image kakao  = Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\kakaofriend.jpg");
	Image kakao1 = kakao.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	public ImageIcon Fkakao = new ImageIcon(kakao1);
	
	Image Skakao  = Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\kaka.png");
	Image Skakao1 = kakao.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	public ImageIcon SFkakao = new ImageIcon(Skakao1);
	
	public ChatRoomDisplay(ClientThread thread) {
		super("��ȭ��");

		chat_room_thread = thread;
		isSelected = false;
		isAdmin = false;
		font = new Font("����", Font.PLAIN, 12);

		Container c = getContentPane();
		c.setLayout(null);

		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBounds(425, 10, 140, 175);
		p.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "������"));

		roomerInfo = new JList();
		roomerInfo.setFont(font);
		JScrollPane jaspan2 = new JScrollPane(roomerInfo);
		roomerInfo.addListSelectionListener(this);
		jaspan2.setBounds(15, 25, 110, 135);
		p.add(jaspan2);

		c.add(p);

		p = new JPanel();
		p.setLayout(null);
		p.setBounds(10, 10, 410, 340);
		p.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "ä��â"));

		view = new JViewport();
		messages = new JTextArea();
		messages.setFont(font);
		messages.setEditable(false);
		view.add(messages);
		view.addChangeListener(this);
		jspane = new JScrollPane(view);
		jspane.setBounds(15, 25, 380, 270);
		p.add(jspane);

		message = new JTextField();
		message.setFont(font);
		message.addKeyListener(this);
		message.setBounds(15, 305, 380, 20);
		message.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		p.add(message);

		c.add(p);

		forceOut = new JButton("�� �� ");
		forceOut.setFont(font);
		forceOut.addActionListener(this);
		forceOut.setBounds(445, 195, 100, 30);
		forceOut.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		c.add(forceOut);

		sendWord = new JButton("�� �� �� ");
		sendWord.setFont(font);
		sendWord.addActionListener(this);
		sendWord.setBounds(445, 235, 100, 30);
		sendWord.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		c.add(sendWord);

		sendFile = new JButton("�� �� �� ��");
		sendFile.setFont(font);
		sendFile.addActionListener(this);
		sendFile.setBounds(445, 275, 100, 30);
		sendFile.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		c.add(sendFile);
		
		

		quitRoom = new JButton(" �� �� �� ");
		quitRoom.setFont(font);
		quitRoom.addActionListener(this);
		quitRoom.setBounds(445, 315, 100, 30);
		quitRoom.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		c.add(quitRoom);

		Dimension dim = getToolkit().getScreenSize();
		setSize(580, 430);
		setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
		show();

		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				message.requestFocusInWindow();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				chat_room_thread.requestQuitRoom();
			}
		});
	}

	public void resetComponents() {
		messages.setText("");
		message.setText("");
		message.requestFocusInWindow();
	}

	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
			String words = message.getText();
			String data;
			String idTo;
			if (words.startsWith("/w")) {
				StringTokenizer st = new StringTokenizer(words, " ");
				String command = st.nextToken();
				idTo = st.nextToken();
				data = st.nextToken();
				chat_room_thread.requestSendWordTo(data, idTo);
				message.setText("");
			} else {
				chat_room_thread.requestSendWord(words);
				message.requestFocusInWindow();
			}
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		isSelected = true;
		idTo = String.valueOf(((JList) e.getSource()).getSelectedValue());
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == forceOut) {
			if (!isAdmin) {
				JOptionPane.showMessageDialog(this, "������ �����ϴ�.", "��������", JOptionPane.ERROR_MESSAGE);
			} else if (!isSelected) {
				JOptionPane.showMessageDialog(this, "���� ���� ��ų �г��� : ", "��������", JOptionPane.ERROR_MESSAGE);
			} else {
				chat_room_thread.requestforceOut(idTo);
				isSelected = false;
			}
		} else if (ae.getSource() == quitRoom) {
			chat_room_thread.requestQuitRoom();
		} else if (ae.getSource() == sendWord) {
			String idTo, data;
			if ((idTo = JOptionPane.showInputDialog("���� �г��� : ")) != null) {
				if ((data = JOptionPane.showInputDialog("������ ���� : ")) != null) {
					chat_room_thread.requestSendWordTo(data, idTo);
				}
			}
		} else if (ae.getSource() == sendFile) {
			String idTo;
			if ((idTo = JOptionPane.showInputDialog("���� �г��� : ")) != null) {
				chat_room_thread.requestSendFile(idTo);
			}
		}else if(ae.getSource() == emoticon) {
			new Emoticon();
			String a;
			byte[] emo = new byte[10000];
			

			
			if(Emoticon.find) {
				if(Emoticon.getImg()==1) {
					
					//byte[] emo1 = extractBytes("��������/�̸�Ƽ��.png");  //�̹����� ����Ʈ�� ��ȯ
				
				}
				else if(Emoticon.getImg()==2) {
					//byte[] emo2 = extractBytes("��������/kaka.png");
				}
					
					
			}
				
				
		}
	}

	private byte[] extractBytes(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public void stateChanged(ChangeEvent e) {
		jspane.getVerticalScrollBar().setValue((jspane.getVerticalScrollBar().getValue() + 20));
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
