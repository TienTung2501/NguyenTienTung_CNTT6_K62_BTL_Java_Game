Cách chạy chương trình khi máy không có IDE mà chỉ có JDK:

B1: Tải dự án trên github về, giải nén ra Desktop, đổi tên thành Game (tên trước khi giải nén là NguyenTienTung_CNTT6_K62_BTL_Java_Game)

	(link git: https://github.com/TienTung2501/NguyenTienTung_CNTT6_K62_BTL_Java_Game/tree/master)
	
B2: Mở Command Prompt lên và tạo đường dẫn đến folder Game( folder của dự án đã giải nén ra)	
 Thực hiện:
 
	- Bật CMD lên, sau khi bật trạng thái của CMD là:"C:\Users\NGUYEN TIEN TUNG> "( trên máy của thầy thì tên users sẽ khác máy của em ạ )
	
	- Tiếp theo --> cd Desktop 
	
	- Tiếp theo --> cd Game

	(Hiện tại các file java của dự án đã được biên dịch thành các file .class 
	Các file .class này đang được lưu ở thư mục bin nên ta chỉ cần chạy file chứa hàm main là chạy được chương trình)
	
B3: Chạy file chứa hàm main

  Thực hiện
  
	  --> java -cp bin Main.MainClass
	  
 ( Em thưa thầy, vì là hiện tại kết nối SQL của dự án đang là Local nên khi chạy dự án trên máy thầy không thể sử dụng được nên có thể phát sinh lỗi không kết nối SQL ạ.)
