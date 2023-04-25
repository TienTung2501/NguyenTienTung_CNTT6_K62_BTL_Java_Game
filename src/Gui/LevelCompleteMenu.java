package Gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Controllers.Map;
import Main.Game;
import Menu.Continue;
import Menu.GameStatus;
import audioPlayer.AudioPlayer;

import static Controllers.Controller.Gui.UrmButtons.*;

public class LevelCompleteMenu {
	private Continue continuee;
	private UrmButton menu,next;
	private BufferedImage img;
	private int bgX,bgY,bgW,bgH;
	
	
	public LevelCompleteMenu(Continue continuee) {
		this.continuee=continuee;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX= (int)(330* Game.SCALE);
		int nextX= (int)(445 * Game.SCALE);
		int y=(int) (195* Game.SCALE);
		next = new UrmButton(nextX, y, URM_SOUND_SIZE, URM_SOUND_SIZE, 0);// chỉ số hàng
		menu = new UrmButton(menuX, y, URM_SOUND_SIZE, URM_SOUND_SIZE, 2);// chỉ số hàng hoạt ảnh
		
	}

	private void initImg() {
		img = Map.GetMap(Map.LEVEL_COMPLETE_MENU);
		bgW=(int) (img.getWidth()*Game.SCALE);
		bgH=(int)(img.getHeight()*Game.SCALE);
		bgX=Game.WIDTH_SIZE/2- bgW/2;
		bgY=(int) (75*Game.SCALE);	
	}
	public void update() {
		next.update();
		menu.update();
	}
	public void draw(Graphics g) {
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		menu.draw(g);
		next.draw(g);
	}
	private boolean isIn(UrmButton b,MouseEvent e) {
		return b.getBounds().contains(e.getX(),e.getY());
	}
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(next, e))
			next.setMouseOver(true);
		
	}
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(next, e))
			next.setMousePressed(true);
	}
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
		//	continuee.resetAll();
		//		continuee.loadNextLevel();
				continuee.setGameStatus(GameStatus.MENU);
				continuee.getGame().getAudioPlayer().stopSong();
				continuee.getGame().getAudioPlayer().playSong(AudioPlayer.MENU_1);
			}		
		}
		else if(isIn(next, e)) {
			if(next.isMousePressed()) {
				continuee.loadNextLevel();
				continuee.getGame().getAudioPlayer().setLevelSong(continuee.getLevelManager().getLevelIndex());
			}
		}
		menu.resetBoolButton();
		next.resetBoolButton();
	}
}
