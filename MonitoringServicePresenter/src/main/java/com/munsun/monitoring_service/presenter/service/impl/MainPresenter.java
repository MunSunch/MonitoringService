package com.munsun.monitoring_service.presenter.service.impl;

import com.munsun.monitoring_service.backend.exceptions.AccountNotFoundException;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.exceptions.DatabaseConstraintException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.SecurityService;
import com.munsun.monitoring_service.backend.services.MonitoringService;
import com.munsun.monitoring_service.commons.dto.in.MeterReadingsDtoIn;
import com.munsun.monitoring_service.commons.dto.out.LongMeterReadingDtoOut;
import com.munsun.monitoring_service.commons.dto.out.MeterReadingDtoOut;
import com.munsun.monitoring_service.commons.enums.ItemsMainMenu;
import com.munsun.monitoring_service.frontend.in.service.View;
import com.munsun.monitoring_service.presenter.service.Presenter;
import com.munsun.utils.logger.repositories.impl.JournalRepositoryImpl;
import com.munsun.utils.logger.service.JournalService;
import com.munsun.utils.logger.service.impl.LoggerServiceImpl;

import java.time.Month;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Implementing a representative manipulating the Service and View components
 *
 * @author apple
 * @version $Id: $Id
 */
public class MainPresenter extends Presenter {
    private SecurityService securityService;
    private final JournalService logger = new LoggerServiceImpl(MainPresenter.class, new JournalRepositoryImpl());

    /**
     * <p>Constructor for MainPresenter.</p>
     *
     * @param view a {@link com.munsun.monitoring_service.frontend.in.service.View} object
     * @param service a {@link com.munsun.monitoring_service.backend.services.MonitoringService} object
     * @param securityService a {@link com.munsun.monitoring_service.backend.security.SecurityService} object
     */
    public MainPresenter(View view, MonitoringService service, SecurityService securityService) {
        super(view, service);
        this.securityService = securityService;
    }

