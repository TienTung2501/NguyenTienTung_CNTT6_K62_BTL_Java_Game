package Items;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controllers.Map;
import Level.Level;
import Main.Game;
import Menu.Continue;
import static Controllers.Controller.ItemsInGame.*;


public class ItemManager {
	private Continue continuee;
	private BufferedImage[][] ItemImgs,ItemNotLoadImgs;
	private ArrayList<ItemTypes> itemLoad;
	private ArrayList<ItemNotLoad> itemNotLoad;
	
	public ItemManager(Continue continuee) {
		this.continuee=continuee;
		loadImgs();
		itemLoad = new ArrayList<>();
		itemNotLoad= new ArrayList<>();
	}
	public void loadItemNewLevel(Level levelcurrent) {
		itemLoad = levelcurrent.getItemLoad();
		itemNotLoad=levelcurrent.getItemNotLoad();
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
				ItemNotLoadImgs[j][i]=imgItemNotLoad.getSubimage(i*40,30 *j, 40, 30);
	}
	public void checkItemTouchedPlayer(Rectangle2D.Float hitbox) {
		for(ItemTypes i: itemLoad) {
			if(hitbox.intersects(i.getHitbox())){// kiểm tra giao giữa người và item
				i.setActiveItem( false);
				effectToPlayer(i);
			}
		}
		
		
	}
	public void effectToPlayer(ItemTypes i) {
		if(i.getItemType()==RED_LOAD_ITEM)
			continuee.getPlayer().changeHealth(-RED_ITEM_VALUE);
		else 
			continuee.getPlayer().changePower(BLUE_ITEM_VALUE);
	}
	public void checkItemHit(Rectangle2D.Float attackBox) {// check người chơi tấn công vào hộp
		for(ItemNotLoad iNot:itemNotLoad) {
			if(iNot.isActiveItem()) {
				if(iNot.getHitbox().intersects(attackBox)) {
					iNot.setDoItem(true);
					int type=0;
					if(iNot.getItemType()==BARREL)
						type=1;
					itemLoad.add(new ItemTypes((int)(iNot.getHitbox().x+iNot.getHitbox().width/2),
							(int)(iNot.getHitbox().y- iNot.getHitbox().height/2),
							type));
					return;// nếu cố gắng phá huỷ 2 hộp cùng 1 lúc thì không hoạt động đó là lý do dùng return
				}
					
			}
		}
	}
	public void update() {
		for(ItemTypes item:itemLoad) {
			if(item.isActiveItem())
				item.update();
		}
		for(ItemNotLoad item:itemNotLoad) {
			if(item.isActiveItem())
				item.update();	
		}
	}
	public void draw(Graphics g,int xlevelOffset) {
		drawItemTypes(g,xlevelOffset);
		drawItemNotLoad(g,xlevelOffset);
	}
	private void drawItemNotLoad(Graphics g, int xlevelOffset) {
		for(ItemNotLoad item:itemNotLoad) {
			if(item.isActiveItem()) {
				int type=0;
				if(item.getItemType()==BARREL) 
					type=1;
				g.drawImage(ItemNotLoadImgs[type][item.getStatusIndex()], 
						(int) (item.getHitbox().x-item.getxDrawOffset()-xlevelOffset),//xDrawOffset để căn hình của item giữa // xlevelOfset để di chuyển
						(int) (item.getHitbox().y-item.getyDrawOffset()),
						ITEM_WIDTH, 
						ITEM_HEIGHT, null);
				
			}
		}
		
	}
	private void drawItemTypes(Graphics g, int xlevelOffset) {
		for(ItemTypes item:itemLoad) {
			if(item.isActiveItem()) {
				int type=0;
				if(item.getItemType()==RED_LOAD_ITEM) 
						type=1;
				g.drawImage(ItemImgs[type][item.getStatusIndex()], 
						(int) (item.getHitbox().x-item.getxDrawOffset()-xlevelOffset),//xDrawOffset để căn hình của item giữa // xlevelOfset để di chuyển
						(int) (item.getHitbox().y-item.getyDrawOffset()),
						LOAD_ITEM_WIDTH, 
						LOAD_ITEM_HEIGHT, null);
				
			}
		}
		
	}
	public void resetAll() {
		for(ItemNotLoad item:itemNotLoad)
			item.reset();
		for(ItemTypes item:itemLoad)
			item.reset();
	}
	
}
