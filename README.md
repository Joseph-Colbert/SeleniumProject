# Selenium Automation Project

Proyecto de automatización de pruebas web desarrollado con Java, Selenium WebDriver y TestNG.

El proyecto utiliza el patrón **Page Object Model (POM)** para organizar los elementos y acciones de cada página.

## Aplicación utilizada

Las pruebas se realizan sobre la aplicación demo de OrangeHRM:

https://opensource-demo.orangehrmlive.com/

## Tecnologías

- Java 21
- Selenium WebDriver
- TestNG
- Maven
- IntelliJ IDEA
- Google Chrome
- Mozilla Firefox
- Git
- GitHub

## Estructura del proyecto

```text
src
├── main
│   └── java
│       ├── enums
│       │   ├── Gender.java
│       │   └── MaritalStatus.java
│       └── pages
│           ├── BasePage.java
│           ├── LoginPage.java
│           ├── AddEmployeePage.java
│           └── AditionalInformationPage.java
│
└── test
    └── java
        ├── BaseTest.java
        └── PagesTest.java
