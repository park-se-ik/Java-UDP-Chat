import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Emoticon extends JFrame implements ActionListener {
	Image kakao  = Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\kakaofriend.jpg");
	Image kakao1 = kakao.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	ImageIcon Fkakao = new ImageIcon(kakao1);
	
	Image Skakao  = Toolkit.getDefaultToolkit().getImage("D:\\Downloads\\kaka.png");
	Image Skakao1 = kakao.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	ImageIcon SFkakao = new ImageIcon(Skakao1);
	JButton btn1 = new JButton();
	JButton btn2 = new JButton();

	ImageIcon img = new ImageIcon();
	ImageIcon img1 = new ImageIcon();
	
	static boolean find = false;
	static int i=0;	
	public Emoticon() {
	
		super("이모티콘");
		Container con = getContentPane();
		
		con.setLayout(new BoxLayout(con, BoxLayout.X_AXIS));
        
           
		
		btn1.setIcon(Fkakao);
		con.add(btn1);    
		btn1.addActionListener(this);
		
		btn2.setIcon(SFkakao);
		con.add(btn2);
		btn2.addActionListener(this);

		this.setBounds(0,0,300,250);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
		
		}

	
			
		public static void main(String[] args) {
				// TODO Auto-generated method stub
				new Emoticon();

		}
		static public int getImg() {
			return i;
			
		}
		/*class ToolbarHandler implements ActionListener{

			@Override

			public void actionPerformed(ActionEvent e) {

				if(e.getActionCommand().equals(btn1)){
					System.out.println("툴바눌림");

				}

			}

		}*/
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==btn1)
				System.out.println("1번이모티콘선택함");	
			else if(e.getSource()==btn2)
				System.out.println("2번이모티콘선택함");		

		}

	
}


