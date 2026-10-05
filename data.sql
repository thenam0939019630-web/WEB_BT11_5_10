CREATE DATABASE WebShop05;
GO

USE WebShop05;
GO

CREATE TABLE UserRoles
(
    roleId INT IDENTITY(1,1) PRIMARY KEY,
    roleName NVARCHAR(50)
);


INSERT INTO UserRoles(roleName)
VALUES
(N'Admin'),
(N'User'),
(N'Seller');

CREATE TABLE Seller
(
    sellerId INT IDENTITY(1,1) PRIMARY KEY,
    sellername NVARCHAR(50),
    images NVARCHAR(500),
    status INT
);

INSERT INTO Seller
VALUES
(N'Laptop Store','seller1.jpg',1),
(N'Phone Store','seller2.jpg',1);


CREATE TABLE Users
(
    userid INT IDENTITY(1,1) PRIMARY KEY,

    username NVARCHAR(50),
    email NVARCHAR(100),
    fullname NVARCHAR(50),
    password NVARCHAR(50),

    images NVARCHAR(500),
    phone NVARCHAR(20),

    status INT,

    code NVARCHAR(50),

    roleId INT,

    sellerId INT,


    CONSTRAINT FK_User_Role
    FOREIGN KEY(roleId)
    REFERENCES UserRoles(roleId),


    CONSTRAINT FK_User_Seller
    FOREIGN KEY(sellerId)
    REFERENCES Seller(sellerId)

);

INSERT INTO Users
(
username,
email,
fullname,
password,
status,
roleId
)
VALUES
(
'admin',
'admin@gmail.com',
'Administrator',
'123456',
1,
1
);

CREATE TABLE Category
(
    categoryId INT IDENTITY(1,1) PRIMARY KEY,

    categoryName NVARCHAR(200),

    images NVARCHAR(500),

    status INT
);

INSERT INTO Category
VALUES
(N'Laptop','laptop.jpg',1),
(N'Điện thoại','phone.jpg',1);

CREATE TABLE Product
(
    productId INT IDENTITY(1,1) PRIMARY KEY,

    productName NVARCHAR(200),

    productCode BIGINT,

    categoryId INT,

    description NVARCHAR(500),

    price FLOAT,

    amount INT,

    stock INT,

    images NVARCHAR(500),

    wishlist INT,

    status INT,

    createDate DATE,

    sellerId INT,


    CONSTRAINT FK_Product_Category
    FOREIGN KEY(categoryId)
    REFERENCES Category(categoryId),


    CONSTRAINT FK_Product_Seller
    FOREIGN KEY(sellerId)
    REFERENCES Seller(sellerId)

);

INSERT INTO Product
(
productName,
productCode,
categoryId,
description,
price,
amount,
stock,
images,
wishlist,
status,
createDate,
sellerId
)
VALUES
(
N'Laptop MSI GF65',
10001,
1,
N'Laptop gaming RTX3060',
25000000,
10,
20,
'laptop.jpeg',
0,
1,
GETDATE(),
1
);

SELECT 
p.productName,
p.price,
c.categoryName,
s.sellername

FROM Product p

JOIN Category c
ON p.categoryId=c.categoryId

JOIN Seller s
ON p.sellerId=s.sellerId;


INSERT INTO Product
(
productName,
productCode,
categoryId,
description,
price,
amount,
stock,
images,
wishlist,
status,
createDate,
sellerId
)
VALUES

(N'Iphone 15',10002,2,
N'Điện thoại Apple',
22000000,
5,
10,
'laptop.jpeg',
0,
1,
GETDATE(),
2),


(N'MSI Katana',
10003,
1,
N'Laptop gaming',
30000000,
8,
15,
'laptop.jpeg',
0,
1,
GETDATE(),
1);

INSERT INTO Product
(
productName,
productCode,
categoryId,
description,
price,
amount,
stock,
images,
wishlist,
status,
createDate,
sellerId
)
VALUES
(N'Laptop Gaming Acer Nitro',10004,1,N'Laptop gaming hiệu nang cao',18500000,7,12,'laptop.jpeg',0,1,GETDATE(),1),
(N'Laptop Lenovo IdeaPad',10005,1,N'Laptop hoc tap va van phong',12500000,12,20,'laptop.jpeg',0,1,GETDATE(),1),
(N'Laptop Dell Inspiron',10006,1,N'Laptop van phong ben va nhe',15000000,9,16,'laptop.jpeg',0,1,GETDATE(),2),
(N'Laptop Asus TUF',10007,1,N'Laptop gaming Asus TUF',21000000,6,10,'laptop.jpeg',0,1,GETDATE(),2),
(N'Laptop HP Pavilion',10008,1,N'Laptop da nang cho sinh vien',14500000,11,18,'laptop.jpeg',0,1,GETDATE(),1),
(N'Laptop MSI Modern',10009,1,N'Laptop mong nhe hien dai',17500000,8,14,'laptop.jpeg',0,1,GETDATE(),2),
(N'Laptop Gigabyte G5',10010,1,N'Laptop gaming Gigabyte',23000000,5,9,'laptop.jpeg',0,1,GETDATE(),1),
(N'Laptop Acer Aspire',10011,1,N'Laptop pho thong cho gia dinh',11000000,15,25,'laptop.jpeg',0,1,GETDATE(),2),
(N'Laptop Lenovo Legion',10012,1,N'Laptop gaming Legion',28000000,4,8,'laptop.jpeg',0,1,GETDATE(),1),
(N'Laptop Dell Vostro',10013,1,N'Laptop doanh nghiep Dell',16500000,10,17,'laptop.jpeg',0,1,GETDATE(),2);

CREATE TABLE Cart
(
    cartId INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL,
    buyDate DATETIME2,
    status INT,
    CONSTRAINT FK_Cart_User FOREIGN KEY(userId) REFERENCES Users(userid)
);

CREATE TABLE CartItem
(
    cartItemId INT IDENTITY(1,1) PRIMARY KEY,
    quantity INT NOT NULL,
    unitPrice FLOAT NOT NULL,
    productId INT NOT NULL,
    cartId INT NOT NULL,
    CONSTRAINT FK_CartItem_Product FOREIGN KEY(productId) REFERENCES Product(productId),
    CONSTRAINT FK_CartItem_Cart FOREIGN KEY(cartId) REFERENCES Cart(cartId)
);