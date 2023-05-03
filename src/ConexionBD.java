import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class ConexionBD {


        static Connection con;
        static Statement st;
        static ResultSet rs;
        static PreparedStatement ps;

        public void abrirConexion() {
            try {
                String userName="zubiri";
                String password="zubiri";
                String url="jdbc:mysql://localhost:3306/museo";
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, userName, password);
                System.out.println("Conexión a la BD");
            }
            catch (Exception e) {
                System.out.println("Error en conexión");
            }
        }

        //Para cerrar la conexión una vez terminadas las consultas
        public void cerrarConexion() {
            try {
                // Cerrar el ResultSet, el objeto PreparedStatement y la conexión
                rs.close();
                ps.close();
                con.close();
                System.out.println("Conexión cerrada");
            }
            catch (SQLException e) {
                System.out.println("Error al cerrar conexión");
            }
        }

    public ArrayList<Fotografos> selectFotografos() {
        try {

            ArrayList<Fotografos> fotografosList = new ArrayList<>();

            // Crear la sentencia SQL para seleccionar todos los registros de la tabla "fotografos"
            String sql = "SELECT * FROM fotografos";
            // Crear el objeto PreparedStatement
            ps = con.prepareStatement(sql);

            // Ejecutar la consulta y obtener el ResultSet
            rs = ps.executeQuery();

            // Procesar los resultados del ResultSet
            while (rs.next()) {

                int id = rs.getInt("fotografoId");
                String nombre = rs.getString("nombre");
                boolean galardonado = rs.getBoolean("galardonado");

                Fotografos fotografos = new Fotografos(id,nombre,galardonado);
                fotografosList.add(fotografos);

            }
            return fotografosList;

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<String> selectNombreFotos() {
        try {


            String sql = "SELECT titulo FROM fotos";
            // Crear el objeto PreparedStatement
            ps = con.prepareStatement(sql);

            // Ejecutar la consulta y obtener el ResultSet
            rs = ps.executeQuery();

            ArrayList<String> nombres = new ArrayList<>();
            // Procesar los resultados del ResultSet
            while (rs.next()) {
                String nombre = rs.getString("titulo");
                nombres.add(nombre);
            }

            return nombres;
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<String> selectNombreFotosPorFotografo(String nombreFotografo) {
        ArrayList<String> nombresFotos = new ArrayList<>();

        try {
            ps = con.prepareStatement("SELECT titulo FROM fotos JOIN fotografos ON fotos.fotografoId = fotografos.fotografoId WHERE fotografos.nombre = ?");
            ps.setString(1, nombreFotografo);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombresFotos.add(rs.getString("titulo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombresFotos;
    }
    public ArrayList<String> selectNombreFotosPorFotografo(String nombreFotografo, String fecha) {
        ArrayList<String> nombresFotos = new ArrayList<>();

        try {
            ps = con.prepareStatement("SELECT titulo FROM fotos JOIN fotografos ON fotos.fotografoId = fotografos.fotografoId WHERE fotografos.nombre = ? and fotos.fecha >= ?");
            ps.setString(1, nombreFotografo);
            ps.setString(2, fecha);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombresFotos.add(rs.getString("titulo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombresFotos;
    }
    void icrementarVisitas(String imagen) {
        try {
            ps = con.prepareStatement("UPDATE fotos SET visitas = visitas + 1 WHERE titulo = ?");
            ps.setString(1,imagen);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        ConexionBD cone = new ConexionBD();
        cone.abrirConexion();
        cone.selectNombreFotos();
        cone.cerrarConexion();
    }

}
