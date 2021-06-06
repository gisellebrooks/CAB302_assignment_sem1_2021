INSERT INTO USER_INFORMATION
VALUES ('user1', 'e127334cf782d06dd8a88c72ca0a6b8157405ee1c7fa8b048f93ce861b1319a1', 'USER', 'org1', 'Mike Wazowski');
-- password is "!123asddasADS@#zxc"

INSERT INTO USER_INFORMATION
VALUES ('user2', 'ecf5577aaca9e2b0a167bc0325f4206b5a2473db0eeb618660fb353a19537624', 'ADMIN', 'org2', 'John Smith');
-- password is "!@z123as#asdasADSxc"

INSERT INTO USER_INFORMATION
VALUES ('user3', '85e6fe2a495e23a81bc93a14608df3e2a58cbdcdd99e01fa4c90d486c7509f6b', 'USER', 'org3', 'Jane Smith');
-- password is "123asPERDS!@#dazxc"

INSERT INTO USER_INFORMATION
VALUES ('user4', '7d20c8a43dab45cfa7b2a7f5c6366f53b6bd064f3b65e16591cd08d69e087a50', 'USER', 'org4', 'Missy Moo');
-- password is "!@z123as#231!@#dsaASD"

INSERT INTO ORGANISATIONAL_UNIT_INFORMATION
VALUES ('org1', 'Hardware Unit', 3000);

INSERT INTO ORGANISATIONAL_UNIT_INFORMATION
VALUES ('org2', 'Human Resources', 3030);

INSERT INTO ORGANISATIONAL_UNIT_INFORMATION
VALUES ('org3', 'Machine Learning Unit', 5000);

INSERT INTO ORGANISATIONAL_UNIT_INFORMATION
VALUES ('org4', 'Web Development Unit', 4700);


INSERT INTO INVENTORY
VALUES ('asset1', 'CPU', 'org1', 30);

INSERT INTO INVENTORY
VALUES ('asset2', 'RAM', 'org4', 30);

INSERT INTO INVENTORY
VALUES ('asset3', 'ARDUINOS', 'org3', 30);

INSERT INTO INVENTORY
VALUES ('asset4', 'CPU', 'org4', 30);

INSERT INTO INVENTORY
VALUES ('asset5', 'RAM', 'org1', 50);

INSERT INTO INVENTORY
VALUES ('asset6', 'DOGE', 'org4', 30);


INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy1', 'user1', 'RAM', 15, 10, '2021-03-24 16:34:27.0');

INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy2', 'user2', 'CPU', 15, 10, '2021-03-24 16:34:26.0');

INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy3', 'user3', 'CPU', 5, 20, '2021-03-24 16:34:26.0');

INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy4', 'user4', 'ARDUINOS', 30, 15, '2021-03-24 16:34:26.0');

INSERT INTO ACTIVE_BUY_ORDERS
VALUES ('buy5', 'user1', 'DOGE', 10, 15, '2021-03-24 16:34:26.0');

INSERT INTO ACTIVE_BUY_ORDERS
VALUES ('buy6', 'user4', 'RAM', 30, 50, '2021-03-24 16:34:26.0');


INSERT INTO ACTIVE_SELL_ORDERS 
VALUES ('sell1', 'user3', 'asset3', 'ARDUINOS', 15, 10, '2021-03-24 16:34:26.0');

INSERT INTO ACTIVE_SELL_ORDERS 
VALUES ('sell2', 'user4', 'asset4', 'CPU', 15, 10, '2021-03-24 16:34:27.0');

INSERT INTO ACTIVE_SELL_ORDERS 
VALUES ('sell3', 'user1', 'asset1', 'CPU', 10, 12, '2021-03-24 16:34:28.0');

INSERT INTO ACTIVE_SELL_ORDERS
VALUES ('sell4', 'user1', 'asset5', 'RAM', 10, 17, '2021-03-24 16:34:29.0');

INSERT INTO ACTIVE_SELL_ORDERS
VALUES ('sell5', 'user4', 'asset6', 'DOGE', 10, 10, '2021-03-24 16:34:30.0');



