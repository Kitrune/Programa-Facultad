package conexion;

import java.util.*;
import java.sql.*;

/**
 * Es la clase curso que se comunica con la base de datos
 *
 * @author Ornelas Munguía Axel Leonardo
 * @version 27.11.2020
 */
public class HorarioDAO extends ConexionBD {

    private static final String TABLA = "HORARIO";
    private static final String CLAVE_HORARIO = "claveHorario";
    private static final String DIA = "dia";
    private static final String TURNO = "turno";
    private static final String HR_INICIO = "hrInicio";
    private static final String HR_FIN = "hrFin";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLA;
    private static final String SQL_INSERT = "INSERT INTO " + TABLA + "(," + DIA + ", "
            + TURNO +", "+ HR_INICIO + ", "+ HR_FIN +",  ) VALUES(?,?,?,?)";
    private static final String SQL_READ = "SELECT*FROM " + TABLA + " WHERE " + CLAVE_HORARIO + " = ?;";
    private static final String SQL_DELETE = "DELETE  FROM " + TABLA + " WHERE " + CLAVE_HORARIO + " = ?";
    private static final String SQL_UPDATE = "UPDATE "+ TABLA +" SET " + DIA + " = ?,"
            + TURNO +" = ?, "+ HR_INICIO +" = ?, "+ HR_FIN +" = ? WHERE " + CLAVE_HORARIO + " = ?";

    /**
     * Constructor de la clase
     */
    public HorarioDAO() {
        super();
    }

    /**
     * Devuelve un arreglo de los datos
     *
     * @return El arreglo de objetos
     * @throws Exception Devuelve error
     */
    public ArrayList<Horario> readAll() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Se utilza para almacenar los objetos
        ArrayList<Horario> result = new ArrayList();
        //Manda al comando
        ps = conexion.prepareStatement(SQL_SELECT_ALL);
        //Ejecuta el comando y devuelve el resultado del comando
        rs = ps.executeQuery();
        //Recorre por todos los resultados
        while (rs.next()) {
            result.add(getObject(rs));
        }
        //Cierra las conexiones
        cerrar(ps);
        cerrar(rs);
        return result;
    }

    /**
     * Ingresa los datos a la tabla
     *
     * @param dto Es el objeto a ingresar
     * @throws Exception Devuelve un error
     */
    public void append(Horario dto) throws Exception {
        PreparedStatement ps = null;
        //Manda al comando
        ps = conexion.prepareStatement(SQL_INSERT);
        //Les asigna los valores que deben tener los ?
        ps.setString(1, dto.getClaveHorario()+ "");
        ps.setString(2, dto.getDia());
        ps.setString(3, dto.getTurno());
        ps.setTime(4, dto.getHrInicio());
        ps.setTime(5, dto.getHrFin());
        //Ejecuta el comando y acutaliza
        ps.executeUpdate();
        //Cierra la conexión
        cerrar(ps);
    }

    /**
     * Se encarga de actualizar cualquier dato
     *
     * @param dto Son los nuevos datos a actualizar
     * @throws Exception Devuelve un error
     */
    public void update(Horario dto) throws Exception {
        PreparedStatement ps = null;
        //Manda el comando
        ps = conexion.prepareStatement(SQL_UPDATE);
        //Les asigna los valores que deben tener los ?
        ps.setString(1, dto.getClaveHorario()+ "");
        ps.setString(2, dto.getDia());
        ps.setString(3, dto.getTurno());
        ps.setTime(4, dto.getHrInicio());
        ps.setTime(5, dto.getHrFin());
        //Ejecuta el comando y actualiza
        ps.executeUpdate();
        //Cierra la conexión
        cerrar(ps);
    }

    /**
     * Borra un dato de la tabla
     *
     * @param dto Es el dto que se borra
     * @throws Exception Devuelve error
     */
    public void delete(Horario dto) throws Exception {
        PreparedStatement ps = null;
        //Manda el comando
        ps = conexion.prepareStatement(SQL_DELETE);
        //Les asigna los valores que deben tener los ?
        ps.setString(1, dto.getClaveHorario()+ "");
        //Ejecuta el comando y actualiza
        ps.executeUpdate();
        //Cierra la conexión
        cerrar(ps);
    }

    /**
     * Lee la información de un profesor en especifico
     *
     * @param dto Es el profesor a buscar
     * @return Devuelve el objeto dto si lo encuentra
     * @throws Exception Devuelve un error
     */
    public Horario read(Horario dto) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Horario result = null;
        //Manda el comando
        ps = conexion.prepareStatement(SQL_READ);
        //Les asigna los valores que deben tener los ?
        ps.setString(1, dto.getClaveHorario() + "");
        //Ejecuta el comando y devuelve el resultado del comando
        rs = ps.executeQuery();
        //Recorre por todos los resultados
        if (rs.next()) {
            result = getObject(rs);
        }
        //Cierra las conexiones
        cerrar(ps);
        cerrar(rs);

        return result;
    }

    /**
     * Detecta el objeto enviado por el resultset y lo devuelve convertido a dto
     *
     * @param rs Es la salida de la consola
     * @return Devuelve el objeto dto
     * @throws Exception Devuelve un error
     */
    private Horario getObject(ResultSet rs) throws Exception {
        return new Horario(Integer.parseInt(rs.getString(CLAVE_HORARIO)), rs.getString(DIA), 
                rs.getString(TURNO),rs.getTime(HR_INICIO),rs.getTime(HR_FIN));
    }
}
