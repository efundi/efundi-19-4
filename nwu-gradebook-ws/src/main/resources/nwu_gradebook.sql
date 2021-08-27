CREATE TABLE NWU_GRADEBOOK_DATA (
    ID BIGINT(20) NOT NULL AUTO_INCREMENT,
    SITE_ID VARCHAR(255) NOT NULL,
    SITE_TITLE VARCHAR(255) NOT NULL,
    MODULE VARCHAR(255) NOT NULL,
    ASSESSMENT_NAME VARCHAR(255) NOT NULL,
    STUDENT_NUMBER VARCHAR(255) NOT NULL,
    EVAL_DESCR_ID INT(11),
    GRADE DOUBLE NOT NULL,
    TOTAL_MARK DOUBLE NOT NULL,
    GRADABLE_OBJECT_ID BIGINT(20) NOT NULL,
    RECORDED_DATE DATETIME NOT NULL,
    DUE_DATE DATETIME NOT NULL,
    CREATED_DATE DATETIME NOT NULL,
    MODIFIED_DATE DATETIME,
    STATUS VARCHAR(255) NOT NULL,
    RETRY_COUNT INT(11),
    DESCRIPTION VARCHAR(255),
    PRIMARY KEY (ID),
    INDEX `SITE_ID_FI_1` (`SITE_ID`),
    INDEX `STUDENT_NUMBER_FI_1` (`STUDENT_NUMBER`),
    INDEX `EVAL_DESCR_ID_FI_1` (`EVAL_DESCR_ID`),
    INDEX `GRADE_FI_1` (`GRADE`),
    INDEX `GRADABLE_OBJECT_ID_FI_1` (`GRADABLE_OBJECT_ID`),
    INDEX `RECORDED_DATE_FI_1` (`RECORDED_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `nwu_gradebook_data` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SITE_ID` varchar(255) NOT NULL,
  `MODULE` varchar(255) NOT NULL,
  `EVAL_DESCR` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `SITE_ID_FI_1` (`SITE_ID`),
  KEY `MODULE_FI_1` (`MODULE`),
  KEY `EVAL_DESCR_FI_1` (`EVAL_DESCR`)
) ENGINE=InnoDB AUTO_INCREMENT=450 DEFAULT CHARSET=utf8;
