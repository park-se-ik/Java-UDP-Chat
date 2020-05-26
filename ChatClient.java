import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ChatClient {
	//Id id;
	
	public static String getloginID() {
		new Id();
		String loginID = "";

			try {
				while (Id.fact1 == false) {}  //�α��� Ȯ�� �ɶ����� ���ѷ���
				loginID = Id.getId();  //�α��� Ȯ�ν� Id�� �ޱ�
			} catch (NullPointerException e) {
				System.exit(0);
			}
			
		return loginID;

	}

	public static void main(String args[]) {
		String id = getloginID();
		//if(!id.equals("")) {
			try {
				if (args.length == 0) {
					ClientThread thread = new ClientThread();
					thread.start();
					thread.requestlogin(id);
				} else if (args.length == 1) {
					ClientThread thread = new ClientThread(args[0]);
					thread.start();
					thread.requestlogin(id);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	//}
}

class Id extends JFrame implements ActionListener{
 	
    private JPanel card1 = new JPanel();
   	private JPanel panelA = new JPanel();
   	private JPanel panelB = new JPanel();
   	private JPanel card2 = new JPanel();
   	private JButton btn = new JButton("ȸ������");//ȸ������''
   	private JButton btn1 = new JButton("�α���");//�α���
   	private JLabel label1 = new JLabel("ID   :   ");
   	private JLabel label2 = new JLabel("PWD : ");
   	static JTextField T1 = new JTextField(20);
	private JPasswordField P1 = new JPasswordField(20);
	//static boolean Test = false;
	//private CardLayout card;
	static boolean fact1 = false;
	static boolean isFirst = false;
	static String i1= "0",i2 = "0";
	 
    register rg;
    //public Id(WriteThread wt, ClientFrame cf){}
  
    public Id() {
    	 
         this.setSize(300,200);
         panelA.add(label1);
         panelA.add(T1);
         card1.add(panelA,BorderLayout.CENTER);
         panelB.add(label2);
         panelB.add(P1);
         card1.add(panelB,BorderLayout.CENTER);
         card1.add(btn);
         card1.add(btn1);
         
         this.add(card1);
         btn.addActionListener(this);
         btn1.addActionListener(this);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setVisible(true);

   }
    
  public void actionPerformed(ActionEvent e) {     
	  
	  
	  
	   if(e.getSource() == btn){ //btn�� ȸ������ 
		  //���� �޾Ƽ� ���ϼӿ� �Է��ؾ���
		   register rg = new register();
    		try{ 
    			this.hide();	//�����͵��� �����
    			//i1 = T1.getText();
    			//i2 = P1.getText();    	
    			rg.setVisible(true); //register Ŭ������ �����ؾ� ȸ�������� �Ҽ��ְ� ���⼭ ���̵� �ִ� ���̵����� �ƴ��� �˻縦 �Ѵ�.
    		   	if(i1 == "0"){
					JOptionPane.showMessageDialog(null, "�Է��� �ϼž� �մϴ�.");
				}
    		}catch(NullPointerException e1) {
    			System.out.println(e);
    		}
	   }
	   else if(e.getSource()== btn1){//�α���
		   MemberDAO dao = new MemberDAO();
		   String ID = T1.getText();
		   String PW = P1.getText();
		   if(dao.getMemberDTOid(ID)) {
			   if(dao.getMemberDTOpw(PW)) {
				   this.hide();	
				   fact1 = true;
			   }
			   else {
				   JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ�����ʽ��ϴ�.");
			   }
				  
		   }
		   else {
			   JOptionPane.showMessageDialog(null, "�Է��Ͻ� ID�� ��ġ�ϴ� ������ �����ϴ�.");
		   }
		  
		   if(i1 == "" || i2 ==""){
				JOptionPane.showMessageDialog(null, "�Է��� �ϼž� �մϴ�.");
			}
           
	   }
	   
	 
   }
   static public String getId(){
         return T1.getText();
   }
}

class register extends JFrame implements ActionListener{
	private JButton btn3 = new JButton("�ߺ�");//ȸ������ ���� �� ����� �� ��
	private JButton btn4 = new JButton("�ߺ�");//ȸ������ ���� �� ����� �� ��
 	private JLabel label3 = new JLabel("IP  :  ");
	private JLabel label4 = new JLabel("ID  :  ");
	private JLabel label5 = new JLabel("PWD  :  ");
	private JLabel label6 = new JLabel("PWDȮ�� : ");
	private JButton btn5 = new JButton("ȸ������");//ȸ������ ���� �� ����� �� ��
	private JTextField T2 = new JTextField(15);
	private JTextField T3 = new JTextField(15) ;
	private JTextField pw1 = new JPasswordField(20);
	private JTextField pw2 = new JPasswordField(20);
	private JPanel panel = new JPanel();
	private JPanel p1 = new JPanel();//
	private JPanel p2 = new JPanel();
	private JPanel p3 = new JPanel();
	private boolean FBconf = false;
	private boolean SBconf = false;
	private boolean PWconf = false;
	
	private Vector Conf = new Vector<>();
	private Vector Org = new Vector<>();
	String s1,s2;
	Id id;
	MemberDTO dto = new MemberDTO();
	
	public register() {
		this.setTitle("ȸ������");
		this.setSize(320,220);
		
		panel.add(label3);
		panel.add(T2);
		panel.add(btn3);
		btn3.addActionListener(this);
		
		p1.add(label4);
		p1.add(T3);
		p1.add(btn4);
		btn4.addActionListener(this);
		
		p2.add(label5);
		p2.add(pw1);	
		pw1.addActionListener(this);
		p2.add(label6);
		p2.add(pw2);
		pw2.addActionListener(this);
		
		
		p1.setPreferredSize(new Dimension(300,35));
		p2.setPreferredSize(new Dimension(350,30));
		
		
		//��й�ȣ ��ġ Ȯ���ϴ� ���� �ʿ�
		panel.add(p1);
		panel.add(p2);
		panel.add(label6);
		panel.add(pw2);
		//panel.add(p3);
		panel.add(btn5);
		btn5.addActionListener(this);
		this.add(panel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	String i1="0",i2="0",i3= "0";
	 public MemberDTO getViewData(){
	       
	        //ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
	        MemberDTO dto = new MemberDTO();
	        String IP = T2.getText();
	        String ID = T3.getText();
	        String PW1 = pw1.getText();
	       	
	        //dto�� ��´�.
	        dto.setIP(IP);
	        dto.setID(ID);
	        dto.setPassword(PW1);
	        
	        return dto;
	    }
	 
	private void insertMember() {
		MemberDTO dto = getViewData();
        MemberDAO dao = new MemberDAO();       
        boolean ok = dao.insertMember(dto);
       
        if(ok) {
            JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�.");
            new Id();
        }else{ 
            JOptionPane.showMessageDialog(this, "������ ���������� ó������ �ʾҽ��ϴ�.");
            new register();
        }
       	       
    }//insertMember
	
	public void actionPerformed(ActionEvent e){
		MemberDAO dao = new MemberDAO();
		if(e.getSource()== btn3) { //�ߺ�
			
			String IP = T2.getText();
			if(dao.getMemberDTOip(IP)) {
				FBconf = false;
				 JOptionPane.showMessageDialog(this, "�ߺ��� �����ǰ� �����մϴ�.");
			}
				
			else {
				FBconf = true;
				 JOptionPane.showMessageDialog(this, "��밡���� ������ �Դϴ�");
			}
		}
		else if(e.getSource()== btn4) { //�ߺ�
			String ID = T2.getText();
			if(dao.getMemberDTOid(ID)) {
				SBconf = false;
				 JOptionPane.showMessageDialog(this, "�ߺ��� ���̵� �����մϴ�.");
			}
				
			else {
				SBconf = true;
				 JOptionPane.showMessageDialog(this, "��밡���� ���̵��Դϴ�.");
			}	
		}
		else if(e.getSource()==pw1 || e.getSource()==pw2) {
			
			s1 = (String)pw1.getText();
			s2 = (String)pw2.getText();
		}
		
		else if(e.getSource()== btn5) {//ȸ�����Դ�����
			
			
			if(FBconf ==true && SBconf==true) {
				Id id= new Id();
				insertMember();
				s1 = (String)pw1.getText();
				s2 = (String)pw2.getText();
					if(s1.equals(s2)) {
						PWconf = !PWconf;
					}
					if(PWconf == false) {
						JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ�����ʽ��ϴ�");
					}
					else {
						this.hide();
						//id = new Id(wt.cf);
						id.setVisible(true);
					}							 
			}
			else if(FBconf== true && SBconf == false) {
				JOptionPane.showMessageDialog(null, "���̵� �ߺ�Ȯ���� ���ּ���");
			}
			else if(FBconf== false && SBconf == true) {
				JOptionPane.showMessageDialog(null, "IP �ߺ�Ȯ���� ���ּ���");
			}
			else if(FBconf== false && SBconf == false) {
				JOptionPane.showMessageDialog(null, "ä���ϰ������ ID,IP �ߺ�Ȯ���ض� ^^");
			}
		}
	}		
}