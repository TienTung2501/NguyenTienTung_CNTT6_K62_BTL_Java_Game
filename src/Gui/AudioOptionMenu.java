package Gui;

import Main.Game;
import static Controllers.Controller.Gui.VolumeButton.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import static Controllers.Controller.Gui.ButtonSound.*;
public class AudioOptionMenu {
	private Game game;
	private VolumeButton volumbutton;
	private ButtonSound musicButton,sfxButton;
	public AudioOptionMenu(Game game) {
		this.game=game;
		createSoundButton();
		CreateVolButton();
	}
	private void CreateVolButton() {
		int volX = (int)(309*Game.SCALE);
		int volY = (int)(278* Game.SCALE);
		volumbutton = new VolumeButton(volX, volY, CONTAINER_VOL_WIDTH_SIZE , VOLUME_HEIGHT_SIZE);
		
	}
	private void createSoundButton() {
		int soundX = (int) (450 * Game.SCALE);
		int soundY = (int) (140 *Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new ButtonSound(soundX, soundY,BTN_SOUND_SIZE ,BTN_SOUND_SIZE );
		sfxButton = new ButtonSound(soundX, sfxY, BTN_SOUND_SIZE, BTN_SOUND_SIZE);
		}
	public void update() {
		musicButton.update();
		sfxButton.update();
		volumbutton.update();
	}
	public void draw(Graphics g) {
		
		musicButton.draw(g);
		sfxButton.draw(g);
		volumbutton.draw(g);
	}
	public void mouseDragged(MouseEvent e) {// khi khéo chuột thì thay đổi vị trí của x
		if (volumbutton.isMousePress()) {
			float valueBefore=volumbutton.getValueVolume();
			volumbutton.changeX(e.getX());
			float valueAfter=volumbutton.getValueVolume();
			if(valueBefore!=valueAfter) {
				game.getAudioPlayer().setVolume(valueAfter);
			}
		}
	}
// sau khi sảy ra các sự kiện chuột cần phải update lại trạng thái
	public void mousePressed(MouseEvent e) {
		if(isIn(e,musicButton))// nếu chuột đang ở bên trong vị trí của button thì chuyển vị trí của button sang trạng thái ở cột 2
			musicButton.setMousePressed(true);
		else if(isIn(e,sfxButton))// nếu đang ở vị trí của sfxButton thì chuyển vị trí sang cột 2 của  sfxButton 
			sfxButton.setMousePressed(true);
		else if(isIn(e,volumbutton))
			volumbutton.setMousePressed(true);

	}

	public void mouseReleased(MouseEvent e) {//khi nhả chuột
		if(isIn(e,musicButton)) {// nếu dang trong vị trí button
			if(musicButton.isMousePressed()) {// đang trong vị trí của musibutton 
				musicButton.setStatusMute(!musicButton.isStatusMute());// set chuyển trạng thái cho musicButton			
				game.getAudioPlayer().toggleSongMute();// tắt nhạc nền
			}	
		}
		else if(isIn(e,sfxButton)) {
			if(sfxButton.isMousePressed()) {// đang trong vị trí của musibutton 
				sfxButton.setStatusMute(!sfxButton.isStatusMute());// set chuyển trạng thái cho musicButton
				game.getAudioPlayer().toggleEffectMute();// tắt nhạc hiệu ứng nhân vật
			}
		}
		musicButton.resetBoolButton();
		sfxButton.resetBoolButton();
		volumbutton.resetBoolButton();	
	}

	public void mouseMoved(MouseEvent e) {	
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumbutton.setMouseOver(false);
		
		
		if(isIn(e,musicButton))// nếu chuột đang ở bên trong vị trí của button thì chuyển vị trí của button sang trạng thái ở cột 2
			musicButton.setMouseOver(true);
		else if(isIn(e,sfxButton))// nếu đang ở vị trí của sfxButton thì chuyển vị trí sang cột 2 của  sfxButton 
			sfxButton.setMouseOver(true);
		else if(isIn(e,volumbutton))
			volumbutton.setMouseOver(true);
	}
	private boolean isIn(MouseEvent e, PauseButton b) {// check tại vị trí xảy ra sự kiện
		 return b.getBounds().contains(e.getX(), e.getY());//kiểm tra điều kiện bounds có chứa vị trí xảy ra sự kiện ko
	}
}
