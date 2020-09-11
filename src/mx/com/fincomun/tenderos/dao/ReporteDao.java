package mx.com.fincomun.tenderos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.PedidoProveedor;
import mx.com.fincomun.tenderos.bean.PorcentajeSemanal;
import mx.com.fincomun.tenderos.bean.ProductosTopVendidos;
import mx.com.fincomun.tenderos.bean.ProveedoresVenta;
import mx.com.fincomun.tenderos.bean.ReporteCompraMes;
import mx.com.fincomun.tenderos.bean.ReporteRequest;
import mx.com.fincomun.tenderos.bean.ReporteVentaDia;
import mx.com.fincomun.tenderos.bean.ReporteVentaMes;
import mx.com.fincomun.tenderos.bean.VentaArticulos;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.Util;

@Service
public class ReporteDao extends GeneralDao {
	
	private static Logger log = Logger.getLogger(ReporteDao.class);
	
	public ReporteVentaMes getTotalVentasMes(ReporteRequest request){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        ReporteVentaMes response = null;
		try {
            conn = this.getConnectionOracle();
            
            String query = "SELECT SUM(MONTO_TOTAL) AS TOTAL_VENTAS "
            		+ "FROM  TENDEROS_VENTAS "
            		+ "WHERE ID_USUARIO = "+request.getIdUsuario()+" "
            		+ "AND FECHA  between to_timestamp('"+request.getFechaInicio()+"') AND to_timestamp('"+request.getFechaFin()+"')+.9999999 ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	response = new ReporteVentaMes();
            	response.setTotalVenta(rs.getFloat("TOTAL_VENTAS"));
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
	
	public ReporteCompraMes getCompraTPMes(ReporteRequest request){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        ReporteCompraMes response = null;
		try {
            conn = this.getConnectionOracle();
            
            String query = "SELECT SUM(MONTO_TOTAL) AS TOTAL_COMPRAS "
            		+ "FROM  TENDEROS_PEDIDO "
            		+ "WHERE  ID_USUARIO = "+request.getIdUsuario()+" "
            		+ "AND FECHA  between to_timestamp('"+request.getFechaInicio()+"') AND to_timestamp('"+request.getFechaFin()+"')+.9999999";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	response = new ReporteCompraMes();
            	response.setTotalCompra(rs.getFloat("TOTAL_COMPRAS"));
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
	
	public String idsTopVendidos(ReporteRequest request){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        String listArticulos = "";
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT TVA.ARTICULO_ID "
            		+ "FROM  TENDEROS_VENTA_ARTICULOS TVA "
            		+ "WHERE TVA.VENTAS_ID IN (SELECT TV.ID  FROM TENDEROS_VENTAS TV WHERE TV.ID_USUARIO = "+request.getIdUsuario()+" "
            				+ " AND TV.FECHA  between to_timestamp('"+request.getFechaInicio()+"') "
            				+ " AND to_timestamp('"+request.getFechaFin()+"')+.9999999) "
            		+ "GROUP BY TVA.ARTICULO_ID "
            		+ "ORDER BY SUM(TVA.NUMERO_ARTICULOS) DESC";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	listArticulos += rs.getInt("ARTICULO_ID")+",";
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
		return listArticulos; 
	}
	
	public List<ProductosTopVendidos> getProductosTopVendidos(String listIds){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<ProductosTopVendidos> response = new ArrayList<ProductosTopVendidos>();
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT TA.ID, TA.NOMBRE, TA.IMAGEN, TE.NOMBRE AS NOMBRE_EMPRESA, TA.CODIGO_QR, TA.PRECIO_VENTA "
            		+ "FROM  TENDEROS_ARTICULO TA "
            		+ "LEFT OUTER JOIN  TENDEROS_PROVEEDOR TP ON TP.ID = TA.TENDEROS_PROVEEDOR_ID "
            		+ "LEFT OUTER JOIN  TENDEROS_EMPRESA TE ON TE.ID = TP.EMPRESA_ID "
            		+ "WHERE TA.ID IN ("+listIds+") "
            		+ "AND ROWNUM <= 10";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProductosTopVendidos producto = new ProductosTopVendidos();
            	producto.setIdProducto(rs.getInt("ID"));
            	producto.setNombre(rs.getString("NOMBRE"));
            	producto.setImagen(rs.getString("IMAGEN"));
            	producto.setCodigoQR(rs.getString("CODIGO_QR"));
            	producto.setPrecioVenta(rs.getFloat("PRECIO_VENTA"));
            	producto.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
            	response.add(producto);
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

	public List<ReporteVentaDia> getVentasDia(ReporteRequest request){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<ReporteVentaDia> response = new ArrayList<ReporteVentaDia>();
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT TV.ID, TV.MONTO_TOTAL, TO_CHAR(TV.FECHA, 'DD-MM-YYYY') AS FECHA, TO_CHAR(TV.FECHA, 'HH24:MI') AS HORA, "
            		+ "TV.TIPO_PAGO_ID, TTP.NOMBRE "
            		+ "FROM  TENDEROS_VENTAS TV  "
            		+ "INNER JOIN  TENDEROS_TIPO_PAGO TTP ON TTP.ID = TV.TIPO_PAGO_ID "
            		+ "WHERE TV.ID_USUARIO = "+request.getIdUsuario()+" "
            		+ "AND TRUNC(TV.FECHA) = '"+request.getFecha()+"' "
            		+ "ORDER BY TV.FECHA ASC";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ReporteVentaDia reporte = new ReporteVentaDia();
            	reporte.setMonto(rs.getFloat("MONTO_TOTAL"));
            	reporte.setTipoPago(rs.getString("NOMBRE"));
            	reporte.setIdTipoPago(rs.getInt("TIPO_PAGO_ID"));
            	reporte.setId(rs.getInt("ID"));
            	reporte.setFecha(Util.getFechaDDMESYYYY(rs.getString("FECHA")));
            	reporte.setHora(rs.getString("HORA"));
            	response.add(reporte);
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

	
	public List<PorcentajeSemanal> getPorcentajeMensual(long idUsuario, String fechaInicio, String fechaFin){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<PorcentajeSemanal> response = new ArrayList<PorcentajeSemanal>();
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM(TV.MONTO_TOTAL) AS TOTAL_VENTAS, TV.TIPO_PAGO_ID, TTP.NOMBRE ,ROUND(((SELECT COUNT(ID) * 100  "
            						+ "FROM  TENDEROS_VENTAS "
            						+ "WHERE ID_USUARIO = "+idUsuario+" "
            						+ "AND FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999)/ "
            								+ "(SELECT COUNT(ID) "
            								+ "FROM  TENDEROS_VENTAS "
            								+ "WHERE FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999) ),2) AS PORCENTAJE "
							+ "FROM  TENDEROS_VENTAS TV "
							+ "INNER JOIN  TENDEROS_TIPO_PAGO TTP ON TTP.ID = TV.TIPO_PAGO_ID "
							+ "WHERE  TV.ID_USUARIO = "+idUsuario+" "
							+ "GROUP BY TV.TIPO_PAGO_ID,TTP.NOMBRE ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	PorcentajeSemanal bean = new PorcentajeSemanal();
            	bean.setIdTipoPago(rs.getInt("TIPO_PAGO_ID"));
            	bean.setNombrePago(rs.getString("NOMBRE"));
            	bean.setPorcentaje(rs.getFloat("PORCENTAJE"));
            	response.add(bean);
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
	
	public float getVentaPorSemana(long idUsuario, String fechaInicio, String fechaFin){
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        float monto = 0;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM(TV.MONTO_TOTAL) AS TOTAL_VENTAS "
            		+ "FROM  TENDEROS_VENTAS TV "
            		+ "WHERE  TV.ID_USUARIO = "+idUsuario+" "
            		+ "AND FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999 ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	monto = rs.getFloat("TOTAL_VENTAS");
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
		return monto; 
	}
	
	public List<ProveedoresVenta> getListVentas(long idUsuario, String fechaInicio, String fechaFin){
		List<ProveedoresVenta> listVentas = new ArrayList<ProveedoresVenta>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM(TV.MONTO_TOTAL) AS TOTAL_PEDIDO, "
            		+ "TE.NOMBRE AS NOMBRE_EMPRESA, TPR.NOMBRE AS NOMBRE_PROVEEDOR "
            		+ "FROM  TENDEROS_VENTAS TV "
            		+ "INNER JOIN  TENDEROS_VENTA_ARTICULOS TVA ON TVA.VENTAS_ID = TV.ID "
            		+ "LEFT JOIN  TENDEROS_ARTICULO TA ON TA.ID = TVA.ARTICULO_ID "
            		+ "LEFT JOIN  TENDEROS_PROVEEDOR TPR ON TPR.ID = TA.TENDEROS_PROVEEDOR_ID "
            		+ "LEFT JOIN  TENDEROS_EMPRESA TE ON TE.ID = TPR.EMPRESA_ID "
            		+ "WHERE TV.ID_USUARIO = "+idUsuario+" "
            		+ "AND FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999 "
            		+ "GROUP BY TPR.NOMBRE, TE.NOMBRE ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProveedoresVenta bean = new ProveedoresVenta();
            	bean.setTotalPedido(rs.getFloat("TOTAL_PEDIDO"));
            	bean.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
            	bean.setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
            	listVentas.add(bean);
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
		return listVentas;
	}
	
	public List<ProveedoresVenta> getListVentasDirectas(long idUsuario, String fechaInicio, String fechaFin){
		List<ProveedoresVenta> listVentas = new ArrayList<ProveedoresVenta>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = " SELECT SUM(TV.MONTO_TOTAL) AS TOTAL_PEDIDO "
            		+ "FROM  TENDEROS_VENTAS TV  "
            		+ "WHERE TV.ID_USUARIO = "+idUsuario+" AND FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999 "
            		+ "GROUP BY TV.ID_USUARIO ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProveedoresVenta bean = new ProveedoresVenta();
            	bean.setTotalPedido(rs.getFloat("TOTAL_PEDIDO"));
            	bean.setNombreEmpresa(Constantes.MSJ_COMPRA_DIRECTA);
            	bean.setNombreProveedor(Constantes.MSJ_COMPRA_DIRECTA);
            	listVentas.add(bean);
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
		return listVentas;
	}
	
	public List<ProveedoresVenta> getListCompras(long idUsuario, String fechaInicio, String fechaFin){
		List<ProveedoresVenta> listVentas = new ArrayList<ProveedoresVenta>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM(TP.MONTO_TOTAL) AS TOTAL_PEDIDO, TPR.NOMBRE AS NOMBRE_PROVEEDOR, "
            		+ "TE.NOMBRE AS NOMBRE_EMPRESA "
            		+ "FROM  TENDEROS_PEDIDO TP "
            		+ "LEFT JOIN  TENDEROS_PROVEEDOR TPR ON TPR.ID = TP.PROVEEDOR_ID "
            		+ "LEFT JOIN  TENDEROS_EMPRESA TE ON TE.ID = TPR.EMPRESA_ID "
            		+ "WHERE TP.ID_USUARIO = "+idUsuario+" "
            		+ "AND TP.FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999 "
            		+ "GROUP BY TPR.NOMBRE, TE.NOMBRE ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProveedoresVenta bean = new ProveedoresVenta();
            	bean.setTotalPedido(rs.getFloat("TOTAL_PEDIDO"));
            	bean.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
            	bean.setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
            	listVentas.add(bean);
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
		return listVentas;
	}
	
	public List<ProveedoresVenta> getListComprasDirectas(long idUsuario, String fechaInicio, String fechaFin){
		List<ProveedoresVenta> listVentas = new ArrayList<ProveedoresVenta>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM(TV.MONTO_TOTAL) AS TOTAL_PEDIDO "
            		+ "FROM  TENDEROS_VENTAS TV  "
            		+ "WHERE TV.ID_USUARIO = "+idUsuario+" "
            		+ "AND FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	ProveedoresVenta bean = new ProveedoresVenta();
            	bean.setTotalPedido(rs.getFloat("TOTAL_PEDIDO"));
            	bean.setNombreEmpresa(Constantes.MSJ_COMPRA_DIRECTA);
            	bean.setNombreProveedor(Constantes.MSJ_COMPRA_DIRECTA);
            	listVentas.add(bean);
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
		return listVentas;
	}
	
	public float getTotalCompras(long idUsuario, String fechaInicio, String fechaFin){
		float totalCompras = 0;
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM(TP.MONTO_TOTAL) AS TOTAL_PEDIDO "
            		+ "FROM  TENDEROS_PEDIDO TP "
            		+ "WHERE TP.ID_USUARIO = "+idUsuario+" "
            		+ "AND TP.FECHA  between to_timestamp('"+fechaInicio+"') AND to_timestamp('"+fechaFin+"')+.9999999 ";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	totalCompras = rs.getFloat("TOTAL_PEDIDO");
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
		return totalCompras;
	}
	
	public List<VentaArticulos> getVentaPorUsuario(ReporteRequest request){
		List<VentaArticulos> listVentas = new ArrayList<VentaArticulos>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT ARTICULO_ID, SUM(NUMERO_ARTICULOS) AS NUMERO_ARTICULOS "
            		+ "FROM  TENDEROS_VENTA_ARTICULOS "
            		+ "WHERE VENTAS_ID in (SELECT ID "
            				+ "FROM  TENDEROS_VENTAS "
            				+ "WHERE ID_USUARIO = "+request.getIdUsuario()+" "
            				+ "AND FECHA  between to_timestamp('"+request.getFechaInicio()+"') AND to_timestamp('"+request.getFechaFin()+"')+.9999999) "
            		+ "GROUP BY ARTICULO_ID";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	VentaArticulos bean = new VentaArticulos();
            	bean.setArticuloId(rs.getInt("ARTICULO_ID"));
            	bean.setNumeroArticulos(rs.getInt("NUMERO_ARTICULOS"));
            	listVentas.add(bean);
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
		return listVentas;
	}
	
	public List<VentaArticulos> getTipoPagoPorUsuario(ReporteRequest request){
		List<VentaArticulos> listVentas = new ArrayList<VentaArticulos>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT TVA.ARTICULO_ID, TV.TIPO_PAGO_ID, SUM(TVA.NUMERO_ARTICULOS) AS NUMERO_ARTICULOS "
            		+ "FROM  TENDEROS_VENTAS TV "
            		+ "INNER JOIN  TENDEROS_VENTA_ARTICULOS TVA ON TVA.VENTAS_ID = TV.ID "
            		+ "WHERE TV.ID_USUARIO = "+request.getIdUsuario()+" "
            		+ "AND TV.FECHA  between to_timestamp('"+request.getFechaInicio()+"') AND to_timestamp('"+request.getFechaFin()+"')+.9999999 "
            		+ "GROUP BY TVA.ARTICULO_ID, TV.TIPO_PAGO_ID "
            		+ "ORDER BY TV.TIPO_PAGO_ID ASC";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	VentaArticulos bean = new VentaArticulos();
            	bean.setArticuloId(rs.getInt("ARTICULO_ID"));
            	bean.setTipoPagoId(rs.getInt("TIPO_PAGO_ID"));
            	bean.setNumeroArticulos(rs.getInt("NUMERO_ARTICULOS"));
            	listVentas.add(bean);
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
		return listVentas;
	}
	
	public List<PedidoProveedor> getComprasPorUsuario(ReporteRequest request){
		List<PedidoProveedor> listCompras = new ArrayList<PedidoProveedor>();
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT ARTICULO_ID, SUM(NUMERO_ARTICULOS) AS NUMERO_ARTICULOS "
            		+ "FROM  TENDEROS_PEDIDO_PROVEEDOR "
            		+ "WHERE PEDIDO_ID in (SELECT ID "
            					+ "FROM  TENDEROS_PEDIDO "
            					+ "WHERE ID_USUARIO = "+request.getIdUsuario()+" "
            					+ "AND FECHA  between to_timestamp('"+request.getFechaInicio()+"') AND to_timestamp('"+request.getFechaFin()+"')+.9999999) "
            		+ "GROUP BY ARTICULO_ID";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	PedidoProveedor bean = new PedidoProveedor();
            	bean.setArticuloId(rs.getInt("ARTICULO_ID"));
            	bean.setNumeroArticulos(rs.getInt("NUMERO_ARTICULOS"));
            	listCompras.add(bean);
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
		return listCompras;
	}
	
	public float getTotalInventarioVenta(long idUsuario){
		float totalInventario = 0;
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM((STOCK_INICIAL * PRECIO_VENTA)) AS TOTAL_INVENTARIO "
            		+ "FROM  TENDEROS_ARTICULO "
            		+ "WHERE ID_USUARIO = "+idUsuario+"";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	totalInventario = rs.getInt("TOTAL_INVENTARIO");
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
		return totalInventario;
	}
	
	public float getTotalInventarioCosto(long idUsuario){
		float totalInventario = 0;
		Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
		try {
            conn = this.getConnectionOracle();
            String query = "SELECT SUM((STOCK_INICIAL * PRECIO_COSTO)) AS TOTAL_INVENTARIO "
            		+ "FROM  TENDEROS_ARTICULO "
            		+ "WHERE ID_USUARIO = "+idUsuario+"";

            log.info("query : " + query);

            cs = conn.prepareCall(query);   
            cs.executeUpdate();
            rs = (ResultSet) cs.getResultSet();
            
            while (rs.next()){
            	totalInventario = rs.getInt("TOTAL_INVENTARIO");
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
		return totalInventario;
	}
}
