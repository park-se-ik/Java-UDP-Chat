
import java.sql.*;
import java.util.Vector;

 
public class MemberDAO {
	private static String url = "jdbc:mysql://localhost:3306/chat?useSSL=false";	// book_db가 생성되어 있어야 한다!
	private static String id = "root";
	private static String password = "apple071631!";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	public MemberDAO() {}
	
	
	public Connection getConn() {
		Connection con = null;
		try {
            Class.forName(DRIVER); //1. 드라이버 로딩
            con = DriverManager.getConnection(url,id,password); //2. 드라이버 연결
           
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return con;
    
	}
	boolean getMemberDTOid(String id){
	       
		boolean fact = false;
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
       
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
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
       
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
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
       
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
       
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
       
        try{
           
            con = getConn();
            String sql = "insert into chats(" + "NO,IP,ID,Password)" + "values(?,?,?,?)";
           
            ps = con.prepareStatement(sql);
            ps.setString(1, dto.getNO());
            ps.setString(2, dto.getIP());
            ps.setString(3, dto.getID());
            ps.setString(4, dto.getPassword());

      
            int r = ps.executeUpdate(); //실행 -> 저장
           
           
            if(r>0){
                System.out.println("가입 성공");   
                ok=true;
            }else{
                System.out.println("가입 실패");
            }
           
               
           
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return ok;
    }//insertMmeber
	
	public Vector getMemberList() {
		 Vector data = new Vector();  //Jtable에 값을 쉽게 넣는 방법 1. 2차원배열   2. Vector 에 vector추가
	            
	        Connection con = null;       //연결
	        PreparedStatement ps = null; //명령
	        ResultSet rs = null;         //결과
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
