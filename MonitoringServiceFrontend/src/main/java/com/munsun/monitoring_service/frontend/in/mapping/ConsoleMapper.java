package com.munsun.monitoring_service.frontend.in.mapping;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.in.SelectedUserItemDtoIn;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.frontend.in.enums.menu.ItemsStartMenu;
import com.munsun.utils.logger.model.JournalRecord;

import java.time.Month;
import java.util.List;
import java.util.Map;

/**
 * An abstraction containing the functionality of converting user input from the keyboard into objects for further use
 */
public interface ConsoleMapper {
    /**
     * Converts the DTO, which contains the number of the start menu item
     * selected by the user, to the start menu item specified by the enumeration
     * @param userResponse the start menu item specified by the enumeration
     * @return the start menu item specified by the enumeration ItemsStartMenu.java
     */
    ItemsStartMenu toStartItem(SelectedUserItemDtoIn userResponse);

    /**
     * Converts key-value format data received from user input into a LoginPasswordDto object for further authentication
     * @param form key-value collection
     * @return DTO object LoginPasswordDtoIn.java
     */
    LoginPasswordDtoIn toLoginPasswordDto(Map<String, String> form);

    /**
     * Converts data received from user input into an AccountDtoIn object for
     * the purpose of further registration of a new user
     * @param form key-value collection
     * @return DTO object AccountDtoIn.java
     */
    AccountDtoIn toAccountDto(Map<String, String> form);

    /**
     * Converts the DTO, which contains the number of the main menu item
     * selected by the user, to the start menu item specified by the enumeration
     * @param item the main menu item specified by the enumeration
     * @return the start menu item specified by the enumeration ItemsMainMenu.java
     */
    ItemsMainMenu toMainItem(SelectedUserItemDtoIn item);

    /**
     * Converts a string received from user input to a month
     * @param month not-empty string contains number
     * @return enum Month constants
     */
    Month toMonth(String month);

    /**
     * Converts the data received from user input into meter readings
     * MeterReadingsDtoIn into an In object for recording in the database
     * @param form key-value collection
     * @return DTO object MeterReadingsDtoIn.java
     */
    MeterReadingsDtoIn toMeterReadingsDtoIn(Map<String, String> form);

    /**
     * Converts a list of logs into a list of strings for further output to the user
     * @param allLogs list of JournalRecord objects
     * @return list of strings
     */
    List<String> journalToString(List<JournalRecord> allLogs);
}