package main;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window{
	
	public static int WIDTH = 890, HEIGHT = 629;
	private Board board;
	private Board1 board1;
	private Title title;
	static JFrame window;
	JPanel mainPanel;
	
	public Window(){
		window = new JFrame("Tetris");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);	
		
		board = new Board();
		board1 = new Board1();

		title = new Title(this);
		
		window.addKeyListener(board);
		window.addKeyListener(board1);
		window.addMouseMotionListener(title);
		window.addMouseListener(title);
		window.add(title);
		window.setVisible(true);
	}
	
	public void startTetris(){
		WIDTH = 445;
		window.setSize(WIDTH, HEIGHT);
		window.remove(title);
		
		window.addMouseMotionListener(board);
		window.addMouseListener(board);
		window.add(board);
		
		board.startGame();
		window.revalidate();
	}
	
	public void startTetrisMulti(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		window.remove(title);
		
		window.addMouseMotionListener(board);
		window.addMouseListener(board);
		mainPanel.add(board);
		
		window.addMouseMotionListener(board1);
		window.addMouseListener(board1);
		mainPanel.add(board1);
		
		window.add(mainPanel);

		board.startGame();
		board1.startGame();

		window.revalidate();
	}
	
	public static JFrame getWindow() {
		return window;
	}
	public static int setWidth(int width) {
		return WIDTH = width;
	}
	
	public static void main(String[] args) {
		new Window();
	}
	
}
