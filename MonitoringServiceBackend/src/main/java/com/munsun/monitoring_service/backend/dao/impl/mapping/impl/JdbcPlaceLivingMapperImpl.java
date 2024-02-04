package com.munsun.monitoring_service.backend.dao.impl.mapping.impl;

import com.munsun.monitoring_service.backend.dao.impl.mapping.JdbcPlaceLivingMapper;
import com.munsun.monitoring_service.backend.dao.impl.enums.NamesColumnsTableAccounts;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPlaceLivingMapperImpl implements JdbcPlaceLivingMapper {
    @Override
    public PlaceLivingEmbedded toPlaceLiving(ResultSet result) throws SQLException {
        return PlaceLivingEmbedded.builder()
                    .country(result.getString(NamesColumnsTableAccounts.COUNTRY.getTitle()))
                    .city(result.getString(NamesColumnsTableAccounts.CITY.getTitle()))
                    .street(result.getString(NamesColumnsTableAccounts.STREET.getTitle()))
                    .house(result.getString(NamesColumnsTableAccounts.HOUSE.getTitle()))
                    .level(result.getString(NamesColumnsTableAccounts.LEVEL.getTitle()))
                    .apartmentNumber(result.getString(NamesColumnsTableAccounts.APARTMENT_NUMBER.getTitle()))
                .build();
    }

    @Override
    public void preparedSaveStatement(PreparedStatement preparedStatement, Account account) throws SQLException {
        var placeLiving = account.getPlaceLiving();
        preparedStatement.setString(NamesColumnsTableAccounts.COUNTRY.ordinal(), placeLiving.getCountry());
        preparedStatement.setString(NamesColumnsTableAccounts.CITY.ordinal(), placeLiving.getCity());
        preparedStatement.setString(NamesColumnsTableAccounts.STREET.ordinal(), placeLiving.getStreet());
        preparedStatement.setString(NamesColumnsTableAccounts.HOUSE.ordinal(), placeLiving.getHouse());
        preparedStatement.setString(NamesColumnsTableAccounts.LEVEL.ordinal(), placeLiving.getLevel());
        preparedStatement.setString(NamesColumnsTableAccounts.APARTMENT_NUMBER.ordinal(), placeLiving.getApartmentNumber());
    }
}
