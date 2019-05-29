package libraries;

import java.sql.*;
import libraries.Parameters;

public class DBOperation 
{
	protected Parameters oParameters;
	Connection con = null;
	public String stringData = null;

	public DBOperation(Parameters oParameters) 
	{
		this.oParameters = oParameters;
		readTestData();
	}

	public Connection getConnection() 
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://ipdevimpauto5/ccm_automation", "dbadmin", "dbadmin");

			if (con != null)
				System.out.println("Connection to DB is successfull");
			else
				System.out.println("Connection to DB is Unsuccessfull");
		}
		catch (Exception e) 
		{
			System.out.println("Exception in Db Connectiob" + e);
		}
		return con;
	}

	public void readData(Connection con) 
	{
		String VRNAME = oParameters.GetParameters("TESTNAME");
		String environment = oParameters.GetParameters("environment");
		String readQuery = "SELECT * FROM test_input WHERE vr_name = '" + VRNAME + "' and environment = '" + environment
				+ "' ";

		oParameters.SetParameters("readQuery", readQuery);

		ResultSet rs = null;

		try 
		{
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(oParameters.GetParameters("readQUERY"));

			System.out.println("Query Executed");

			while (rs.next()) 
			{
				String vrname = rs.getString(1);
				oParameters.SetParameters("vrname", vrname);
				System.out.println(vrname);

				String vrid = rs.getString(2);
				oParameters.SetParameters("vrid", vrid);
				System.out.println(vrid);

				// To get Blob data
				Blob bs = (Blob) rs.getBlob(3);

				// To convert Blob to String
				byte[] bdata = bs.getBytes(1, (int) bs.length());
				stringData = new String(bdata);
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in reading data" + e);
		}
	}

	public void preference_data(Connection con) 
	{
		String readQuery = "SELECT * FROM preference WHERE sl_no = '1' ";
		oParameters.SetParameters("readQuery", readQuery);

		ResultSet rs = null;

		try 
		{
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(oParameters.GetParameters("readQUERY"));

			System.out.println("Query Executed");

			while (rs.next()) 
			{
				// To get Blob data
				Blob bs = (Blob) rs.getBlob(2);

				// To convert Blob to String
				byte[] bdata = bs.getBytes(1, (int) bs.length());
				stringData = new String(bdata);

				Json_Parsor();
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exception in reading data" + e);
		}
	}

	public void writeData() 
	{
		String writeQuery = "INSERT INTO test_output(vr_name, vr_id, execution_id, execution_date, output_data, environment, html_report_link, passed_steps, failed_steps, failed_step_discription, warning_messages, fatal_message, exception, test_result) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		oParameters.SetParameters("writeQuery", writeQuery);

		getConnection();

		oParameters.hashToString();
		PreparedStatement stmt = null;

		try 
		{
			stmt = con.prepareStatement(oParameters.GetParameters("writeQUERY"));

			stmt.setString(1, oParameters.GetParameters("TESTNAME"));
			stmt.setString(2, oParameters.GetParameters("VRID"));
			stmt.setString(3, oParameters.GetParameters("EXECUTION_ID"));
			stmt.setString(4, oParameters.GetParameters("EXECUTION_DATE"));
			stmt.setString(5, oParameters.mapToJSON());
			stmt.setString(6, oParameters.GetParameters("ENVIRONMENT"));
			stmt.setString(7, oParameters.GetParameters("HTML_REPORT_NAME"));
			stmt.setString(8, oParameters.GetParameters("PASSED_STEPS"));
			stmt.setString(9, oParameters.GetParameters("FAILED_STEPS"));
			stmt.setString(10, oParameters.GetParameters("FAILED_SCENARIO_DISCRIPTION"));
			stmt.setString(11, oParameters.GetParameters("WARNING_MESSAGES"));
			stmt.setString(12, oParameters.GetParameters("FATAL_MESSAGE"));
			stmt.setString(13, oParameters.GetParameters("EXCEPTION"));
			stmt.setString(14, oParameters.GetParameters("TESTSTATUS"));
			
			stmt.executeUpdate();

			System.out.println(" records inserted");
		}
		catch (Exception e) 
		{
			System.out.println("Exception Caught :" + e);
		}
	}

	public void closeConnection() 
	{
		try 
		{
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void Json_Parsor() 
	{
		String[] Array = stringData.split("\n");
		int len = Array.length;

		for (int i = 2; i <= len - 3; i++) 
		{
			String[] ex = Array[i].split(":");
			// System.out.println(ex[0] + "=");

			String[] ex2 = ex[1].split(",");
			// System.out.println(ex2[0]);

			oParameters.SetParameters(ex[0].replace("\"", " ").trim(), ex2[0].replace("\"", " ").trim());
			// System.out.println(oParameters.GetParameters("Negoatior Email"));
		}
	}

	public void readTestData() 
	{
		// To connect to DB
		con = getConnection();

		// to read preference data from DB
		// preference_data(con);

		// To read data from DB
		readData(con);

		// to convert JSON to String and store data in Hash table in the form of Key
		// value pair
		Json_Parsor();

		// To close the connection
		closeConnection();
	}
}
