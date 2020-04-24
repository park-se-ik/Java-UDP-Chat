package CHAT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; //寃쎄퀬李쎈찓�떆吏�
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;


 class Id extends JFrame implements ActionListener{
	 	
<<<<<<< HEAD
        
=======
        private JPanel card1 = new JPanel();
	   	private JPanel panelA = new JPanel();
	   	private JPanel panelB = new JPanel();
	   	private JPanel card2 = new JPanel();
	   	private JButton btn = new JButton("�쉶�썝媛��엯");//�쉶�썝媛��엯''
	   	private JButton btn1 = new JButton("濡쒓렇�씤");//濡쒓렇�씤
	   	private JLabel label1 = new JLabel("ID   :   ");
	   	private JLabel label2 = new JLabel("PWD : ");
	   	static JTextField T1 = new JTextField(20);
		private JPasswordField P1 = new JPasswordField(20);
		//static boolean Test = false;
		//private CardLayout card;
		static boolean fact1 = false;
		
		static String i1= "0",i2 = "0";
		File file = new File("D:\\java�떎�뒿\\ChatProgram\\Writer.txt");
		
		ClientFrame cf;
	   	WriteThread wt;    
	    register rg;
	    NewWindow nw;
	    //public Id(WriteThread wt, ClientFrame cf){}
	    public Id() {};
	    public Id(WriteThread wt, ClientFrame cf) {
	    	  this.wt = wt;
	          this.cf = cf;
            
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
    	  
    	  
    	  
    	   if(e.getSource() == btn){ //btn�� �쉶�썝媛��엯 
    		  //媛믪쓣 諛쏆븘�꽌 �뙆�씪�냽�뿉 �엯�젰�빐�빞�븿
    		   register rg = new register();
	    		try{ 
	    			this.hide();	//�벐�뜕寃껊뱾�쓣 �닲湲곌린
	    			//i1 = T1.getText();
	    			//i2 = P1.getText();    	
	    			rg.setVisible(true); //register �겢�옒�뒪瑜� �떎�뻾�빐�빞 �쉶�썝媛��엯�쓣 �븷�닔�엳怨� �뿬湲곗꽌 �븘�씠�뵒媛� �엳�뒗 �븘�씠�뵒�씤吏� �븘�땶吏� 寃��궗瑜� �븳�떎.
	    		   	if(i1 == "0"){
						JOptionPane.showMessageDialog(null, "�엯�젰�쓣 �븯�뀛�빞 �빀�땲�떎.");
					}
	    		}catch(NullPointerException e1) {
	    			System.out.println(e);
	    		}
    	   }
    	   else if(e.getSource()== btn1){//濡쒓렇�씤
    		   MemberDAO dao = new MemberDAO();
    		   String ID = T1.getText();
    		   String PW = P1.getText();
    		   if(dao.getMemberDTOid(ID)) {
    			   if(dao.getMemberDTOpw(PW)) {
    				   this.hide();	
    				   fact1 = true;
    				   new NewWindow(wt,cf);
    			   }
    			   else {
    				   JOptionPane.showMessageDialog(null, "鍮꾨�踰덊샇媛� �씪移섑븯吏��븡�뒿�땲�떎.");
    			   }
    				  
    		   }
    		   else {
    			   JOptionPane.showMessageDialog(null, "�엯�젰�븯�떊 ID�� �씪移섑븯�뒗 �젙蹂닿� �뾾�뒿�땲�떎.");
    		   }
    		  
    		   if(i1 == "" || i2 ==""){
					JOptionPane.showMessageDialog(null, "�엯�젰�쓣 �븯�뀛�빞 �빀�땲�떎.");
				}
               
    	   }
>>>>>>> refs/remotes/origin/master
            
       }
       static public String getId(){
             return T1.getText();
       }
}



 class register extends JFrame implements ActionListener{
		private JButton btn3 = new JButton("以묐났");//�쉶�썝媛��엯 �뱾�뼱媛붿쓣 �븣 留뚮뱾湲� �븳 寃�
		private JButton btn4 = new JButton("以묐났");//�쉶�썝媛��엯 �뱾�뼱媛붿쓣 �븣 留뚮뱾湲� �븳 寃�
	 	private JLabel label3 = new JLabel("IP  :  ");
		private JLabel label4 = new JLabel("ID  :  ");
		private JLabel label5 = new JLabel("PWD  :  ");
		private JLabel label6 = new JLabel("PWD�솗�씤 : ");
		private JButton btn5 = new JButton("�쉶�썝媛��엯");//�쉶�썝媛��엯 �뱾�뼱媛붿쓣 �븣 留뚮뱾湲� �븳 寃�
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
		WriteThread wt;
		ClientFrame cf;
		public register() {
			this.setTitle("�쉶�썝媛��엯");
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
			
			
			//鍮꾨�踰덊샇 �씪移� �솗�씤�븯�뒗 寃껋씠 �븘�슂
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
		File file = new File("D:\\java�떎�뒿\\ChatProgram\\Writer.txt");
		String i1="0",i2="0",i3= "0";
		 public MemberDTO getViewData(){
		       
		        //�솕硫댁뿉�꽌 �궗�슜�옄媛� �엯�젰�븳 �궡�슜�쓣 �뼸�뒗�떎.
		        MemberDTO dto = new MemberDTO();
		        String IP = T2.getText();
		        String ID = T3.getText();
		        String PW1 = pw1.getText();
		       	
		        //dto�뿉 �떞�뒗�떎.
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
	            JOptionPane.showMessageDialog(this, "등록되었습니다.");
	            new Id(wt,cf);
	        }else{ 
	            JOptionPane.showMessageDialog(this, "중복된 아이디가 존재합니다.");
	            new register();
	        }
	       	       
	    }//insertMember

	
		public void actionPerformed(ActionEvent e){
			MemberDAO dao = new MemberDAO();
			if(e.getSource()== btn3) { //중복
				
				String IP = T2.getText();
				if(dao.getMemberDTOip(IP)) {
					FBconf = false;
					 JOptionPane.showMessageDialog(this, "중복된 아이피가 존재합니다.");
				}
					
				else {
					FBconf = true;
					 JOptionPane.showMessageDialog(this, "사용가능한 아이피 입니다");
				}
			}
			else if(e.getSource()== btn4) { //중복
				String ID = T2.getText();
				if(dao.getMemberDTOid(ID)) {
					SBconf = false;
					 JOptionPane.showMessageDialog(this, "중복된 아이디가 존재합니다.");
				}
					
				else {
					SBconf = true;
					 JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
				}	
			}
			else if(e.getSource()==pw1 || e.getSource()==pw2) {
				
				s1 = (String)pw1.getText();
				s2 = (String)pw2.getText();
			}
			
			else if(e.getSource()== btn5) {//회원가입누를시
				
				
				if(FBconf ==true && SBconf==true) {
					Id id= new Id();
					insertMember();
					s1 = (String)pw1.getText();
					s2 = (String)pw2.getText();
						if(s1.equals(s2)) {
							PWconf = !PWconf;
						}
						if(PWconf == false) {
							JOptionPane.showMessageDialog(null, "비밀번호가 일치하지않습니다");
						}
						else {
							this.hide();
							//id = new Id(wt.cf);
							id.setVisible(true);
						}							 
				}
				else if(FBconf== true && SBconf == false) {
					JOptionPane.showMessageDialog(null, "아이디 중복확인을 해주세요");
				}
				else if(FBconf== false && SBconf == true) {
					JOptionPane.showMessageDialog(null, "IP 중복확인을 해주세요");
				}
				else if(FBconf== false && SBconf == false) {
					JOptionPane.showMessageDialog(null, "채팅하고싶으면 ID,IP 중복확인해라 ^^");
				}
			}
		}		
=======
>>>>>>> refs/remotes/origin/master
 }

 
 
 
 
