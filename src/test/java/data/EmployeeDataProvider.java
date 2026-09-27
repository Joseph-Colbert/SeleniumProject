package data;

import enums.Gender;
import enums.MaritalStatus;
import org.testng.annotations.DataProvider;

/** Proporciona los dos empleados con los que se repite el mismo test. */
public class EmployeeDataProvider {

    @DataProvider(name = "empleados")
    public static Object[][] employees() {
        return new Object[][] {
                { new EmployeeData("Juan", "Perez", "juanperez", "JuanPerez123!",
                        "12345678", "2028-09-23", "Japanese", MaritalStatus.SINGLE,
                        "1995-06-15", Gender.MALE) },
                { new EmployeeData("Ana", "Lopez", "analopez", "AnaLopez123!",
                        "87654321", "2029-08-12", "Japanese", MaritalStatus.MARRIED,
                        "1997-04-20", Gender.FEMALE) }
        };
    }
}
