package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Controllers.Map;
import Main.Game;
import Menu.Continue;
import Menu.GameStatus;
import audioPlayer.AudioPlayer;

import static Controllers.Controller.Gui.UrmButtons.*;

public class GameOverMenu {
	private Continue continuee;
	private UrmButton menu,tryAgain;
	private BufferedImage img;
	private int bgX,bgY,bgW,bgH;
	
	public GameOverMenu(Continue continuee) {
		this.continuee = continuee;
		loadImgBg();
		initButton();
	}
	private void initButton() {
		int menuX= (int)(330* Game.SCALE);
		int tryAgainX= (int)(445 * Game.SCALE);
		int y=(int) (195* Game.SCALE);
		tryAgain = new UrmButton(tryAgainX, y, URM_SOUND_SIZE, URM_SOUND_SIZE, 0);// chỉ số hàng
		menu = new UrmButton(menuX, y, URM_SOUND_SIZE, URM_SOUND_SIZE, 2);// chỉ số hàng hoạt ảnh
	}
	private void loadImgBg() {
		img = Map.GetMap(Map.GAMEOVER_MENU);
		bgW=(int) (img.getWidth()*Game.SCALE);
		bgH=(int)(img.getHeight()*Game.SCALE);
		bgX=Game.WIDTH_SIZE/2- bgW/2;
		bgY=(int) (75*Game.SCALE);	
		
	}
	public void update() {
		menu.update();
		tryAgain.update();
		continuee.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
	}
	public void draw(Graphics g) {
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, Game.WIDTH_SIZE, Game.HEIGHT_SIZE);
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		menu.draw(g);
		tryAgain.draw(g);	
	}
	public void keyPress(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {		
			continuee.resetAll();
			GameStatus.status=GameStatus.MENU;
			
		}	
	}
	private boolean isIn(UrmButton b,MouseEvent e) {
		return b.getBounds().contains(e.getX(),e.getY());
	}
	public void mouseMoved(MouseEvent e) {
		tryAgain.setMouseOver(false);
		menu.setMouseOver(false);
		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(tryAgain, e))
			tryAgain.setMouseOver(true);
		
	}
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(tryAgain, e))
			tryAgain.setMousePressed(true);
	}
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
		//	continuee.resetAll();
		//		continuee.loadNextLevel();
				continuee.setGameStatus(GameStatus.MENU);
			}		
		}
		else if(isIn(tryAgain, e)) {
			if(tryAgain.isMousePressed()) {
				continuee.getPlayer().resetAll();// set chuyển trạng thái cho musicButton
				continuee.getEnemyManager().resetAll();
				continuee.getItemManager().resetAll();
				continuee.unPauseGame();
				continuee.setPlayerDying(false);
				continuee.setGameOver(false);
				continuee.getGame().getAudioPlayer().setLevelSong(continuee.getLevelManager().getLevelIndex());
				
			}
		}
		menu.resetBoolButton();
		tryAgain.resetBoolButton();
	}
}
