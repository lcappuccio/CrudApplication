CREATE TABLE EMPLOYEES (
  EMPLOYEE_ID      INT(11)     NOT NULL,
  EMPLOYEE_NAME    VARCHAR(25) NOT NULL,
  EMPLOYEE_SURNAME VARCHAR(25) NOT NULL,
  PRIMARY KEY (EMPLOYEE_ID)
);

CREATE TABLE EMPLOYEES_SALARY (
  EMPLOYEE_ID DECIMAL(10, 0) NOT NULL,
  SALARY      FLOAT          NULL
);