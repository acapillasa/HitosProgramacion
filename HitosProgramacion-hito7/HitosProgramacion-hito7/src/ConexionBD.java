import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConexionBD {


        private Connection con;
        private Statement st;
        private ResultSet rs;
        private PreparedStatement ps;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    void abrirConexion() {
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
    void cerrarConexion() {
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


     List<Fotografos> selectFotografos() {
        try {

            List<Fotografos> fotografosList = new ArrayList<>();

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

    List<String> selectNombreFotos() {
        try {


            String sql = "SELECT titulo FROM fotos";
            // Crear el objeto PreparedStatement
            ps = con.prepareStatement(sql);

            // Ejecutar la consulta y obtener el ResultSet
            rs = ps.executeQuery();

            List<String> nombres = new ArrayList<>();
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

    List<String> selectNombreFotosPorFotografo(String nombreFotografo) {
        List<String> nombresFotos = new ArrayList<>();

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

    List<String> selectNombreFotosPorFotografo(String nombreFotografo, String fecha) {
        List<String> nombresFotos = new ArrayList<>();

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

    int visitasFotografo(int idFotografo) {
        int visitasTotales = 0;
        try {
            ps = con.prepareStatement("SELECT sum(visitas) FROM fotos where fotografoId = ?");
            ps.setInt(1,idFotografo);
            rs = ps.executeQuery();
            while (rs.next()) {
                visitasTotales = rs.getInt("sum(visitas)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitasTotales;
    }

    Map<Integer, Integer> createVisitasMap() {
        Map<Integer, Integer> mapa = new HashMap<>();
        List<Fotografos> listaFotografos = selectFotografos();

        for (int i = 0; i < listaFotografos.size(); i++) {
            mapa.put(listaFotografos.get(i).getFotografoId(),visitasFotografo(listaFotografos.get(i).getFotografoId()));
        }
        return mapa;
    }

    List<Integer> listaNoPremiados() {
        List<Integer> listaNo = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT fotografoId FROM fotografos WHERE galardonado = 0");
            rs = ps.executeQuery();

            while (rs.next()) {
                listaNo.add(rs.getInt("fotografoId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaNo;
    }
    Map<Integer,String> fotosNo(List<Integer> lista) {
        Map<Integer,String> mapaNo = new HashMap<>();
        try {
            for (int i = 0; i < lista.size(); i++) {
                ps = con.prepareStatement("SELECT fotosId,titulo FROM fotos WHERE fotografoId = ? AND visitas = 0");
                ps.setInt(1,lista.get(i));
                rs = ps.executeQuery();
                while (rs.next()) {
                    mapaNo.put(rs.getInt("fotosId"),rs.getString("titulo"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapaNo;
    }

    void eliminarFoto(int id) {
        try {
            ps = con.prepareStatement("DELETE FROM fotos WHERE fotosId = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int fotografoSinFotos(int id) {
        int filas = 0;
        try {
            ps = con.prepareStatement("SELECT * FROM fotos WHERE fotografoId = ?");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                filas++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filas;
    }

    void eliminarFotografo(int id) {
        try {
            ps = con.prepareStatement("DELETE FROM fotografos WHERE fotografoId = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    int idFotografoConIdFoto(int id) {
        int idFotografo = 0;
        try {
            ps = con.prepareStatement("SELECT fotografoId FROM fotos WHERE fotosId = ?");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                idFotografo = rs.getInt("fotografoId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idFotografo;
    }



    public static void main(String[] args) {
        ConexionBD cone = new ConexionBD();
        cone.abrirConexion();

        for (int id: cone.createVisitasMap().keySet()) {
            System.out.println("IdFotografo: " + id + " Visitas: " + cone.createVisitasMap().get(id));
        }
        cone.cerrarConexion();
    }

}
