package Gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import Controllers.Map;
import Main.Game;
import Menu.Continue;
import Menu.GameStatus;

import static Controllers.Controller.Gui.UrmButtons.*;
public class PauseGame {
	private Continue continuee;// khai bao trang thai dang choi
	private BufferedImage PauseGameBackground;
	private AudioOptionMenu audioOptionMenu;
	
	private int xBgr,yBgr,BgrWidth,BgrHeight;
	private UrmButton menuB,relayB,unPauseB,saveB;
	public PauseGame(Continue continuee) {
		this.continuee=continuee;
		audioOptionMenu=continuee.getGame().getAudioOption();
		loadBackground();
		creatUrmButton();
		
	}
	private void creatUrmButton() {
		int saveY=yBgr+URM_SOUND_SIZE+30;
		int saveX=xBgr+BgrWidth+10;
		int menuX= (int)(313* Game.SCALE);
		int relayX= (int)(387 * Game.SCALE);
		int unPauseX= (int)(462 *Game.SCALE);
		int bY=(int) (325* Game.SCALE);
		menuB = new UrmButton(menuX, bY, URM_SOUND_SIZE, URM_SOUND_SIZE, 2);
		relayB = new UrmButton(relayX, bY, URM_SOUND_SIZE, URM_SOUND_SIZE, 1);
		unPauseB = new UrmButton(unPauseX, bY, URM_SOUND_SIZE, URM_SOUND_SIZE, 0);
		saveB = new UrmButton(saveX, saveY, URM_SOUND_SIZE,URM_SOUND_SIZE, 3);
	}
	private void loadBackground() {
		PauseGameBackground = Map.GetMap(Map.MENU_PAUSE);
		BgrWidth =(int) (PauseGameBackground.getWidth()*Game.SCALE);
		BgrHeight = (int) (PauseGameBackground.getHeight()*Game.SCALE);
		xBgr = Game.WIDTH_SIZE/2 - BgrWidth/2;
		yBgr =(int) (25* Game.SCALE);
	}
	public void update() {
		menuB.update();
		relayB.update();
		unPauseB.update();
		saveB.update();
		audioOptionMenu.update();
	}
	public void draw(Graphics g) {
		// vẽ menu
		g.drawImage(PauseGameBackground, xBgr, yBgr, BgrWidth, BgrHeight, null);
		// vẽ button
		menuB.draw(g);
		relayB.draw(g);
		unPauseB.draw(g);
		saveB.draw(g);
		audioOptionMenu.draw(g);
	}
	public void mouseDragged(MouseEvent e) {// khi khéo chuột thì thay đổi vị trí của x
		audioOptionMenu.mouseDragged(e);
	}
// sau khi sảy ra các sự kiện chuột cần phải update lại trạng thái
	public void mousePressed(MouseEvent e) {
		 if(isIn(e,menuB))
			menuB.setMousePressed(true);
		else if(isIn(e,relayB))
			relayB.setMousePressed(true);
		else if(isIn(e,unPauseB))
			unPauseB.setMousePressed(true);
		else if(isIn(e,saveB))
			saveB.setMousePressed(true);
		else
			audioOptionMenu.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {//khi nhả chuột
		
		if(isIn(e,menuB)) {
			if(menuB.isMousePressed()) {// đang trong vị trí của musibutton 
				GameStatus.status = GameStatus.MENU;// set chuyển trạng thái cho musicButton
				continuee.unPauseGame();// sau khi chuyen sang menu thi trang thai cua game van la paused nen can phai set lai
			}
		}
		else if(isIn(e,relayB)) {
			if(relayB.isMousePressed()) {// đang trong vị trí của musibutton 
				continuee.getPlayer().resetAll();// set chuyển trạng thái cho musicButton
				continuee.getEnemyManager().resetAll();
				continuee.unPauseGame();
			}
		}
		else if(isIn(e,unPauseB)) {
			if(unPauseB.isMousePressed()) {// đang trong vị trí của musibutton 
				continuee.unPauseGame();;// set chuyển trạng thái cho musicButton
			}
		}
		else if(isIn(e,saveB)) {
			if(saveB.isMousePressed()) {// đang trong vị trí của musibutton 
				try {
					continuee.getGame().getDatabase().collectDataForGame();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else 
			audioOptionMenu.mouseReleased(e);
		
		menuB.resetBoolButton();
		relayB.resetBoolButton();
		unPauseB.resetBoolButton();
		saveB.resetBoolButton();		
	}

	public void mouseMoved(MouseEvent e) {	
		menuB.setMouseOver(false);
		relayB.setMouseOver(false);
		unPauseB.setMouseOver(false);
		saveB.setMouseOver(false);
		if(isIn(e,menuB))
			menuB.setMouseOver(true);
		else if(isIn(e,relayB))
			relayB.setMouseOver(true);
		else if(isIn(e,unPauseB))
			unPauseB.setMouseOver(true);
		else if(isIn(e,saveB))
			saveB.setMouseOver(true);
		else 
			audioOptionMenu.mouseMoved(e);
	}
	private boolean isIn(MouseEvent e, PauseButton b) {// check tại vị trí xảy ra sự kiện
		 return b.getBounds().contains(e.getX(), e.getY());//kiểm tra điều kiện bounds có chứa vị trí xảy ra sự kiện ko
		 
	}
}
