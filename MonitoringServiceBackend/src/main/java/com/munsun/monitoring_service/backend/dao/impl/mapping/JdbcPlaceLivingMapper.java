package com.munsun.monitoring_service.backend.dao.impl.mapping;

import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JdbcPlaceLivingMapper {
    PlaceLivingEmbedded toPlaceLiving(ResultSet result) throws SQLException;

    void preparedSaveStatement(PreparedStatement preparedStatement, Account account) throws SQLException;
}
