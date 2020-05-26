import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {
	private Socket server_threadsock;
	private DataInputStream server_threadin;
	private DataOutputStream server_threadout;
	private StringBuffer server_threadbuffer;
	private WaitRoom server_threadwaitRoom;
	public String server_threadID;
	public int server_threadroom_Number;

	private static final int WAITROOM = 0;

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

	public ServerThread(Socket sock) {
		try {
			server_threadsock = sock;
			server_threadin = new DataInputStream(sock.getInputStream());
			server_threadout = new DataOutputStream(sock.getOutputStream());
			server_threadbuffer = new StringBuffer(2048);
			server_threadwaitRoom = new WaitRoom();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void sendErrCode(int message, int errCode) throws IOException {
		server_threadbuffer.setLength(0);
		server_threadbuffer.append(message);
		server_threadbuffer.append(" : ");
		server_threadbuffer.append(errCode);
		send(server_threadbuffer.toString());
	}

	private void modifyWaitRoom() throws IOException {
		server_threadbuffer.setLength(0);
		server_threadbuffer.append(�������������);
		server_threadbuffer.append(" : ");
		server_threadbuffer.append(server_threadwaitRoom.getWaitRoomInfo());
		broadcast(server_threadbuffer.toString(), WAITROOM);
	}

	private void modifyWaitUser() throws IOException {
		String ids = server_threadwaitRoom.getUsers();
		server_threadbuffer.setLength(0);
		server_threadbuffer.append(����ڼ���);
		server_threadbuffer.append(" : ");
		server_threadbuffer.append(ids);
		broadcast(server_threadbuffer.toString(), WAITROOM);
	}

	private void modify������(int room_Number, String id, int code) throws IOException {
		String ids = server_threadwaitRoom.getRoomInfo(room_Number);
		server_threadbuffer.setLength(0);
		server_threadbuffer.append(�����ڼ���);
		server_threadbuffer.append(" : ");
		server_threadbuffer.append(id);
		server_threadbuffer.append(" : ");
		server_threadbuffer.append(code);
		server_threadbuffer.append(" : ");
		server_threadbuffer.append(ids);
		broadcast(server_threadbuffer.toString(), room_Number);
	}

	private void send(String sendData) throws IOException {
		synchronized (server_threadout) {

			System.out.println(sendData);

			server_threadout.writeUTF(sendData);
			server_threadout.flush();
		}
	}

	private synchronized void broadcast(String sendData, int room_Number) throws IOException {
		ServerThread client;
		Hashtable clients = server_threadwaitRoom.getClients(room_Number);
		Enumeration enu = clients.keys();
		while (enu.hasMoreElements()) {
			client = (ServerThread) clients.get(enu.nextElement());
			client.send(sendData);
		}
	}

	public void run() {
		try {
			while (true) {
				String recvData = server_threadin.readUTF();

				System.out.println(recvData);

				StringTokenizer st = new StringTokenizer(recvData, " : ");
				int command = Integer.parseInt(st.nextToken());
				switch (command) {
				case �α��ο�û: {
					server_threadroom_Number = WAITROOM;
					int result;
					server_threadID = st.nextToken();
					result = server_threadwaitRoom.addUser(server_threadID, this);
					server_threadbuffer.setLength(0);
					if (result == 0) {
						server_threadbuffer.append(�α��μ���);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(server_threadwaitRoom.getRooms());
						send(server_threadbuffer.toString());
						modifyWaitUser();
						System.out.println(server_threadID + "�� �����û ����");
					} else {
						sendErrCode(�α��ΰ���, result);
					}
					break;
				}
				case �������û: {
					String id, roomName, password;
					int ���ִ�����, result;
					boolean isLock;

					id = st.nextToken();
					String roomInfo = st.nextToken();
					StringTokenizer room = new StringTokenizer(roomInfo, "'");
					roomName = room.nextToken();
					���ִ����� = Integer.parseInt(room.nextToken());
					isLock = (Integer.parseInt(room.nextToken()) == 0) ? false : true;
					password = room.nextToken();

					ChatRoom chatRoom = new ChatRoom(roomName, ���ִ�����, isLock, password, id);
					result = server_threadwaitRoom.addRoom(chatRoom);
					if (result == 0) {
						server_threadroom_Number = ChatRoom.getroom_Number();
						boolean temp = chatRoom.addUser(server_threadID, this);
						server_threadwaitRoom.delUser(server_threadID);

						server_threadbuffer.setLength(0);
						server_threadbuffer.append(���������);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(server_threadroom_Number);
						send(server_threadbuffer.toString());
						modifyWaitRoom();
						modify������(server_threadroom_Number, id, 1);
					} else {
						sendErrCode(���������, result);
					}
					break;
				}
				case �������û: {
					String id, password;
					int room_Number, result;
					id = st.nextToken();
					room_Number = Integer.parseInt(st.nextToken());
					try {
						password = st.nextToken();
					} catch (NoSuchElementException e) {
						password = "0";
					}
					result = server_threadwaitRoom.joinRoom(id, this, room_Number, password);

					if (result == 0) {
						server_threadbuffer.setLength(0);
						server_threadbuffer.append(���������);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(room_Number);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(id);
						server_threadroom_Number = room_Number;
						send(server_threadbuffer.toString());
						modify������(room_Number, id, 1);
						modifyWaitRoom();
					} else {
						sendErrCode(���������, result);
					}
					break;
				}
				case �������û: {
					String id;
					int room_Number;
					boolean updateWaitInfo;
					id = st.nextToken();
					room_Number = Integer.parseInt(st.nextToken());

					updateWaitInfo = server_threadwaitRoom.quitRoom(id, room_Number, this);

					server_threadbuffer.setLength(0);
					server_threadbuffer.append(���������);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(id);
					send(server_threadbuffer.toString());
					server_threadroom_Number = WAITROOM;

					if (updateWaitInfo) {
						modifyWaitRoom();
					} else {
						modifyWaitRoom();
						modify������(room_Number, id, 0);
					}
					break;
				}
				case �α׾ƿ���û: {
					String id = st.nextToken();
					server_threadwaitRoom.delUser(id);

					server_threadbuffer.setLength(0);
					server_threadbuffer.append(�α׾ƿ�����);
					send(server_threadbuffer.toString());
					modifyWaitUser();
					release();
					break;
				}
				case �۽��ڿ�û: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());

					server_threadbuffer.setLength(0);
					server_threadbuffer.append(�۽��ڼ���);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(id);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(server_threadroom_Number);
					server_threadbuffer.append(" : ");
					try {
						String data = st.nextToken();
						server_threadbuffer.append(data);
					} catch (NoSuchElementException e) {
					}

					broadcast(server_threadbuffer.toString(), room_Number);
					break;
				}
				case �����ڿ�û: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					String idTo = st.nextToken();

					Hashtable room = server_threadwaitRoom.getClients(room_Number);
					ServerThread client = null;
					if ((client = (ServerThread) room.get(idTo)) != null) {
						server_threadbuffer.setLength(0);
						server_threadbuffer.append(�����ڼ���);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(id);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(idTo);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(server_threadroom_Number);
						server_threadbuffer.append(" : ");
						try {
							String data = st.nextToken();
							server_threadbuffer.append(data);
						} catch (NoSuchElementException e) {
						}
						client.send(server_threadbuffer.toString());
						send(server_threadbuffer.toString());
						break;
					} else {
						server_threadbuffer.setLength(0);
						server_threadbuffer.append(�����ڰ���);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(idTo);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(server_threadroom_Number);
						send(server_threadbuffer.toString());
						break;
					}
				}
				case �������ۿ�û: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					String idTo = st.nextToken();

					Hashtable room = server_threadwaitRoom.getClients(room_Number);
					ServerThread client = null;
					if ((client = (ServerThread) room.get(idTo)) != null) {
						server_threadbuffer.setLength(0);
						server_threadbuffer.append(�������ۿ�û);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(id);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(server_threadroom_Number);
						client.send(server_threadbuffer.toString());
						break;
					} else {
						server_threadbuffer.setLength(0);
						server_threadbuffer.append(�������۰���);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(����ھ���);
						server_threadbuffer.append(" : ");
						server_threadbuffer.append(idTo);
						send(server_threadbuffer.toString());
						break;
					}
				}
				case �������۰���: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					String idTo = st.nextToken();

					Hashtable room = server_threadwaitRoom.getClients(room_Number);
					ServerThread client = null;
					client = (ServerThread) room.get(idTo);

					server_threadbuffer.setLength(0);
					server_threadbuffer.append(�������۰���);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(�źε�);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(id);

					client.send(server_threadbuffer.toString());
					break;
				}
				case �������ۼ���: {
					String id = st.nextToken();
					int room_Number = Integer.parseInt(st.nextToken());
					String idTo = st.nextToken();
					String hostaddr = st.nextToken();

					Hashtable room = server_threadwaitRoom.getClients(room_Number);
					ServerThread client = null;
					client = (ServerThread) room.get(idTo);

					server_threadbuffer.setLength(0);
					server_threadbuffer.append(�������ۼ���);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(id);
					server_threadbuffer.append(" : ");
					server_threadbuffer.append(hostaddr);

					client.send(server_threadbuffer.toString());
					break;
				}
				case ���������û: {
					int room_Number = Integer.parseInt(st.nextToken());
					String idTo = st.nextToken();
					boolean updateWaitInfo;
					Hashtable room = server_threadwaitRoom.getClients(room_Number);
					ServerThread client = null;
					client = (ServerThread) room.get(idTo);
					updateWaitInfo = server_threadwaitRoom.quitRoom(idTo, room_Number, client);

					server_threadbuffer.setLength(0);
					server_threadbuffer.append(�����������);
					client.send(server_threadbuffer.toString());
					client.server_threadroom_Number = 0;

					if (updateWaitInfo) {
						modifyWaitRoom();
					} else {
						modifyWaitRoom();
						modify������(room_Number, idTo, 2);
					}
					break;
				}
				}
				Thread.sleep(100);
			}
		} catch (NullPointerException e) {
		} catch (InterruptedException e) {
			System.out.println(e);

			if (server_threadroom_Number == 0) {
				server_threadwaitRoom.delUser(server_threadID);
			} else {
				boolean temp = server_threadwaitRoom.quitRoom(server_threadID, server_threadroom_Number, this);
				server_threadwaitRoom.delUser(server_threadID);
			}
			release();
		} catch (IOException e) {
			System.out.println(e);

			if (server_threadroom_Number == 0) {
				server_threadwaitRoom.delUser(server_threadID);
			} else {
				boolean temp = server_threadwaitRoom.quitRoom(server_threadID, server_threadroom_Number, this);
				server_threadwaitRoom.delUser(server_threadID);
			}
			release();
		}
	}

	public void release() {
		try {
			if (server_threadin != null)
				server_threadin.close();
		} catch (IOException e1) {
		} finally {
			server_threadin = null;
		}
		try {
			if (server_threadout != null)
				server_threadout.close();
		} catch (IOException e1) {
		} finally {
			server_threadout = null;
		}
		try {
			if (server_threadsock != null)
				server_threadsock.close();
		} catch (IOException e1) {
		} finally {
			server_threadsock = null;
		}

		if (server_threadID != null) {
			System.out.println(server_threadID + "�� ������ �����մϴ�.");
			server_threadID = null;
		}
	}
}
