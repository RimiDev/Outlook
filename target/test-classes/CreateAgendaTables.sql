USE AGENDAdb;

DROP TABLE IF EXISTS EMAIL;
DROP TABLE IF EXISTS APPOINTMENTGROUP;
DROP TABLE IF EXISTS APPOINTMENT;

CREATE TABLE EMAIL (
	UNAME varchar(60) PRIMARY KEY,
	EMAIL varchar(60) NOT NULL,
	PASSWORD varchar(60) NOT NULL,
	URL varchar(60) NOT NULL,
	PORT int NOT NULL DEFAULT 465,
        ISDEFAULT boolean NOT NULL DEFAULT 0,
        REMINDER int NOT NULL DEFAULT 0
);

CREATE TABLE APPOINTMENTGROUP (
	GROUPNUMBER int NOT NULL AUTO_INCREMENT PRIMARY KEY,
        GROUPNAME varchar(30) NOT NULL,
	COLOR char(10)
);


CREATE TABLE APPOINTMENT (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TITLE varchar(60) NOT NULL,
	LOCATION varchar(60) NOT NULL,
	STARTTIME timestamp DEFAULT 0,
	ENDTIME timestamp DEFAULT 0,
	DETAILS varchar(60),
	WHOLEDAY boolean,
	APPOINTMENTGROUP int,
	ALARM boolean
);

INSERT INTO APPOINTMENTGROUP (GROUPNAME, COLOR) values
("Family", "Yellow"),
("Meetings", "Green"),
("School Work", "Red"),
("Fun", "Pink");


