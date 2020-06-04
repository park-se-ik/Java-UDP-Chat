import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ClientThread extends Thread {
	private WaitRoomDisplay client_thread_waitRoom;
	private ChatRoomDisplay client_thread_chatRoom;
	private Socket client_thread_sock;
	private DataInputStream client_thread_in;
	private DataOutputStream client_thread_out;
	private ObjectOutputStream client_thread_imo_out;
	private ObjectInputStream client_thread_imo_in1;
	
	private StringBuffer client_thread_buffer;
	private Thread thisThread;
	private String client_thread_loginID;
	private int client_thread_room_Number;
	private static MessageBox msgBox, loginbox, fileTransBox;

	private static final int �α��ο�û = 1000;
	private static final int �α��μ��� = 1001;
	private static final int �α��ΰ��� = 1002;

	private static final int �������û = 1010;
	private static final int ��������� = 1011;
	private static final int ��������� = 1012;

	private static final int �������û = 1020;
	private static final int ��������� = 1021;
	private static final int ��������� = 1022;

	private static final int �������û = 1030;
	private static final int ��������� = 1031;

	private static final int �α׾ƿ���û = 1040;
	private static final int �α׾ƿ����� = 1041;

	private static final int �۽��ڿ�û = 1050;
	private static final int �۽��ڼ��� = 1051;

	private static final int �̸�Ƽ�ܼ��� = 1052;
	private static final int �̸�Ƽ�ܰ��� = 1053;

	
	private static final int �����ڿ�û = 1060;
	private static final int �����ڼ��� = 1061;
	private static final int �����ڰ��� = 1062;

	private static final int ���������û = 1070;
	private static final int ����������� = 1071;

	private static final int �������ۿ�û = 1080;
	private static final int �������ۼ��� = 1081;
	private static final int �������۰��� = 1082;

	private static final int ����ڼ��� = 2000;
	private static final int ������������� = 3000;
	private static final int �����ڼ��� = 4000;

	private static final int ����� = 9999;
	private static final int ������ȭ = 9998;
	private static final int �����ȭ = 9997;
	private static final int �氡���� = 9996;
	private static final int Ʋ����й�ȣ = 9995;
	private static final int �źε� = 9994;
	private static final int ����ھ��� = 9993;

	public ClientThread() {
		client_thread_waitRoom = new WaitRoomDisplay(this);
		client_thread_chatRoom = null;
		try {
			client_thread_sock = new Socket(InetAddress.getLocalHost(), 2777);
			client_thread_in = new DataInputStream(client_thread_sock.getInputStream());
			client_thread_out = new DataOutputStream(client_thread_sock.getOutputStream());
			client_thread_buffer = new StringBuffer(4096);
			thisThread = this;
		} catch (IOException e) {
			MessageBoxLess msgout = new MessageBoxLess(client_thread_waitRoom, "���ῡ��", "������ ������ �� �����ϴ�.");
			msgout.show();
		}
	}

	public ClientThread(String hostaddr) {
		client_thread_waitRoom = new WaitRoomDisplay(this);
		client_thread_chatRoom = null;
		try {
			client_thread_sock = new Socket(hostaddr, 2777);
			client_thread_in = new DataInputStream(client_thread_sock.getInputStream());
			client_thread_out = new DataOutputStream(client_thread_sock.getOutputStream());
			client_thread_buffer = new StringBuffer(4096);
			thisThread = this;
		} catch (IOException e) {
			MessageBoxLess msgout = new MessageBoxLess(client_thread_waitRoom, "���ῡ��", "������ ������ �� �����ϴ�.");
			msgout.show();
		}
	}

	public void run() {
		try {
			Thread currThread = Thread.currentThread();
			while (currThread == thisThread) {
				String recvData = client_thread_in.readUTF();
				StringTokenizer st = new StringTokenizer(recvData, " : ");
				int command = Integer.parseInt(st.nextToken());
				switch (command) {
				case �α��μ���: {
					loginbox.dispose();
					client_thread_room_Number = 0;
					try {
						StringTokenizer st1 = new StringTokenizer(st.nextToken(), "'");
						Vector roomInfo = new Vector();
						while (st1.hasMoreTokens()) {
							String temp = st1.nextToken();
							if (!temp.equals("empty")) {
								roomInfo.addElement(temp);
							}
						}
						client_thread_waitRoom.roomInfo.setListData(roomInfo);
						client_thread_waitRoom.message.requestFocusInWindow();
					} catch (NoSuchElementException e) {
						client_thread_waitRoom.message.requestFocusInWindow();
					}
					break;
				}
				case �α��ΰ���: {
					String id;
					int errCode = Integer.parseInt(st.nextToken());
					if (errCode == �����) {
						loginbox.dispose();
						JOptionPane.showMessageDialog(client_thread_waitRoom, "�̹� �ٸ� ����ڰ� �ֽ��ϴ�.", "�α���",
								JOptionPane.ERROR_MESSAGE);
						id = ChatClient.getloginID();
						requestlogin(id);
					} else if (errCode == ������ȭ) {
						loginbox.dispose();
						JOptionPane.showMessageDialog(client_thread_waitRoom, "��ȭ���� ���� á���ϴ�.", "�α���", JOptionPane.ERROR_MESSAGE);
						id = ChatClient.getloginID();
						requestlogin(id);
					}
					break;
				}
				case ����ڼ���: {
					StringTokenizer st1 = new StringTokenizer(st.nextToken(), "'");
					Vector user = new Vector();
					while (st1.hasMoreTokens()) {
						user.addElement(st1.nextToken());
					}
					client_thread_waitRoom.waiterInfo.setListData(user);
					client_thread_waitRoom.message.requestFocusInWindow();
					break;
				}
				case ���������: {
					client_thread_room_Number = Integer.parseInt(st.nextToken());
					client_thread_waitRoom.hide();
					if (client_thread_chatRoom == null) {
						client_thread_chatRoom = new ChatRoomDisplay(this);
						client_thread_chatRoom.isAdmin = true;
					} else {
						client_thread_chatRoom.show();
						client_thread_chatRoom.isAdmin = true;
						client_thread_chatRoom.resetComponents();
					}
					break;
				}
				case ���������: {
					int errCode = Integer.parseInt(st.nextToken());
					if (errCode == �����ȭ) {
						msgBox = new MessageBox(client_thread_waitRoom, "��ȭ�����", "�� �̻� ��ȭ���� ���� �� �� �����ϴ�.");
						msgBox.show();
					}
					break;
				}
				case �������������: {
					StringTokenizer st1 = new StringTokenizer(st.nextToken(), "'");
					StringTokenizer st2 = new StringTokenizer(st.nextToken(), "'");

					Vector rooms = new Vector();
					Vector users = new Vector();
					while (st1.hasMoreTokens()) {
						String temp = st1.nextToken();
						if (!temp.equals("empty")) {
							rooms.addElement(temp);
						}
					}
					client_thread_waitRoom.roomInfo.setListData(rooms);

					while (st2.hasMoreTokens()) {
						users.addElement(st2.nextToken());
					}

					client_thread_waitRoom.waiterInfo.setListData(users);
					client_thread_waitRoom.message.requestFocusInWindow();

					break;
				}
				case ���������: {
					client_thread_room_Number = Integer.parseInt(st.nextToken());
					String id = st.nextToken();
					client_thread_waitRoom.hide();
					if (client_thread_chatRoom == null) {
						client_thread_chatRoom = new ChatRoomDisplay(this);
					} else {
						client_thread_chatRoom.show();
						client_thread_chatRoom.resetComponents();
					}
					break;
				}
				case ���������: {
					int errCode = Integer.parseInt(st.nextToken());
					if (errCode == �氡����) {
						msgBox = new MessageBox(client_thread_waitRoom, "��ȭ������", "��ȭ���� ���� á���ϴ�.");
						msgBox.show();
					} else if (errCode == Ʋ����й�ȣ) {
						msgBox = new MessageBox(client_thread_waitRoom, "��ȭ������", "��й�ȣ�� Ʋ���ϴ�.");
						msgBox.show();
					}
					break;
				}
				case �����ڼ���: {
					String id = st.nextToken();
					int code = Integer.parseInt(st.nextToken());

					StringTokenizer st1 = new StringTokenizer(st.nextToken(), "'");
					Vector user = new Vector();
					while (st1.hasMoreTokens()) {
						user.addElement(st1.nextToken());
					}
					client_thread_chatRoom.roomerInfo.setListData(user);
					if (code == 1) {
						client_thread_chatRoom.messages.append("### " + id + "���� �����ϼ̽��ϴ�. ###\n");
					} else if (code == 2) {
						client_thread_chatRoom.messages.append("### " + id + "���� �������� �Ǿ����ϴ�. ###\n");
					} else {
						client_thread_chatRoom.messages.append("### " + id + "���� �����ϼ̽��ϴ�. ###\n");
					}
					client_thread_chatRoom.message.requestFocusInWindow();
					break;
				}
				case ���������: {
					String id = st.nextToken();
					if (client_thread_chatRoom.isAdmin)
						client_thread_chatRoom.isAdmin = false;
					client_thread_chatRoom.hide();
					client_thread_waitRoom.show();
					client_thread_waitRoom.resetComponents();
					client_thread_room_Number = 0;
					break;
				}
				case �α׾ƿ�����: {
					client_thread_waitRoom.dispose();
					if (client_thread_chatRoom != null) {
						client_thread_chatRoom.dispose();
					}
					release();
					break;
				}
				case �۽��ڼ���: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					try {
						String data = st.nextToken();
						if (room_Number == 0) {
							client_thread_waitRoom.messages.append(id + " : " + data + "\n");
							if (id.equals(client_thread_loginID)) {
								client_thread_waitRoom.message.setText("");
								client_thread_waitRoom.message.requestFocusInWindow();
							}
							client_thread_waitRoom.message.requestFocusInWindow();
						} else {
							client_thread_chatRoom.messages.append(id + " : " + data + "\n");
							if (id.equals(client_thread_loginID)) {
								client_thread_chatRoom.message.setText("");
							}
							client_thread_chatRoom.message.requestFocusInWindow();
						}

					} catch (NoSuchElementException e) {
						if (room_Number == 0)
							client_thread_waitRoom.message.requestFocusInWindow();
						else
							client_thread_chatRoom.message.requestFocusInWindow();
					}
					break;
				}
				case �̸�Ƽ�ܼ���: {//�ؾ��Ұ� 1.�޾Ƽ� ��Ʈ�� ���� ����Ʈ�� ��ȯ 2. ����Ʈ�� ���Ϸ� ��ȯ
					//3.textaread�� messages���ٰ� append
					String id = st.nextToken();
					String a = null;
					
					
					int room_Number = Integer.parseInt(st.nextToken());
					try {
						String data = st.nextToken();
						byte[] emo = new byte [(int)data.length()];
						Object AA = client_thread_imo_in1.readObject();
						
						emo = data.getBytes();
						
						client_thread_chatRoom.messages.append(id + " : " + AA + "\n");
						if (id.equals(client_thread_loginID)) {
							client_thread_chatRoom.message.setText("");
						}
						client_thread_chatRoom.message.requestFocusInWindow();
						

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case �����ڼ���: {
					String id = st.nextToken();
					String idTo = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					try {
						String data = st.nextToken();
						if (room_Number == 0) {
							if (id.equals(client_thread_loginID)) {
								client_thread_waitRoom.message.setText("");
								client_thread_waitRoom.messages.append("�ӼӸ�<to:" + idTo + "> : " + data + "\n");
							} else {
								client_thread_waitRoom.messages.append("�ӼӸ�<from:" + id + "> : " + data + "\n");
							}
							client_thread_waitRoom.message.requestFocusInWindow();
						} else {

							if (id.equals(client_thread_loginID)) {
								client_thread_chatRoom.message.setText("");
								client_thread_chatRoom.messages.append("�ӼӸ�<to:" + idTo + "> : " + data + "\n");
							} else {
								client_thread_chatRoom.messages.append("�ӼӸ�<from:" + id + "> : " + data + "\n");
							}
							client_thread_chatRoom.message.requestFocusInWindow();
						}
					} catch (NoSuchElementException e) {
						if (room_Number == 0)
							client_thread_waitRoom.message.requestFocusInWindow();
						else
							client_thread_chatRoom.message.requestFocusInWindow();
					}
					break;
				}
				case �����ڰ���: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					String message = "";
					if (room_Number == 0) {
						message = "���ǿ� " + id + "���� �������� �ʽ��ϴ�.";
						JOptionPane.showMessageDialog(client_thread_waitRoom, message, "�ӼӸ� ����", JOptionPane.ERROR_MESSAGE);
					} else {
						message = "�� ��ȭ�濡 " + id + "���� �������� �ʽ��ϴ�.";
						JOptionPane.showMessageDialog(client_thread_chatRoom, message, "�ӼӸ� ����", JOptionPane.ERROR_MESSAGE);
					}
					break;
				}
				case �������ۿ�û: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					String message = id + "�� ���� ���������� �����Ͻðڽ��ϱ�?";
					int value = JOptionPane.showConfirmDialog(client_thread_chatRoom, message, "���ϼ���", JOptionPane.YES_NO_OPTION);
					if (value == 1) {
						try {
							client_thread_buffer.setLength(0);
							client_thread_buffer.append(�������۰���);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(client_thread_loginID);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(room_Number);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(id);
							send(client_thread_buffer.toString());
						} catch (IOException e) {
							System.out.println(e);
						}
					} else {
						StringTokenizer addr = new StringTokenizer(InetAddress.getLocalHost().toString(), "/");
						String hostname = "";
						String hostaddr = "";

						hostname = addr.nextToken();
						try {
							hostaddr = addr.nextToken();
						} catch (NoSuchElementException err) {
							hostaddr = hostname;
						}

						try {
							client_thread_buffer.setLength(0);
							client_thread_buffer.append(�������ۼ���);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(client_thread_loginID);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(room_Number);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(id);
							client_thread_buffer.append(" : ");
							client_thread_buffer.append(hostaddr);
							send(client_thread_buffer.toString());
						} catch (IOException e) {
							System.out.println(e);
						}
						// ���� ���� ��������.
						new ReciveFile();
					}
					break;
				}
				case �������۰���: {
					int code = Integer.parseInt(st.nextToken());
					String id = st.nextToken();
					fileTransBox.dispose();

					if (code == �źε�) {
						String message = id + "���� ���ϼ����� �ź��Ͽ����ϴ�.";
						JOptionPane.showMessageDialog(client_thread_chatRoom, message, "��������", JOptionPane.ERROR_MESSAGE);
						break;
					} else if (code == ����ھ���) {
						String message = id + "���� �� �濡 �������� �ʽ��ϴ�.";
						JOptionPane.showMessageDialog(client_thread_chatRoom, message, "��������", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
				case �������ۼ���: {
					String id = st.nextToken();
					String addr = st.nextToken();

					fileTransBox.dispose();
					// ���� �۽� Ŭ���̾�Ʈ ����.
					new SendFile(addr);
					break;
				}
				case �����������: {
					client_thread_chatRoom.hide();
					client_thread_waitRoom.show();
					client_thread_waitRoom.resetComponents();
					client_thread_room_Number = 0;
					client_thread_waitRoom.messages.append("### ���忡 ���� �������� �Ǿ����ϴ�. ###\n");
					break;
				}
				}
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			System.out.println(e);
			release();
		} catch (IOException e) {
			System.out.println(e);
			release();
		}
	}

	public void requestlogin(String id) {
		try {
			loginbox = new MessageBox(client_thread_waitRoom, "�α���", "������ �α��� ���Դϴ�.");
			loginbox.show();
			client_thread_loginID = id;
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�α��ο�û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(id);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestCreateRoom(String roomName, int ���ִ�����, int isLock, String password) {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�������û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(roomName);
			client_thread_buffer.append("'");
			client_thread_buffer.append(���ִ�����);
			client_thread_buffer.append("'");
			client_thread_buffer.append(isLock);
			client_thread_buffer.append("'");
			client_thread_buffer.append(password);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestEnterRoom(int room_Number, String password) {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�������û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(room_Number);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(password);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestQuitRoom() {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�������û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_room_Number);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestLogout() {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�α׾ƿ���û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestSendWord(String data) {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�۽��ڿ�û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_room_Number);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(data);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestSendWordTo(String data, String idTo) {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�����ڿ�û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_room_Number);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(idTo);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(data);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	public void requesetSendEmoticon(int i) {
		Image kakao  = Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\kakaofriend.jpg");
		Image kakao1 = kakao.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon Fkakao = new ImageIcon(kakao1);
		
		Image Skakao  = Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\kaka.png");
		Image Skakao1 = kakao.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon SFkakao = new ImageIcon(Skakao1);
		
		try {
			if(i == 1) {
				client_thread_buffer.setLength(0);
				client_thread_buffer.append(�̸�Ƽ�ܼ���);
				client_thread_buffer.append(" : ");
				client_thread_buffer.append(client_thread_loginID);
				client_thread_buffer.append(" : ");
				client_thread_buffer.append(client_thread_room_Number);
				client_thread_buffer.append(" : ");
				client_thread_buffer.append(Fkakao);
				send(client_thread_buffer.toString());
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestforceOut(String idTo) {
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(���������û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_room_Number);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(idTo);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void requestSendFile(String idTo) {
		fileTransBox = new MessageBox(client_thread_chatRoom, "��������", "������ ������ ��ٸ��ϴ�.");
		fileTransBox.show();
		try {
			client_thread_buffer.setLength(0);
			client_thread_buffer.append(�������ۿ�û);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_loginID);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(client_thread_room_Number);
			client_thread_buffer.append(" : ");
			client_thread_buffer.append(idTo);
			send(client_thread_buffer.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void send(String sendData) throws IOException {
		client_thread_out.writeUTF(sendData);
		client_thread_out.flush();
	}

	public void release() {
		if (thisThread != null) {
			thisThread = null;
		}
		try {
			if (client_thread_out != null) {
				client_thread_out.close();
			}
		} catch (IOException e) {
		} finally {
			client_thread_out = null;
		}
		try {
			if (client_thread_in != null) {
				client_thread_in.close();
			}
		} catch (IOException e) {
		} finally {
			client_thread_in = null;
		}
		try {
			if (client_thread_sock != null) {
				client_thread_sock.close();
			}
		} catch (IOException e) {
		} finally {
			client_thread_sock = null;
		}
		System.exit(0);
	}
}