class NewWindow extends JFrame implements ActionListener,ItemListener,MouseListener{
	String number[] = {"2","3","4"}; 
	private JTextField t1;
	private JPanel panel= new JPanel();
	private JButton btn  = new JButton("�깮�꽦");
	private JButton btn1  = new JButton("�엯�옣");
	private JPanel Lpanel0 = new JPanel();
	private JPanel Rpanel0 = new JPanel();
	private JPanel panel1 = new JPanel();
	private JLabel Flabel0 = new JLabel("梨꾪똿李쎌씠由�");
	private JLabel Slabel0 = new JLabel("鍮꾨�踰덊샇");
	private JLabel Flabel01 = new JLabel("梨꾪똿李쎌씠由�");
	private JLabel Slabel01 = new JLabel("鍮꾨�踰덊샇");
	private JCheckBox CHB = new  JCheckBox("");
	private JTextField FT = new JTextField(20);
	private JPasswordField PST = new JPasswordField(18);
	private JTextField RFT = new JTextField(20);
	private JPasswordField RSP = new JPasswordField(20);
	private JTable table;
<<<<<<< HEAD
	}
	
	public Vector<String> makeInVector(String[] array) {
		Vector<String> in = new Vector<>();
		for(String data : array){
			in.add(data);
			}		
			return in;
		}

		public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			password = true;
			fact = true;
			PST.setEditable(fact);
		}
		else {
			password = false;
			fact = false;
			PST.setEditable(fact);
		
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		

=======
	private Vector Header = new Vector<>(5);
	private Vector<Vector> Contents =  new Vector <>(5);	
	DefaultTableModel dtm;
	private JScrollPane BSP;
	private JComboBox CB = new JComboBox(number);
	boolean password = false; //鍮꾨�踰덊샇 �꽕�젙 �뿬遺�
	boolean fact = false;
	static String pw;
	static String pass;
	int Rnum = 0; //諛⑸쾲�샇 �꽕�젙
	int ConN = 1;
	boolean F = false; //�뙣�뒪�썙�뱶媛�  鍮꾧탳瑜� RSP�넻�빐�꽌 �븯湲� �쐞�븳 蹂��닔
	
	WriteThread wt;    
    ClientFrame cf;
    register rg;
   
	public NewWindow() {}
	public NewWindow(WriteThread wt, ClientFrame cf){
		this.wt = wt;
        this.cf = cf;
        if(Id.fact1 == false)
        	new Id(wt,cf);
       
        this.setSize(640,350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CHAT");
		
		Lpanel0.setLayout(new FlowLayout());
		Lpanel0.add(Flabel0);
		Lpanel0.add(FT);
		FT.addActionListener(this);
		Flabel0.setLocation(200, 200);
		Lpanel0.add(Slabel0);
		
>>>>>>> refs/remotes/origin/master
	
	
}

public class ClientFrame extends JFrame implements ActionListener{
       JTextArea txtA = new JTextArea();
       JTextField txtF = new JTextField(15);
       JButton btnTransfer = new JButton("�쟾�넚");
       JButton btnChb = new JButton("諛붽씀�옄");
       JButton btnExit = new JButton("�떕湲�");
       boolean isFirst=true;
       JPanel p1 = new JPanel();
       Socket socket;
       WriteThread wt;
       register rg;
      
       
       public ClientFrame(Socket socket) {
             super("梨꾪똿�씠�굹 �빐蹂쇨퉴");
             this.socket = socket;
             wt = new WriteThread(this);
             //new Id();
             new NewWindow(wt,this);
             add("Center", txtA);          
             p1.add(txtF);
             p1.add(btnTransfer);
             //p1.add(btnChb);
             p1.add(btnExit);
             add("South", p1);          
             //硫붿꽭吏�瑜� �쟾�넚�븯�뒗 �겢�옒�뒪 �깮�꽦
             btnTransfer.addActionListener(this);
             btnExit.addActionListener(this);
             setDefaultCloseOperation(EXIT_ON_CLOSE);
             setBounds(300, 300, 350, 300);
             setVisible(false); 
       }

      

       public void actionPerformed(ActionEvent e){
    	 
             String id = Id.getId();
             if(e.getSource()==btnTransfer){//�쟾�넚踰꾪듉 �닃���쓣 寃쎌슦
                    //硫붿꽭吏� �엯�젰�뾾�씠 �쟾�넚踰꾪듉留� �닃���쓣 寃쎌슦
                    if(txtF.getText().equals("")){
                           return;
                    }                  
                    txtA.append("["+id+"] "+ txtF.getText()+"\n");
                    wt.sendMsg();
                    txtF.setText("");
             }
             //else if
             else{
                    this.dispose();
             }

       }

}







