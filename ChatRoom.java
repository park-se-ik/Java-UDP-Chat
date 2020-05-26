import java.util.*;

class ChatRoom {
	public static int room_Number = 0;
	private Vector ��������;
	private Hashtable �����ؽ�;
	private String roomName;
	private int ���ִ�����;
	private int ������;
	private boolean isLock;
	private String password;
	private String admin;

	public ChatRoom(String roomName, int ���ִ�����, boolean isLock, String password, String admin) {
		room_Number++;
		this.roomName = roomName;
		this.���ִ����� = ���ִ�����;
		this.������ = 0;
		this.isLock = isLock;
		this.password = password;
		this.admin = admin;
		this.�������� = new Vector(���ִ�����);
		this.�����ؽ� = new Hashtable(���ִ�����);
	}

	public boolean addUser(String id, ServerThread client) {
		if (������ == ���ִ�����) {
			return false;
		}
		��������.addElement(id);
		�����ؽ�.put(id, client);
		������++;
		return true;
	}

	public boolean checkPassword(String passwd) {
		return password.equals(passwd);
	}

	public boolean checkUserIDs(String id) {
		Enumeration ids = ��������.elements();
		while (ids.hasMoreElements()) {
			String tempId = (String) ids.nextElement();
			if (tempId.equals(id))
				return true;
		}
		return false;
	}

	public boolean isLocked() {
		return isLock;
	}

	public boolean delUser(String id) {
		��������.removeElement(id);
		�����ؽ�.remove(id);
		������--;
		return ��������.isEmpty();
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

	public Hashtable getClients() {
		return �����ؽ�;
	}

	public String toString() {
		StringBuffer room = new StringBuffer();
		room.append(roomName);
		room.append(" = ");
		room.append(String.valueOf(������));
		room.append(" = ");
		room.append(String.valueOf(���ִ�����));
		room.append(" = ");
		if (isLock) {
			room.append("�����");
		} else {
			room.append("����");
		}
		room.append(" = ");
		room.append(admin);
		return room.toString();
	}

	public static synchronized int getroom_Number() {
		return room_Number;
	}
}
