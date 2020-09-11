package mx.com.fincomun.tenderos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.CompraDirectaRequest;
import mx.com.fincomun.tenderos.bean.CompraRequest;

@Service
public class ComprasDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(ComprasDao.class);
	
	public int insertCompra(CompraRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		int idCompra = 0;
		
		try{
			int sequenceValue = callSequence("TENDEROS_PEDIDO_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_PEDIDO VALUES (?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setInt(3, 0);
			pstmt.setFloat(4, request.getMontoTotal());
			
			java.sql.Clob clobImagen = oracle.sql.CLOB.createTemporary(conn, false, oracle.sql.CLOB.DURATION_SESSION);
			clobImagen.setString(1, request.getCodigoQR());
			pstmt.setClob(5, clobImagen);
			
			pstmt.setLong(6, request.getIdUsuario());
			pstmt.setLong(7, request.getIdProveedor());
			pstmt.setInt(8, request.getIdTipoPago());
			
			pstmt.execute();
			
			idCompra = sequenceValue;
			
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
		return idCompra;
	}
	
	public int insertCompraDirecta(CompraDirectaRequest request){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		int idCompra = 0;
		
		try{
			int sequenceValue = callSequence("TENDEROS_PEDIDO_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_PEDIDO (ID, FECHA, MONTO_TOTAL, CODIGO_QR, ID_USUARIO, TIPO_PAGO_ID, PROVEEDOR_ID)  VALUES (?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setFloat(3, request.getMontoTotal());
			
			java.sql.Clob clobImagen = oracle.sql.CLOB.createTemporary(conn, false, oracle.sql.CLOB.DURATION_SESSION);
			clobImagen.setString(1, request.getCodigoQR());
			pstmt.setClob(4, clobImagen);
			
			pstmt.setLong(5, request.getIdUsuario());
			pstmt.setInt(6, request.getIdTipoPago());
			pstmt.setLong(7, request.getIdProveedor());
			
			pstmt.execute();
			
			idCompra = sequenceValue;
			
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
		return idCompra;
	}
	
	public boolean insertCompraArticulos(int idCompra, int idArticulo, int numeroArticulos){
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		boolean isInsert = true;
		
		try{
			int sequenceValue = callSequence("TENDEROS_PEDIDO_PROVEEDOR_SEQ");
			
			conn = this.getConnectionOracle();
			
			String in = "INSERT INTO TENDEROS_PEDIDO_PROVEEDOR VALUES (?,?,?,?)";
			
			pstmt = conn.prepareStatement(in.toString());
			pstmt.setInt(1, sequenceValue);
			pstmt.setInt(2, idCompra);
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
