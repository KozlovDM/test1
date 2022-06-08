INSERT INTO CAMPAIGN(name) values ('1');
INSERT INTO CAMPAIGN(name, status) values ('2', 'ACTIVE');
INSERT INTO CAMPAIGN(name) values ('3');
INSERT INTO CAMPAIGN(name, status) values ('4', 'DEACTIVATED');
INSERT INTO CAMPAIGN(name, status) values ('5', 'ACTIVE');

INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values ('s1', '2022-06-06 10:23:54', '2022-09-06 10:23:54', SELECT id FROM CAMPAIGN where name = '1');
INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values ('s2', '2022-02-06 10:23:54', '2022-05-06 10:23:54', SELECT id FROM CAMPAIGN where name = '4');
INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values ('s3', '2022-05-06 10:23:54', '2022-07-06 10:23:54', SELECT id FROM CAMPAIGN where name = '2');
INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values ('s4', '2022-07-06 10:23:54', '2023-09-06 10:23:54', SELECT id FROM CAMPAIGN where name = '3');
INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values ('s5', '2022-07-06 10:23:54', '2022-12-06 10:23:54', SELECT id FROM CAMPAIGN where name = '3');
INSERT INTO SCENARIOS(name, start_date, end_date, campaign_id) values ('s6', '2022-04-06 10:23:54', '2022-07-06 10:23:54', SELECT id FROM CAMPAIGN where name = '5');