    /**
     * {@inheritDoc}
     *
     *  Displays the start menu and asks the user for the number of the item in this menu.
     *  Depending on the input, it transfers the user either to the authentication or authorization form
     * @exception ArrayIndexOutOfBoundsException if the water item does not contain the number of the start menu item
     * @exception InputMismatchException if the entry point contains third-party characters besides numbers
     */
    @Override
    public void start() {
        try {
            var item = getView().showStartMenu();
            switch (item) {
                case LOGIN -> login();
                case REGISTER -> register();
            }
        } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
            getView().showError("Ошибка ввода!");
            start();
        }
    }

    /**
     * Displays a form for the user to enter the username and password required for authentication, and requests this data.
     * Depending on the success of authentication, it takes the user to the main menu.
     * If an exception is thrown, it takes you to the start menu.
     * @exception AccountNotFoundException if the specified username is not found in the user database
     * @exception AuthenticationException if the specified username/password does not correspond to the users specified in the database
     */
    private void login() {
        var loginPassword = getView().showLoginForm();
        try {
                logger.journal("Попытка аутентификации; login = "+loginPassword.login());
            securityService.authenticate(loginPassword);
                logger.journal("Аутентификации успешно; login = "+loginPassword.login());
            mainMenu();
        } catch (AuthenticationException | AccountNotFoundException e) {
                logger.journal("Аутентификации провалено; login = "+loginPassword.login());
            getView().showError(e.getMessage());
            start();
        }
    }

    /**
     * Displays a form for registering a new user to the user and requests this data.
     * Upon successful registration, it issues the saved username to the user, saves
     * the user and transfers to the start menu, if it fails, it issues an error message
     * and transfers to the start menu.
     * @exception DatabaseConstraintException generated by the database when the entity is saved
     */
    private void register() {
        var accountDtoIn = getView().showRegisterForm();
        try {
                logger.journal("Попытка регистрации нового аккаунта; login = "+accountDtoIn.login());
            var accountDtoOut = securityService.register(accountDtoIn);
                logger.journal("Регистрация нового аккаунта успешно; login = "+accountDtoIn.login());
            getView().showSuccessRegister(accountDtoOut);
        } catch (DatabaseConstraintException e) {
            getView().showError("Account already exists");
        }
        start();
    }

    /**
     *  Displays the main menu to the user and requests an item. Before the transfer,
     *  it checks the access rights of this user to the corresponding item:
     *  if authorization fails, it throws an exception with an error message.
     *  @exception ArrayIndexOutOfBoundsException if the water item does not contain the number of the main menu item
     *  @exception InputMismatchException if the entry point contains third-party characters besides numbers
     */
    private void mainMenu() {
        try {
            var authorizedAccount = securityService.getSecurityContext().getAuthorizedAccount();
            var item = getView().showMainMenu();
            authorization(authorizedAccount, item);
                logger.journal("Пользователь login="+authorizedAccount.getLogin() + " в главном меню");
            switch(item) {
                case GET_SHOW_HISTORY -> getAllHistory();
                case RECORD_METER_READINGS -> recordMeterReadings();
                case GET_SHOW_HISTORY_MONTH -> getHistoryMonth();
                case GET_ACTUAL_METER_READINGS -> getActualMeterReadings();
                case GET_SHOW_HISTORY_ALL_USERS -> getAllHistoryAllUsers();
                case GET_SHOW_HISTORY_MONTH_ALL_USERS -> getHistoryMonthAllUsers();
                case GET_ACTUAL_METER_READINGS_ALL_USERS -> getActualMeterReadingsAllUsers();
                case ADD_METER_READING -> addNewMeterReading();
                case GET_LOGS -> getLogs();
                case EXIT -> exitToStartMenu();
            }
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            getView().showError("Error input!");
            mainMenu();
        }
    }

    /**
     * Return the result of the user's access to the resource.
     * @param account entity Account.java
     * @param item point of main menu
     */
    private void authorization(Account account, ItemsMainMenu item) {
            logger.journal("Попытка авторизации; login="+account.getLogin() + " для " + item.getTitle());
        var statusAccess = securityService.getAccess(account.getRole(), item);
        if(!statusAccess) {
                logger.journal("Авторизация провалено; login="+account.getLogin() + " для " + item.getTitle());
            getView().showError("В доступе отказано! Недостаточно прав");
            mainMenu();
        }
            logger.journal("Авторизация успешно; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin());
    }

    /**
     * Provides a form for entering a new meter indicator in order to expand meter readings
     */
    private void addNewMeterReading() {
            logger.journal("Добавление нового типа показания; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin());
        String nameNewMeterReading = getView().showAddNewNameMeterReadingForm();
        getService().expandMeterReading(nameNewMeterReading);
        mainMenu();
    }

    /**
     * Display the current meter readings of all users
     */
    private void getActualMeterReadingsAllUsers() {
            logger.journal("Получение актуальных записей всех юзеров; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin()
                        + " role=" +securityService.getSecurityContext().getAuthorizedAccount().getRole());
        List<LongMeterReadingDtoOut> meterReadingDtoOuts = getService().getActualMeterReadingsAllUsers();
        getView().showLongMeterReading(meterReadingDtoOuts);
        mainMenu();
    }

    /**
     * Display the current meter readings of all users for a specific month.
     * Displays a form for the user to read the month
     */
    private void getHistoryMonthAllUsers() {
        Month month = getView().showHistoryMonthForm();
            logger.journal("Получение записей всех юзеров за " + month + "; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin()
                + " role=" +securityService.getSecurityContext().getAuthorizedAccount().getRole());
        List<LongMeterReadingDtoOut> meterReadingDtoOuts = getService().getHistoryMonthAllUsers(month);
        getView().showLongMeterReading(meterReadingDtoOuts);
        mainMenu();
    }

    /**
     * Display the meter readings of all users for all time
     */
    private void getAllHistoryAllUsers() {
        logger.journal("Получение записей всех юзеров; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin()
                + " role=" +securityService.getSecurityContext().getAuthorizedAccount().getRole());
        List<LongMeterReadingDtoOut> metersReadings = getService().getAllMeterReadingsAllUsers();
        getView().showLongMeterReading(metersReadings);
        mainMenu();
    }

    /**
     * Display the current meter readings
     */
    private void getActualMeterReadings() {
        var authorizedAccount = securityService.getSecurityContext().getAuthorizedAccount();
            logger.journal("Получение актуальных записей; login="+authorizedAccount.getLogin());
        try {
            MeterReadingDtoOut meterReadingDtoOut = getService().getActualMeterReadings(authorizedAccount.getId());
            getView().showLongMeterReading(meterReadingDtoOut);
        } catch (AccountNotFoundException e) {
            getView().showError(e.getMessage());
        }
        mainMenu();
    }

    /**
     * Display meter readings for a specific month.
     */
    private void getHistoryMonth() {
        var authorizedAccount = securityService.getSecurityContext().getAuthorizedAccount();
        Month month = getView().showHistoryMonthForm();
            logger.journal("Получение записей за "+month+"; login="+authorizedAccount.getLogin());
        List<MeterReadingDtoOut> meterReadingDtoOuts = getService().getHistoryMonth(authorizedAccount.getId(), month);
        getView().showMeterReading(meterReadingDtoOuts);
        mainMenu();
    }

    /**
     * Print a form for entering a new meter reading and request data to enter
     * @exception NumberFormatException outlier when entering non-numeric values
     */
    private void recordMeterReadings() {
        try {
            var authorizedAccount = securityService.getSecurityContext().getAuthorizedAccount();
                logger.journal("Попытка добавления новой записи; login="+authorizedAccount.getLogin());
            MeterReadingsDtoIn dtoIn = getView().showAddMeterReadingsForm(getService().getNamesReadingsMeters());
            MeterReadingDtoOut dtoOut = getService().addMeterReadings(authorizedAccount.getId(), dtoIn);
            getView().showSuccessAddMeterReading(dtoOut);
        } catch (NumberFormatException e) {
                logger.journal("Неудачная попытка добавления новой записи");
            getView().showError("Нарушен формат входных данных! Используйте числа");
        } catch (DatabaseConstraintException | AccountNotFoundException | IllegalArgumentException e) {
                logger.journal("Неудачная попытка добавления новой записи");
            getView().showError(e.getMessage());
        }
        mainMenu();
    }

    /**
     * Display information to the user about the indicators of the counters for the entire time
     */
    private void getAllHistory() {
        var authorizedAccount = securityService.getSecurityContext().getAuthorizedAccount();
            logger.journal("Получение всех записей: login="+authorizedAccount.getLogin());
        List<MeterReadingDtoOut> metersReadings = getService().getAllHistory(authorizedAccount.getId());
        getView().showMeterReading(metersReadings);
        mainMenu();
    }

    /**
     * Show the user information about the actions of all users
     */
    private void getLogs() {
            logger.journal("Получение логов; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin()
                        +" role="+securityService.getSecurityContext().getAuthorizedAccount().getRole());
        getView().showJournalRecords(logger.getJournalRecords());
    }

    /**
     * Displays the user to the start menu
     */
    private void exitToStartMenu() {
            logger.journal("Попытка выход из системы; login="+securityService.getSecurityContext().getAuthorizedAccount().getLogin());
        securityService.logout();
            logger.journal("Выход из системы успешно");
        start();
    }
}
