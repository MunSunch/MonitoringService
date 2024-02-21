package com.munsun.monitoring_service.backend.dao.impl.mapping.impl;

import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcPlaceLivingMapper;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableAccounts;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.commons.exceptions.ParseSqlQueryException;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JdbcPlaceLivingMapperImpl implements JdbcPlaceLivingMapper {
    @Override
    public PlaceLivingEmbedded toPlaceLiving(ResultSet result) {
        try {
            return PlaceLivingEmbedded.builder()
                    .country(result.getString(NamesColumnsTableAccounts.COUNTRY.getTitle()))
                    .city(result.getString(NamesColumnsTableAccounts.CITY.getTitle()))
                    .street(result.getString(NamesColumnsTableAccounts.STREET.getTitle()))
                    .house(result.getString(NamesColumnsTableAccounts.HOUSE.getTitle()))
                    .level(result.getString(NamesColumnsTableAccounts.LEVEL.getTitle()))
                    .apartmentNumber(result.getString(NamesColumnsTableAccounts.APARTMENT_NUMBER.getTitle()))
                    .build();
        } catch (SQLException e) {
            throw new ParseSqlQueryException(e);
        }
    }

    @Override
    public void preparedSaveStatement(PreparedStatement preparedStatement, Account account) {
        try {
            var placeLiving = account.getPlaceLiving();
            preparedStatement.setString(5, placeLiving.getCountry());
            preparedStatement.setString(6, placeLiving.getCity());
            preparedStatement.setString(7, placeLiving.getStreet());
            preparedStatement.setString(8, placeLiving.getHouse());
            preparedStatement.setString(9, placeLiving.getLevel());
            preparedStatement.setString(10, placeLiving.getApartmentNumber());
        } catch (SQLException e) {
            throw new ParseSqlQueryException(e);
        }
    }
}
