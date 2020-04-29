package Protect;

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
import javax.swing.JOptionPane; //경고창메시지
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;


 class Id extends JFrame implements ActionListener{
	 	
        private JPanel card1 = new JPanel();
	   	private JPanel panelA = new JPanel();
	   	private JPanel panelB = new JPanel();
	   	private JPanel card2 = new JPanel();
	   	private JButton btn = new JButton("회원가입");//회원가입''
	   	private JButton btn1 = new JButton("로그인");//로그인
	   	private JLabel label1 = new JLabel("ID   :   ");
	   	private JLabel label2 = new JLabel("PWD : ");
	   	static JTextField T1 = new JTextField(20);
		private JPasswordField P1 = new JPasswordField(20);
		//static boolean Test = false;
		//private CardLayout card;
		static boolean fact1 = false;
		
		static String i1= "0",i2 = "0";
		File file = new File("D:\\java실습\\ChatProgram\\Writer.txt");
		
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
    	  
    	  
    	  
    	   if(e.getSource() == btn){ //btn은 회원가입 
    		  //값을 받아서 파일속에 입력해야함
    		   register rg = new register();
	    		try{ 
	    			this.hide();	//쓰던것들을 숨기기
	    			//i1 = T1.getText();
	    			//i2 = P1.getText();    	
	    			rg.setVisible(true); //register 클래스를 실행해야 회원가입을 할수있고 여기서 아이디가 있는 아이디인지 아닌지 검사를 한다.
	    		   	if(i1 == "0"){
						JOptionPane.showMessageDialog(null, "입력을 하셔야 합니다.");
					}
	    		}catch(NullPointerException e1) {
	    			System.out.println(e);
	    		}
    	   }
    	   else if(e.getSource()== btn1){//로그인
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
    				   JOptionPane.showMessageDialog(null, "비밀번호가 일치하지않습니다.");
    			   }
    				  
    		   }
    		   else {
    			   JOptionPane.showMessageDialog(null, "입력하신 ID와 일치하는 정보가 없습니다.");
    		   }
    		  
    		   if(i1 == "" || i2 ==""){
					JOptionPane.showMessageDialog(null, "입력을 하셔야 합니다.");
				}
               
    	   }
            
       }
       static public String getId(){
             return T1.getText();
       }
}


	

 class register extends JFrame implements ActionListener{
		private JButton btn3 = new JButton("중복");//회원가입 들어갔을 때 만들기 한 것
		private JButton btn4 = new JButton("중복");//회원가입 들어갔을 때 만들기 한 것
	 	private JLabel label3 = new JLabel("IP  :  ");
		private JLabel label4 = new JLabel("ID  :  ");
		private JLabel label5 = new JLabel("PWD  :  ");
		private JLabel label6 = new JLabel("PWD확인 : ");
		private JButton btn5 = new JButton("회원가입");//회원가입 들어갔을 때 만들기 한 것
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
			this.setTitle("회원가입");
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
			
			
			//비밀번호 일치 확인하는 것이 필요
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
		File file = new File("D:\\java실습\\ChatProgram\\Writer.txt");
		String i1="0",i2="0",i3= "0";
		 public MemberDTO getViewData(){
		       
		        //화면에서 사용자가 입력한 내용을 얻는다.
		        MemberDTO dto = new MemberDTO();
		        String IP = T2.getText();
		        String ID = T3.getText();
		        String PW1 = pw1.getText();
		       	
		        //dto에 담는다.
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
	            JOptionPane.showMessageDialog(this, "가입이 완료되었습니다.");
	            new Id(wt,cf);
	        }else{ 
	            JOptionPane.showMessageDialog(this, "가입이 정상적으로 처리되지 않았습니다.");
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
 }

 
 
 
 
class NewWindow extends JFrame implements ActionListener,ItemListener,MouseListener{
	String number[] = {"2","3","4"}; 
	private JTextField t1;
	private JPanel panel= new JPanel();
	private JButton btn  = new JButton("생성");
	private JButton btn1  = new JButton("입장");
	private JPanel Lpanel0 = new JPanel();
	private JPanel Rpanel0 = new JPanel();
	private JPanel panel1 = new JPanel();
	private JLabel Flabel0 = new JLabel("채팅창이름");
	private JLabel Slabel0 = new JLabel("비밀번호");
	private JLabel Flabel01 = new JLabel("채팅창이름");
	private JLabel Slabel01 = new JLabel("비밀번호");
	private JCheckBox CHB = new  JCheckBox("");
	private JTextField FT = new JTextField(20);
	private JPasswordField PST = new JPasswordField(18);
	private JTextField RFT = new JTextField(20);
	private JPasswordField RSP = new JPasswordField(20);
	private JTable table;
	private Vector Header = new Vector<>(5);
	private Vector<Vector> Contents =  new Vector <>(5);	
	DefaultTableModel dtm;
	private JScrollPane BSP;
	private JComboBox CB = new JComboBox(number);
	boolean password = false; //비밀번호 설정 여부
	boolean fact = false;
	static String pw;
	static String pass;
	int Rnum = 0; //방번호 설정
	int ConN = 1;
	boolean F = false; //패스워드값  비교를 RSP통해서 하기 위한 변수
	
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
		Lpanel0.add(CHB);
		CHB.addActionListener(this);
		CHB.addItemListener(this);
		Lpanel0.add(PST);
		PST.setEditable(fact);
		PST.addActionListener(this);
		CB.setBounds(50,50,90,20);
		CB.addActionListener(this);
		Lpanel0.add(CB);
		Lpanel0.add(btn);
		btn.addActionListener(this);
		Lpanel0.setPreferredSize(new Dimension(300,200));
	
		
		Header.add("번호");
		Header.add("채팅방이름");
		Header.add("접속중인 인원수");
		Header.add("최대 접속 가능수");
		Header.add("비밀번호 여부");
		dtm = new DefaultTableModel(Contents,Header) {
			public boolean isCellEditable(int row, int column) {
	        if (column >= 0) {
	            return false;
	        } else {
	            return true;
	        }
	    }};
		table = new JTable(dtm);
		BSP = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		Rpanel0.add(BSP);
		BSP.setPreferredSize(new Dimension(300,200));
		Rpanel0.add(Flabel01);
		Rpanel0.add(RFT);
		Rpanel0.add(Slabel01);
		Rpanel0.add(RSP);
		RSP.setEditable(F);
		RSP.addActionListener(this);
		Rpanel0.add(btn1);
		btn1.addActionListener(this);
		Rpanel0.setSize(10,10);
		Rpanel0.setPreferredSize(new Dimension(300,300));
		
		panel.setLayout(new BorderLayout());
		panel.add(Lpanel0,BorderLayout.WEST);
		panel.add(Rpanel0,BorderLayout.CENTER);
		
		table.addMouseListener(this);
		this.add(panel);
		if(Id.fact1)
			this.setVisible(true);
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
		Vector bowl = new Vector<>(5);
		/*if(e.getSource()== CHB) {
			PST.setEditable(fact);
		}*/
		
		if( e.getSource() == btn  ) {// 생성버튼 사용시
				if( password ==true ) { //라디오버튼을 누른 상태	
						String s1 = FT.getText();
						int a = ++Rnum;
						bowl.add(makeInVector(new String[] {""+a,""+s1,"0",""+(String)CB.getSelectedItem(),"O"}));
						pw = PST.getText();
						for(int i = 0; i<5;i++) {
							Contents.addAll(bowl);
							bowl.clear();
						}
						dtm.setDataVector(Contents, Header);
						FT.setText("");
						PST.setText("");
					}
				else {
					/*input[0] = ++Rnum;
					input[1] = FT.getText();	
					input[2] = 0;
					input[3] = CB.getSelectedItem().toString();
					input[4] = "X";
					dtm.addRow(input);*/
					int a = ++Rnum;
					String s1 = FT.getText();
					
					bowl.add(makeInVector(new String[] {""+Rnum,""+s1,"0",""+(String)CB.getSelectedItem(),"X"}));
					for(int i = 0; i<5;i++) {
						Contents.addAll(bowl);
						bowl.clear();
					}
						
					dtm.setDataVector(Contents, Header);
					FT.setText("");
					PST.setText("");
				
					
				}
				
		}
		if(F == true) {
			 if (e.getSource() == btn1){//입장
				 	
				 if(pw.equals(RSP.getText())) {
						wt.sendMsg();
					 	cf.isFirst = false;
					 	cf.setVisible(true);
					 	//this.hide();
					 	F = false;
						PST.setEditable(F);
		               //this.hide();
						this.hide();	//쓰던것들을 숨기기
						
					 
				 }
			 }
		}
		
		
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		 int row = table.getSelectedRow();
		 String value = (String)dtm.getValueAt(row,1) ;
		 pass = (String)dtm.getValueAt(row,4) ;
		 RFT.setText(""+value);
		 if(pass.equals("O")) {
			 F = true;
			 RSP.setEditable(F);
			
		 }
		
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override//마우스가 버튼 안으로 들어오면 빨간색으로 바뀜
    public void mouseEntered(MouseEvent e) {
       
    }
    @Override//마우스가 버튼 밖으로 나가면 노란색으로 바뀜
    public void mouseExited(MouseEvent e) {
        
    }

	
	
}

public class ClientFrame extends JFrame implements ActionListener{
       JTextArea txtA = new JTextArea();
       JTextField txtF = new JTextField(15);
       JButton btnTransfer = new JButton("전송");
       JButton btnChb = new JButton("바꾸자");
       JButton btnExit = new JButton("닫기");
       boolean isFirst=true;
       JPanel p1 = new JPanel();
       Socket socket;
       WriteThread wt;
       register rg;
      
       
       public ClientFrame(Socket socket) {
             super("채팅이나 해볼까");
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
             //메세지를 전송하는 클래스 생성
             btnTransfer.addActionListener(this);
             btnExit.addActionListener(this);
             setDefaultCloseOperation(EXIT_ON_CLOSE);
             setBounds(300, 300, 350, 300);
             setVisible(false); 
       }

      

       public void actionPerformed(ActionEvent e){
    	 
             String id = Id.getId();
             if(e.getSource()==btnTransfer){//전송버튼 눌렀을 경우
                    //메세지 입력없이 전송버튼만 눌렀을 경우
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







