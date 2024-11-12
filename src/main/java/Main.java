import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.awt.Desktop;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        if (autenticarUsuario(nombreUsuario, contrasena)) {
            System.out.println("Inicio de sesión exitoso. Abriendo página de bienvenida...");
            abrirPaginaBienvenida();
        } else {
            System.out.println("Credenciales incorrectas. Intente de nuevo.");
        }

        scanner.close();
    }

    public static boolean autenticarUsuario(String nombreUsuario, String contrasena) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM usuario WHERE nombre_usuario = ? AND contraseña = ?");
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void abrirPaginaBienvenida() {
        try {
            URI uri = new URI("http://localhost:8082/bienvenida.html");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            } else {
                System.out.println("No se pudo abrir el navegador automáticamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
