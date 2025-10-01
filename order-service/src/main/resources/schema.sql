CREATE TABLE IF NOT EXISTS customer_order (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    total NUMERIC(12, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_customer_order_product ON customer_order (product_id);
