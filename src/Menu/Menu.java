package Menu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Controllers.Map;
import Gui.MenuButton;
import Main.Game;

public class Menu extends Status implements StatusMethod{
	private MenuButton[] buttons = new MenuButton[3];
	private MenuButton menuButtonNewGame;
	private BufferedImage backgroundImg,backgroundMenu;
	private int menuX, menuY, menuWidth, menuHeight;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
	}

	private void loadBackground() {
		backgroundMenu=Map.GetMap(Map.MENU_BACKGROUND_GAME);
		backgroundImg = Map.GetMap(Map.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.WIDTH_SIZE / 2 - menuWidth / 2;
		menuY = (int) (45 * Game.SCALE);

	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.WIDTH_SIZE / 2, (int) (160 * Game.SCALE), 3, GameStatus.CONTINUE);//0,1,2 là rowindex
		buttons[1] = new MenuButton(Game.WIDTH_SIZE / 2, (int) (280 * Game.SCALE), 1, GameStatus.OPTIONS);// [i] chính là chỉ số
		buttons[2] = new MenuButton(Game.WIDTH_SIZE / 2, (int) (340 * Game.SCALE), 2, GameStatus.QUIT);
		menuButtonNewGame = new MenuButton(Game.WIDTH_SIZE / 2, (int) (220 * Game.SCALE),0 , GameStatus.CONTINUE);
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
		menuButtonNewGame.update();
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(backgroundMenu, 0, 0, Game.WIDTH_SIZE, Game.HEIGHT_SIZE, null);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight+70, null);
		for (MenuButton mb : buttons)
			mb.draw(g);
		menuButtonNewGame.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
		if (isIn(e, menuButtonNewGame))
			menuButtonNewGame.setMousePressed(true);
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGameStatus();// nếu nút nào được áp dụng thì gọi tra trạng thái cho nut đấy nếu trạng thái cho nút đấy là gì đấy thì phải set âm thanh cho trạng thái ấy
				if(mb.getStatus()==GameStatus.CONTINUE)// cần phải tạo một phương thức lấy trạng thái của game hiện tại ở lớp menubutton để
					game.getAudioPlayer().setLevelSong(game.getContinue().getLevelManager().getLevelIndex());
				break;
			}
		}
		if (isIn(e, menuButtonNewGame)) {// thiết lập new game cho game
			if (menuButtonNewGame.isMousePressed()) {
				menuButtonNewGame.applyGameStatus();
				game.getContinue().resetAll();
				game.getContinue().getLevelManager().resetLevel();
			}
				
		}
		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();
		
		menuButtonNewGame.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);
		menuButtonNewGame.setMouseOver(false);
		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		if(isIn(e, menuButtonNewGame))
			menuButtonNewGame.setMouseOver(true);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			GameStatus.status = GameStatus.CONTINUE;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
