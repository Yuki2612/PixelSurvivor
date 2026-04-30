# Pixel Survivor - Ultimate Developer Documentation (Full & Precise)

## 1. Kiến trúc Hệ thống & Package (Xác thực 100%)
Dự án gồm 8 package chức năng:
- `gameproject`: Lõi engine (`GamePanel`, SFX, VFX, Input, UpgradeManager).
- `gameproject.entity`: Thực thể (Player, Enemy, Boss, Drops).
- `gameproject.environment`: Vật lý & Môi trường (Map, FlowField, Hitbox, Obstacles).
- `gameproject.meta`: Dữ liệu (PlayerData, CharacterClass).
- `gameproject.skill`: Kỹ năng (Upgrade, PassiveSkill).
- `gameproject.state`: Máy trạng thái (State pattern).
- `gameproject.ui`: Giao diện (HUD, Menu, Stats).
- `gameproject.weapon`: Chiến đấu (Weapon, Projectile).

## 2. Hệ thống Môi trường tương tác (Environment)
- **Vật thể (Obstacles)**: 
    - Bao gồm `Tree`, `Rock`, `Wall`, `WoodenCrate`. 
    - Tất cả kế thừa từ `Obstacle`, có thuộc tính `hp` và `isSolid()`. 
    - Kẻ địch có thể tấn công và phá hủy các vật thể này nếu bị chặn đường (`handleObstacleBreaking`).
- **Sinh bản đồ (Map Generation)**:
    - Sử dụng thuật toán `generateStructuredMap` trong `MapManager` để xây dựng 25 tòa nhà dựa trên các hình khối cơ bản (Rectangle, L, T, U).
    - Các vật thể tự nhiên (cây, đá) được sinh ngẫu nhiên trong các vùng không phải là tòa nhà.
- **Hitbox**: 
    - **AABB**: Dùng cho tường và nhà.
    - **Circle**: Dùng cho cây, đá và thùng gỗ để tạo cảm giác va chạm bo tròn mượt mà.

## 3. Hệ thống VFX & Visuals
- **VFXManager**: Quản lý danh sách các hiệu ứng ngắn hạn.
    - **Particles**: Hiệu ứng hạt khi quái chết hoặc đạn nổ.
    - **Damage Text**: Hiển thị sát thương (Trắng cho thường, Vàng cho Chí mạng).
    - **Screen Shake**: Kích hoạt hiệu ứng rung màn hình khi Boss xuất hiện hoặc có vụ nổ lớn.
    - **Zones**: Quản lý vùng lửa (Burn) và vùng Acid (Poison/Corrosive Melt).
- **Y-Sorting**: Toàn bộ thực thể và vật thể môi trường được đưa vào danh sách `Renderable`, sắp xếp theo `getBottomY()` để render đúng thứ tự trước-sau.

## 4. Hệ thống Tài nguyên & Thu thập (Drops)
- **Resource Drops**: Gồm Vàng (Gold) và Linh hồn (Soul).
    - Sinh ra khi quái thường (25% cơ hội) hoặc Boss (100% cơ hội) bị tiêu diệt.
    - Có cơ chế **Magnet**: Tự động bay về phía người chơi khi ở trong tầm hút.
- **Heart Drops**: Vật phẩm hồi máu (1% cơ hội từ quái thường, Boss rơi nhiều hơn).
- **Weapon Chests**: Rơi từ Boss, dùng để tiến hóa vũ khí hoặc chọn kỹ năng đột phá.

## 5. Camera & Xử lý Đầu vào (Input)
- **Camera Logic**: Camera bám theo Player với cơ chế "snapping" (giữ Player ở tâm). Tọa độ camera được giới hạn trong phạm vi World (0,0 đến 6000,6000) để không nhìn thấy khoảng trống ngoài map.
- **InputManager**: 
    - Lắng nghe sự kiện bàn phím (WASD, Shift, ESC).
    - Lắng nghe sự kiện chuột (Tọa độ X, Y cho hướng bắn và Click/Hold để xả đạn).
    - Hỗ trợ `clearClickAndKey()` để tránh việc nhận diện nhầm lệnh khi chuyển giữa các State (ví dụ: click chọn nâng cấp xong không bị bắn đạn ngay).

## 6. Lõi Game Engine & Logic
- **Game Loop**: Nanosecond precision trong `GamePanel.run()`.
- **FlowField AI**: Độ phân giải 16x16px, giúp quái vật tìm đường thông minh qua các tòa nhà phức tạp.
- **Elemental Reactions**: Sốc nhiệt (x3 DMG + Freeze), Plasma (DoT lây lan), Tan chảy (Nổ axit).

## 7. Hướng dẫn Mở rộng (Developer Integration Guide)
- **Thêm Vũ khí/Tiến hóa**: Tạo lớp Weapon -> Đăng ký Upgrade -> Thiết lập điều kiện trong PlayingState.
- **Thêm Enemy/Boss**: Tạo lớp Enemy -> Thiết lập AI -> Đăng ký trong EntityManager.
- **Thêm Môi trường**: Tạo lớp mới kế thừa `Obstacle` -> Đăng ký sprite trong `ImageManager` -> Thêm logic sinh trong `MapManager`.
- **Thêm SFX/BGM**: Load trong `GamePanel` -> Gọi qua `SoundManager`.
- **Thêm UI/State**: Implement `State` -> Thiết kế lớp UI -> Gọi `changeState()`.
