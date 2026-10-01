# Selenium Automation Project

Proyecto de automatización de pruebas web desarrollado con **Java, Selenium WebDriver, TestNG y Maven**.

El proyecto utiliza el patrón **Page Object Model (POM)** para separar los elementos y acciones de cada página de la lógica de los casos de prueba.

## Integrantes

| Apellidos | Nombres |
|-----------|---------|
| Colbert Martinez | Joseph Jonathan |
| Chuquimia Huanca | Pablo Ivan |

## Aplicación utilizada

Las pruebas se realizan sobre la aplicación demo de OrangeHRM:

https://opensource-demo.orangehrmlive.com/

## Tecnologías

- Java 21
- Selenium WebDriver 4.48.0
- TestNG 7.11.0
- Maven
- Maven Surefire Plugin 3.5.4
- ExtentReports 5.1.2
- Google Chrome
- Mozilla Firefox
- IntelliJ IDEA
- Git
- GitHub

## Estructura del proyecto

```text
Project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── enums/
│   │   │   │   ├── Gender.java
│   │   │   │   └── MaritalStatus.java
│   │   │   └── pages/
│   │   │       ├── AddEmployeePage.java
│   │   │       ├── AdditionalInformationPage.java
│   │   │       ├── BasePage.java
│   │   │       ├── EmployeeListPage.java
│   │   │       └── LoginPage.java
│   │   └── resources/
│   │
│   └── test/
│       ├── java/
│       │   ├── conf/
│       │   │   └── BaseTest.java
│       │   ├── data/
│       │   │   ├── EmployeeData.java
│       │   │   └── EmployeeDataProvider.java
│       │   ├── helpers/
│       │   │   ├── ReportListener.java
│       │   │   ├── ReportManager.java
│       │   │   └── ScreenshotHelper.java
│       │   └── test/
│       │       └── PagesTest.java
│       └── resources/
│           └── logs/
│               └── screenshots/
│
├── .gitignore
├── pom.xml
├── README.md
└── testng.xml
```

## Flujo automatizado

El caso de prueba realiza el siguiente flujo:

1. Inicia sesión en OrangeHRM.
2. Crea un nuevo empleado.
3. Valida que el empleado haya sido guardado correctamente.
4. Obtiene el ID generado para el empleado.
5. Completa su información adicional.
6. Valida el guardado de la información.
7. Busca al empleado en **PIM → Employee List**.
8. Verifica que el empleado creado se encuentre registrado.

Los datos del empleado son proporcionados mediante un **DataProvider de TestNG**.

Los localizadores y acciones se encuentran encapsulados dentro de los Page Objects, mientras que las validaciones mediante `Assert` se realizan desde la clase de prueba.

## Ejecución Cross-Browser

La suite `testng.xml` configura la ejecución del mismo caso de prueba en:

- Mozilla Firefox
- Google Chrome

`BaseTest` recibe el navegador definido en la suite e inicializa el WebDriver correspondiente.

## Requisitos

Para ejecutar el proyecto se necesita:

- Java 21
- Maven
- Google Chrome
- Mozilla Firefox

Verificar las versiones instaladas con:

```bash
java -version
mvn -version
```

Maven debe estar utilizando Java 21.

## Ejecución

Desde una terminal ubicada en la raíz del proyecto ejecutar:

```bash
mvn clean test
```

Maven Surefire ejecutará la suite definida en `testng.xml`, realizando el flujo automatizado en Firefox y Chrome.

## Reportes

El reporte HTML generado por **ExtentReports** se encuentra en:

```text
target/reports/ExtentReport.html
```

Si una prueba falla, se genera una captura de pantalla como evidencia y se adjunta al reporte.

Las capturas se almacenan en:

```text
src/test/resources/logs/screenshots/
```

El directorio `target/` y las capturas generadas durante las pruebas se excluyen del repositorio mediante `.gitignore`.