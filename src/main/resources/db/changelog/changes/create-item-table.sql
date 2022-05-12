CREATE TABLE items
(
    ID          BIGINT       NOT NULL AUTO_INCREMENT,
    CATEGORY    varchar(255) NOT NULL,
    DESCRIPTION varchar(255) NOT NULL,
    MODEL       varchar(255) NOT NULL,
    PRICE       int          NOT NULL,
    QUANTITY    int          NOT NULL,
    PRIMARY KEY (ID)
)
