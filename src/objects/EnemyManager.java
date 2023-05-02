package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controllers.Map;
import Level.Level;
import Menu.Continue;

import static Controllers.Controller.ControllerEnemy.*;
public class EnemyManager {
	private Continue continuee;
	private BufferedImage[][] crabbys;
	private ArrayList<Crabby> crabbies = new ArrayList<>();
	
	public EnemyManager(Continue continuee) {
		this.continuee=continuee;
		loadEnemyImgs();
	//	addEnemies();
	}
	public void addEnemies(Level level) {
		crabbies = level.getCrabbs();
		
	}
	public void checkPlayerHit(Rectangle2D.Float attackBox) {
		for(Crabby c:crabbies) 
			if(c.getCurrentHealth()>0)
				if(c.isAcitve()) 
				{		
					if(attackBox.intersects(c.getHitBox())) {// kiểm tra xem các hộp sát thương của nhân vật và hộp chứa kẻ thù có va chạm vào nhau không
						c.hurt(5);
						return;
					}						
				}
	}
	private void loadEnemyImgs() {
		crabbys = new BufferedImage[5][9];
		BufferedImage crabbyMap;
		crabbyMap = Map.GetMap(Map.CRABBYS);
		for(int j=0;j<crabbys.length;j++)
			for(int i=0;i<crabbys[j].length;i++)
				crabbys[j][i]=crabbyMap.getSubimage(i*CRABBY_WIDTH_DEFAULT, j*CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
	}
	public void update(int[][] levelData,Player player) {
		boolean isDead=false;
		for(Crabby c:crabbies) {
			if(c.isAcitve()) {
				c.update(levelData,player);
				isDead=true;
			}			
		}
		if(!isDead)
			continuee.setLevelCompleted(true);
	}
	private void isAllDead() {
		// TODO Auto-generated method stub
		
	}
	public void draw(Graphics g,int xleveOffset) {
		drawCrabby(g,xleveOffset);
	}
	private void drawCrabby(Graphics g,int xleveOffset) {
		for(Crabby c:crabbies) {
			if(c.isAcitve()) {		
				g.drawImage(crabbys[c.getEnemyStatus()][c.getEnemyIndex()],
						(int)c.getHitBox().x-xleveOffset-CRABBY_DRAW_OFFSET_X+c.flipX(),
						(int) c.getHitBox().y-CRABBY_DRAW_OFFSET_Y, 
						CRABBY_WIDTH*c.flipY(), CRABBY_HEIGHT, null);
//				c.drawHitBox(g, xleveOffset);
//				c.drawActtackBox(g, xleveOffset);
			}
		}
		
	}
	public void resetAll() {
		for(Crabby c:crabbies) {
			c.resetEnemy();
		}
		
	}
}
