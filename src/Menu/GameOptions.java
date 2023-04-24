package Menu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Controllers.Map;
import Gui.AudioOptionMenu;
import Gui.PauseButton;
import Gui.UrmButton;
import Main.Game;
import static Controllers.Controller.Gui.UrmButtons.*;
public class GameOptions extends Status implements StatusMethod{
	private AudioOptionMenu audioOptionMenu;
	private BufferedImage backGrImg,optionBackGr;
	private int bgX,bgY,bgW,bgH;
	private	UrmButton menuB;
	public GameOptions(Game game) {
		super(game);
		audioOptionMenu=game.getAudioOption();	
		loadImg();
		loadButons();
	}

	private void loadButons() {
		int menuX = (int) (387 * Game.SCALE);
		int menuY = (int) (325 * Game.SCALE);

		menuB = new UrmButton(menuX, menuY, URM_SOUND_SIZE, URM_SOUND_SIZE, 2);
		
	}

	private void loadImg() {
		backGrImg=Map.GetMap(Map.MENU_BACKGROUND_GAME);
		optionBackGr=Map.GetMap(Map.OPTION_MENU);
		bgW=(int) (optionBackGr.getWidth()*Game.SCALE);
		bgH=(int) (optionBackGr.getHeight()*Game.SCALE);
		bgX=Game.WIDTH_SIZE/2-bgW/2;
		bgY=(int) (33*Game.SCALE);
		
	}

	@Override
	public void update() {
		menuB.update();
		audioOptionMenu.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backGrImg, 0, 0, Game.WIDTH_SIZE, Game.HEIGHT_SIZE, null);
		g.drawImage(optionBackGr, bgX, bgY, bgW, bgH, null);
		menuB.draw(g);
		audioOptionMenu.draw(g);
	}
	public void mouseDragged(MouseEvent e) {
		audioOptionMenu.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		} else
			audioOptionMenu.mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed())
				GameStatus.status = GameStatus.MENU;
		} else
			audioOptionMenu.mouseReleased(e);

		menuB.resetBoolButton();		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOptionMenu.mouseMoved(e);		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			GameStatus.status = GameStatus.MENU;		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
