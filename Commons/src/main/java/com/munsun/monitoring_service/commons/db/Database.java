package com.munsun.monitoring_service.commons.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection getConnection() throws SQLException;
}
