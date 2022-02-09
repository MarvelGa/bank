package com.banksystem.dao.dbmanager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBManager {
    private static DBManager instance;
    private DataSource ds;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/bank_system");
        } catch (NamingException ex) {
            ex.getStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return con;
    }

    public static void close(final Connection con, final Statement stmt, final ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    public static void close(final Connection con, final PreparedStatement psmt) {
        close(psmt);
        close(con);
    }

    public static void close(final Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
    }

    public static void rollback(final Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
    }

    private static void close(final Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
    }


    private static void close(final PreparedStatement psmt) {
        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
            }
        }
    }

    private static void close(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
}
