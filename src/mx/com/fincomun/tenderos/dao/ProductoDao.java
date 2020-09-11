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

import mx.com.fincomun.tenderos.bean.Producto;
import mx.com.fincomun.tenderos.bean.ProductoInventario;
import mx.com.fincomun.tenderos.bean.ProductoRequest;

@Service
public class ProductoDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(ProductoDao.class);
	
	public boolean insertProducto(ProductoRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isInsert = true;
		
		try{
			int sequenceValue = callSequence("TENDEROS_ARTICULO_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_ARTICULO VALUES (?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setString(2, request.getNombre());
			pstmt.setFloat(3, request.getPrecioVenta());
			pstmt.setFloat(4, request.getPrecioCosto());
			
			java.sql.Clob clobImagen = oracle.sql.CLOB.createTemporary(conn, false, oracle.sql.CLOB.DURATION_SESSION);
			clobImagen.setString(1, request.getImagen());
			pstmt.setClob(5, clobImagen);
			
			java.sql.Clob clobCodigoQR = oracle.sql.CLOB.createTemporary(conn, false, oracle.sql.CLOB.DURATION_SESSION);
			clobCodigoQR.setString(1, request.getCodigoQR());
			pstmt.setClob(6, clobCodigoQR);
			
			pstmt.setInt(7, request.getStock());
			pstmt.setTimestamp(8, new Timestamp(new Date().getTime()));
			pstmt.setLong(9, request.getIdUsuario());
			pstmt.setInt(10, request.getIdProveedor());
			
			pstmt.execute();
			
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

	public boolean updateProducto(ProductoRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isUpdate = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "UPDATE TENDEROS_ARTICULO SET NOMBRE = ? , PRECIO_VENTA = ?, PRECIO_COSTO = ?, STOCK = ? WHERE ID = ?";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setString(1, request.getNombre());
			pstmt.setFloat(2, request.getPrecioVenta());
			pstmt.setFloat(3, request.getPrecioCosto());
			pstmt.setInt(4, request.getStock());
			pstmt.setInt(5, request.getIdProducto());
			
			int update = pstmt.executeUpdate();
			
			if(update == 1){
				isUpdate = true;
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar producto: " + e);
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
	
	public boolean updateProductoStockInicial(ProductoRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isUpdate = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "UPDATE TENDEROS_ARTICULO SET STOCK = ?, STOCK_INICIAL = ? WHERE ID = ?";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, request.getStock());
			pstmt.setInt(2, request.getStockInicial());
			pstmt.setInt(3, request.getIdProducto());
			
			int update = pstmt.executeUpdate();
			
			if(update == 1){
				isUpdate = true;
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar producto: " + e);
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
	
	public boolean updateProductoStock(ProductoRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isUpdate = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "UPDATE TENDEROS_ARTICULO SET STOCK = ? WHERE ID = ?";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, request.getStock());
			pstmt.setInt(2, request.getIdProducto());
			
			int update = pstmt.executeUpdate();
			
			if(update == 1){
				isUpdate = true;
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar producto: " + e);
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
	
	public boolean updateProductoCierreMes(){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isUpdate = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "UPDATE TENDEROS_ARTICULO SET STOCK_INICIAL = STOCK ";
			
			pstmt = conn.prepareStatement(in.toString());
			
			int update = pstmt.executeUpdate();
			
			if(update == 1){
				isUpdate = true;
			}
			
		} catch (Exception e) {
			log.error("Error al actualizar producto: " + e);
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
	
	public boolean deleteProducto(ProductoRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isDelete = false;
		
		try{
			conn = this.getConnectionOracle();
			
			String in = "DELETE FROM TENDEROS_ARTICULO WHERE ID = ?";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, request.getIdProducto());
			
			int delete = pstmt.executeUpdate();
			
			if(delete == 1){
				isDelete = true;
			}
			
		} catch (Exception e) {
			log.error("Error al eliminar producto: " + e);
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
	
	public ProductoInventario getPorId(long idProducto){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        ProductoInventario response = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT * FROM  TENDEROS_ARTICULO WHERE ID = "+idProducto;

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	response = new ProductoInventario();
            	response.setIdProducto(rs.getInt("ID"));
            	response.setNombre(rs.getString("NOMBRE"));
            	response.setPrecioVenta(rs.getFloat("PRECIO_VENTA"));
            	response.setImagen(rs.getString("IMAGEN"));
            	response.setCodigoQR(rs.getString("CODIGO_QR"));
            	response.setPrecioCosto(rs.getFloat("PRECIO_COSTO"));
            	response.setIdProveedor(rs.getInt("TENDEROS_PROVEEDOR_ID"));
            	response.setStock(rs.getInt("STOCK"));
            	response.setStockInicial(rs.getInt("STOCK_INICIAL"));
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
	
	public Producto getPorNombre(String nombre, long idUsuario){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        Producto response = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT * FROM TENDEROS_ARTICULO WHERE UPPER(NOMBRE) = '"+nombre+"' AND ID_USUARIO = "+idUsuario+"";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	response = new Producto();
            	response.setIdProducto(rs.getInt("ID"));
            	response.setNombre(rs.getString("NOMBRE"));
            	response.setPrecioVenta(rs.getFloat("PRECIO_VENTA"));
            	response.setImagen(rs.getString("IMAGEN"));
            	response.setCodigoQR(rs.getString("CODIGO_QR"));
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
	
	public List<Producto> listProductos(long idUsuario){
		List<Producto> productos = new ArrayList<Producto>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT * FROM TENDEROS_ARTICULO WHERE ID_USUARIO = "+idUsuario+" ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	Producto producto = new Producto();
            	producto.setIdProducto(rs.getInt("ID"));
            	producto.setNombre(rs.getString("NOMBRE"));
            	producto.setPrecioVenta(rs.getFloat("PRECIO_VENTA"));
            	producto.setImagen(rs.getString("IMAGEN"));
            	producto.setCodigoQR(rs.getString("CODIGO_QR"));
            	productos.add(producto);
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
		return productos;
	}
	
	public List<ProductoInventario> getListProductosPorFiltro(String filtro,  long idUsuario){
		List<ProductoInventario> productos = new ArrayList<ProductoInventario>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            
            String query = "SELECT TA.*, TE.NOMBRE AS NOMBRE_EMPRESA "
            		+ "FROM TENDEROS_ARTICULO TA "
            		+ "LEFT JOIN TENDEROS_PROVEEDOR TP ON TP.ID = TA.TENDEROS_PROVEEDOR_ID "
            		+ "LEFT JOIN TENDEROS_EMPRESA TE ON TE.ID = TP.EMPRESA_ID "
            		+ "WHERE TA.ID_USUARIO = "+idUsuario+"  "
            		+ "ORDER BY  "+filtro+" ";
            
            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProductoInventario producto = new ProductoInventario();
            	producto.setIdProducto(rs.getInt("ID"));
            	producto.setNombre(rs.getString("NOMBRE"));
            	producto.setPrecioVenta(rs.getFloat("PRECIO_VENTA"));
            	producto.setImagen(rs.getString("IMAGEN"));
            	producto.setCodigoQR(rs.getString("CODIGO_QR"));
            	producto.setPrecioCosto(rs.getFloat("PRECIO_COSTO"));
            	producto.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
            	producto.setIdProveedor(rs.getInt("TENDEROS_PROVEEDOR_ID"));
            	producto.setStock(rs.getInt("STOCK"));
            	productos.add(producto);
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
		return productos;
	}
	
	public List<ProductoInventario> getListProductos(){
		List<ProductoInventario> productos = new ArrayList<ProductoInventario>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT * FROM TENDEROS_ARTICULO ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProductoInventario producto = new ProductoInventario();
            	producto.setIdProducto(rs.getInt("ID"));
            	producto.setNombre(rs.getString("NOMBRE"));
            	producto.setPrecioVenta(rs.getFloat("PRECIO_VENTA"));
            	producto.setImagen(rs.getString("IMAGEN"));
            	producto.setCodigoQR(rs.getString("CODIGO_QR"));
            	producto.setPrecioCosto(rs.getFloat("PRECIO_COSTO"));
            	productos.add(producto);
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
		return productos;
	}
}
