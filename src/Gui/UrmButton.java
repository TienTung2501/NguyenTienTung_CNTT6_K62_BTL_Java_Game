package Gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.Map;
import static Controllers.Controller.Gui.UrmButtons.*;

public class UrmButton extends PauseButton {
	private BufferedImage[] urmButton;
	private boolean mouseOver,mousePressed;
	private int rowIndex, index;
	public UrmButton(int x,int y,int width,int height,int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		setImages();
	}
	private void setImages() {
		BufferedImage img = Map.GetMap(Map.URM_BUTTON);
		urmButton = new BufferedImage[3];
		for(int i=0;i<urmButton.length;i++)
			urmButton[i]= img.getSubimage(i*URM_SOUND_DEFAULT, rowIndex*URM_SOUND_DEFAULT, URM_SOUND_DEFAULT, URM_SOUND_DEFAULT);	
	}
	public void update() {
		index=0;
		if(mouseOver)
			index =1;
		if(mousePressed)
			index=2;
			
	}
	public void resetBoolButton() {
		mouseOver = false;
		mousePressed = false;
	}
	public void draw(Graphics g) {
		g.drawImage(urmButton[index], x, y, URM_SOUND_SIZE, URM_SOUND_SIZE, null);
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
}
