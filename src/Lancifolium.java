

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import core.GnCalculate;

@SuppressWarnings("serial")
public class Lancifolium extends JFrame {
	public GnCalculate mainbord; // 
	//public JavaLancifolium lancval; // 
	public int colour;
	
	public ReserveGameBoard gameBoard;
	
	public JMenuBar menuBar;
	public JMenu[] menu = { new JMenu("File"), new JMenu("help")};
	public JMenuItem[] filemenu = { new JMenuItem("open file"), new JMenuItem("Refresh")};
	public JMenuItem[] helpmenu = { new JMenuItem("web"), new JMenuItem("move")};
	
	MouseClicked mclick = new MouseClicked();

	public Lancifolium() {
		// TODO Auto-generated constructor stub
		mainbord = new GnCalculate();
		this.colour = 1;
		
		this.setTitle("Lancifolium Manuals");
		this.setSize(660, 660);
		this.setResizable(false);
		System.out.println("after size");
		
		menuBar = new JMenuBar();
		filemenu[0].setActionCommand("MENU_OPEN_FILE_");
		filemenu[1].setActionCommand("REFRESH_");
		helpmenu[0].setActionCommand("WEB_");
		helpmenu[1].setActionCommand("MOV_");
		menu[0].add(filemenu[0]);
		menu[0].add(filemenu[1]);
		menu[1].add(helpmenu[0]);
		menu[1].add(helpmenu[1]);
		this.setJMenuBar(menuBar);

		gameBoard = new ReserveGameBoard();
		this.getContentPane().add(new JPanel(), "West"); // 左邊添加條目
		this.getContentPane().add(new JPanel(), "East"); // 右邊添加一條
		this.getContentPane().add(gameBoard, "Center");
		
		gameBoard.addMouseListener(mclick);
		
		this.setLocation(300, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	class ReserveGameBoard extends JPanel {
		public ImageIcon backmap;
		public ImageIcon ston_bmap;
		public ImageIcon ston_wmap;
		public int current;
		
		public ReserveGameBoard() { // 
			backmap = new ImageIcon(getClass().getResource("sources/bord.png"));
			ston_bmap = new ImageIcon(getClass().getResource("sources/stb.png"));
			ston_wmap = new ImageIcon(getClass().getResource("sources/stw.png"));
			current = -1;
		}
		public void paint(Graphics graphic) {
			super.paint(graphic);
			backmap.paintIcon(this, graphic, 10, 10); // 
			System.out.println("Before draw bord" + mainbord.siz);
			graphic.setColor(Color.BLUE);
			for (int tmpx = 0; tmpx < mainbord.siz; tmpx++) {
				for(int tmpy = 0; tmpy < mainbord.siz; tmpy++) {
					if (mainbord.ston[tmpx][tmpy] == 1) {
						ston_bmap.paintIcon(this, graphic, 26 + 30 * tmpx, 26 + 30 * tmpy);
					}
					else if (mainbord.ston[tmpx][tmpy] == 2)
						ston_wmap.paintIcon(this, graphic, 26 + 30 * tmpx, 26 + 30 * tmpy);
				}
			}
			if (current >= 0) {
				graphic.fillRect(31 + 30 * (current / 100), 31 + 30 * (current % 100), 18, 18);
			}
		}
	}
	
	class MouseClicked extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			int drpx = evt.getX();
			int drpy = evt.getY();
			if (drpx < 25 || drpx > 595) {
				System.out.println("X: " + drpx); return;
			}
			if (drpy < 25 || drpy > 595) {
				System.out.println("Y: " + drpx); return;
			}
			int mov = ((drpx - 25) / 30 * 100 + (drpy - 25) / 30); // 
			System.out.println(drpx+" "+drpy + " " + mov);
			
			switch(mainbord.configDropStone(colour, mov)) {
			case 0:
				return;
			default:
				if (colour == 1) colour = 2;
				else colour = 1;
				gameBoard.current = mov;
			}
			gameBoard.repaint();
		}
	}

	public static void main(String[] args) {
		new Lancifolium();
	}
}

