package Level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controllers.Map;
import Main.Game;
import Menu.GameStatus;
import audioPlayer.AudioPlayer;

public class LevelManager {
	// hoàn thành việc quản lý hình ảnh cho các caps độ giờ cần sang lớp continue để quản lý việc chuyển đối giữa các cấp độ
		private Game game;
		private BufferedImage[] levelMap;
		private ArrayList<Level> levels;
		private int levelIndex=0;
		
		public LevelManager(Game game) {
			this.game = game;
			importMap();	
			levels = new ArrayList<Level>();
			buildAllLevels();
		}
		public void updateLvel() {
			Level  Levelcurrent= levels.get(levelIndex);// tạo 1 level mới có hình ảnh chỉ số index đang được lưu trong mảng
			game.getContinue().getEnemyManager().addEnemies(Levelcurrent);// tạo ra các quái cho trò chơi
			game.getContinue().getPlayer().setLevelData(Levelcurrent.getLevelData());// khởi tạo trạng thái cho nhân vật
			game.getContinue().getItemManager().resetAll();
			game.getContinue().setLevelOffset(Levelcurrent.getLevelOffset());// sét toạ độ của map hiện tại cho bản đồ
			game.getContinue().getItemManager().loadItemNewLevel(Levelcurrent);
			
		}
		public void loadNextLevel() {
			levelIndex++;// thay đổi chỉ số của level trong mảng level để truy vấn
			if(levelIndex>=levels.size()) {
				levelIndex=0;
				game.getContinue().setGameStatus(GameStatus.MENU);
			}
			 updateLvel();
		}
		private void buildAllLevels() {
			BufferedImage[] allLevels=Map.GetAllLevels();// gán tất cả các map hình ảnh cho mảng hình ảnh
			for(BufferedImage img:allLevels)// duyệt qua tất cả các hình trảnh trong alllevel
				levels.add(new Level(img));//add hình vào mảng để quản lý
		}
		private void importMap() {
			BufferedImage img = Map.GetMap(Map.Level_Map);// lấy ảnh chứa tất cả các ô để xây dựng map
			levelMap = new BufferedImage[48];// vì ảnh chứa 48 ô để dựng map nên có tổng 48 level cho map tức là có 48 loại ô
			for(int j=0;j<4;j++)
				for(int i=0;i<12;i++) {
					int index =j*12+i;
					levelMap[index] = img.getSubimage(i*32, j*32, 32,32);// cho các loại ô này lưu vào mảng, mảng này chứa các subImage;
				}	
		}
		public void draw(Graphics g,int levelOffset) {// có thể tưởng tượng bản chất khung hình đang đứng yên và chính những hoạt ảnh nhỏ đang chuyển động so với khung hình vì trên màn hình toạ độ của gốc toạ độ luôn không đổi
			for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
				for(int i=0;i<levels.get(levelIndex).getLevelData()[0].length;i++) {// ta cho man hinh jfram co kich co la ma tran tổng Ô nang* tổng ô dọc thì ta sẽ cho ô đó giá trị id của bức hình 
					int index = levels.get(levelIndex).getMapIndex(i, j);//lấy trị số id của khung hình;
					g.drawImage(levelMap[index], i*Game.TILES_SIZE - levelOffset,j*Game.TILES_SIZE,Game.TILES_SIZE,Game.TILES_SIZE , null);
				}
//			g.drawImage(levelMap[2],0,0 , null);
			
		}
		public Level getCurrentLevel() {//tra về trị số của ô hiện tại, vì trong map mỗi một khung hình nhỏ sẽ có 1 giá trị đại diện coi như 1 id của nó;
			return levels.get(levelIndex);
		}
		public void update() {
		//	loadNextLevel();
		}
		
		public int getLevelIndex() {
			return levelIndex;
		}
		public void setLevelIndex(int levelIndex) {
			this.levelIndex = levelIndex;
		}
		public int getNumberOffLevel() {
			return levels.size();
		}
		// tạo game mới cần cập nhật lại map
		public void resetLevel() {
			levelIndex=0;
			Level  newLevel= levels.get(levelIndex);// tạo 1 level mới có hình ảnh chỉ số index đang được lưu trong mảng
			game.getContinue().getEnemyManager().addEnemies(newLevel);// tạo ra các quái cho trò chơi
			game.getContinue().getPlayer().setLevelData(newLevel.getLevelData());// khởi tạo trạng thái cho nhân vật
			game.getContinue().getPlayer().setSpawn(newLevel.getPlayerSpawn());
			game.getContinue().getItemManager().resetAll();
			game.getContinue().setLevelOffset(newLevel.getLevelOffset());
			game.getContinue().getItemManager().loadItemNewLevel(newLevel);
			
		}
}
