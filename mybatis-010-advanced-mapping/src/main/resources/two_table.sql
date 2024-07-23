DROP TABLE IF EXISTS t_clazz;
DROP TABLE IF EXISTS t_stu;

CREATE TABLE t_clazz (
    cid SERIAL PRIMARY KEY,
    cname VARCHAR(255)
);

CREATE TABLE t_stu (
    sid SERIAL PRIMARY KEY,
    sname VARCHAR(255),
    cid INT,
    FOREIGN KEY (cid) REFERENCES t_clazz(cid)
);
