CREATE TABLE Polls (pollId BIGINT NOT NULL, isdeleted BOOLEAN, memberId BIGINT, pollDescription VARCHAR(255), pollshortText VARCHAR(255), fbPollId BIGINT, PRIMARY KEY (pollId))
CREATE TABLE ConnectionsPolled (ID BIGINT NOT NULL, name VARCHAR(255), CONNECTIONID BIGINT, fbPollId BIGINT, POLLSENTDATE VARCHAR(255), RATING INTEGER, REPONSESTRING VARCHAR(255), RESPONSERECEIVEDDATE VARCHAR(255), pollId BIGINT, PRIMARY KEY (ID))
ALTER TABLE ConnectionsPolled ADD CONSTRAINT FK_ConnectionsPolled_pollId FOREIGN KEY (pollId) REFERENCES Polls (pollId)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
