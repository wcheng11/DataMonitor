/**
 * 
 */
package edu.thss.monitor.base.dataaccess.imp;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.pub.sys.AppContext;

/**
 * @author fx
 *
 */
public class LaUDBaseDAOTest {
	ApplicationContext ctx;
	LaUDBaseDAO laudDAO;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ctx = AppContext.getSpringContext();
		laudDAO = (LaUDBaseDAO)ctx.getBean("laUDBaseDAO");
		
		String creatKeyspace = "create keyspace laudUnitTest with strategy_class ='org.apache.cassandra.locator.SimpleStrategy' " +
			"and strategy_options:replication_factor=1;";
		String useKeyspace = "use laudUnitTest;";
		String creatColumnFamily = "create columnfamily languages (id varchar primary key, name varchar, paradigm varchar)";
		try {
			laudDAO.execute(creatKeyspace);
			laudDAO.execute(useKeyspace);
			laudDAO.execute(creatColumnFamily);
		} catch (RSPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		String dropColumnFamily = "drop columnfamily languages";
		String dropKeyspace = "drop keyspace laudUnitTest";
		try {
			laudDAO.execute(dropColumnFamily);
			laudDAO.execute(dropKeyspace);
		} catch (RSPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO#setConnectPool(edu.thss.monitor.base.dataaccess.imp.ConnectPool)}.
	 */
	@Test
	public final void testSetConnectPool() {
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO#getConnection()}.
	 */
	@Test
	public final void testGetConnection() {
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO#releaseConnection(java.sql.Connection)}.
	 */
	@Test
	public final void testReleaseConnection() {
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO#execute(java.lang.String)}.
	 */
	@Test
	public final void testExecute() {
		String useKeyspace = "use laudUnitTest;";
		String insertSql1 = "insert into languages(id,name,paradigm) values (1,'Perl','multi-paradigm')";
		String insertSql2 = "insert into languages(id,name,author) values (1,'Perl','Larry Wall')";

		try {
			laudDAO.execute(useKeyspace);
			laudDAO.execute(insertSql1);
			laudDAO.execute(insertSql2);
		} catch (RSPException e) {
			e.printStackTrace();
		}
		ResultSet rs = query();
		try {
			assertEquals(rs.getInt("id"), 1);
			assertEquals(rs.getString("name"), "Perl");
			assertEquals(rs.getString("paradigm"), "multi-paradigm");
			assertEquals(rs.getString("author"), "Larry Wall");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link edu.thss.monitor.base.dataaccess.imp.LaUDBaseDAO#batchExecute(java.util.List)}.
	 */
	@Test
	public final void testBatchExecute() {
		String useKeyspace = "use laudUnitTest;";
		String insertSql1 = "insert into languages(id,name,author) values(2,'Go','Robert Griesmer, Rob Pike, Ken Thompson')";
		String insertSql2 = "insert into languages(id,name,paradigm) values(2,'Go','concurrent')";
		try {
			laudDAO.batchExecute(Arrays.asList(
					new String[]{useKeyspace,insertSql1,insertSql2}));
		} catch (RSPException e) {
			e.printStackTrace();
		}
		ResultSet rs = query();
		try {
			assertEquals(rs.getInt("id"), 2);
			assertEquals(rs.getString("name"), "Go");
			assertEquals(rs.getString("author"), "Robert Griesmer, Rob Pike, Ken Thompson");
			assertEquals(rs.getString("paradigm"), "concurrent");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询测试用例中插入laud数据库的数据
	 */
	public final ResultSet query() {
		ConnectPool pool = (ConnectPool)ctx.getBean("connectPool");
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;

		String useKeyspace = "use laudUnitTest;";
		String query = "select id,name,paradigm,author from languages";

		try {
			laudDAO.execute(useKeyspace);
			
			con = pool.getCon();
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			statement.close();
			pool.release(con);
			while(rs.next()){
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}
}
