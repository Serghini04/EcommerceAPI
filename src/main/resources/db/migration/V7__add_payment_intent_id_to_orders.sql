ALTER TABLE orders ADD COLUMN payment_intent_id VARCHAR(100) DEFAULT NULL AFTER order_status;
