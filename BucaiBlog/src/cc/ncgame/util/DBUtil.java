package cc.ncgame.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

	private final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://localhost:3306/bucaiblog?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
	private final static String DB_USERNAME = "root";
	private final static String DB_PASSWORD = "";

	public Connection connection;
	public PreparedStatement preparedStatement;

	public ResultSet reSet;

	static {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private PreparedStatement createPreparedStatement(String sql, Object... params) throws SQLException {
		preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

		for (int i = 0; i < params.length; i++) {
			preparedStatement.setObject(i + 1, params[i]);
		}
		return preparedStatement;

	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	public void close() {

		if (reSet != null) {

			try {
				reSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (preparedStatement != null) {

			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {

			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int executeUpdate(String sql, Object... params) throws SQLException {


		preparedStatement = execute(sql, params);

		int executeUpdate = preparedStatement.executeUpdate();
		
		close();

		return executeUpdate;

	}
	
	private PreparedStatement execute(String sql, Object... params)  throws SQLException{
		
		connection = getConnection();

		return createPreparedStatement(sql, params);
		
	}
	
	public int executeUpdateGetID(String sql, Object... params) throws SQLException {

		
		preparedStatement = execute(sql, params);
		
		preparedStatement.executeUpdate();
		
		ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		
		if(generatedKeys.next()) {
			return (int)generatedKeys.getInt(1);
		}
		
		return 0;

	}
	

	private Object timestampFiltr(Object obj) {

		if (obj instanceof Timestamp) {
			Timestamp timestamp = (Timestamp) obj;
			obj = timestamp.getTime();
		
		}

		return obj;

	}

	public List<Map<String, Object>> executeAllQuery(String sql, Object... params) throws SQLException {

		java.util.List<Map<String, Object>> list = new ArrayList<>();

		preparedStatement = execute(sql, params);

		reSet = preparedStatement.executeQuery();
		ResultSetMetaData md = reSet.getMetaData();

		int columnCount = md.getColumnCount(); // Map rowData;

		while (reSet.next()) {
			Map<String, Object> rowData = new HashMap<>();

			for (int i = 1; i <= columnCount; i++) {
				
				Object object = timestampFiltr(reSet.getObject(i));

				rowData.put(md.getColumnName(i), object);

			}

			list.add(rowData);
		}
		close();
		return list;

	}

	
	
}
