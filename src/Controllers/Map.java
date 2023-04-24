package Controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Map {
	public static final String PLAYER_STATUS = "player_sprites.png";
	public static final String Level_Map = "map_1.png";
	public static final String MENU_BUTTONS= "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String MENU_PAUSE = "pause_menu.png";
	public static final String SOUND_BUTTON= "sound_button.png";
	public static final String URM_BUTTON = "urm_buttons.png";
	public static final String VOLUME_BUTTON = "volume_buttons.png";
	public static final String MENU_BACKGROUND_GAME = "background_menu.png";
	public static final String BACKGROUND_SKY = "playing_bg_img.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
	public static final String SMALL_CLOUDS = "small_clouds.png";
	public static final String CRABBYS = "crabby_sprite.png";
	public static final String BAR_HEALTH_POWER = "health_power_bar.png";
	public static final String LEVEL_COMPLETE_MENU = "completed_sprite.png";
	public static final String ITEMS_PACKAGE = "items_sprite.png";
	public static final String LOAD_ITEMS_PACKAGE = "itemsLoad_sprite.png";
	public static final String GAMEOVER_MENU = "death_screen.png";
	public static final String OPTION_MENU = "options_background.png";
	
	
	public static BufferedImage[] GetAllLevels() {// trả về mảng các bức ảnh
		URL url= Map.class.getResource("/levelMap");// ví trí mà tài nguyên đang được chứa trong nó
		File file =null;
		try {
			file =new File(url.toURI());// khởi tạo file vào gán tài nguyên thực đang chứa trong file level map cho biến file
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File[] files=file.listFiles();// duyệt qua tất cả các file bên trong nó

		File[] fileSorted = new File[files.length];
		// khi đọc các file thì vị trí các file sẽ bị thay đổi không theo 1 thứ tự vậy cần sắp xếp chúng theo một thứ
		// tự để khi truy vấn nó dễ hơn
		for(int i=0;i<fileSorted.length;i++)
			for(int j=0;j<files.length;j++) {
				if(files[j].getName().equals(""+(i+1)+".png"))
					fileSorted[i]=files[j];
			}
//		for(File f:files)
//			System.out.println("file:"+f.getName());		
//		for(File f:fileSorted)
//			System.out.println("fileSorted:"+f.getName());
		BufferedImage[] imgs= new BufferedImage[fileSorted.length];
		for(int i=0;i<imgs.length;i++)
			try {
				imgs[i]=ImageIO.read(fileSorted[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return imgs;
	}
	
	public static BufferedImage GetMap(String fileMap) {
		BufferedImage img = null;
		InputStream is = Map.class.getResourceAsStream("/" + fileMap);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
	                is.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

}
