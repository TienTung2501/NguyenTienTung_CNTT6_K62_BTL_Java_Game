package Items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controllers.Map;
import Main.Game;
import Menu.Continue;

public class ItemManager {
	private Continue continuee;
	private BufferedImage[][] ItemImgs,ItemNotLoadImgs;
	private ArrayList<ItemTypes> itemType;
	private ArrayList<ItemNotLoad> itemNotLoad;
	public ItemManager(Continue continuee) {
		this.continuee=continuee;
		loadImgs();
	}
	private void loadImgs() {
		BufferedImage imgItemTypes= Map.GetMap(Map.LOAD_ITEMS_PACKAGE);
		ItemImgs = new BufferedImage[2][7];
		for(int j=0;j<ItemImgs.length;j++)
			for(int i=0;i<ItemImgs[j].length;i++)
				ItemImgs[j][i]=imgItemTypes.getSubimage(i*12,16 *j, 12, 16);
		
		BufferedImage imgItemNotLoad= Map.GetMap(Map.ITEMS_PACKAGE);
		ItemNotLoadImgs = new BufferedImage[2][8];
		for(int j=0;j<ItemNotLoadImgs.length;j++)
			for(int i=0;i<ItemNotLoadImgs[j].length;i++)
				ItemNotLoadImgs[j][i]=imgItemTypes.getSubimage(i*40,30 *j, 40, 30);
	}
	public void update() {
		for(ItemTypes item:itemType) {
			if(item.isActiveItem())
				item.update();
		}
		for(ItemNotLoad item:itemNotLoad) {
			if(item.isActiveItem())
				item.update();	
		}
	}
//	public void draw(Graphics g,int xlevelOffset) {
//		drawItemTypes(g,xlevelOffset);
//		drawItemNotLoad(g,xlevelOffset)
//	}
	private void drawItemTypes(Graphics g, int xlevelOffset) {
		// TODO Auto-generated method stub
		
	}
}
