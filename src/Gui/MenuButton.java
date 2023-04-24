package Gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Controllers.Map;
import Menu.GameStatus;

import static Controllers.Controller.Gui.Buttons.*;

public class MenuButton {
	private int xPos,yPos,rowIndex,index;// toạ độ của button rowindex là chi số dòng của các hoạt ảnh của nút, index là chỉ số của nút
	private int xOffsetCenter= BTN_WIDTH/2;// căn vị trí cho button
	private GameStatus status;// trạng thái của game
	private BufferedImage[] imgs;// mảng này chứa các button của menu trong đó có 9 button
	private boolean mouseOver,mousePressed;// các biến này để kiểm tra xem chuột có nằm trong vùng button không
	private Rectangle bounds;// đây để xác định vùng chuột di chuyển vào .Biến bounds được sử dụng để xác định khu vực chứa 
	//MenuButton trên giao diện. Biến này được khởi tạo trong phương thức initBounds() với các thông số tọa độ x, y, chiều rộng 
	//và chiều cao được tính toán. Khi đã có biến bounds, chúng ta có thể sử dụng nó để xác định xem vị trí chuột có nằm trong khu
	// vực của MenuButton hay không. Điều này là cần thiết để bắt sự kiện nhấn chuột trái hoặc di chuyển chuột trên nút. Khi sự kiện 
	//này xảy ra, chương trình có thể thực hiện một số hành động nhất định, chẳng hạn như thay đổi trạng thái của MenuButton, hoặc thay đổi trạng thái của trò chơi.
	//Nói tóm lại, biến bounds rất quan trọng trong việc quản lý và xử lý các sự kiện trên giao diện.
	public MenuButton(int xPos, int yPos, int rowIndex, GameStatus status ) {// khởi tạo một menubutton
		this.xPos=xPos;
		this.yPos=yPos;
		this.status = status;
		this.rowIndex=rowIndex; // dòng hiện tại để trỏ đến button cần
		loadImg();
		initBounds();
	}
	private void initBounds() {
		bounds = new Rectangle(xPos-xOffsetCenter,yPos,BTN_WIDTH,BTN_HEIGHT);
		
	}
	private void loadImg() {// lấy 3 button ở cột đầu tiên trong ảnh button cho vào mảng
		imgs = new BufferedImage[3];
		BufferedImage menuBtn= Map.GetMap(Map.MENU_BUTTONS);
		for(int i=0;i<imgs.length;i++) {
			imgs[i] = menuBtn.getSubimage(i*BTN_WIDTH_DEFAULT,rowIndex*BTN_HEIGHT_DEFAULT, BTN_WIDTH_DEFAULT, BTN_HEIGHT_DEFAULT);
		}								// i là biến để lưu toạ độ x của button khi i=0 row=0 nó là đầu								
		
		
		
	}
	public void draw(Graphics g) {// biến index sẽ được truyền trong hàm draw ở bên khởi tạo các button
		g.drawImage(imgs[index],xPos -xOffsetCenter ,yPos, BTN_WIDTH,BTN_HEIGHT,null );
	}
	public void update() {// 
		index=0;// button đang ở trạng thái default: tức là nó đang ở cột 0
		if(mouseOver)//cập nhật nếu button hiện tại đang được di chuột vào thì nó ở trạng thái cột hình ảnh 1 màu được tô
			index=1;
		if(mousePressed)// nếu nó được nhấn xuống thì nó ở cột 2 tức là nó bị nén xuống
			index=2;
	}
	// set và get biến kiểm tra mouse trên bounds,
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void applyGameStatus() {// áp dụng status cho thằng button tương ứng
		GameStatus.status = status;
	}
	public void resetBools() {// khi khong có mouse Even thì không làm gì
		mouseOver = false;
		mousePressed = false;
	}
}
