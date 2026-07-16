CREATE DATABASE `banking_system`;


--accounts table schema
CREATE TABLE `accounts` (
  `account_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `account_number` bigint NOT NULL,
  `account_type` enum('SAVINGS','CURRENT') NOT NULL,
  `balance` decimal(15,2) DEFAULT '0.00',
  `created_at` timestamp NULL DEFAULT NULL,
  `loan_limit` decimal(15,2) NOT NULL DEFAULT '50000.00',
  `outstanding_loan` decimal(15,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `fk_accounts_1_idx` (`user_id`),
  CONSTRAINT `fk_accounts_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--alert table schema
CREATE TABLE `alerts` (
  `alert_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `alert_type` enum('INFO','SECURITY','WARNING') DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`alert_id`),
  KEY `fk_alerts_1_idx` (`user_id`),
  CONSTRAINT `fk_alerts_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--loans table schema
CREATE TABLE `loans` (
  `loan_id` bigint NOT NULL,
  `account_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `loan_amount` decimal(15,2) NOT NULL,
  `remaining_balance` decimal(15,2) NOT NULL,
  `loan_status` enum('ACTIVE','PAID') NOT NULL,
  `borrowed_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `paid_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`loan_id`),
  KEY `fk_loan_account` (`account_id`),
  KEY `fk_loan_user` (`user_id`),
  CONSTRAINT `fk_loan_account` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_loan_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--session table schema
CREATE TABLE `sessions` (
  `session_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `logout_time` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `ip_address` varchar(45) DEFAULT NULL,
  `device_info` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`session_id`),
  KEY `fk_sessions_1_idx` (`user_id`),
  CONSTRAINT `fk_sessions_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--transaction table schema
CREATE TABLE `transactions` (
  `transaction_id` varchar(15) NOT NULL,
  `account_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `amount` decimal(15,2) NOT NULL,
  `recipient_account_id` bigint DEFAULT NULL,
  `transaction_type` enum('DEPOSIT','WITHDRAW','AIRTIME','TRANSFER SENT','TRANSFER RECEIVED','BORROW','REPAY') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_transactions_1_idx` (`account_id`),
  KEY `fk_transactions_2_idx` (`user_id`),
  CONSTRAINT `fk_transactions_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_transactions_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--user table
CREATE TABLE `users` (
  `user_id` bigint NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `kra_pin` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  UNIQUE KEY `kra_pin_UNIQUE` (`kra_pin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


