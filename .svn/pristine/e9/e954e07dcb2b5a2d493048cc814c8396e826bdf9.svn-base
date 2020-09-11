package mx.com.fincomun.tenderos.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import mx.com.fincomun.tenderos.exception.DAOException;

public class GeneralDao {
	
	static Logger log = Logger.getLogger(GeneralDao.class);
	
	public Connection getConnectionOracle() throws NamingException,
			SQLException {
		DataSource dataSource = ServiceLocatorDB.getInstance()
			.getDataSourceOracle();
		return dataSource.getConnection();
	}
	
	public void closeResultSet(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();log.error(e);
			throw e;
		}
	}

	public void closeStatement(Statement stmt) throws SQLException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();log.error(e);
			throw e;
		}
	}

	public void closeConnection(Connection conn) throws SQLException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();log.error(e);
			throw e;
		}
	}
	
	public int callSequence(String sequenceName) throws DAOException{
		Connection conn = null;
		Statement pstmt = null;
		ResultSet rs = null;		 
		int nextVal = 0;
		 
		try {
			conn = getConnectionOracle();
			conn.setAutoCommit(false);
			
			StringBuffer  query = new StringBuffer ("select " + sequenceName.trim()+".nextval from dual");
			
			pstmt = conn.prepareCall(query.toString());
			ResultSet result = pstmt.executeQuery(query.toString());
			
			if (result != null) {
				if (result.next()) {
					nextVal = result.getInt("NEXTVAL");
				}
			}
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();log.error(e);
			try {
				if(conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			} catch (SQLException ex) {
				ex.printStackTrace();
				log.error(e);
			}
			throw new DAOException(e);
		
		}finally {
			try {
				closeResultSet(rs);
				closeStatement(pstmt);
				closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();log.error(e);
				throw new DAOException(e);
			}
		}	
		return nextVal;
	}
}