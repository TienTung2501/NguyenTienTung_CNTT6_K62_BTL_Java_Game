package Gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.Map;
import static Controllers.Controller.Gui.ButtonSound.*;
public class ButtonSound extends PauseButton{
	private BufferedImage[][] buttonSounds;
	private boolean mouseOver,mousePressed;
	private boolean statusMute;// nếu là false thì mảng hoạt ảnh của soundButton nó đang ở dòng dưới, nếu là true thì mảng hoạt ảnh đang ở dòng trên 
	private int rowIndex, colIndex;// ma trận hoạt ảnh của các soundButton
	public ButtonSound(int x, int y, int width, int height) {
		super(x, y, width, height);
		setButtonSonuds();
	}
	private void setButtonSonuds() {
		BufferedImage img = Map.GetMap(Map.SOUND_BUTTON);
		
		buttonSounds = new BufferedImage[2][3];
		for(int j=0;j<buttonSounds.length;j++)
			for(int i=0;i<buttonSounds[j].length;i++)
				buttonSounds[j][i] = img.getSubimage(i*BTN_SOUND_DEFAULT, j*BTN_SOUND_DEFAULT, BTN_SOUND_DEFAULT,BTN_SOUND_DEFAULT);
	}
	public void update() {
		if(statusMute)
			rowIndex = 1;
		else 
			rowIndex = 0;
		colIndex = 0;// hiện tại đang vẽ button tại vị trí [0][0] trạng thái mặc định
		if(mouseOver)// nếu di chuột vào thì nó chuyển sang trạng thái 2
			colIndex = 1;
		if(mousePressed)// nhấn chuột thì nó chuyển sang trạng thái 3
			colIndex = 2;
	}
	public void resetBoolButton(){
		mouseOver = false;
		mousePressed = false;
	}
	public void draw(Graphics g) {
		g.drawImage(buttonSounds[rowIndex][colIndex], x, y, width, height, null);
	}
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePress) {
		this.mousePressed = mousePress;
	}
	public boolean isStatusMute() {
		return statusMute;
	}
	public void setStatusMute(boolean statusMute) {
		this.statusMute = statusMute;
	}
	
}
