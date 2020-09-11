package mx.com.fincomun.tenderos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.VentaDirectaRequest;
import mx.com.fincomun.tenderos.bean.VentaPorArticuloRequest;

@Service
public class VentasDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(VentasDao.class);
	
	public boolean insertVentaDirecta(VentaDirectaRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isInsert = true;
		
		try{
			int sequenceValue = callSequence("TENDEROS_VENTAS_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_VENTAS (ID, MONTO_TOTAL, FECHA, TIPO_PAGO_ID, ID_USUARIO)  VALUES (?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setFloat(2, request.getMontoTotal());
			pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
			pstmt.setInt(4, request.getIdTipoPago());
			pstmt.setLong(5, request.getIdUsuario());
			
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
	
	public int insertVentaPorArticulo(VentaPorArticuloRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		int idVenta = 0;
		
		try{
			int sequenceValue = callSequence("TENDEROS_VENTAS_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_VENTAS VALUES (?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			
			java.sql.Clob clobImagen = oracle.sql.CLOB.createTemporary(conn, false, oracle.sql.CLOB.DURATION_SESSION);
			clobImagen.setString(1, request.getCodigoQR());
			pstmt.setClob(3, clobImagen);
			
			
			pstmt.setFloat(4, request.getMontoTotal());
			pstmt.setLong(5, request.getIdUsuario());
			pstmt.setInt(6, request.getIdTipoPago());
			
			pstmt.execute();
			
			idVenta = sequenceValue;
			
		} catch (Exception e) {
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
		return idVenta;
	}
	
	public boolean insertVentaArticulos(int idVenta, int idArticulo, int numeroArticulos){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isInsert = true;
		
		try{
			int sequenceValue = callSequence("TENDEROS_VENTA_ARTICULOS_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_VENTA_ARTICULOS VALUES (?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setInt(2, idVenta);
			pstmt.setInt(3, idArticulo);
			pstmt.setInt(4, numeroArticulos);
			
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
}
