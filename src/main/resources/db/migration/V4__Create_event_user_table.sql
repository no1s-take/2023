CREATE TABLE event_users (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'イベントユーザID',
  event_id int NOT NULL COMMENT 'イベントID',
  user_id int NOT NULL COMMENT 'ユーザID',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '作成日時',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='イベントユーザ';
