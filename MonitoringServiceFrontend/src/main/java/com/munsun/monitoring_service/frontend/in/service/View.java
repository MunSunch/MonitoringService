package com.munsun.monitoring_service.frontend.in.service;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.frontend.in.enums.menu.ItemsStartMenu;
import com.munsun.utils.logger.model.JournalRecord;

import java.time.Month;
import java.util.List;

/**
 * An abstraction that contains functionality for user interaction
 *
 * @author apple
 * @version $Id: $Id
 */
public interface View {
    /**
     * Display the user's account registration form and read the data
     *
     * @return DTO object AccountDtoIn.java
     */
    AccountDtoIn showRegisterForm();

    /**
     * Display the user's account authentication form and read the data
     *
     * @return DTO object LoginPasswordDtoIn.java
     */
    LoginPasswordDtoIn showLoginForm();

    /**
     * Display the start menu to the user and read the data
     *
     * @return the start menu item specified by the enumeration ItemsStartMenu.java
     */
    ItemsStartMenu showStartMenu();

    /**
     * Display an error message to the user with the message
     *
     * @param s message
     */
    void showError(String s);

    /**
     * Display a message to the user about the successful registration of a new account
     *
     * @param accountDtoOut DTO object
     */
    void showSuccessRegister(AccountDtoOut accountDtoOut);

    /**
     * Display the main menu to the user and read the data
     *
     * @return the main menu item specified by the enumeration ItemsMainMenu.java
     */
    ItemsMainMenu showMainMenu();

    /**
     * Display the counter reading to the user with the administrator role
     *
     * @param meterReadingDtoOut DTO object
     */
    void showLongMeterReading(MeterReadingDtoOut meterReadingDtoOut);

    /**
     * display a form for filling out meter readings to the user
     *
     * @param namesMetersReadings list of names meter readings
     * @return DTO object MeterReadingsDtoIn.java
     */
    MeterReadingsDtoIn showAddMeterReadingsForm(List<String> namesMetersReadings);

    /**
     * Show the user the form for getting the month number(1-12)
     *
     * @return Month
     */
    Month showHistoryMonthForm();

    /**
     * display a message to the user about the successful addition of a meter reading
     *
     * @param dtoOut DTO object
     */
    void showSuccessAddMeterReading(MeterReadingDtoOut dtoOut);

    /**
     * display to the user a list of meter readings for all time
     *
     * @param metersReadings list of DTO object MeterReadingDtoOut.java
     */
    void showMeterReading(List<MeterReadingDtoOut> metersReadings);

    /**
     * Display a list of meter readings to a user with the administrator role
     *
     * @param meterReadingDtoOuts DTO object
     */
    void showLongMeterReading(List<LongMeterReadingDtoOut> meterReadingDtoOuts);

    /**
     * display the logging list to the user
     *
     * @param allLogs list of DTO object JournalRecord.java
     */
    void showJournalRecords(List<JournalRecord> allLogs);

    /**
     * Display a form for the user to read the new name of the counter indicator
     *
     * @return name of new meter reading
     */
    String showAddNewNameMeterReadingForm();
}
