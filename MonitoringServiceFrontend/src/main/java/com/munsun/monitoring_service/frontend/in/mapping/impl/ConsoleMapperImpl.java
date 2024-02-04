package com.munsun.monitoring_service.frontend.in.mapping.impl;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.in.SelectedUserItemDtoIn;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.frontend.in.enums.forms.ItemsRegisterForm;
import com.munsun.monitoring_service.frontend.in.enums.menu.ItemsStartMenu;
import com.munsun.monitoring_service.frontend.in.enums.forms.ItemsLoginForm;
import com.munsun.monitoring_service.frontend.in.mapping.ConsoleMapper;
import com.munsun.utils.logger.model.JournalRecord;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Converter from console input to DTO objects
 *
 * @author apple
 * @version $Id: $Id
 */
public class ConsoleMapperImpl implements ConsoleMapper {

    /**
     * {@inheritDoc}
     *
     * Converts a list of logs into a list of strings for further output to the user.
     * Splits the argument into components , then concatenates using separator ="\t"
     */
    @Override
    public List<String> journalToString(List<JournalRecord> allLogs) {
        String separator = "\t";
        return allLogs.stream()
                .map(x -> x.getDate() + separator + x.getMessage())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * Converts the data received from user input into meter readings
     * MeterReadingsDtoIn into an In object for recording in the database
     */
    @Override
    public MeterReadingsDtoIn toMeterReadingsDtoIn(Map<String, String> form) {
        var updatedMap = form.entrySet().stream()
                            .collect(Collectors.toMap(entry -> entry.getKey(),
                                                      entry -> Long.parseLong(entry.getValue())));
        return new MeterReadingsDtoIn(updatedMap);
    }

    /**
     * {@inheritDoc}
     *
     * Converts the DTO, which contains the number of the main menu item
     * selected by the user, to the start menu item specified by the enumeration
     */
    @Override
    public ItemsMainMenu toMainItem(SelectedUserItemDtoIn userResponse) {
        return ItemsMainMenu.valueOf(findItem(ItemsMainMenu.class, userResponse));
    }

    /**
     * {@inheritDoc}
     *
     * Converts a string received from user input to a month
     */
    @Override
    public Month toMonth(String month) {
        return Month.of(Integer.parseInt(month));
    }

    /**
     * {@inheritDoc}
     *
     * Converts the DTO, which contains the number of the start menu item
     * selected by the user, to the start menu item specified by the enumeration
     */
    @Override
    public ItemsStartMenu toStartItem(SelectedUserItemDtoIn userResponse) {
        return ItemsStartMenu.valueOf(findItem(ItemsStartMenu.class, userResponse));
    }

    private String findItem(Class<?> enumClass, SelectedUserItemDtoIn userResponse) {
        var field = Optional.of(enumClass.getFields()[userResponse.command()]);
        if(field.isEmpty())
            throw new IllegalArgumentException("Item not found");
        return field.get().getName();
    }

    /**
     * {@inheritDoc}
     *
     * Converts key-value format data received from user input into a LoginPasswordDto object for further authentication
     */
    @Override
    public LoginPasswordDtoIn toLoginPasswordDto(Map<String, String> form) {
        return new LoginPasswordDtoIn(form.get(ItemsLoginForm.FIELD_LOGIN.getTitle()),
                                      form.get(ItemsLoginForm.FIELD_PASSWORD.getTitle()));
    }

    /**
     * {@inheritDoc}
     *
     * Converts data received from user input into an AccountDtoIn object for
     * the purpose of further registration of a new user
     */
    @Override
    public AccountDtoIn toAccountDto(Map<String, String> form) {
        return new AccountDtoIn(form.get(ItemsRegisterForm.FIELD_LOGIN.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_PASSWORD.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_COUNTRY.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_CITY.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_STREET.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_HOUSE.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_LEVEL.getTitle()),
                                form.get(ItemsRegisterForm.FIELD_APARTMENT_NUMBER.getTitle()));
    }
}
