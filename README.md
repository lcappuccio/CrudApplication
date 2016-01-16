# CrudApplication 
A basic crud application example

**Master**

[![Build Status](https://travis-ci.org/lcappuccio/crud-application.svg?branch=master)](https://travis-ci.org/lcappuccio/crud-application)
[![codecov.io](https://codecov.io/github/lcappuccio/crud-application/coverage.svg?branch=master)](https://codecov.io/github/lcappuccio/crud-application?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/0160e9182d744e18af71422dd3776e88)](https://www.codacy.com/app/leo_4/crud-application)

**Develop**

[![Build Status](https://travis-ci.org/lcappuccio/crud-application.svg?branch=develop)](https://travis-ci.org/lcappuccio/crud-application)
[![codecov.io](https://codecov.io/github/lcappuccio/crud-application/coverage.svg?branch=develop)](https://codecov.io/github/lcappuccio/crud-application?branch=develop)


## Installation
1. Create the necessary database table on your favourite RDBMS (see script.sql)
2. Download and change JDBC URL, username and password in org.systemexception.crudapplication.impl.EmployeeDaoImpl
3. Compile and deploy to you favourite web container

## Dependencies

### Testing
Use `glassfish-embedded-all` and comment out `javaee-web-api`.

### Production
Use `javaee-web-api` and comment out `glassfish-embedded-all`.

## Usage
Available servlets:
* BadWorld: just generates a bunch of random hex codes
* HelloWorld: pretty explicative
* InsertEmployee: **c**reate an employee
* RandomEmployee: **r**eads a random employee
* ListEmployees: **r**eads a table with all employees in the database
* EmployeesJson: **r**eads the full employee list in a JSON
* UpdateEmployee: **u**pdate employee record
* DeleteEmployee: **d**elete an employee