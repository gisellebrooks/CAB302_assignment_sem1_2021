INSERT INTO USER_INFORMATION
VALUES ('user1', '14e3885dc3a6764f84023badcdaa54b9f3f6121ff28c68174636f533ce97e3a5', 'user', 'org1', 'Mike Wazowski');
-- password123

INSERT INTO USER_INFORMATION
VALUES ('user2', '14e3885dc3a6764f84023badcdaa54b9f3f6121ff28c68174636f533ce97e3a5', 'admin', 'org2', 'John Smith');
-- password123

INSERT INTO USER_INFORMATION
VALUES ('user3', '434848baf76dabf925abbf64374170f281fcf08e5fd6fda3648275ad3a602819', 'user', 'org3', 'Jane Smith');
-- password is 123ps

INSERT INTO USER_INFORMATION
VALUES ('user4', '8213448bc7c05ea87c37e2d8c02b5a9f46c7391c8ea8697b4d776c831b01545f', 'user', 'org4', 'Missy Moo');
-- password is 123

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


INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy1', 'user1', 'RAM', 15, 10, '2021-03-24 16:34:26.66');

INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy2', 'user2', 'CPU', 15, 10, '2021-03-24 16:34:26.66');

INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy3', 'user3', 'CPU', 5, 20, '2021-03-24 16:34:26.66');

INSERT INTO ACTIVE_BUY_ORDERS 
VALUES ('buy4', 'user4', 'ARDUINOS', 30, 15, '2021-03-24 16:34:26.66');


INSERT INTO ACTIVE_SELL_ORDERS 
VALUES ('sell1', 'user3', 'asset3', 'ARDUINOS', 15, 10, '2021-03-24 16:34:26.66');

INSERT INTO ACTIVE_SELL_ORDERS 
VALUES ('sell2', 'user4', 'asset4', 'CPU', 15, 10, '2021-03-24 16:34:26.66');

INSERT INTO ACTIVE_SELL_ORDERS 
VALUES ('sell3', 'user1', 'asset1', 'CPU', 10, 12, '2021-03-24 16:34:26.66');




