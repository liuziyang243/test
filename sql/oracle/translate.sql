-- entry
CREATE TABLE Entry (
  id              NUMBER(11) NOT NULL PRIMARY KEY,
  originalWord    VARCHAR2(100),
  translationWord VARCHAR2(200),
  languageName    VARCHAR2(30),
  reviseFlag      NUMBER(1)
);

CREATE SEQUENCE Entry_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER Entry_Trigger
BEFORE
INSERT ON Entry
FOR EACH ROW
  BEGIN
    SELECT Entry_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- language
CREATE TABLE Language (
  languageName VARCHAR2(30) NOT NULL PRIMARY KEY
);
