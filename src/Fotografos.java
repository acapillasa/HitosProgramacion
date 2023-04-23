import java.security.PrivateKey;
import java.util.ArrayList;

public class Fotografos extends ConexionBD {
    private int fotografoId;
    private String nombre;
    private boolean galardonado;

    public Fotografos(int fotografoId,String nombre,boolean galardonado) {
        this.fotografoId = fotografoId;
        this.nombre = nombre;
        this.galardonado = galardonado;
    }
    public Fotografos() {

    }

    @Override
    public ArrayList<Fotografos> selectFotografos() {
        return super.selectFotografos();
    }

    @Override
    public void abrirConexion() {
        super.abrirConexion();
    }

    @Override
    public void cerrarConexion() {
        super.cerrarConexion();
    }

    public int getFotografoId() {
        return fotografoId;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isGalardonado() {
        return galardonado;
    }
}
