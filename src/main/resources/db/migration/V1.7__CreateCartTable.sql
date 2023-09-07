CREATE TABLE CART (ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
PRODUCT_ID INT ,
USER_ID BIGINT ,
QUANTITY INT,
PRIMARY KEY(ID),
CONSTRAINT FK_PRODUCT
FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCT(ID) ON DELETE CASCADE,
CONSTRAINT FK_USER
FOREIGN KEY(USER_ID) REFERENCES USERTABLE(ID) ON DELETE CASCADE);