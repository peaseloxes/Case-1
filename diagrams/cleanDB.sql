--------------------------------------------------------
--  File created - Monday-October-05-2015   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence SEQ_ADDRESS
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_ADDRESS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_COMPANY
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_COMPANY"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_COURSE
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_COURSE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_COURSEINSTANCE
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_COURSEINSTANCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_DISCOUNT
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_DISCOUNT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_LOCATION
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_LOCATION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_MATERIAL
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_MATERIAL"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_PRIVATE
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_PRIVATE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_STUDENT
--------------------------------------------------------

   CREATE SEQUENCE  "CASE1"."SEQ_STUDENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table ADDRESS
--------------------------------------------------------

  CREATE TABLE "CASE1"."ADDRESS" 
   (	"ID" NUMBER(10,0), 
	"STREETNAME" VARCHAR2(255 BYTE), 
	"STREETNUMBER" VARCHAR2(255 BYTE), 
	"POSTALCODE" VARCHAR2(255 BYTE), 
	"CITY" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table COMPANY
--------------------------------------------------------

  CREATE TABLE "CASE1"."COMPANY" 
   (	"ID" NUMBER(10,0), 
	"ADDRESSID" NUMBER(10,0), 
	"NAME" VARCHAR2(255 BYTE), 
	"ACCOUNTNUMBER" VARCHAR2(255 BYTE), 
	"BIDNUMER" VARCHAR2(255 BYTE), 
	"DEPARTMENT" VARCHAR2(255 BYTE), 
	"PHONENUMBER" VARCHAR2(255 BYTE), 
	"CONTACTNAME" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table COURSE
--------------------------------------------------------

  CREATE TABLE "CASE1"."COURSE" 
   (	"ID" NUMBER(10,0), 
	"COURSECODE" VARCHAR2(255 BYTE), 
	"TITLE" VARCHAR2(255 BYTE), 
	"DURATIONDAYS" NUMBER(10,0), 
	"MAXAPPLICANTS" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table COURSEINSTANCE
--------------------------------------------------------

  CREATE TABLE "CASE1"."COURSEINSTANCE" 
   (	"ID" NUMBER(10,0), 
	"COURSEID" NUMBER(10,0), 
	"STARTDATE" DATE, 
	"ENDDATE" DATE, 
	"DEFINITIVE" NUMBER(1,0), 
	"BASEPRICE" NUMBER(10,2)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table DISCOUNT
--------------------------------------------------------

  CREATE TABLE "CASE1"."DISCOUNT" 
   (	"ID" NUMBER(10,0), 
	"TYPE" VARCHAR2(255 BYTE), 
	"PERCENTAGE" NUMBER(3,2), 
	"AMOUNT" NUMBER(10,2)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table LOCATION
--------------------------------------------------------

  CREATE TABLE "CASE1"."LOCATION" 
   (	"ID" NUMBER(10,0), 
	"COURSEINSTANCEID" NUMBER(10,0), 
	"ROOMNUMBER" VARCHAR2(255 BYTE), 
	"NAME" VARCHAR2(255 BYTE), 
	"ADDRESSID" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table MATERIAL
--------------------------------------------------------

  CREATE TABLE "CASE1"."MATERIAL" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(255 BYTE), 
	"TYPE" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table MATERIAL_COURSE
--------------------------------------------------------

  CREATE TABLE "CASE1"."MATERIAL_COURSE" 
   (	"MATERIALID" NUMBER(10,0), 
	"COURSEID" NUMBER(10,0), 
	"AMOUNT" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table PRIVATE
--------------------------------------------------------

  CREATE TABLE "CASE1"."PRIVATE" 
   (	"ID" NUMBER(10,0), 
	"ADDRESSID" NUMBER(10,0), 
	"ACCOUNTNUMBER" VARCHAR2(255 BYTE), 
	"PHONENUMBER" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table STUDENT
--------------------------------------------------------

  CREATE TABLE "CASE1"."STUDENT" 
   (	"ID" NUMBER(10,0), 
	"FIRSTNAME" VARCHAR2(255 BYTE), 
	"LASTNAME" VARCHAR2(255 BYTE), 
	"EMAIL" VARCHAR2(255 BYTE), 
	"COMPANYID" NUMBER(10,0), 
	"PRIVATEID" NUMBER(10,0), 
	"DISCOUNTID" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table STUDENT_COURSEINSTANCE
--------------------------------------------------------

  CREATE TABLE "CASE1"."STUDENT_COURSEINSTANCE" 
   (	"STUDENTID" NUMBER(10,0), 
	"COURSEINSTANCEID" NUMBER(10,0), 
	"APPLICATIONDATE" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into CASE1.ADDRESS
SET DEFINE OFF;
REM INSERTING into CASE1.COMPANY
SET DEFINE OFF;
REM INSERTING into CASE1.COURSE
SET DEFINE OFF;
REM INSERTING into CASE1.COURSEINSTANCE
SET DEFINE OFF;
REM INSERTING into CASE1.DISCOUNT
SET DEFINE OFF;
REM INSERTING into CASE1.LOCATION
SET DEFINE OFF;
REM INSERTING into CASE1.MATERIAL
SET DEFINE OFF;
REM INSERTING into CASE1.MATERIAL_COURSE
SET DEFINE OFF;
REM INSERTING into CASE1.PRIVATE
SET DEFINE OFF;
REM INSERTING into CASE1.STUDENT
SET DEFINE OFF;
REM INSERTING into CASE1.STUDENT_COURSEINSTANCE
SET DEFINE OFF;
--------------------------------------------------------
--  Constraints for Table LOCATION
--------------------------------------------------------

  ALTER TABLE "CASE1"."LOCATION" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."LOCATION" MODIFY ("ROOMNUMBER" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."LOCATION" MODIFY ("COURSEINSTANCEID" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."LOCATION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "CASE1"."STUDENT" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."STUDENT" MODIFY ("LASTNAME" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."STUDENT" MODIFY ("FIRSTNAME" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."STUDENT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COMPANY
--------------------------------------------------------

  ALTER TABLE "CASE1"."COMPANY" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."COMPANY" MODIFY ("BIDNUMER" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COMPANY" MODIFY ("ACCOUNTNUMBER" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COMPANY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COMPANY" MODIFY ("ADDRESSID" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COMPANY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MATERIAL
--------------------------------------------------------

  ALTER TABLE "CASE1"."MATERIAL" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."MATERIAL" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."MATERIAL" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDENT_COURSEINSTANCE
--------------------------------------------------------

  ALTER TABLE "CASE1"."STUDENT_COURSEINSTANCE" ADD PRIMARY KEY ("STUDENTID", "COURSEINSTANCEID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."STUDENT_COURSEINSTANCE" MODIFY ("APPLICATIONDATE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."STUDENT_COURSEINSTANCE" MODIFY ("COURSEINSTANCEID" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."STUDENT_COURSEINSTANCE" MODIFY ("STUDENTID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COURSEINSTANCE
--------------------------------------------------------

  ALTER TABLE "CASE1"."COURSEINSTANCE" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."COURSEINSTANCE" MODIFY ("BASEPRICE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSEINSTANCE" MODIFY ("DEFINITIVE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSEINSTANCE" MODIFY ("ENDDATE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSEINSTANCE" MODIFY ("STARTDATE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSEINSTANCE" MODIFY ("COURSEID" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSEINSTANCE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADDRESS
--------------------------------------------------------

  ALTER TABLE "CASE1"."ADDRESS" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."ADDRESS" MODIFY ("CITY" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."ADDRESS" MODIFY ("POSTALCODE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."ADDRESS" MODIFY ("STREETNUMBER" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."ADDRESS" MODIFY ("STREETNAME" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."ADDRESS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MATERIAL_COURSE
--------------------------------------------------------

  ALTER TABLE "CASE1"."MATERIAL_COURSE" ADD PRIMARY KEY ("MATERIALID", "COURSEID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."MATERIAL_COURSE" MODIFY ("AMOUNT" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."MATERIAL_COURSE" MODIFY ("COURSEID" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."MATERIAL_COURSE" MODIFY ("MATERIALID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COURSE
--------------------------------------------------------

  ALTER TABLE "CASE1"."COURSE" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."COURSE" MODIFY ("MAXAPPLICANTS" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSE" MODIFY ("DURATIONDAYS" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSE" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSE" MODIFY ("COURSECODE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."COURSE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DISCOUNT
--------------------------------------------------------

  ALTER TABLE "CASE1"."DISCOUNT" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."DISCOUNT" MODIFY ("TYPE" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."DISCOUNT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRIVATE
--------------------------------------------------------

  ALTER TABLE "CASE1"."PRIVATE" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "CASE1"."PRIVATE" MODIFY ("PHONENUMBER" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."PRIVATE" MODIFY ("ACCOUNTNUMBER" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."PRIVATE" MODIFY ("ADDRESSID" NOT NULL ENABLE);
  ALTER TABLE "CASE1"."PRIVATE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table COMPANY
--------------------------------------------------------

  ALTER TABLE "CASE1"."COMPANY" ADD CONSTRAINT "FKCOMPANY648494" FOREIGN KEY ("ADDRESSID")
	  REFERENCES "CASE1"."ADDRESS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table COURSEINSTANCE
--------------------------------------------------------

  ALTER TABLE "CASE1"."COURSEINSTANCE" ADD CONSTRAINT "FKCOURSEINST934475" FOREIGN KEY ("COURSEID")
	  REFERENCES "CASE1"."COURSE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOCATION
--------------------------------------------------------

  ALTER TABLE "CASE1"."LOCATION" ADD CONSTRAINT "FKLOCATION198237" FOREIGN KEY ("ADDRESSID")
	  REFERENCES "CASE1"."ADDRESS" ("ID") ENABLE;
  ALTER TABLE "CASE1"."LOCATION" ADD CONSTRAINT "FKLOCATION199944" FOREIGN KEY ("COURSEINSTANCEID")
	  REFERENCES "CASE1"."COURSEINSTANCE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MATERIAL_COURSE
--------------------------------------------------------

  ALTER TABLE "CASE1"."MATERIAL_COURSE" ADD CONSTRAINT "FKMATERIAL_C71690" FOREIGN KEY ("MATERIALID")
	  REFERENCES "CASE1"."MATERIAL" ("ID") ENABLE;
  ALTER TABLE "CASE1"."MATERIAL_COURSE" ADD CONSTRAINT "FKMATERIAL_C955190" FOREIGN KEY ("COURSEID")
	  REFERENCES "CASE1"."COURSE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRIVATE
--------------------------------------------------------

  ALTER TABLE "CASE1"."PRIVATE" ADD CONSTRAINT "FKPRIVATE665475" FOREIGN KEY ("ADDRESSID")
	  REFERENCES "CASE1"."ADDRESS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "CASE1"."STUDENT" ADD CONSTRAINT "FKSTUDENT169868" FOREIGN KEY ("COMPANYID")
	  REFERENCES "CASE1"."COMPANY" ("ID") ENABLE;
  ALTER TABLE "CASE1"."STUDENT" ADD CONSTRAINT "FKSTUDENT280406" FOREIGN KEY ("DISCOUNTID")
	  REFERENCES "CASE1"."DISCOUNT" ("ID") ENABLE;
  ALTER TABLE "CASE1"."STUDENT" ADD CONSTRAINT "FKSTUDENT97706" FOREIGN KEY ("PRIVATEID")
	  REFERENCES "CASE1"."PRIVATE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STUDENT_COURSEINSTANCE
--------------------------------------------------------

  ALTER TABLE "CASE1"."STUDENT_COURSEINSTANCE" ADD CONSTRAINT "FKSTUDENT_CO407679" FOREIGN KEY ("COURSEINSTANCEID")
	  REFERENCES "CASE1"."COURSEINSTANCE" ("ID") ENABLE;
  ALTER TABLE "CASE1"."STUDENT_COURSEINSTANCE" ADD CONSTRAINT "FKSTUDENT_CO590289" FOREIGN KEY ("STUDENTID")
	  REFERENCES "CASE1"."STUDENT" ("ID") ENABLE;
