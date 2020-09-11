package mx.com.fincomun.tenderos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.Empresa;
import mx.com.fincomun.tenderos.bean.Proveedor;
import mx.com.fincomun.tenderos.bean.ProveedorRequest;

@Service
public class ProveedorDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(ProveedorDao.class);
	
	public boolean insertProveedor(ProveedorRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isInsert = true;
		
		try{
			int sequenceValue = callSequence("TENDEROS_PROVEEDOR_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_PROVEEDOR VALUES (?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setString(2, request.getNombreProveedor());
			pstmt.setString(3, request.getNumeroContacto() == null || request.getNumeroContacto().trim().length() == 0 ? "0":request.getNumeroContacto());
			pstmt.setString(4, request.getCorreo() == null || request.getCorreo().trim().length() ==0 ? "  " : request.getCorreo());
			pstmt.setTimestamp(5, new Timestamp(new Date().getTime()));
			pstmt.setLong(6, request.getIdUsuario());
			pstmt.setInt(7, request.getIdEmpresa());
			
			boolean insert = pstmt.execute();
			log.info("insert: "+insert);
			
		} catch (Exception e) {
			isInsert = false;
			log.error("Error al registrar proveedor: " + e);
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException ex) {
				e.printStackTrace();log.error(e);
			}
		} finally{
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException e) {
				e.printStackTrace();log.error(e);		
			}
		}
		return isInsert;
	}
	
	public boolean updateProveedor(ProveedorRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isUpdate = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "UPDATE TENDEROS_PROVEEDOR SET NOMBRE = ? , TELEFONO = ?, CORREO = ? WHERE ID = ?";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setString(1, request.getNombreProveedor());
			pstmt.setString(2, request.getNumeroContacto() == null || request.getNumeroContacto().trim().length() == 0 ? "0":request.getNumeroContacto());
			pstmt.setString(3, request.getCorreo() == null || request.getCorreo().trim().length() == 0 ? "  " : request.getCorreo());
			pstmt.setLong(4, request.getIdProveedor());
			
			int update = pstmt.executeUpdate();
			
			if(update == 1){
				isUpdate = true;
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar proveedor: " + e);
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException ex) {
				e.printStackTrace();log.error(e);
			}
		} finally{
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException e) {
				e.printStackTrace();log.error(e);		
			}
		}
		return isUpdate;
	}
	
	public boolean deleteProveedor(ProveedorRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isDelete = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "DELETE FROM TENDEROS_PROVEEDOR WHERE ID = ?";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setLong(1, request.getIdProveedor());
			
			int delete = pstmt.executeUpdate();
			
			if(delete == 1){
				isDelete = true;
			}
			
		} catch (Exception e) {
			log.error("Error al eliminar proveedor: " + e);
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException ex) {
				e.printStackTrace();log.error(e);
			}
		} finally{
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException e) {
				e.printStackTrace();log.error(e);		
			}
		}
		return isDelete;
	}
	
	public Proveedor getProveedorById(long id){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		Proveedor response = null;
		try {
            conn = this.getConnectionOracle();

            String query = "SELECT * FROM TENDEROS_PROVEEDOR WHERE ID = "+id+"";

            log.info("query : " + query);

            cs = conn.prepareCall(query);          
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	response = new Proveedor();
            	response.setIdProveedor(rs.getInt("ID"));
            	response.setNombreProveedor(rs.getString("NOMBRE"));
            	response.setCorreo(rs.getString("CORREO"));
            }       
            conn.commit();
        } catch (Exception e) {
            log.info("Exception ERROR:" + e);
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException ex) {
                e.printStackTrace();
                log.error(e +"");
            }
        } finally {
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e + "");
            }
        }
		return response;
	}
	
	public List<Proveedor> getListProveedores(long idUsuario){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		try {
            conn = this.getConnectionOracle();

            String query = "SELECT * FROM TENDEROS_PROVEEDOR WHERE ID_USUARIO = "+idUsuario+" ORDER BY NOMBRE DESC";

            log.info("query : " + query);

            cs = conn.prepareCall(query);          
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	Proveedor proveedor = new Proveedor();
            	proveedor.setIdProveedor(rs.getInt("ID"));
            	proveedor.setNombreProveedor(rs.getString("NOMBRE"));
            	proveedor.setCorreo(rs.getString("CORREO"));
            	proveedores.add(proveedor);
            }       
            conn.commit();
        } catch (Exception e) {
            log.info("Exception ERROR:" + e);
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException ex) {
                e.printStackTrace();
                log.error(e +"");
            }
        } finally {
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e + "");
            }
        }
		return proveedores;
	}
	
	public List<Proveedor> getListProveedoresEmpresa(long idUsuario){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		try {
            conn = this.getConnectionOracle();

            String query = "SELECT TP.ID, TP.NOMBRE, TP.TELEFONO, TP.CORREO, TE.NOMBRE AS NOMBRE_EMPRESA "
            		+ "FROM TENDEROS_PROVEEDOR TP "
            		+ "LEFT OUTER JOIN TENDEROS_EMPRESA TE "
            		+ "ON TP.EMPRESA_ID = TE.ID "
            		+ "WHERE TP.ID_USUARIO = "+idUsuario+" "
            		+ "ORDER BY NOMBRE DESC";

            log.info("query : " + query);

            cs = conn.prepareCall(query);          
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	Proveedor proveedor = new Proveedor();
            	proveedor.setIdProveedor(rs.getInt("ID"));
            	proveedor.setNombreProveedor(rs.getString("NOMBRE"));
            	proveedor.setCorreo(rs.getString("CORREO"));
            	proveedor.setTelefono(rs.getString("TELEFONO"));
            	proveedor.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
            	proveedores.add(proveedor);
            }       
            conn.commit();
        } catch (Exception e) {
            log.info("Exception ERROR:" + e);
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException ex) {
                e.printStackTrace();
                log.error(e +"");
            }
        } finally {
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e + "");
            }
        }
		return proveedores;
	}
	
	public int insertEmpresa(String empresa){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		int sequenceValue = 0;
		
		try{
			sequenceValue = callSequence("TENDEROS_EMPRESA_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_EMPRESA VALUES (?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setString(2, empresa);
			
			pstmt.execute();
			
		} catch (Exception e) {
			log.error("Error al registrar en bitacora: " + e);
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException ex) {
				e.printStackTrace();log.error(e);
			}
		} finally{
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			}catch (SQLException e) {
				e.printStackTrace();log.error(e);		
			}
		}
		return sequenceValue;
	}
	
	public List<Empresa> getEmpresas(){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		List<Empresa> empresas = new ArrayList<Empresa>();
		try {
            conn = this.getConnectionOracle();

            String query = "SELECT * FROM TENDEROS_EMPRESA";

            log.info("query : " + query);

            cs = conn.prepareCall(query);          
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	Empresa empresa = new Empresa();
            	empresa.setId(rs.getInt("ID"));
            	empresa.setNombre(rs.getString("NOMBRE"));
            	empresas.add(empresa);
            }       
            conn.commit();
        } catch (Exception e) {
            log.info("Exception ERROR:" + e);
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException ex) {
                e.printStackTrace();
                log.error(e +"");
            }
        } finally {
            try {
                closeResultSet(rs);
                closeStatement(cs);
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e + "");
            }
        }
		return empresas;
	}
}