INSERT INTO APPOINTMENT (TITLE, LOCATION, STARTTIME, ENDTIME, DETAILS, WHOLEDAY, APPOINTMENTGROUP, ALARM) values
("Dinner", "John's place", "2017-09-01 12:00:00", "2017-09-01 14:00:00", "Celebration, buy gift after", 0, 1, 1),
("Supper", "John's place", "2017-09-01 18:00:00", "2017-09-01 21:00:00", "Celebration, bring gift", 0, 1, 1),
("Soccer", "Tim's highschool", "2017-09-03 10:00:00", "2017-09-01 14:00:00", "Practice", 0, 1, 1),
("Family time", "Home", "2017-09-01 15:00:00", "2017-09-01 22:00:00", "Buy a new board game", 0, 1, 1),
("Music class", "Dawson College", "2017-10-01 11:00:00", "2017-10-01 12:30:00", "Don't forget to bring your drums", 0, 1,1),
("Gym class", "Dawson College", "2017-10-01 13:00:00", "2017-10-01 15:00:00", "Gym clothes and shoes", 0, 1, 1),
("Breakfast", "Tim's place", "2017-11-01 8:00:00", "2017-11-01 10:00:00", "Bring bacon", 0, 1, 1),
("Swimming", "Verdun park", "2017-11-01 12:00:00", "2017-11-01 17:00:00", "Call Tim to confirm", 0, 1, 1),
("Supper", "Home", "2017-11-01 18:00:00", "2017-11-01 20:00:00", "", 0, 1, 1),
("Movie", "Home", "2017-11-01 20:00:00", "2017-11-01 22:00:00", "", 0, 1, 1),
("Breakfast", "Bob's place", "2017-12-01 8:00:00", "2017-12-01 10:00:00", "", 0, 1,1),
("Karate class", "Wellington st", "2017-12-01 11:00:00", "2017-12-01 13:00:00", "", 0, 1,1),
("Party","Juan's place","2017-07-02 21:00:00", "2017-07-03 3:00:00", "Bring GG", 0, 1,1),
("Java class","Dawson College","2017-07-03 10:00:00", "2017-07-03 13:00:00", "Don't be too hungover", 0, 1, 1),
("Gym class","Dawson college","2017-07-03 14:00:00", "2017-07-03 17:00:00", "Don't puke hombre", 0, 1, 1),
("Accounting course online sign-up","","2017-07-05 10:00:00", "2017-07-05 10:05:00", "", 0, 1, 1),
("Buisness course online sign-up","","2017-07-06 10:00:00", "2017-07-06 10:05:00", "", 0, 1, 1),
("Art's course online sign-up","","2017-07-07 10:00:00", "2017-07-06 10:05:00", "", 0, 1, 1),
("C# course online sign-up","","2017-07-07 10:00:00", "2017-07-07 10:05:00", "", 0, 1, 1),
("Java course online sign-up","","2017-07-07 11:00:00", "2017-07-07 11:05:00", "", 0, 1, 1),
("PHP course online sign-up","","2017-07-07 12:00:00", "2017-07-07 12:05:00", "", 0, 1, 1),
("Dora's exploration course online sign-up","","2017-07-10 2:00:00", "2017-07-06 2:05:00", "", 0, 1, 1),
("Gym course online sign-up","","2017-07-11 10:00:00", "2017-07-11 10:05:00", "", 0, 1, 1),
("Valerie's Birthday","Moe's pub","2017-07-15 18:00:00", "2017-07-16 3:00:00", "Get her roses!", 0, 1, 1),
("Rock climbing","Dawson College","2017-07-21 18:00:00", "2017-07-21 21:00:00", "6$ admission", 0, 1, 1),
("Nascar rally","McGill campus","2017-08-01 12:00:00", "2017-07-16 21:00:00", "Bring tickets and beer!", 1, 1, 1),
("University student for a day","McGill campus","2017-08-03 10:00:00", "2017-08-03 18:00:00", "Bring student transript and notepad", 0, 1, 1),
("University onsite registration","McGill campus","2017-08-05 8:00:00", "2017-08-05 13:00:00", "Bring student transript and ID", 0, 0, 1),
("Recieving Waffle maker","Post Canada","2017-08-07 10:00:00", "2017-08-03 10:05:00", "Bring recipt i printed out", 0, 2, 1),
("Waffles!","Home","2017-08-08 8:00:00", "2017-08-08 10:00:00", "Read the manuel, don't burn them!", 0, 2, 1),
("Ben's giraffe viewing class ","McGill campus","2017-08-10 10:00:00", "2017-08-10 18:00:00", "Bring binoculars", 0, 2, 1),
("Meeting with Ken Fogel","Dawson College","2017-09-01 10:00:00", "2017-09-01 12:00:00", "Bring your smile!", 0, 2, 1),
("Java homework due", "Dawson College", "2017-04-01 8:30:00", "2017-04-01 8:30:01", "DB project", 0, 3, 1),
("C# homework due", "Dawson College", "2017-05-03 8:30:00", "2017-05-03 8:30:00", "Monogame phase", 0, 3, 1),
("Gym homework due", "Dawson College", "2017-05-06 8:30:00", "2017-05-06 8:30:00", "Squat sheet", 0, 3, 1),
("Marketing homework due", "Dawson College", "2017-04-05 8:30:00", "2017-04-05 8:30:01", "Dollar value sheet pg.17", 0, 3, 1),
("Accounting homework due", "Dawson College", "2017-04-09 8:30:00", "2017-04-09 8:30:01", "Excerise 18+19", 0, 3, 1),
("Java homework due", "Dawson College", "2017-04-01 8:30:00", "2017-04-01 8:30:01", "Observer Class", 0, 3, 1),
("Geo homework due", "Dawson College", "2017-01-01 8:30:00", "2017-01-01 8:30:01", "Map sheet pg.7", 0, 3, 1),
("Dance with Marie", "Churchill", "2017-07-19 21:00:00", "2017-07-20 4:00:00", "Dance your life away!", 0, 2, 1),
("Brush the cat!", "Home", "2017-08-02 8:00:00", "2017-08-02 9:00:00", "Take hair off after!", 0, 1, 1),
("Presentatino on wolves", "McGill", "2017-01-02 8:30:00", "2017-01-02 10:30:00", "", 0, 1, 1),
("Zoo", "Zoo Granby", "2017-02-03 10:30:00", "2017-02-03 20:30:00", "Bring the camera!", 1, 3, 1),
("Jungle Gym", "Jungle Gym inc.", "2017-01-06 10:30:00", "2017-02-03 14:30:00", "", 0, 2, 1),
("Alexander the great presentation","McGill", "2017-02-06 10:30:00", "2017-02-06 13:30:00", "", 0, 1, 1),
("History Class", "Dawson College", "2017-03-19 8:30:00", "2017-03-19 10:30:00", "Bring textbook", 0, 1, 1),
("Buy school books", "Dawson College", "2017-08-30 8:30:00", "2017-08-30 9:30:00", "Be the first in line!", 0, 2, 1),
("Date time!", "Studio Movie", "2017-09-30 21:30:00", "2017-09-30 2:30:00", "Wear the suit she gave you!", 0, 3, 1),
("Dance class", "Dawson College", "2017-09-30 10:30:00", "2017-09-30 12:30:00", "Bring the appropriate shoes", 0, 1, 1),
("Buy gym shoes", "Alexis Nihon", "2017-08-30 11:30:00", "2017-08-30 13:30:00", "Don't forget the coupon!", 0, 2, 1);


INSERT INTO EMAIL (UNAME, EMAIL, PASSWORD, URL, PORT, ISDEFAULT, REMINDER) values
("Sebastian Gregoire", "mangoMan123@gmail.com", "ilovemangos", "smtp.gmail.com", 25, 0, 1),
("Sebastian Laureau", "cardude123@gmail.com", "mustangforlife", "smtp.gmail.com", 587, 0, 0),
("Max Lacasse", "JAM1537681@gmail.com", "JAMproject", "smtp.gmail.com", 587, 1, 120);

