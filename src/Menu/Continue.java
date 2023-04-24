package Menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Controllers.Map;
import Gui.GameOverMenu;
import Gui.LevelCompleteMenu;
import Gui.PauseGame;
import Level.LevelManager;
import Main.Game;
import objects.EnemyManager;
import objects.Player;
import static Controllers.Controller.Gui.Cloud.*;
public class Continue extends Status implements StatusMethod{
	private Player player;// trang thai continue cua game se co player va quan ly map
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseGame pauseGame;
	private GameOverMenu gameOverMenu;
	private LevelCompleteMenu levelCompleteMenu;
	private boolean paused = false ;// biến này cho biết khi nào sẽ chuyển sang màn hình pause game;
	private boolean levelCompleted;
	private boolean playerDying;
	
	private int xLevelOffset;// đây là vị trí bản đổ trên trục x
	private int leftBorder=(int) (0.2*Game.WIDTH_SIZE); // nếu vị trí nhân vật đang đứng cách đường biên giới trái lớn hơn 20% chiều rộng jframe thì không cần phải chuyển khung hình
	private int righttBorder=(int) (0.8*Game.WIDTH_SIZE);// nếu vị trí nhân vật nhỏ hơn biên giới của bên phải thì không cần phải vẽ hình và ngược lại
//	private int levelTilesWide= Map.getLevelData()[0].length; // số lượng ô vuông trên chiều rộng của bản đồ
//	private int maxTilesOffset = levelTilesWide -Game.TILES_IN_WIDTH; //số ô vuông tối đa mà bản đồ có thể di chuyển theo chiều rộng.
	private int maxLevelOffset; 
	private boolean gameOver;
	private BufferedImage backGrSky,bigClouds,smallClouds;
	private int[] someSmallClouds;
	private Random randomPosYClouds= new Random();
	public Continue(Game game) {// khoi tao trang thái cho continue vi continue là trạng thái đang chơi game nên cần khởi tạo nhân vật, map, vị trí
		super(game);
		initStaus();// khoi tao nhan vat, map
		importBackGrSky();
		calcLevelOffset();
		loadStartLevel();
		
	}


	private void loadStartLevel() {// tải hình ảnh cho levelmap
		enemyManager.addEnemies(levelManager.getCurrentLevel());
	}
	public void loadNextLevel() {// sau khi hoàn thành 1 level thì sẽ sét cho nó một vị trí
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());// vị trí mà người chơi sẽ xuất hiện tại map mới
	}
	private void calcLevelOffset() {// tính toán các khoảng bù cho từng cấp độ bản đồ
		maxLevelOffset=levelManager.getCurrentLevel().getLevelOffset();
	}

	private void importBackGrSky() {
		backGrSky = Map.GetMap(Map.BACKGROUND_SKY);
		bigClouds = Map.GetMap(Map.BIG_CLOUDS);
		smallClouds= Map.GetMap(Map.SMALL_CLOUDS);
		someSmallClouds = new int[8];
		for(int i=0;i<someSmallClouds.length;i++)
			someSmallClouds[i]=(int)(70*Game.SCALE)+ randomPosYClouds.nextInt((int)(100*Game.SCALE));// randomPos.nextInt dùng để sinh ra 1 số nguyên bất kì trong phạm vi trong ngoặc
		
	}

	private void initStaus() {// khởi tạo các thực thể, nhân vật, các trạng thái menu của continue tức là màn hình game đang chơi
		levelManager = new LevelManager(game);// tao map cho nhan vat
		enemyManager = new EnemyManager(this);
		player = new Player(100,200,(int)(64*Game.SCALE),(int)(40*Game.SCALE),this);// khởi tạo nhân vật
		player.setLevelData(levelManager.getCurrentLevel().getLevelData());//set trị số id của khung hình mà nhân vật đang đứng
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());//vị trí mà người chơi sẽ xuất hiện tại map mới	
		pauseGame = new PauseGame(this);
		gameOverMenu = new GameOverMenu(this);
		levelCompleteMenu =new LevelCompleteMenu(this);
	}

	@Override
	public void update() {// update cho map đang choi như là map, nhân vật
		if(paused) {
			pauseGame.update();
		}
		else if(levelCompleted) {
			levelCompleteMenu.update();			
		}
		else if(gameOver) {
			
			gameOverMenu.update();
		}	
		else if(playerDying) {
			player.update();
		}
		else if(!gameOver) {
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkNearBorder();
		}
	
	}

	private void checkNearBorder() {
		int playerX = (int) player.getHitBox().x;
		int deltaX = playerX-xLevelOffset; //kiểm tra độ lệch toạ độ của nhân vật và vị trí của map hiện tại
		if(deltaX >righttBorder)// nếu vị trí của nhân vật lớn hơn biên phải thì toạ độ của Map thì di truyển toạ độ map một khoảng chính bằng tốc độ của nhân vật
			xLevelOffset+=deltaX -righttBorder;
		if(deltaX<leftBorder)
			xLevelOffset+=deltaX-leftBorder;
		if(xLevelOffset>maxLevelOffset)// làm cho levelOffset luôn nằm trong map
			xLevelOffset=maxLevelOffset;
		else if(xLevelOffset<0)
			xLevelOffset=0;
		// sau khi kiểm tra xong rồi cần phải xử lý cho thằng quản lý map
	}
