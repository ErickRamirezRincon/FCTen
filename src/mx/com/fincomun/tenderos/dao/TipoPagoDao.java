package mx.com.fincomun.tenderos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.TipoPago;

@Service
public class TipoPagoDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(TipoPagoDao.class);
	
	public TipoPago buscarTipoPago(int idTipoPago){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        TipoPago response = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT * FROM TENDEROS_TIPO_PAGO WHERE ID = "+idTipoPago+" ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	response = new TipoPago();
            	response.setIdTipoPago(rs.getInt("ID"));
            	response.setNombre(rs.getString("NOMBRE"));
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


}