-- ("George King", "allhailme@gmail.com", "bunchoflosersbelowme", "smtp.gmail.com", 25),
-- ("Amanda George", "myexistoogoodforme@gmail.com", "ilovebadboys", "smtp.gmail.com", 587),
-- ("Wi tong Jackson", "wingwambestie@gmail.com", "noodlesoapftw", "smtp.gmail.com", 25),
-- ("Charlie Brown", "snoopydoopyboy@gmail.com", "wheresnoopyat", "smtp.gmail.com", 25),
-- ("Elizabeth Ju", "puppystarpo@gmail.com", "doggerkiwi", "smtp.gmail.com", 587),
-- ("Natasha Lacasse", "rockingpompoms123@gmail.com", "iluvd", "smtp.gmail.com", 465),
-- ("Muhammed Roq", "tangerinesaregod@gmail.com", "tangerineking",  "smtp.gmail.com", 465),
-- ("Daniela Elputa", "maamigomuyputa@gmail.com", "luvputas",  "smtp.gmail.com", 465),
-- ("George Labelle", "iamaboydontjudgemyname@gmail.com", "judgemeanddie",  "smtp.gmail.com", 465),
-- ("Alexander Thebad", "icomesecondtothegreat@gmail.com", "istillwingirls",  "smtp.gmail.com", 465),
-- ("Ken King", "geniousatwork@gmail.com", "iluvhelping",  "smtp.gmail.com", 465),
-- ("Mathieu Charles", "footlessalarm@gmail.com", "footlessnighttable",  "smtp.gmail.com", 465),
-- ("Max Elbest", "thethingsiwanttodo@gmail.com", "max123",  "smtp.gmail.com", 465),
-- ("Tim Timothy", "timsarecoolokay@gmail.com", "timisthebest",  "smtp.gmail.com", 465),
-- ("Cassandra Cote", "ilovehairstylists@gmail.com", "givemehairsamples",  "smtp.gmail.com", 465),
-- ("Marilou Hot", "ilovemaxforever@gmail.com", "maxismyworld",  "smtp.gmail.com", 465),
-- ("Issac Germ", "wundabar@gmail.com", "nein",  "smtp.gmail.com", 465),
-- ("Bill Junior", "howmanyoftheseareleft@gmail.com", "omgwhat",  "smtp.gmail.com", 465),
-- ("Jill Bunior", "thesearequitelongtomake@gmail.com", "whatomg",  "smtp.gmail.com", 465),
-- ("Bill Clunt", "dontrunoutofideas@gmail.com", "neversaidmymom",  "smtp.gmail.com", 465),
-- ("Pamela Lisette", "iamyourfavouritedream@gmail.com", "iwishiwasalive",  "smtp.gmail.com", 465),
-- ("Chloe Breton", "ilovemypitbull@gmail.com", "whatiwoulddoforapitbull",  "smtp.gmail.com", 465),
-- ("Melina Teasedale", "tatsaremylife@gmail.com", "anelallday",  "smtp.gmail.com", 465),
-- ("Mark Denis", "ithinkimcoolenoughforemail@gmail.com", "liealldayerrday",  "smtp.gmail.com", 465),
-- ("Marc Andree", "iamtheworst@gmail.com", "ihatemylife",  "smtp.gmail.com", 465),
-- ("Robert Dale", "chairsarecomfortable@gmail.com", "sellmychair",  "smtp.gmail.com", 465),
-- ("Valerie Audet", "highschoolwasgreatestpart@gmail.com", "maxlovedme",  "smtp.gmail.com", 465),
-- ("Jessica Dore", "iluvcouchestositon@gmail.com", "wutisthetime",  "smtp.gmail.com", 465),
-- ("Bob Sagot", "sagotsRuletheWorld@gmail.com", "sagot4life",  "smtp.gmail.com", 465),
-- ("Jeffery Jeff", "Jeff123@gmail.com", "bikesarelife",  "smtp.gmail.com", 465),
-- ("Jennifer Dihon", "Mustardlover@gmail.com", "mynameisJennactually",  "smtp.gmail.com", 465),
-- ("Dice Roll", "DiceRollingCasino@gmail.com", "casinoVegas123",  "smtp.gmail.com", 465),
-- ("Marie Pari", "MangoSpicy@gmail.com", "maxlover",  "smtp.gmail.com", 465),
-- ("Hen Die", "Henneverdies@gmail.com", "idislikemyname",  "smtp.gmail.com", 465),
-- ("Nuh Dye", "Dyeliveslong123@gmail.com", "password",  "smtp.gmail.com", 465),
-- ("Natasha Leveque", "hottieschoolgirl69@gmail.com", "password123",  "smtp.gmail.com", 465);
--



