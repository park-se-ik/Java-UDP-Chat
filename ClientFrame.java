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
import javax.swing.JOptionPane; //���â�޽���
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;


 class Id extends JFrame implements ActionListener{
	 	
        
            
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
		WriteThread wt;
		ClientFrame cf;
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
		File file = new File("D:\\java�ǽ�\\ChatProgram\\Writer.txt");
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
	            new Id(wt,cf);
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

 
 
 
 
class NewWindow extends JFrame implements ActionListener,ItemListener,MouseListener{
	String number[] = {"2","3","4"}; 
	private JTextField t1;
	private JPanel panel= new JPanel();
	private JButton btn  = new JButton("����");
	private JButton btn1  = new JButton("����");
	private JPanel Lpanel0 = new JPanel();
	private JPanel Rpanel0 = new JPanel();
	private JPanel panel1 = new JPanel();
	private JLabel Flabel0 = new JLabel("ä��â�̸�");
	private JLabel Slabel0 = new JLabel("��й�ȣ");
	private JLabel Flabel01 = new JLabel("ä��â�̸�");
	private JLabel Slabel01 = new JLabel("��й�ȣ");
	private JCheckBox CHB = new  JCheckBox("");
	private JTextField FT = new JTextField(20);
	private JPasswordField PST = new JPasswordField(18);
	private JTextField RFT = new JTextField(20);
	private JPasswordField RSP = new JPasswordField(20);
	private JTable table;
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
		

	
	
}

public class ClientFrame extends JFrame implements ActionListener{
       JTextArea txtA = new JTextArea();
       JTextField txtF = new JTextField(15);
       JButton btnTransfer = new JButton("����");
       JButton btnChb = new JButton("�ٲ���");
       JButton btnExit = new JButton("�ݱ�");
       boolean isFirst=true;
       JPanel p1 = new JPanel();
       Socket socket;
       WriteThread wt;
       register rg;
      
       
       public ClientFrame(Socket socket) {
             super("ä���̳� �غ���");
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
             //�޼����� �����ϴ� Ŭ���� ����
             btnTransfer.addActionListener(this);
             btnExit.addActionListener(this);
             setDefaultCloseOperation(EXIT_ON_CLOSE);
             setBounds(300, 300, 350, 300);
             setVisible(false); 
       }

      

       public void actionPerformed(ActionEvent e){
    	 
             String id = Id.getId();
             if(e.getSource()==btnTransfer){//���۹�ư ������ ���
                    //�޼��� �Է¾��� ���۹�ư�� ������ ���
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







