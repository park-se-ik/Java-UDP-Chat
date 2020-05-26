import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

class WaitRoomDisplay extends JFrame implements ActionListener, KeyListener, MouseListener, ChangeListener {
	private ClientThread chat_client_thread;
	private int room_Number;
	private String password, select;
	private boolean isLock, isSelected;

	private JLabel rooms, waiter, label;
	public JList roomInfo, waiterInfo;
	private JButton create, join, sendword, logout;
	private Font font;
	private JViewport view;
	private JScrollPane jspane;
	public JTextArea messages;
	public JTextField message;

	public WaitRoomDisplay(ClientThread thread) {
		super("����");

		chat_client_thread = thread;
		room_Number = 0;
		password = "0";
		isLock = false;
		isSelected = false;
		font = new Font("����", Font.PLAIN, 12);

		Container c = getContentPane();
		c.setLayout(null);

		rooms = new JLabel("��ȭ��");

		JPanel p = new JPanel();
		p.setLayout(null);
		p.setBounds(5, 10, 460, 215);
		p.setFont(font);
		p.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "��ȭ�� ���"));

		label = new JLabel("�� ȣ");
		label.setBounds(15, 25, 40, 20);
		label.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		label.setFont(font);
		p.add(label);

		label = new JLabel("�� ��");
		label.setBounds(55, 25, 210, 20);
		label.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		label.setFont(font);
		p.add(label);

		label = new JLabel("����/�ִ�");
		label.setBounds(265, 25, 60, 20);
		label.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		label.setFont(font);
		p.add(label);

		label = new JLabel("��������");
		label.setBounds(325, 25, 60, 20);
		label.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		label.setFont(font);
		p.add(label);

		label = new JLabel("�� �� ��");
		label.setBounds(385, 25, 58, 20);
		label.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		label.setFont(font);
		p.add(label);

		roomInfo = new JList();
		roomInfo.setFont(font);
		WaitListCellRenderer renderer = new WaitListCellRenderer();
		JScrollPane jspane1 = new JScrollPane(roomInfo);
		roomInfo.addMouseListener(this);
		renderer.setDefaultTab(20);
		renderer.setTabs(new int[] { 40, 265, 285, 315, 375, 430 });
		roomInfo.setCellRenderer(renderer);
		jspane1.setBounds(15, 45, 430, 155);
		p.add(jspane1);

		c.add(p);

		p = new JPanel();
		p.setLayout(null);
		p.setBounds(470, 10, 150, 215);
		p.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "�����"));

		waiterInfo = new JList();
		waiterInfo.setFont(font);
		JScrollPane jaspan2 = new JScrollPane(waiterInfo);
		jaspan2.setBounds(15, 25, 115, 175);
		p.add(jaspan2);

		c.add(p);

		p = new JPanel();
		p.setLayout(null);
		p.setBounds(5, 230, 450, 200);
		p.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "ä��â"));

		view = new JViewport();
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setFont(font);
		view.add(messages);
		view.addChangeListener(this);
		jspane = new JScrollPane(view);
		jspane.setBounds(15, 25, 430, 135);
		view.addChangeListener(this);
		p.add(jspane);

		view = (JViewport) jspane.getViewport().getView();
		view.addChangeListener(this);

		message = new JTextField();
		message.setFont(font);
		message.setBounds(15, 160, 430, 20);
		message.addKeyListener(this);
		message.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		p.add(message);

		c.add(p);

		create = new JButton("��ȭ�� ����");
		create.setFont(font);
		create.setBounds(500, 250, 100, 30);
		create.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		create.addActionListener(this);
		c.add(create);

		join = new JButton("��ȭ�� ����");
		join.setFont(font);
		join.setBounds(500, 290, 100, 30);
		join.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		join.addActionListener(this);
		c.add(join);

		sendword = new JButton("�� �� ��");
		sendword.setFont(font);
		sendword.setBounds(500, 330, 100, 30);
		sendword.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		sendword.addActionListener(this);
		c.add(sendword);

		logout = new JButton("�� �� �� ��");
		logout.setFont(font);
		logout.setBounds(500, 370, 100, 30);
		logout.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		logout.addActionListener(this);
		c.add(logout);

		Dimension dim = getToolkit().getScreenSize();
		setSize(640, 460);
		setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
		show();

		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				message.requestFocusInWindow();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				chat_client_thread.requestLogout();
			}
		});
	}

	public void resetComponents() {
		messages.setText("");
		message.setText("");
		room_Number = 0;
		password = "0";
		isLock = false;
		isSelected = false;
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
				chat_client_thread.requestSendWordTo(data, idTo);
				message.setText("");
			} else {
				chat_client_thread.requestSendWord(words);
				message.requestFocusInWindow();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		try {
			isSelected = true;
			String select = String.valueOf(((JList) e.getSource()).getSelectedValue());
			setSelectedRoomInfo(select);
		} catch (Exception err) {
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == create) {
			CreateRoomDisplay createRoom = new CreateRoomDisplay(this, chat_client_thread);
		} else if (ae.getSource() == join) {
			if (!isSelected) {
				JOptionPane.showMessageDialog(this, "�����ϰ� ���� �� ���� ", "��ȭ�� ����.", JOptionPane.ERROR_MESSAGE);
			} else if (isLock && password.equals("0")) {
				if ((password = JOptionPane.showInputDialog("��й�ȣ �Է�")) != null) {
					if (!password.equals("")) {
						chat_client_thread.requestEnterRoom(room_Number, password);
						password = "0";
					} else {
						password = "0";
						chat_client_thread.requestEnterRoom(room_Number, password);
					}
				} else {
					password = "0";
				}
			} else {
				chat_client_thread.requestEnterRoom(room_Number, password);
			}
		} else if (ae.getSource() == logout) {
			chat_client_thread.requestLogout();
		} else if (ae.getSource() == sendword) {
			String idTo, data;
			if ((idTo = JOptionPane.showInputDialog("�г��� �Է� : ")) != null) {
				if ((data = JOptionPane.showInputDialog("�޼��� �Է� :")) != null) {
					chat_client_thread.requestSendWordTo(data, idTo);
				}
			}
		}

	}

	private void setSelectedRoomInfo(String select) {
		StringTokenizer st = new StringTokenizer(select, "=");
		room_Number = Integer.parseInt(st.nextToken());
		String roomName = st.nextToken();
		int maxUser = Integer.parseInt(st.nextToken());
		int user = Integer.parseInt(st.nextToken());
		isLock = st.nextToken().equals("�����") ? true : false;
	}

	public void stateChanged(ChangeEvent e) {
		jspane.getVerticalScrollBar().setValue((jspane.getVerticalScrollBar().getValue() + 20));
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
