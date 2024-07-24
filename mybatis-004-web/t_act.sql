DROP TABLE IF EXISTS t_act;
CREATE TABLE t_act (
    id BIGSERIAL PRIMARY KEY,
    actno VARCHAR(255),
    balance DECIMAL(15, 2)
);

-- 插入範例資料
INSERT INTO t_act (actno, balance) VALUES ('act001', 50000.00);
INSERT INTO t_act (actno, balance) VALUES ('act002', 0.00);
