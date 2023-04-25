package Level;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controllers.LogicChangePos;
import Items.ItemNotLoad;
import Items.ItemTypes;
import Main.Game;
import objects.Crabby;
import static Controllers.LogicChangePos.*;
public class Level {
	private BufferedImage img;
	private int[][] levelData;
	private ArrayList<Crabby> crabs;
	private ArrayList<ItemNotLoad> itemNotLoad;
	private ArrayList<ItemTypes> itemLoad;
	private int levelTilesWide;// số lượng ô vuông trên chiều rộng của bản đồ
	private int maxTilesOffset ; //số ô vuông tối đa mà bản đồ có thể di chuyển theo chiều rộng.
	private int maxLevelOffset ;// kích thước tối đa mà bản đồ có thể di truyểm
	private Point levelSpawn;
	public Level(BufferedImage img) {
		this.img=img;
		createLevelData();
		createEnemies();
		createItemLoad();
		createItemNotLoad();
		calcLevelOffset();
	}
	private void createItemNotLoad() {
		itemLoad= LogicChangePos.getItemLoad(img);
	}
	private void createItemLoad() {
		itemNotLoad= LogicChangePos.getItemNotLoad(img);
		
	}
	private void calcLevelOffset() {
		levelTilesWide= img.getWidth();
		maxTilesOffset= levelTilesWide-Game.TILES_IN_WIDTH;
		maxLevelOffset=Game.TILES_SIZE*maxTilesOffset;
		calcPlayerSpawn();
	}
	private void calcPlayerSpawn() {
		levelSpawn=GetPlayerSpawn(img);
		
	}
	private void createEnemies() {
		crabs = GetCrabbies(img);
	}
	private void createLevelData() {
		levelData=GetLevelData(img);
		
	}
	public int getMapIndex(int x,int y) {
		return levelData[y][x];
	}
	public int[][] getLevelData() {
		return levelData;
	}
	public int getLevelOffset() {
		return  maxLevelOffset;
	}
	public ArrayList<Crabby> getCrabbs(){
		return crabs;
	}
	public Point getPlayerSpawn() {
		return levelSpawn;
	}
	public ArrayList<ItemTypes> getItemLoad(){
		return itemLoad;
	}
	public ArrayList<ItemNotLoad> getItemNotLoad(){
		return itemNotLoad;
	}
}
