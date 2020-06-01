
import java.sql.*;
import java.util.Vector;

 
public class MemberDAO {
	private static String url = "jdbc:mysql://localhost:3306/chat?useSSL=false";	// book_db�� �����Ǿ� �־�� �Ѵ�!
	private static String id = "root";
	private static String password = "apple071631!";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	public MemberDAO() {}
	
	
	public Connection getConn() {
		Connection con = null;
		try {
            Class.forName(DRIVER); //1. ����̹� �ε�
            con = DriverManager.getConnection(url,id,password); //2. ����̹� ����
           
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return con;
    
	}
	boolean getMemberDTOid(String id){
	       
		boolean fact = false;
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���
       
        try {
           
            con = getConn();
            String sql = "select id from chats where id in ("+id+")";
            ps = con.prepareStatement(sql);
           
            rs = ps.executeQuery();
           
            while(rs.next()){
               String find = rs.getString("ID");
               if(id.equals(find)) {
            	   fact = true;
            	   break;
               }
            	   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
       
        return fact;    
    }
	
	boolean getMemberDTOip(String ip){
	       
      
		boolean fact = false;
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���
       
        try {
           
            con = getConn();
            String sql = "select ip from chats where ip in ("+ip+")";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String find = rs.getString("IP");
                if(ip.equals(find)) {
             	   fact = true;
             	   break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
       
        return fact;    
    }
	boolean getMemberDTOpw(String password){
	       
		boolean fact = false;
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���
       
        try {
           
            con = getConn();
            String sql = "select Password from chats where Password in ("+password+")";
            ps = con.prepareStatement(sql);
           
            rs = ps.executeQuery();
           
            while(rs.next()){
               String find = rs.getString("Password");
               if(password.equals(find)) {
            	   fact = true;
            	   break;
               }
            	   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
       
        return fact;    
    }
	public boolean insertMember(MemberDTO dto){
	       
        boolean ok = false;
       
        Connection con = null;       //����
        PreparedStatement ps = null; //���
       
        try{
           
            con = getConn();
            String sql = "insert into chats(" + "NO,IP,ID,Password)" + "values(?,?,?,?)";
           
            ps = con.prepareStatement(sql);
            ps.setString(1, dto.getNO());
            ps.setString(2, dto.getIP());
            ps.setString(3, dto.getID());
            ps.setString(4, dto.getPassword());

      
            int r = ps.executeUpdate(); //���� -> ����
           
           
            if(r>0){
                System.out.println("���� ����");   
                ok=true;
            }else{
                System.out.println("���� ����");
            }
           
               
           
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return ok;
    }//insertMmeber
	
	public Vector getMemberList() {
		 Vector data = new Vector();  //Jtable�� ���� ���� �ִ� ��� 1. 2�����迭   2. Vector �� vector�߰�
	            
	        Connection con = null;       //����
	        PreparedStatement ps = null; //���
	        ResultSet rs = null;         //���
	        try{
	            
	            con = getConn();
	            String sql = "select * from chats order by NO asc";
	            ps = con.prepareStatement(sql);
	            rs = ps.executeQuery();
	           
	            while(rs.next()){
	                String NO = rs.getString("NO");
	                String IP = rs.getString("IP");
	                String ID = rs.getString("ID");
	                String Password = rs.getString("Password");
	              
	                Vector row = new Vector();
	                row.add(NO);
	                row.add(IP);
	                row.add(ID);
	                row.add(Password);
	                  
	                data.add(row);             
	            }//while
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return data;
	  }//getMemberList()
	

	 
}
