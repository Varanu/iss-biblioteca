create database Biblioteca;

use Biblioteca;

create table Angajat(
	id_a int not null primary key,
    userid varchar(100),
    pass varchar(100)
)ENGINE = InnoDB;

create table Carte(
	id_c int not null primary key,
	titlu varchar(100),
    autor varchar(100),
    editura varchar(100),
    an_aparitie int,
    nr_exemplare int
)ENGINE = InnoDB;

create table Utilizator(
	id_u int not null primary key,
    username varchar(100),
    parola varchar(100),
    nume varchar(100),
    prenume varchar(100),
    adresa varchar(100),
    cnp varchar(100)
)ENGINE = InnoDB;

create table Imprumuturi(
    data_imprumut date,
    data_restituire date,
    numar_exemplare int,
    id_u int not null,
    id_c int not null,
    FOREIGN KEY (id_u) REFERENCES Utilizator(id_u)
    on update restrict
    on delete cascade,
	FOREIGN KEY (id_c) REFERENCES Carte(id_c)
    on update restrict
    on delete cascade,
    CONSTRAINT pk_Imprumuturi PRIMARY KEY (id_u, id_c)
)ENGINE = InnoDB;

INSERT INTO Angajat
VALUES
(1, "andrei", "and"),
(2, "mihai", "mih"),
(3, "andreea", "andr");

INSERT INTO Carte
VALUES
(1,"Red Queen","Victoria Aveyard","HarperCollins Publishers","2015","12"), 
(2,"Six of Crows","Leigh Bardugo","Macmillan Publishers","2015","10"),
(3,"Throne of Glass","Sarah Maas","Bloomsbury Publishing","2012","7"),
(4,"Harry Potter and the Sorcerer's Stone","J.K. Rowling","Bloomsbury Publishing","1997","23"),
(5,"A Court of Thorns and Roses","Sarah Maas","Bloomsbury Publishing","2015","8"),
(6," Serpent & Dove","Shelby Mahurin","HarperCollins Publishers","2019","4"),
(7,"Caraval","Stephanie Garber","Macmillan Publishers","2016","5"),
(8,"Harry Potter and the Chamber of Secrets","J.K. Rowling","Bloomsbury Publishing","1998","19"),
(9,"Shatter Me","Tahereh Mafi","HarperCollins Publishers","2011","10"),
(10,"Cinder","Marissa Meyer","HarperCollins Publishers","2012","5");

INSERT INTO Utilizator
VALUES
(1, "andrei", "and", "Varan", "Andrei", "str. Ciresilor nr.8" , "1991221225443"),
(2, "daniel", "dan", "Pop", "Daniel", 19),
(3, "Radu", "Andreea", 20),
(4, "Covaci", "Dana", 24),
(5, "Felecan", "Ovidiu", 19),
(6, "Dumitru", "Ion", 29),
(7, "Damian", "Marcel", 30),
(8, "Marian", "Valeria", 23),
(9, "Patrascu", "Rodica", 22),
(10, "Ilie", "Maria", 25),
(11, "Patrascu", "Daniela", 19),
(12, "Pop", "Alexandru", 21),
(13, "Dumitrescu", "Florin", 22);