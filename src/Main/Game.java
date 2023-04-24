package Main;

import java.awt.Graphics;
import java.sql.SQLException;

import Gui.AudioOptionMenu;
import Level.LevelManager;
import Menu.Continue;
import Menu.GameOptions;
import Menu.GameStatus;
import Menu.Menu;
import SQL.Database;
import objects.Player;

public class Game implements Runnable{
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private Player player;
	private LevelManager levelManager;
	private Database database;

	private Continue continuee; // TRONG GAME CO THEM 2 TRANG THAI DO LA continue va menu de thuc hien chuyen
															// doi giua 2 trang thai
	private Menu menu;
	private AudioOptionMenu audioOption;
	private GameOptions gameOptions;
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;// CHIA CHIEU RONG THANH 26 O
	public final static int TILES_IN_HEIGHT = 14;// CHIEU CAO THANH 14 O
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int WIDTH_SIZE = TILES_SIZE * TILES_IN_WIDTH;
	public final static int HEIGHT_SIZE = TILES_SIZE * TILES_IN_HEIGHT;

	private final int FPS_Set = 120;// trong 1 giây ta sẽ sét cho 120 khung hình chạy
	// dùng biến final vì không cần ghi đè cũng như là không thể thay đổi vào biến
	// này
	private final int UPS_Set = 200;// Trong 1 giây sẽ thực hiện 200 lần cập nhật khung hình tức là giữa 2 lần cập
	// nhật sẽ cách nhau 1/200s; như vậy để giảm độ chễ của game

	public Game() {
		initStatus();// khoi tao cac trang thai cho game
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);// đảm bảo rằng các sự kiện bàn phím nhận được
		gamePanel.requestFocus();// lấy giá trị của inputfocus
		startGameLoop();
		initDatabae();
	}

	private void initDatabae() {
		database = new Database(this);
		try {
			database.setDataForGame();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void initStatus() {// khoi tao cac trang thai cho game
		audioOption = new AudioOptionMenu(this);
		menu = new Menu(this);	
		continuee = new Continue(this);	
		gameOptions = new GameOptions(this);

	}

	public void update() {
		switch (GameStatus.status) {// trang thai cua game la Menu thi update
			case MENU:
				menu.update();
				break;
			case CONTINUE:// trang thai cua game la continue thi update
				continuee.update();
				break;
			case OPTIONS:
				gameOptions.update();
				break;
			case QUIT:
			default:
				System.exit(0);
				break;

		}
	}

	public void render(Graphics g) {// trang thai cua game la Menu thi render
		switch (GameStatus.status) {//
			case MENU:
				menu.draw(g);
				break;
			case CONTINUE:// trang thai cua game la continue thi render ra game tiep tuc choi
				continuee.draw(g);
				break;
			case OPTIONS:
				gameOptions.draw(g);
				break;
			default:
				break;
		}
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {// 1s=10^9 nano s
		long previousTime = System.nanoTime();
		long lastCheck = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		double deltaU = 0;
		double deltaF = 0;
		double timeFerFrame = 1000000000.0 / FPS_Set; // đây là một biến lưu trữ thời lượng mà mỗi khung hình sẽ kéo dài
		// mỗi khung hình sẽ tồn tại trong bao lâu( 1 giây chia cho số khung hình)
		// và chúng sẽ hiển thị sau vài giây. và chúng ta cần nano giây trong mỗi khung
		// hình. tức là mỗi khung hình
		// sẽ tồn tại trong bao nhiêu nano giây.
		// khi sử lý 1 vòng lặp rất nhỏ trong trò chơi có thể tính bằng milisecond điều
		// này có thể chúng ta sẽ không làm
		// được gì nên chúng ta cần phải sét thời gian chạy một khung hình là vô cùng
		// nhỏ
		// chúng ta cần kiểm tra khung hình trước đó cho đến bây giờ khi chúng ta chạy
		// nó nếu thời gian đó trooi qua
		// cho đến khi khung hình mới vào vì vậy chúng ta thực hiện 1 lần làm mới màn
		// hình trò chơi trong trường hợp này rồi vẽ lại
		double timeFerUpdate = 1000000000.0 / UPS_Set;
		while (true) {
			long currentTime = System.nanoTime();
			deltaU += (currentTime - previousTime) / timeFerUpdate;
			deltaF += (currentTime - previousTime) / timeFerFrame;
			previousTime = currentTime;
			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;

			}

			// tạo một vòng lặp vô hạn
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				// System.out.println("FPS "+frames +" || UPS "+ updates);
				lastCheck = System.currentTimeMillis();
				frames = 0;
				updates = 0;
			}

		}
	}

	public void windowFocusLost() {// tiêu điểm của game ở ngoài
		if (GameStatus.status == GameStatus.CONTINUE)// nếu trạng thái của game đang là chơi game
			continuee.getPlayer().resetDirectionBoolean();// cho nhân vật không di truyển được khi mất tiêu điểm
	}

	public Menu getMenu() {// lay trang thai menu cua game
		return menu;
	}

	public Continue getContinue() {
		return continuee;// lay trang thai continue cua game
	}

	public Database getDatabase() {
		return database;
	}

	public AudioOptionMenu getAudioOption() {
		return audioOption;
	}

	public GameOptions getGameOptions() {
		return gameOptions;
	}

}
