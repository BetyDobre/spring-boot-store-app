# CREATE DATABASE IF NOT EXISTS homemadestore;
#
# CREATE TABLE IF NOT EXISTS customers(
#     customerId long not null auto_increment primary key,
#     firstName varchar(100) not null,
#     lastName varchar(100) not null,
#     address varchar(100) not null,
#     phoneNumber varchar(10) not null
# );
#
# CREATE TABLE IF NOT EXISTS  bankAccounts(
#       bankAccountCVV long not null auto_increment primary key,
#       cardNumber varchar(16),
#       accountNumber varchar(100) not null,
#       balance double not null,
#       customer int not null,
#       CONSTRAINT FK_customerId_account FOREIGN KEY (customer) REFERENCES customers(customerId)
# );
#
# CREATE TABLE IF NOT EXISTS  decorations(
#       decorationId long not null auto_increment primary key,
#       decorationName varchar(100) not null,
#       price double not null,
#       category varchar(45) not null,
#       description varchar(100) not null
# );
#
# CREATE TABLE IF NOT EXISTS orders(
#      orderId long not null auto_increment primary key,
#      totalAmount double not null,
#      orderPlaced date not null,
#      account int not null,
#      customer int not null,
#      CONSTRAINT FK_accountId_order FOREIGN KEY (account) REFERENCES bankAccounts(bankAccountCVV),
#      CONSTRAINT FK_customerId_order FOREIGN KEY (customer) REFERENCES customers(customerId)
# );
#
# CREATE TABLE IF NOT EXISTS orderItems(
#     orderItemId long not null auto_increment primary key,
#     quantity int not null,
#     price double not null,
#     decoration int not null,
#     orders int not null,
#     CONSTRAINT FK_productId_item FOREIGN KEY (decoration) REFERENCES decorations(decorationId),
#     CONSTRAINT FK_orderId_item FOREIGN KEY (orders) REFERENCES orders(orderId)
# );
#
# drop table if exists cart;
#
# create table if not exists cart(
#    cartId int not null auto_increment primary key,
#    totalAmount double not null,
#    customer int not null,
#    CONSTRAINT FK_customerId_cart FOREIGN KEY (customer) REFERENCES customers(customerId)
# );