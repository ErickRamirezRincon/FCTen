package mx.com.fincomun.tenderos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.TipoPago;

@Service
public class CatalogoDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(CatalogoDao.class);

	public List<TipoPago> getListProductos(){
		List<TipoPago> tipoPagos = new ArrayList<TipoPago>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT * FROM TENDEROS_TIPO_PAGO ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	TipoPago tipoPago = new TipoPago();
            	tipoPago.setIdTipoPago(rs.getInt("ID"));
            	tipoPago.setNombre(rs.getString("NOMBRE"));
            	tipoPagos.add(tipoPago);
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
		return tipoPagos;
	}
}