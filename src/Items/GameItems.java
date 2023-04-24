package Items;

import static Controllers.Controller.STATUSSPEED;
import static Controllers.Controller.ControllerEnemy.*;
import static Controllers.Controller.ItemsInGame.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public class GameItems {
	protected int x,y,itemType;
	protected Rectangle2D.Float hitbox;
	protected boolean doItem,activeItem=true;// doItem là biến tạo hiệu ứng cho các item sau khi bị phá huỷ
	protected int statusTick,statusIndex;
	protected int xDrawOffset,yDrawOffset;
	public GameItems(int x,int y,int itemType) {
		this.x=x;
		this.y=y;
		this.itemType=itemType;
	}
	protected void updateStatusItem() {
		statusTick++;
		if(statusTick>=STATUSSPEED) {
			statusTick=0;
			statusIndex++;
			if(statusIndex>=GetSpriteAmount(itemType)) {
				statusIndex=0;
				if(itemType== BARREL ||itemType== BOX) {// nếu loại item là thùng hoặc hộp thì chưa bị phá huỷ -> hiệu ứng của item khi load vẫn chưa được kích hoạt
					doItem=false;
					activeItem=false;// khi bị phá thì kích hoạt trạng thái vỡ
				}
				
				}
			}
		}
	public void reset() {
		statusIndex=0;
		statusTick=0;
		activeItem=true;
		//
		if(itemType== BARREL ||itemType== BOX)
			doItem=false;
		else 
			doItem=true;
	}
	public void initHitBox(int width,int height) {//de ta co the khoi tao tu ben ngoai
		hitbox = new Rectangle2D.Float( x, y,(int) (width *Game.SCALE), (int)(height*Game.SCALE));
	}
	public void drawHitBox(Graphics g,int xlevelOffset) {
		g.setColor(Color.RED);// ở dưới phải trừ đi xlevelOffset vì nếu không trừ thì hộp sẽ ko thể đi chung với người và người có thể rơi xuống một khung hình nào đó
		g.drawRect((int)hitbox.x-xlevelOffset,(int) hitbox.y,(int) hitbox.width,(int) hitbox.height);
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	public boolean isDoItem() {
		return doItem;
	}
	public boolean isActiveItem() {
		return activeItem;
	}
	public void setActiveIten(boolean activeIte) {
		this.activeItem=activeIte;
	}
	public int getxDrawOffset() {
		return xDrawOffset;
	}
	public int getyDrawOffset() {
		return yDrawOffset;
	}
	public int getStatusIndex() {
		return statusIndex;
	}
}
