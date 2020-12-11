-- We need to create the permission in the SAKAI_REALM_FUNCTION table

INSERT INTO SAKAI_REALM_FUNCTION VALUES (DEFAULT, 'asn.marker');


-- Instructor

-- INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!site.template'), 
-- (select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'instructor'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));

-- INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!group.template'), 
-- (select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'instructor'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));

INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!group.template.course'), 
(select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'instructor'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));

INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!site.template.course'), 
(select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'instructor'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));



-- maintain
INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!site.template'), 
(select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'maintain'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));
INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!group.template'), 
(select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'maintain'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));
-- INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!group.template.course'), 
-- (select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'maintain'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));
-- INSERT INTO SAKAI_REALM_RL_FN VALUES((select REALM_KEY from SAKAI_REALM where REALM_ID = '!site.template.course'), 
-- (select ROLE_KEY from SAKAI_REALM_ROLE where ROLE_NAME = 'maintain'), (select FUNCTION_KEY from SAKAI_REALM_FUNCTION where FUNCTION_NAME = 'asn.marker'));


-- BackDating for past sites.


-- for each realm that has a role matching something in this table, we will add to that role the function from this table

CREATE TABLE PERMISSIONS_SRC_TEMP (ROLE_NAME VARCHAR(99), FUNCTION_NAME VARCHAR(99));



-- maintain

INSERT INTO PERMISSIONS_SRC_TEMP values ('maintain','asn.marker');

-- Instructor

INSERT INTO PERMISSIONS_SRC_TEMP values ('Instructor','asn.marker');

-- lookup the role and function numbers

CREATE TABLE PERMISSIONS_TEMP (ROLE_KEY INTEGER, FUNCTION_KEY INTEGER);

INSERT INTO PERMISSIONS_TEMP (ROLE_KEY, FUNCTION_KEY)

SELECT SRR.ROLE_KEY, SRF.FUNCTION_KEY

from PERMISSIONS_SRC_TEMP TMPSRC

JOIN SAKAI_REALM_ROLE SRR ON (TMPSRC.ROLE_NAME = SRR.ROLE_NAME)

JOIN SAKAI_REALM_FUNCTION SRF ON (TMPSRC.FUNCTION_NAME = SRF.FUNCTION_NAME);



-- insert the new functions into the roles of any existing realm that has the role (don't convert the "!site.helper" or "!user.template")

INSERT INTO SAKAI_REALM_RL_FN (REALM_KEY, ROLE_KEY, FUNCTION_KEY)

SELECT

SRRFD.REALM_KEY, SRRFD.ROLE_KEY, TMP.FUNCTION_KEY

FROM

(SELECT DISTINCT SRRF.REALM_KEY, SRRF.ROLE_KEY FROM SAKAI_REALM_RL_FN SRRF) SRRFD

JOIN PERMISSIONS_TEMP TMP ON (SRRFD.ROLE_KEY = TMP.ROLE_KEY)

JOIN SAKAI_REALM SR ON (SRRFD.REALM_KEY = SR.REALM_KEY)

WHERE SR.REALM_ID != '!site.helper' AND SR.REALM_ID NOT LIKE '!user.template%'

AND NOT EXISTS (

SELECT 1

FROM SAKAI_REALM_RL_FN SRRFI

WHERE SRRFI.REALM_KEY=SRRFD.REALM_KEY AND SRRFI.ROLE_KEY=SRRFD.ROLE_KEY AND SRRFI.FUNCTION_KEY=TMP.FUNCTION_KEY

);



-- clean up the temp tables

DROP TABLE PERMISSIONS_TEMP;

DROP TABLE PERMISSIONS_SRC_TEMP;


-- Update current assignments
UPDATE asn_assignment SET IS_MARKER = true WHERE SUBMISSION_TYPE = 6;
UPDATE asn_assignment SET IS_MARKER = false WHERE SUBMISSION_TYPE != 6;
