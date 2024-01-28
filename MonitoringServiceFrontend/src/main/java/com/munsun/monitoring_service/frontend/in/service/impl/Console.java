package com.munsun.monitoring_service.frontend.in.service.impl;

import com.munsun.monitoring_service.commons.dto.in.AccountDtoIn;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.in.SelectedUserItemDtoIn;
import com.munsun.monitoring_service.commons.dto.out.AccountDtoOut;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.frontend.in.enums.forms.ItemsRegisterForm;
import com.munsun.monitoring_service.frontend.in.enums.menu.ItemsStartMenu;
import com.munsun.monitoring_service.frontend.in.mapping.ConsoleMapper;
import com.munsun.monitoring_service.frontend.in.mapping.impl.ConsoleMapperImpl;
import com.munsun.monitoring_service.frontend.in.service.View;
import com.munsun.monitoring_service.frontend.in.enums.forms.ItemsLoginForm;
import com.munsun.utils.logger.model.JournalRecord;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Interacts with user input by outputting and inputting data in the form of forms and lists
 */
public class Console implements View {
    private Scanner scanner;
    private ConsoleMapper consoleMapper;

    public Console() {
        scanner = new Scanner(System.in);
        consoleMapper = new ConsoleMapperImpl();
    }

    /**
     * Display a form for the user to read the new name of the counter indicator
     * @return name of new meter reading
     */
    @Override
    public String showAddNewNameMeterReadingForm() {
        Map<String, String> form = printForm(List.of("Название"));
        return form.get("Название");
    }

    /**
     * display the logging list to the user
     * @param allLogs list of DTO object JournalRecord.java
     */
    @Override
    public void showJournalRecords(List<JournalRecord> allLogs) {
        printHeader("Journal");
        printList(consoleMapper.journalToString(allLogs), "%d) ");
    }

    /**
     * display to the user a list of meter readings for all time
     * @param meterReadingDtoOuts list of DTO object MeterReadingDtoOut.java
     */
    @Override
    public void showMeterReading(List<MeterReadingDtoOut> meterReadingDtoOuts) {
        for(var meterReading: meterReadingDtoOuts) {
            printHeader(meterReading.date());
            System.out.println("Meter readings: ");
            printList(meterReading.meterReadings(), "\t\t%d) ");
        }
    }

    /**
     * Display a list of meter readings to a user with the administrator role
     * @param meterReadingDtoOuts DTO object
     */
    @Override
    public void showLongMeterReading(List<LongMeterReadingDtoOut> meterReadingDtoOuts) {
        for(var meterReading: meterReadingDtoOuts) {
            printHeader(meterReading.login());
            System.out.println("Date: " + meterReading.date());
            System.out.println("Meter readings: ");
            printList(meterReading.meterReadings(), "\t\t%d) ");
        }
    }

    /**
     * Show the user the form for getting the month number(1-12)
     * @return Month
     */
    @Override
    public Month showHistoryMonthForm() {
        String month = printForm(List.of("month(1-12)")).get("month(1-12)");
        return consoleMapper.toMonth(month);
    }

    /**
     * display a form for filling out meter readings to the user
     * @param namesMetersReadings list of names meter readings
     * @return DTO object MeterReadingsDtoIn.java
     */
    @Override
    public MeterReadingsDtoIn showAddMeterReadingsForm(List<String> namesMetersReadings) {
        var form = printForm(namesMetersReadings);
        return consoleMapper.toMeterReadingsDtoIn(form);
    }

    /**
     * Display the counter reading to the user with the administrator role
     * @param meterReadingDtoOut DTO object
     */
    @Override
    public void showLongMeterReading(MeterReadingDtoOut meterReadingDtoOut) {
        System.out.println(meterReadingDtoOut.date());
        System.out.println("Meter readings: ");
        printList(meterReadingDtoOut.meterReadings(), "\t\t%d) ");
    }

    /**
     * Display the main menu to the user and read the data
     * @return the main menu item specified by the enumeration ItemsMainMenu.java
     */
    @Override
    public ItemsMainMenu showMainMenu() {
        printHeader("Main menu");
        printList(Arrays.stream(ItemsMainMenu.values())
                .map(ItemsMainMenu::getTitle)
                .collect(Collectors.toList()), "%d) ");
        var item = getUserSelectedItem();
        return consoleMapper.toMainItem(item);
    }

    /**
     * Display a message to the user about the successful registration of a new account
     * @param accountDtoOut DTO object
     */
    @Override
    public void showSuccessRegister(AccountDtoOut accountDtoOut) {
        System.out.println(String.format("%s,You have successfully registered, try logging in using your username and password",
                accountDtoOut.login()));
    }

    /**
     * Display the user's account registration form and read the data
     * @return DTO object AccountDtoIn.java
     */
    @Override
    public AccountDtoIn showRegisterForm() {
        printHeader("Register");
        Map<String,String> form = printForm(Arrays.stream(ItemsRegisterForm.values())
                                                .map(ItemsRegisterForm::getTitle)
                                                .collect(Collectors.toList()));
        return consoleMapper.toAccountDto(form);
    }

    /**
     * Display the user's account authentication form and read the data
     * @return DTO object LoginPasswordDtoIn.java
     */
    @Override
    public LoginPasswordDtoIn showLoginForm() {
        printHeader("Login");
        Map<String,String> form = printForm(List.of(ItemsLoginForm.FIELD_LOGIN.getTitle(),
                                                    ItemsLoginForm.FIELD_PASSWORD.getTitle()));
        return consoleMapper.toLoginPasswordDto(form);
    }

    private Map<String, String> printForm(List<String> strings) {
        Map<String, String> form = new HashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            System.out.print(strings.get(i)+": ");
            form.put(strings.get(i), scanner.next());
        }
        return form;
    }

    /**
     * Display the start menu to the user and read the data
     * @return the start menu item specified by the enumeration ItemsStartMenu.java
     */
    @Override
    public ItemsStartMenu showStartMenu() {
        printHeader("Monitoring-Service");
        printList(List.of(ItemsStartMenu.LOGIN.getTitle(),
                          ItemsStartMenu.REGISTER.getTitle()), "%d) ");
        return consoleMapper.toStartItem(getUserSelectedItem());
    }

    private void printHeader(String header) {
        String headerTemplate = "=================%s=================";
        System.out.println(String.format(headerTemplate, header));
    }

    private void printList(List<String> strings, String pref) {
        String template = pref + "%s";
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(String.format(template, i+1, strings.get(i)));
        }
    }

    private SelectedUserItemDtoIn getUserSelectedItem() {
        String pref = ">>> ";
        System.out.print(pref);
        scanner = new Scanner(System.in);
        int command = scanner.nextInt()-1;
        return new SelectedUserItemDtoIn(command);
    }

    /**
     * Display an error message to the user with the message
     * @param s message
     */
    @Override
    public void showError(String s) {
        System.out.println(s);
    }

    /**
     * display a message to the user about the successful addition of a meter reading
     * @param dtoOut DTO object
     */
    @Override
    public void showSuccessAddMeterReading(MeterReadingDtoOut dtoOut) {
        System.out.println("Success");
    }
}