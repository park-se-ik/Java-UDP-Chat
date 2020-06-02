import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;


public class Emoticon extends JFrame {
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
	public Emoticon() {
		
		super("이모티콘");
		
		//ToolbarHandler tbh = new ToolbarHandler();

		JToolBar toolbar = new JToolBar();

		toolbar.setBounds(0,0,500,100);

		JButton btn1 = new JButton();
		JButton btn2 = new JButton();

		
		btn1.setIcon(Fkakao);

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("2번이모티콘선택함");		
			}

		});


		btn2.setIcon(SFkakao);

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("2번이모티콘선택함");		
			}

		});

		//btn1.setBounds(x, y, width, height);

		toolbar.add(btn1);
		toolbar.add(btn2);

		this.add(toolbar);

		this.setBounds(0,0,300,250);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

		}

	
			
			public static void main(String[] args) {
				// TODO Auto-generated method stub
				new Emoticon();

			}



		

			

}


