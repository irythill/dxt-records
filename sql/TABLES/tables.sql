CREATE SCHEMA dxtrecords;
USE dxtrecords;

/* Criação das tabelas */

CREATE TABLE Artist (
  ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  country VARCHAR(255),
  role VARCHAR(255),
  socialNetwork VARCHAR(255)
);

CREATE TABLE Music (
  ISRC VARCHAR(255) PRIMARY KEY NOT NULL,
  name VARCHAR(255),
  genre VARCHAR(255),
  duration VARCHAR(255),
  releaseDate VARCHAR(255)
);

CREATE TABLE Distributor (
  CID VARCHAR(255) PRIMARY KEY NOT NULL,
  tradeName VARCHAR(255),
  price DOUBLE,
  streamingService VARCHAR(255)
);

CREATE TABLE ArtistMusic (
    artistID INT,
    musicISRC VARCHAR(255),
    FOREIGN KEY (artistID) REFERENCES Artist(ID),
    FOREIGN KEY (musicISRC) REFERENCES Music(ISRC)
);

CREATE TABLE MusicDistributor (
    musicISRC VARCHAR(255),
    distributorCID VARCHAR(255),
    FOREIGN KEY (musicISRC) REFERENCES Music(ISRC),
    FOREIGN KEY (distributorCID) REFERENCES Distributor(CID)
);


