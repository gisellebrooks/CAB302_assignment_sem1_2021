CREATE TABLE IF NOT EXISTS USER_INFORMATION
                (userID VARCHAR(255) NOT NULL, 
                 password CHAR(64), 
                 accountType VARCHAR(255), 
                 orgID VARCHAR(255), 
		 name CHAR(64),
                 PRIMARY KEY ( userID));

CREATE TABLE IF NOT EXISTS ORGANISATIONAL_UNIT_INFORMATION
                (orgID VARCHAR(255), 
                 orgName VARCHAR(255), 
                 credits NUMERIC(19,2), 
                 PRIMARY KEY ( orgID ));

CREATE TABLE IF NOT EXISTS ACTIVE_BUY_ORDERS
                (buyID VARCHAR(255), 
		 orgID VARCHAR(255),
		 assetID VARCHAR(255),
                 assetName VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP, 
                 PRIMARY KEY ( buyID));

CREATE TABLE IF NOT EXISTS ACTIVE_SELL_ORDERS
                (sellID VARCHAR(255),
                 assetName VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP, 
                 PRIMARY KEY ( sellID )) ;

CREATE TABLE IF NOT EXISTS BUY_ORDER_HISTORY 
                (buyID VARCHAR(255), 
                 orgID VARCHAR(255),
                 assetID VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP);

CREATE TABLE IF NOT EXISTS SELL_ORDER_HISTORY
                (sellID VARCHAR(255) NOT NULL, 
                 orgID VARCHAR(255), 
                 assetID VARCHAR(255), 
                 quantity INTEGER, 
                 reconcilePrice NUMERIC(19,2), 
                 reconcileDate TIMESTAMP);

CREATE TABLE IF NOT EXISTS INVENTORY
                (assetID VARCHAR(255), 
		 assetName CHAR(64),
                 orgID VARCHAR(255), 
                 quantity INTEGER, 
                 PRIMARY KEY ( assetID ));