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
- ExtentReports
- Maven
- IntelliJ IDEA
- Google Chrome
- Mozilla Firefox
- Git
- GitHub

## Estructura del proyecto

src
├── main
│ └── java
│ ├── enums
│ │ ├── Gender.java
│ │ └── MaritalStatus.java
│ └── pages
│ ├── BasePage.java
│ ├── LoginPage.java
│ ├── AddEmployeePage.java
│ ├── AditionalInformationPage.java
│ └── EmployeeListPage.java
│
└── test
├── java
│ ├── data
│ │ ├── EmployeeData.java
│ │ └── EmployeeDataProvider.java
│ ├── helpers
│ │ ├── ReportManager.java
│ │ └── ScreenshotHelper.java
│ ├── BaseTest.java
│ ├── ReportListener.java
│ └── PagesTest.java
└── resources
└── logs
└── screenshots (se crea al fallar un test)

## Ejecución

El `@DataProvider(name = "empleados")` define loq es dos casos de prueba. Cada caso inicia un navegador, crea un empleado con un nombre de usuario y un ID únicos, guarda sus datos adicionales y lo busca en **PIM → Employee List** por ese ID. El `@Test` incluye una descripción visible en el reporte.

El reporte HTML se genera en `target/reports/ExtentReport.html`. Si un caso podria digamos fallar, se guarda una captura en `src/test/resources/logs/screenshots/` y se adjunta al reporte. Las capturas y `target/` se ignoran en Git.
