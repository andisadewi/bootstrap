connect 'jdbc:derby://localhost:1527/database;create=true' ;

/*/////////////////////////////ROLES-RIGHTS///////////////////////////////////// */
CREATE TABLE APP.roles(
	roleID int PRIMARY KEY,
	roleName VARCHAR(32) NOT NULL
);

CREATE TABLE APP.rights(
	rightID int PRIMARY KEY,
	rightDescription VARCHAR(64) NOT NULL
);

CREATE TABLE APP.rights_roles(
	roleID int,
	rightID int,
	PRIMARY KEY(roleID,rightID),
	FOREIGN KEY(roleID) REFERENCES APP.roles(roleID) ON DELETE CASCADE,
	FOREIGN KEY(rightID) REFERENCES APP.rights(rightID) ON DELETE CASCADE
);

/*/////////////////////////////USER-LECTURE///////////////////////////////////// */
CREATE table APP."user"(
	loginName VARCHAR(100) PRIMARY KEY,
	role int,
	FOREIGN KEY(role) REFERENCES APP.roles(roleID) ON DELETE CASCADE
);

CREATE table APP.lecture(
	ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	"date" DATE NOT NULL,
	professorID VARCHAR(100),/*entspricht Dozent loginName*/
	room VARCHAR(10),
	name VARCHAR(100) NOT NULL,
	password VARCHAR(50),
	speedCounter int,
	volumeCounter int,
	speedLimit int,	/* threshold for the speed button, default value is  100 */
	volumeLimit int, /* threshold for the volume button, default value is  100 */
	votingLimit int /* threshold for the voting button, default value is  100 */
);

/*/////////////////////////////QUESTION///////////////////////////////////// */
CREATE table APP.question(
	ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	lectureID int not null,
	Text VARCHAR(160) not null,
	answered smallint not null,	/* 0 if not yet checked(answered), 1 if already */
  	FOREIGN KEY(lectureID) references APP.lecture(ID) ON DELETE CASCADE
);

CREATE table APP.voting(
	loginName VARCHAR(100),
	questionID int,
	PRIMARY KEY(loginName,questionID),
	FOREIGN KEY(loginName) references APP."user"(loginName) ON DELETE CASCADE,
	FOREIGN KEY(questionID) references APP.question(ID) ON DELETE CASCADE
);

/*/////////////////////////////SURVEY///////////////////////////////////// */
CREATE table APP.survey(
	ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	text VARCHAR(200) NOT NULL,
	visible boolean NOT NULL, /* true if survey is already published, false if not yet */
	"time" date,
	lectureID int NOT NULL,
    FOREIGN KEY(lectureID) references APP.lecture(ID) ON DELETE CASCADE
);

CREATE table APP.response(
	surveyID int,
	text varchar(200) NOT NULL,
	responseNr int CHECK (responseNr > 0 AND responseNr < 5), /* choice number, max is 4 */
	isRightRes smallint NOT NULL, /* true if it's the right choice, else false */
	PRIMARY KEY(surveyID, responseNr),
	FOREIGN KEY(surveyID) references APP.survey(ID) ON DELETE CASCADE
);

CREATE table APP.studentResponse(
	loginName VARCHAR(100),
	surveyID int,
	responseNr int NOT NULL CHECK (responseNr > 0 AND responseNr < 5),
	PRIMARY KEY(loginName,surveyID, responseNr),
	FOREIGN KEY(surveyID) references APP.survey(ID) ON DELETE CASCADE,
	FOREIGN KEY(loginName) references APP."user"(loginName) ON DELETE CASCADE
);

/*/////////////////////////////BUTTONS///////////////////////////////////// */
CREATE table APP.volumeButton(
	ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	loginName VARCHAR(100),
	lectureID int,
	louder smallint, /* true if a student pressed louder button, else false */
	FOREIGN KEY(loginName) references APP."user"(loginName) ON DELETE CASCADE,
	FOREIGN KEY(lectureID) references APP.lecture(ID) ON DELETE CASCADE
);

CREATE table APP.speedButton(
	ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	loginName VARCHAR(100),
	lectureID int,
	faster smallint, /* true if a student pressed faster button, false if slower */
	FOREIGN KEY(loginName) references APP."user"(loginName) ON DELETE CASCADE,
	FOREIGN KEY(lectureID) references APP.lecture(ID) ON DELETE CASCADE
);


insert into app.roles (roleID,roleName) values (0,'Admin');
insert into app.roles (roleID,roleName) values (1,'Professor');
insert into app.roles (roleID,roleName) values (2,'Student');

insert into app.rights(rightID,rightDescription) values (0,'BenutzerVerwaltung');
insert into app.rights(rightID,rightDescription) values (1,'RechteVerwaltung');
insert into app.rights(rightID,rightDescription) values (2,'VeranstaltungErstellen');
insert into app.rights(rightID,rightDescription) values (3,'UmfragenErstellen');
insert into app.rights(rightID,rightDescription) values (4,'FragenBeantworten');
insert into app.rights(rightID,rightDescription) values (5,'ButtonsAuslesen');
insert into app.rights(rightID,rightDescription) values (6,'VeranstaltungHoeren');
insert into app.rights(rightID,rightDescription) values (7,'UmfragenBeantworten');
insert into app.rights(rightID,rightDescription) values (8,'FragenStellen');
insert into app.rights(rightID,rightDescription) values (9,'ButtonsDruecken');

insert into app.rights_roles (roleID,rightID) values (0,0);
insert into app.rights_roles (roleID,rightID) values (0,1);
insert into app.rights_roles (roleID,rightID) values (0,2);
insert into app.rights_roles (roleID,rightID) values (0,3);
insert into app.rights_roles (roleID,rightID) values (0,4);
insert into app.rights_roles (roleID,rightID) values (0,5);
insert into app.rights_roles (roleID,rightID) values (0,6);
insert into app.rights_roles (roleID,rightID) values (0,7);
insert into app.rights_roles (roleID,rightID) values (0,8);
insert into app.rights_roles (roleID,rightID) values (0,9);

insert into app.rights_roles (roleID,rightID) values (1,2);
insert into app.rights_roles (roleID,rightID) values (1,3);
insert into app.rights_roles (roleID,rightID) values (1,4);
insert into app.rights_roles (roleID,rightID) values (1,5);

insert into app.rights_roles (roleID,rightID) values (2,6);
insert into app.rights_roles (roleID,rightID) values (2,7);
insert into app.rights_roles (roleID,rightID) values (2,8);
insert into app.rights_roles (roleID,rightID) values (2,9);


insert into app."user" (loginName,role) values ('philippf',1);
insert into app."user" (loginName,role) values ('jakobwiller',0);





































