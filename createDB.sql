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
	id_i int PRIMARY KEY auto_increment,
	nr_exemplare int not null,
    data_imprumut date,
    data_restituire date,
    id_u int not null,
    id_c int not null,
    FOREIGN KEY (id_u) REFERENCES Utilizator(id_u)
    on update restrict
    on delete cascade,
	FOREIGN KEY (id_c) REFERENCES Carte(id_c)
    on update restrict
    on delete cascade
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
(6,"Serpent & Dove","Shelby Mahurin","HarperCollins Publishers","2019","4"),
(7,"Caraval","Stephanie Garber","Macmillan Publishers","2016","5"),
(8,"Harry Potter and the Chamber of Secrets","J.K. Rowling","Bloomsbury Publishing","1998","19"),
(9,"Shatter Me","Tahereh Mafi","HarperCollins Publishers","2011","10"),
(10,"Cinder","Marissa Meyer","HarperCollins Publishers","2012","5");

INSERT INTO Utilizator
VALUES
(1, "andreiU", "and", "Varan", "Andrei", "str. Ciresilor nr.8", "1991221225443"),
(2, "daniel", "dan", "Pop", "Daniel", "str. Popesti nr.10", "199234234123"),
(3, "andreeaU", "andr", "Radu", "Andreea", "str. Stramba nr.34", "1232384923423"),
(4, "dana", "dana", "Covaci", "Dana", "str. Razoare nr.22", "1239817233434"),
(5, "ovidiu", "ovi", "Felecan", "Ovidiu", "str. Campului nr. 2A", "102938120312"),
(6, "ion", "ion", "Dumitru", "Ion", "str. Frunzisului nr 110", "123123234234"),
(7, "marcel", "mar", "Damian", "Marcel", "str. Almasului nr, 12", "123982739482"),
(8, "valeria", "val", "Marian", "Valeria", "str. Gherlei nr. 1", "12390123324234"),
(9, "rodica", "rod", "Patrascu", "Rodica", "str. Stramba nr. 3", "435345345435"),
(10, "maria", "mar", "Ilie", "Maria", "str. Calarasi nr. 8", "234892734978234"),
(11, "daniela", "dan", "Patrascu", "Daniela", "str. Uliului nr. 19", "39845739475983"),
(12, "alexandru", "ale", "Pop", "Alexandru", "str. Frizia nr. 3", "938745983745"),
(13, "florin", "flo", "Dumitrescu", "Florin", "str. Coratim nr. 14", "43578398475934");

INSERT INTO Imprumuturi (nr_exemplare, data_imprumut, data_restituire, id_u, id_c)
VALUES
(1, "2020-12-12", "2020-12-22", 1, 2),
(1, "2020-12-14", "2020-12-24", 1, 3),
(2, "2021-03-10", "2021-03-20", 1, 6),
(1, "2021-03-12", "2021-03-22", 1, 5),
(1, "2021-03-10", "2021-03-20", 2, 1),
(2, "2021-04-08", "2021-04-18", 2, 2),
(1, "2021-03-10", "2021-03-20", 2, 10),
(1, "2021-02-12", "2021-02-22", 3, 1),
(1, "2021-03-01", "2021-03-11", 3, 7),
(1, "2021-03-02", "2021-03-12", 3, 5);