USE WebShop05;
GO
-- Chạy một lần cho database hiện có; có thể chạy lại an toàn.
IF OBJECT_ID('dbo.Cart', 'U') IS NULL
CREATE TABLE dbo.Cart (
    cartId INT IDENTITY PRIMARY KEY, userId INT NOT NULL REFERENCES dbo.Users(userid),
    buyDate DATETIME2, status INT
);
IF OBJECT_ID('dbo.CartItem', 'U') IS NULL
CREATE TABLE dbo.CartItem (
    cartItemId INT IDENTITY PRIMARY KEY, quantity INT NOT NULL, unitPrice FLOAT NOT NULL,
    productId INT NOT NULL REFERENCES dbo.Product(productId),
    cartId INT NOT NULL REFERENCES dbo.Cart(cartId)
);
IF COL_LENGTH('dbo.Cart', 'receiverName') IS NULL ALTER TABLE dbo.Cart ADD receiverName NVARCHAR(100);
IF COL_LENGTH('dbo.Cart', 'receiverPhone') IS NULL ALTER TABLE dbo.Cart ADD receiverPhone NVARCHAR(20);
IF COL_LENGTH('dbo.Cart', 'shippingAddress') IS NULL ALTER TABLE dbo.Cart ADD shippingAddress NVARCHAR(500);
IF COL_LENGTH('dbo.Cart', 'paymentMethod') IS NULL ALTER TABLE dbo.Cart ADD paymentMethod VARCHAR(255);
IF COL_LENGTH('dbo.CartItem', 'productName') IS NULL ALTER TABLE dbo.CartItem ADD productName NVARCHAR(255);
GO
-- 0: giỏ hàng đang mua; 1..8: đơn hàng đã đặt.
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name='UX_Cart_ActiveUser' AND object_id=OBJECT_ID('dbo.Cart'))
CREATE UNIQUE INDEX UX_Cart_ActiveUser ON dbo.Cart(userId) WHERE status = 0;
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name='UX_CartItem_Product' AND object_id=OBJECT_ID('dbo.CartItem'))
CREATE UNIQUE INDEX UX_CartItem_Product ON dbo.CartItem(cartId, productId);
GO
