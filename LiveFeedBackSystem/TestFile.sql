connect 'jdbc:derby://localhost:1527/database;create=true' ;

/*
 
insert into app."user" (loginName,role) values ('philip',0);
insert into app."user" (loginName,role) values ('mal',0);
insert into app."user" (loginName,role) values ('jayne',0);
insert into app."user" (loginName,role) values ('kaylee',0);

insert into app.LECTURE ("date", professorID,room, name,password, speedCounter, volumeCounter, 
speedLimit, volumeLimit, votingLimit) values ('2014-04-23', 'admin', 'EN400', 'THECH','password', 0,0,100,100,100);

insert into app.survey (text,visible,lectureID) values ('a','false',1) ;
insert into app.survey (text,visible,lectureID) values ('b','false',1) ;

insert into app.response (surveyID,text,responseNr,isrightRes) values (1,'b',1,0) ;
insert into app.response (surveyID,text,responseNr,isrightRes) values (1,'c',2,0) ;
insert into app.response (surveyID,text,responseNr,isrightRes) values (2,'d',1,0) ;

insert into app.studentResponse (loginName,surveyID,responseNr) values ('philip',1,1) ;
insert into app.studentResponse (loginName,surveyID,responseNr) values ('mal',1,2) ;
insert into app.studentResponse (loginName,surveyID,responseNr) values ('jayne',2,1) ;
insert into app.studentResponse (loginName,surveyID,responseNr) values ('kaylee',1,2) ;

*/
delete from app.survey where ID=1 ; 
insert into app.roles (roleID, roleName) values (0, 'Admin');
insert into app.roles (roleID, roleName) values (1, 'Professor');
insert into app.roles (roleID, roleName) values (2, 'Student');
insert into app."user" (loginName, role) values ('philippf', 0);

/*
select count(*) from response res RIGHT JOIN studentResponse stud ON res.ID = stud.responseID WHERE surveyID = 1 AND responseNr = 1 ;
*/



