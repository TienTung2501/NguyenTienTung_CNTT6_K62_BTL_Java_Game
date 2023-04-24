package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Main.Game;

public abstract class Object {
	protected float x,y;
	protected int width,height;
	protected Rectangle2D.Float hitBox;//hộp bao quanh nhân vật để check điểm bắt đầu va chạm với các thực thể trong game có toạ độ, chiều dài và chiều rộng giống như là 1 Player
	// chung ta co the dung cac chi so cua hinh chu nhat duoi dang float vi de ta lay toa do chinh xac hon
	protected Rectangle2D.Float acttackBox;
	protected int statusTick,statusIndex;
	protected int status;
	protected float airSpeed;
	protected boolean inAir=false;
	protected  int maxHealth;
	protected int currentHealth;
	protected float speed;
	
	public Object(float x,float y,int width,int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	protected void initHitBox(int width,int height) {//de ta co the khoi tao tu ben ngoai
		hitBox = new Rectangle2D.Float( x, y,(int) (width *Game.SCALE), (int)(height*Game.SCALE));
	}
	protected void drawHitBox(Graphics g,int xlevelOffset) {
		g.setColor(Color.RED);// ở dưới phải trừ đi xlevelOffset vì nếu không trừ thì hộp sẽ ko thể đi chung với người và người có thể rơi xuống một khung hình nào đó
		g.drawRect((int)hitBox.x-xlevelOffset,(int) hitBox.y,(int) hitBox.width,(int) hitBox.height);
	}
	public void drawActtackBox(Graphics g,int xlevelOffset) {
	g.setColor(Color.red);
	g.drawRect((int)(acttackBox.x-xlevelOffset),(int) acttackBox.y,(int) acttackBox.width,(int) acttackBox.height);
}
	public void updateHitBox(float X,float Y) {//toa to cua hitBox bang toa do cua player
		hitBox.x= X;
		hitBox.y= Y;	
	}
	public void setHitBox(Rectangle2D.Float hitBox) {
		this.hitBox = hitBox;
	}
	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatusIndex() {
		return statusIndex;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getStatusTick() {
		return statusTick;
	}
	public void setStatusTick(int statusTick) {
		this.statusTick = statusTick;
	}
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	public void setStatusIndex(int statusIndex) {
		this.statusIndex = statusIndex;
	}
}
