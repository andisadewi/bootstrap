connect 'jdbc:derby://localhost:1527/database;create=true' ;

/* INSERT DEFAULT ROLES */
 insert into app.roles (roleID,roleName) values (0, 'Admin');
insert into app.roles (roleID,roleName) values (1, 'Dozent');
insert into app.roles (roleID,roleName) values (2, 'Student');

/* INSERT DEFAULT RIGHTS */
insert into app.rights (rightID,rightDescription) values (1,'BenutzerVerwaltung') ;
insert into app.rights (rightID,rightDescription) values (2,'Rollen/RechteVerwaltung') ;
insert into app.rights (rightID,rightDescription) values (3,'VeranstaltungErstellen') ;
insert into app.rights (rightID,rightDescription) values (4,'UmfragenErstellen') ;
insert into app.rights (rightID,rightDescription) values (5,'FragenBeantworten') ;
insert into app.rights (rightID,rightDescription) values (6,'ButtonsAuslesen') ;
insert into app.rights (rightID,rightDescription) values (7,'VeranstaltungHoeren') ;
insert into app.rights (rightID,rightDescription) values (8,'UmfragenBeantworten') ;
insert into app.rights (rightID,rightDescription) values (9,'FragenStellen') ;
insert into app.rights (rightID,rightDescription) values (10,'ButtonsDruecken') ;

/* ASSIGN RIGHTS TO ROLES */
insert into app.rights_roles(roleID,rightID) values (0,1) ;
insert into app.rights_roles(roleID,rightID) values (0,2) ;
insert into app.rights_roles(roleID,rightID) values (0,3) ;
insert into app.rights_roles(roleID,rightID) values (0,4) ;
insert into app.rights_roles(roleID,rightID) values (0,5) ;
insert into app.rights_roles(roleID,rightID) values (0,6) ;
insert into app.rights_roles(roleID,rightID) values (0,7) ;
insert into app.rights_roles(roleID,rightID) values (0,8) ;
insert into app.rights_roles(roleID,rightID) values (0,9) ;
insert into app.rights_roles(roleID,rightID) values (0,10) ;

insert into app.rights_roles(roleID,rightID) values (1,3) ;
insert into app.rights_roles(roleID,rightID) values (1,4) ;
insert into app.rights_roles(roleID,rightID) values (1,5) ;
insert into app.rights_roles(roleID,rightID) values (1,6) ;

insert into app.rights_roles(roleID,rightID) values (2,7) ;
insert into app.rights_roles(roleID,rightID) values (2,8) ;
insert into app.rights_roles(roleID,rightID) values (2,9) ;
insert into app.rights_roles(roleID,rightID) values (2,10) ;

/* ADMIN */
insert into app."user" (loginName,role) values ('philipf',0);

