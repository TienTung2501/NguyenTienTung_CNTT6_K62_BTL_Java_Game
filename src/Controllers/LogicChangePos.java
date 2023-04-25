package Controllers;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Items.ItemNotLoad;
import Items.ItemTypes;
import Main.Game;
import objects.Crabby;


import static Controllers.Controller.ControllerEnemy.*;
import static Controllers.Controller.ItemsInGame.*;
public class LogicChangePos {
	public static boolean canMoveHere(float x,float y,float width,float height, int [][] levelData) {// kiem tra xem o do co the di truyen duoc khong neu khong return true
		if(!checkPos(x, y, levelData))// neu sai thi tiep tuc check, nghia la co the di tiep// check vi tri hien taij
			if(!checkPos(x+width, y+height, levelData))//check vi tri tren ben phai
				if(!checkPos(x+width, y, levelData))// check vi tri duoi
					if(!checkPos(x, y+height, levelData))// check vi tri duoi ben phai
						return true;// neu dung thi duoc di tiep
		return false;//sai khong duoc di
		// tuy nhien van co the di chuyen qua nhung o nho neu ta chua bao gio va cham vao bat ki goc nao ben trong no dieu nay se bien mat khi chung ta thu nho chiec hop
		// tai sao?
			
	}
	private static boolean checkPos(float x,float y, int [][] levelData) {//kiem tra xem tai vi tri do co chướng ngoại vật không cac doi tuong hay khong
		int maxWidth = levelData[0].length*Game.TILES_SIZE;// chiều rộng max của map;
		if(x<0 || x>=maxWidth) {// check xem hiện tại nhân vật có thể đi được ở vị trí đó không
			return true;// nghia la hien tai o day dang co doi tuong chong cheo vao nhan vat nen khong the di chuyen
		}
		if(y<0||y>= Game.HEIGHT_SIZE) {
			return true;
		}
		float xIndex=x/Game.TILES_SIZE;//tao 1 bien luu toa do cua x 
		float yIndex=y/Game.TILES_SIZE;//tao 1 bien luu toa do cua y
		return checkPosTile((int) xIndex, (int) yIndex, levelData);
	}
	public static boolean checkPosTile(int xTile,int yTile,int [][] levelData) {
		int value = levelData[(int) yTile][(int) xTile];// voi hai bien x, y ta se cho ra gia tri cua du lieu cua map tai [xIndex][yIndex] khong duoc di vao cho nay neu ra thoa man dieu kien o duoi thi khong duoc di vao
		if(value >48 ||value <0|| value !=11) {// 11 chinh la sprite cua thang hinh trong suot ow trong map
			return true;
		}
		return false;
	}
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);// lấy ô hiện tại đang chứa nhân vật
		if (xSpeed > 0) {// nếu di truyển sang phải 
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;// lấy toạ độ giới hạn x vế trái của nhân vật (điểm giới hạn trái) 
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);// lấy thằng nhân vật đang cách cái ô chứa nó là bao nhiêu. lưu ý ô này không phải hitbox
			return tileXPos + xOffset - 1;// trả về vị trí sau khi nhảy
		} else
			// Left
			return currentTile * Game.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);// lấy ô hiện tại đang chứa nhân vật lấy ô 
		if (airSpeed > 0) {// nếu đang trong không khí
			// Falling - touching floor// chạm xuống sàn
			int tileYPos = currentTile * Game.TILES_SIZE;// lấy vị trí trên đỉnh của hộp
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;// trả về vị trí đứng
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;//trả về vị trí đứng

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!checkPos(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))// hiện tại đang không có chướng ngoại vật trên sàn nên nhân vật sẽ bị rơi xuống
			if (!checkPos(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}
	public static boolean IsFloor(Rectangle2D.Float hitbox,float xSpeed,int[][] leveldata) {// tại sao cần cộng thêm 1 vì chúng t không kiểm tra phần của cùng của hộp và kích thước của nó là 1px
		if(xSpeed>0)
			return checkPos(hitbox.x+hitbox.width+xSpeed, hitbox.y+hitbox.height +1, leveldata);// nếu gặp chướng ngoại vật thì khôg là gì nếu không thì làm gì đó
		else 
			return checkPos(hitbox.x+xSpeed, hitbox.y+hitbox.height +1, leveldata);
	}
	
	public static boolean IsAllTileWalkable(int xStart,int xEnd,int y,int[][] levelData) { // dùng để lặp các ô giữa hai nhân vật
		for(int i=0;i<xEnd- xStart;i++) {
			if(checkPosTile(xStart+i, y, levelData))
				return false;
			if(!checkPosTile(xStart+i, y+1, levelData))// kiểm tra xem bên dưới chân kẻ thù hoặc nhân vật có không khí không
				return false;
		}
			
		return true;
	}
// dùng để hàm dưới gọi
	public static boolean IsSightClear(int[][] levelData,Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox,int yTile) {// kiểm tra xem hai đối tượng có nằm trong đường ngắm của nhau không có thể là đường đạn có thể là nhân vật hoặc kẻ thù
		int firstXTile = (int) (firstHitBox.x/Game.TILES_SIZE);
		int secondXTile = (int) (secondHitBox.x/Game.TILES_SIZE);
		if(firstXTile>secondXTile)
//			for(int i=0;i<firstXTile - secondXTile;i++)// duyệt xem ở giữa 2 thằng nhân vật và kẻ thù xem có chướng ngoại vật gì không
//				if(checkPosTile(secondXTile+i, yTile, levelData))
//					return true;
			return IsAllTileWalkable(secondXTile, firstXTile, yTile, levelData);
		else 
			return IsAllTileWalkable(firstXTile, secondXTile, yTile, levelData);
	}
	public static int [][] GetLevelData(BufferedImage img){// tạo 1 mảng xong gán id của bức một hoạt ảnh nhỏ cho mảng
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}
	public static ArrayList<Crabby> GetCrabbies(BufferedImage img){
		ArrayList<Crabby> list= new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRABBY)
					list.add(new Crabby(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
			}
		return list;
	
	}
	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100)
					return new Point(i*Game.TILES_SIZE,j*Game.TILES_SIZE);
				
			}
		return new Point(1*Game.TILES_SIZE,1*Game.TILES_SIZE);
	}
	public static ArrayList<ItemTypes> getItemLoad(BufferedImage img){
		ArrayList<ItemTypes> list= new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == RED_LOAD_ITEM|| value==BLUE_LOAD_ITEM)
					list.add(new ItemTypes(i*Game.TILES_SIZE, j*Game.TILES_SIZE,value));
			}
		return list;
	
	}
	public static ArrayList<ItemNotLoad> getItemNotLoad(BufferedImage img){
		ArrayList<ItemNotLoad> list= new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == BOX || value == BARREL)
					list.add(new ItemNotLoad(i*Game.TILES_SIZE, j*Game.TILES_SIZE,value));
			}
		return list;
	
	}
}
