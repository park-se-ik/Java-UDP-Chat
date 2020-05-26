import java.util.*;

class WaitRoom {
	private static final int �ִ��� = 10;
	private static final int �ִ����� = 100;

	private static final int ����� = 9999;
	private static final int ������ȭ = 9998;
	private static final int �����ȭ = 9997;
	private static final int �氡���� = 9996;
	private static final int Ʋ����й�ȣ = 9995;

	private static Vector ��������, �溤��;
	private static Hashtable �����ؽ�, ���ؽ�;

	private static int ������;
	private static int ���;

	static {
		�������� = new Vector(�ִ�����);
		�溤�� = new Vector(�ִ���);
		�����ؽ� = new Hashtable(�ִ�����);
		���ؽ� = new Hashtable(�ִ���);
		������ = 0;
		��� = 0;
	}

	public WaitRoom() {
	}

	public synchronized int addUser(String id, ServerThread client) {
		if (������ == �ִ�����)
			return ������ȭ;

		Enumeration ids = ��������.elements();
		while (ids.hasMoreElements()) {
			String tempID = (String) ids.nextElement();
			if (tempID.equals(id))
				return �����;
		}
		Enumeration rooms = �溤��.elements();
		while (rooms.hasMoreElements()) {
			ChatRoom tempRoom = (ChatRoom) rooms.nextElement();
			if (tempRoom.checkUserIDs(id))
				return �����;
		}

		��������.addElement(id);
		�����ؽ�.put(id, client);
		client.server_threadID = id;
		client.server_threadroom_Number = 0;
		������++;

		return 0;
	}

	public synchronized void delUser(String id) {
		��������.removeElement(id);
		�����ؽ�.remove(id);
		������--;
	}

	public synchronized String getRooms() {
		StringBuffer room = new StringBuffer();
		String rooms;
		Integer roomNum;
		Enumeration enu = ���ؽ�.keys();
		while (enu.hasMoreElements()) {
			roomNum = (Integer) enu.nextElement();
			ChatRoom tempRoom = (ChatRoom) ���ؽ�.get(roomNum);
			room.append(String.valueOf(roomNum));
			room.append(" = ");
			room.append(tempRoom.toString());
			room.append("'");
		}
		try {
			rooms = new String(room);
			rooms = rooms.substring(0, rooms.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			return "empty";
		}
		return rooms;
	}

	public synchronized String getUsers() {
		StringBuffer id = new StringBuffer();
		String ids;
		Enumeration enu = ��������.elements();
		while (enu.hasMoreElements()) {
			id.append(enu.nextElement());
			id.append("'");
		}
		try {
			ids = new String(id);
			ids = ids.substring(0, ids.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
		return ids;
	}

	public synchronized int addRoom(ChatRoom room) {
		if (��� == �ִ���)
			return �����ȭ;

		�溤��.addElement(room);
		���ؽ�.put(new Integer(ChatRoom.room_Number), room);
		���++;
		return 0;
	}

	public String getWaitRoomInfo() {
		StringBuffer roomInfo = new StringBuffer();
		roomInfo.append(getRooms());
		roomInfo.append(" : ");
		roomInfo.append(getUsers());
		return roomInfo.toString();
	}

	public synchronized int joinRoom(String id, ServerThread client, int room_Number, String password) {
		Integer roomNum = new Integer(room_Number);
		ChatRoom room = (ChatRoom) ���ؽ�.get(roomNum);
		if (room.isLocked()) {
			if (room.checkPassword(password)) {
				if (!room.addUser(id, client)) {
					return �氡����;
				}
			} else {
				return Ʋ����й�ȣ;
			}
		} else if (!room.addUser(id, client)) {
			return �氡����;
		}
		��������.removeElement(id);
		�����ؽ�.remove(id);

		return 0;
	}

	public String getRoomInfo(int room_Number) {
		Integer roomNum = new Integer(room_Number);
		ChatRoom room = (ChatRoom) ���ؽ�.get(roomNum);
		return room.getUsers();
	}

	public synchronized boolean quitRoom(String id, int room_Number, ServerThread client) {
		boolean returnValue = false;
		Integer roomNum = new Integer(room_Number);
		ChatRoom room = (ChatRoom) ���ؽ�.get(roomNum);
		if (room.delUser(id)) {
			�溤��.removeElement(room);
			���ؽ�.remove(roomNum);
			���--;
			returnValue = true;
		}
		��������.addElement(id);
		�����ؽ�.put(id, client);
		return returnValue;
	}

	public synchronized Hashtable getClients(int room_Number) {
		if (room_Number == 0)
			return �����ؽ�;

		Integer roomNum = new Integer(room_Number);
		ChatRoom room = (ChatRoom) ���ؽ�.get(roomNum);
		return room.getClients();
	}
}
