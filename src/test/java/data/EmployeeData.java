package data;

import enums.Gender;
import enums.MaritalStatus;

/** Datos necesarios para una ejecución completa del test de empleado. */
public record EmployeeData(
        String firstName,
        String lastName,
        String usernamePrefix,
        String password,
        String licenseNumber,
        String licenseExpiration,
        String nationality,
        MaritalStatus maritalStatus,
        String birthDate,
        Gender gender) {

    // TestNG muestra este texto en el reporte sin exponer la contraseña.
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
