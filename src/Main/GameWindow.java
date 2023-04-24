package Main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
	//tao 1 cua so de hien thi game
		private JFrame jframe;
		public GameWindow(GamePanel gamePanel) {

			jframe = new JFrame();
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe.add(gamePanel);
			jframe.setTitle("Learn Code Game");
			jframe.setResizable(false);// không cho người dùng thay đổi kích thước
			jframe.pack();//kích thước tuỳ biến có thể chứa được các phần tử trong nó luôn lớn hơn hoặc bằng
			//được fit theo kích cỡ màn hình
			jframe.setLocationRelativeTo(null);
			jframe.setVisible(true);
			jframe.addWindowFocusListener(new WindowFocusListener() {
				@Override
				public void windowGainedFocus(WindowEvent e) {
				}
				@Override
			    public void windowLostFocus(WindowEvent e) {
					gamePanel.getGame().windowFocusLost();
				};
			});//tạo tiêu điểm cho màn hình khi chuột không còn ở trong màn hình thì khi nhấn phim các nút không bị mất hiệu

		}
}
