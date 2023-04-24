package Main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Input.KeyBoardInputs;
import Input.MouseInputs;
import static Main.Game.*;

public class GamePanel extends JPanel{
	private Game game;
//	private Player player;
//	private BufferedImage imgMainer;
//	private float xDelta=100,yDelta=100;
//	private int statusTick,statusIndex,statusSpeed=60; //cho thời gian chạy 1 vòng lặp(sau khi hết thì nó sẽ chuyển sang 1 trạng thái mới) để có 4 hoạt ảnh chạy trong 1 giây
//	private BufferedImage[][] statusOfMainer;


	public GamePanel(Game game) {
//		importImg();
		this.game=game;
//		loadAnimation();//load các trạng thái của mainer vào mảng
		addKeyListener(new KeyBoardInputs(this));
		addMouseListener(new MouseInputs(this));
		addMouseMotionListener(new MouseInputs(this));
		setPanelSize();
	}
	private void setPanelSize() {
		Dimension size = new Dimension(WIDTH_SIZE,HEIGHT_SIZE);// là một lớp cho phép chúng ta khởi tạo kích cho các đối tượng trong game cụ thể ở đây là GamePanel không tính thanh tiêu đề
		// vì ta để 1 ô có kích thước là 40*40 nên ta chọn kích thước như vậy đề ta chia màn hình ra tỉ lệ chẵn các bức ảnh
		setMinimumSize(size);//kích thước nhỏ nhất cho phần tử
		setPreferredSize(size);//khích thước ưa thích của size
		setMaximumSize(size);//kích thước lớn nhất của phần tử
		System.out.println("size:" +WIDTH_SIZE +" * "+ HEIGHT_SIZE);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}
	public Game getGame() {
		return game;
	}
	
}
