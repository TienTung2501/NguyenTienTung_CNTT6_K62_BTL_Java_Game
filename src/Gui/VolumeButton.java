package Gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.Map;

import static Controllers.Controller.Gui.VolumeButton.*;
public class VolumeButton extends PauseButton{
	private BufferedImage[] volumeButton;
	private BufferedImage containerVolume;
	private int index;
	private int buttonX,minX,maxX;// thiết lập toạ độ min max mà button âm thanh có thể đi
	private boolean mousePressed, mouseOver;
	public VolumeButton(int x,int y,int width,int height) {
		super(x+ (width/2), y, VOLUME_WIDTH_SIZE, height);// khởi tạo vị trí cho nút button âm thanh đó là ở giữa thanh
		bounds.x -=  VOLUME_WIDTH_SIZE/2;// bounds.x đang ở giữa nút khi đó thì nếu t nhấn dịch về bên trái một chút sẽ bị mis
		buttonX=x+(width/2);// vị trí của buttonX đang là ở giữa
		this.x = x;
		this.width=width;
		loadImg();
		minX=x+VOLUME_WIDTH_SIZE/2;
		maxX=x+width -VOLUME_WIDTH_SIZE/2;
	}
	
	private void loadImg() {
		BufferedImage img = Map.GetMap(Map.VOLUME_BUTTON);
		volumeButton = new BufferedImage[3];
		for(int i=0;i<volumeButton.length;i++)
			volumeButton[i]=img.getSubimage(i*VOLUME_WIDTH_DEFAULT,0 , VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
		containerVolume = img.getSubimage(3*VOLUME_WIDTH_DEFAULT, 0, CONTAINER_VOL_WIDTH_DEFAULT,VOLUME_HEIGHT_DEFAULT);
	}
	
	public void changeX(int x) {
		if(x<minX)
			buttonX=minX;
		else if(x>maxX)
			buttonX= maxX;
		else
			buttonX = x;
		// khi ta kéo và thả ra thì bounds của button vẫn ở vị trí cũ bởi vậy ta cần thay đổi vị trí cho bounds
		bounds.x= buttonX- VOLUME_WIDTH_SIZE/2;
	}
	public void update() {
		index=0;
		if(mouseOver)
			index=1;
		if(mousePressed)
			index=2;
	}
	public void resetBoolButton() {
		mouseOver = false;
		mousePressed = false;
	}
	public void draw(Graphics g) {
		g.drawImage(containerVolume, x, y, width, height, null);
		g.drawImage(volumeButton[index], buttonX - VOLUME_WIDTH_SIZE/2, y, VOLUME_WIDTH_SIZE, VOLUME_HEIGHT_SIZE, null);
	}
	public boolean isMousePress() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePress) {
		this.mousePressed = mousePress;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
}
