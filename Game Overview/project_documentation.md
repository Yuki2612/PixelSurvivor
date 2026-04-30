# Pixel Survivor - Tài liệu Tổng thể Dự án (Bản Đầy đủ & Chuẩn xác nhất)

## 1. Cơ chế Gameplay Cốt lõi
- **Thể loại**: Roguelike Survivor / Hành động nhập vai.
- **Mục tiêu**: Sống sót qua các đợt quái vật, thu thập EXP để lên cấp, tiến hóa vũ khí và tiêu diệt Boss.
- **Hệ thống Bản đồ**:
    - **Kích thước**: 6000x6000px (Thế giới mở rộng).
    - **Môi trường**: 25 tòa nhà sinh ngẫu nhiên (dạng chữ L, T, U và Hình chữ nhật), thùng gỗ có thể phá hủy, cây và đá.
    - **Biên giới**: Giới hạn thế giới tại (0, 0) đến (6000, 6000).

## 2. Hệ thống Nhân vật (Character Classes)
| Tên | Máu cơ bản | Hệ số Tốc độ | Hệ số Sát thương | Kỹ năng bắt đầu | Giá mở khóa |
| :--- | :---: | :---: | :---: | :--- | :---: |
| **Mercenary** | 5 | 1.0x | 1.0x | Không có | Miễn phí |
| **Ninja** | 3 | 1.1x | 1.1x | Giảm hồi Dash | 1000 Gold |
| **Pyromancer** | 4 | 1.0x | 1.15x | Đường lửa | 2000 Gold |
| **Frost Mage** | 4 | 1.0x | 1.1x | Hào quang băng | 2000 Gold |
| **Necromancer** | 3 | 1.2x | 1.0x | Xác nổ | 5000 Gold |

## 3. Hệ thống Vũ khí & Tiến hóa (Weapons & Evolution)
### Vũ khí Cơ bản
- **Pistol**: Sát thương x1.0, CD 400ms, Tầm bắn 300px.
- **Shotgun**: Bắn 3 viên (mỗi viên x0.8), CD 750ms, Tầm bắn 150px.
- **SMG**: Sát thương x0.4, CD 150ms.
- **Assault Rifle**: Sát thương x1.2, CD 250ms.

### Vũ khí Tiến hóa (Evolution) - Yêu cầu rương Boss
- **HELLFIRE BOOMSTICK (Từ Shotgun)**:
    - *Yêu cầu*: Shotgun + Nâng cấp Sát thương (Lv.3) + Kỹ năng Xác nổ (Lv.1).
    - *Đặc điểm*: Bắn 5 viên loạt, gây nổ diện rộng và hiệu ứng Hellfire.
- **RAILGUN (Từ Assault Rifle)**:
    - *Yêu cầu*: Assault Rifle + Nâng cấp Kính quang học (Lv.3).
    - *Đặc điểm*: Sát thương x4.0, xuyên thấu, tương tác vật lý với tường.
- **LIGHTNING GUN (Từ SMG)**:
    - *Yêu cầu*: SMG + Nâng cấp Tốc độ bắn (Lv.3) + Kỹ năng Sét chuỗi (Lv.1).
    - *Đặc điểm*: Gây trạng thái Shock liên hoàn.

## 4. Hệ thống Kỹ năng & Nâng cấp (Skills & Upgrades)
### Nâng cấp Chỉ số (Normal)
- **Might**: +5 Sát thương.
- **Swift Boots**: +0.3 Tốc độ di chuyển.
- **Vampirism**: 3% cơ hội hồi 1 Tim khi giết địch mỗi cấp.
- **Engine Core**: -150ms hồi Dash.
- **Precision**: +7% Chí mạng.
- **Optical Scope**: +9% Tầm bắn.
- **Aero Bullets**: +12% Tốc độ đạn.

### Kỹ năng Đột phá (Breakthrough - Cap Lv.5)
- **Giáp năng lượng (Energy Shield)**: Hấp thụ đòn đánh, giảm hồi chiêu.
- **Thiên thạch (Meteor Strike)**: Gọi thiên thạch gây sát thương diện rộng.
- **Sóng xung kích (Pulse Wave)**: Đẩy lùi (Knockback) và gây sát thương.
- **Đường lửa (Trail of Fire)**: Để lại lửa khi Dash.
- **Cầu xoay (Orbiting Orbs)**: Bảo vệ và gây sát thương xoay vòng.
- **Hào quang băng (Frost Aura)**: Làm chậm và đóng băng xung quanh.
- **Mây độc (Poison Cloud)**: Gây sát thương độc diện rộng.

## 5. Hệ thống Nguyên tố & Phản ứng (Elemental System)
### Các trạng thái cơ bản
- **HỎA (Fire)**: Gây sát thương theo thời gian (DoT) (33% ATK/0.5s).
- **BĂNG (Ice/Chill)**: Làm chậm **30%** tốc độ di chuyển của địch.
- **ĐIỆN (Shock)**: Tích tụ điện tích để tạo phản ứng.
- **ĐỘC (Poison/Acid)**: Quái vật bị dính độc sẽ nhận thêm **30% sát thương** từ mọi nguồn.

### Các phản ứng kết hợp (Combos)
- **SỐC NHIỆT (Thermal Shock - Hỏa + Băng)**: Gây **3x sát thương ATK** và **ĐÓNG BĂNG** 1.5 giây.
- **PLASMA (Độc + Điện)**: Gây sát thương Plasma (50% ATK) và **Lây lan** cho 5 kẻ địch gần đó (bán kính 150px).
- **TAN CHẢY (Corrosive Melt - Độc + Hỏa)**: Kích hoạt khi quái chết lúc đang bị Độc + Bỏng, gây nổ axit diện rộng.

## 6. Kẻ địch & Boss System
- **Hệ thống Wave**: Boss xuất hiện mỗi 5 wave (Wave 5, 10, 15...).
- **Boss**: Charger (Húc), Teleporter (Dịch chuyển), Tank (Trâu bò).
- **Âm nhạc**: Chuyển đổi nhạc `gamebgm2` kịch tính từ Wave 15. Nhạc Boss tăng âm lượng +5dB.

## 7. Nâng cấp Vĩnh viễn (Meta Progression)
*Dữ liệu từ PlayerData.java:*
- **Máu**: +1 Tim / 10 cấp.
- **Sát thương**: +1 / cấp.
- **Tốc độ**: +2% / cấp.
- **Hồi Dash**: -2% / cấp.
- **Chí mạng**: +1% / cấp.
- **Hồi chiêu vũ khí**: -2% / cấp.

## 8. Thông số Kỹ thuật
- **Ngôn ngữ**: Java. Đồ họa Java 2D, thuật toán Y-Sorting (Z-Depth).
- **Xử lý va chạm**: AABB Hitbox & FlowField Pathfinding cho quái vật.
- **Âm thanh**: `SoundManager` hỗ trợ chuyển đổi BGM thời gian thực và điều khiển Gain logarit.
- **Đa luồng**: Tách biệt luồng Logic và Render với cơ chế đồng bộ hóa an toàn.