//	public void DisPlayPos() {
//		System.out.println("xlevelOffset:"+ xLevelOffset);
//		System.out.println("xPlayer:"+ player.getHitBox().x);
//		for(Crabby c : Map.getCrabbies())
//			System.out.println("du lieu cua ke thu:"+ c.getHitBox().x);
//		
//	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(backGrSky, 0, 0, Game.WIDTH_SIZE, Game.HEIGHT_SIZE, null);
		drawClouds(g);
		levelManager.draw(g,xLevelOffset);
		enemyManager.draw(g,xLevelOffset);
		player.render(g,xLevelOffset);
		if(paused) {
			
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.WIDTH_SIZE, Game.HEIGHT_SIZE);
			pauseGame.draw(g);
		}
		else if(gameOver)
			gameOverMenu.draw(g);
		else if(levelCompleted)
			levelCompleteMenu.draw(g);
		
	}

	private void drawClouds(Graphics g) {
		for(int i=0;i<3;i++)
			g.drawImage(bigClouds, i* BIG_CLOUDS_WIDTH-(int)(0.3*xLevelOffset), (int) (204*Game.SCALE), BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT, null);
		for(int i=0;i<someSmallClouds.length;i++ )
			g.drawImage(smallClouds, i*SMALL_CLOUDS_WIDTH*4-(int) (0.6*xLevelOffset), someSmallClouds[i], SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		if (e.getButton() == MouseEvent.BUTTON1)
//			player.setActtack(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverMenu.keyPress(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:// nếu keyListenner la space thì chuyển status game thành menu khi đó thuộc tính status trong game sẽ là menu và các phương thức
				// của status menu sẽ được thực hiện
				paused = !paused;
				break;
			case KeyEvent.VK_J:
				player.setActtack(true);
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(gameOver)
			gameOverMenu.keyPress(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			}

	}

	@Override // cần phải gọi ra các sự kiện của từng button sau khi gọi rồi cần update cho thằng pause game
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			
			if(paused)// nếu đang ở trong trạng thái pause
				pauseGame.mousePressed(e);	
			else if(levelCompleted)
				levelCompleteMenu.mousePressed(e);
			}
		else  {
			gameOverMenu.mousePressed(e);
		}
	}	

	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
		{
			if(paused)// nếu đang ở trong trạng thái pause
				pauseGame.mouseDragged(e);	
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver)
		{
			if(paused)// nếu đang ở trong trạng thái pause
				pauseGame.mouseReleased(e);	
			else if(levelCompleted)
				levelCompleteMenu.mouseReleased(e);
			
		}
		else  {
			gameOverMenu.mouseReleased(e);
	}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver){
			if(paused)// nếu đang ở trong trạng thái pause
				pauseGame.mouseMoved(e);	
			else if(levelCompleted)
				levelCompleteMenu.mouseMoved(e);
			
		}
		else {
				gameOverMenu.mouseMoved(e);;
		}

	}
	public void setLevelOffset(int levelOffset) {// thiết lập phần bù để căn giữa
		this.maxLevelOffset=levelOffset;
	}
	public void unPauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {// khi game bị mất tiêu điểm
		player.resetDirectionBoolean();
	}

	public Player getPlayer() {
		return player;
	}

	public void resetAll() {
		//resest continue, enemy,levl...
		playerDying=false;
		levelCompleted=false;
		gameOver=false;
		paused=false;
		player.resetAll();
		enemyManager.resetAll();
//		levelManager.resetLevel();// cập nhật lại game có thể comment lại
	}
	public void checkEnemyDamage(Rectangle2D.Float acttackBox) {// kiểm tra xe nhân vật có đang nhận sát thương của kẻ thù không
		enemyManager.checkPlayerHit(acttackBox);
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver=gameOver;
	}
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.levelCompleted=levelCompleted;
		if(levelCompleted)
			game.getAudioPlayer().lvlCompleted();
		
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}


	public boolean isGameOver() {
		return gameOver;
	}


	public void setPlayerDying(boolean b) {
		this.playerDying=b;
		
	}
}
