package data;

import enums.Gender;
import enums.MaritalStatus;
import org.testng.annotations.DataProvider;

/** Proporciona los dos empleados con los que se repite el mismo test. */
public class EmployeeDataProvider {

    @DataProvider(name = "empleados")
    public static Object[][] employees() {
        return new Object[][] {
                // OrangeHRM pide las fechas como yyyy-dd-mm, según el placeholder del formulario.
                { new EmployeeData("Juan", "Perez", "juanperez", "JuanPerez123!",
                        "12345678", "2028-23-09", "Japanese", MaritalStatus.SINGLE,
                        "1995-15-06", Gender.MALE) }
        };
    }
}
/*
    { new EmployeeData("Ana", "Lopez", "analopez", "AnaLopez123!",
                               "87654321", "2029-08-12", "Japanese", MaritalStatus.MARRIED,
                        "1997-04-20", Gender.FEMALE) }*/
