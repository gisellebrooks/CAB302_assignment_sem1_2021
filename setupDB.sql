CREATE TABLE IF NOT EXISTS USER_INFORMATION
                (username VARCHAR(255) NOT NULL, 
                 firstName VARCHAR(255), 
                 lastName VARCHAR(255), 
                 password CHAR(64), 
                 accountType VARCHAR(255), 
                 organisationalUnit VARCHAR(255), 
                 PRIMARY KEY ( username ));

CREATE TABLE IF NOT EXISTS ORGANISATIONAL_UNIT_INFORMATION
                (unit_id INTEGER NOT NULL AUTO_INCREMENT, 
                 unitName VARCHAR(255), 
                 credits NUMERIC(19,2), 
                 assets INTEGER, 
                 PRIMARY KEY ( unit_id ));

CREATE TABLE IF NOT EXISTS ACTIVE_BUY_ORDERS
                (buy_id INTEGER NOT NULL AUTO_INCREMENT, 
                 unitName VARCHAR(255), 
                 assetName VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP, 
                 PRIMARY KEY ( buy_id ));

CREATE TABLE IF NOT EXISTS ACTIVE_SELL_ORDERS
                (sell_id INTEGER NOT NULL AUTO_INCREMENT,
                 unitName VARCHAR(255),
                 assetName VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP, 
                 PRIMARY KEY ( sell_id )) ;

CREATE TABLE IF NOT EXISTS BUY_ORDER_HISTORY 
                (buy_id INTEGER NOT NULL, 
                 unitName VARCHAR(255),
                 assetName VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP);

CREATE TABLE IF NOT EXISTS SELL_ORDER_HISTORY
                (sell_id INTEGER NOT NULL AUTO_INCREMENT, 
                 unitName VARCHAR(255), 
                 assetName VARCHAR(255), 
                 quantity INTEGER, 
                 priceUpper NUMERIC(19,2), 
                 orderDate TIMESTAMP);

CREATE TABLE IF NOT EXISTS ASSETS
                (asset_id INTEGER NOT NULL AUTO_INCREMENT, 
                 assetName VARCHAR(255),
                 unit_id INTEGER, 
                 unitName VARCHAR(255), 
                 quantity INTEGER, 
                 PRIMARY KEY ( asset_id ));