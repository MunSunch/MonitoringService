package com.munsun.monitoring_service.commons.enums;

/**
 * An enumeration containing the main menu items
 *
 * @author apple
 * @version $Id: $Id
 */
public enum ItemsMainMenu {
    GET_ACTUAL_METER_READINGS("получение актуальных показаний счетчиков"),
    RECORD_METER_READINGS("подача показаний"),
    GET_SHOW_HISTORY_MONTH("просмотр показаний за конкретный месяц"),
    GET_SHOW_HISTORY("просмотра истории подачи показаний"),
    GET_ACTUAL_METER_READINGS_ALL_USERS("получение актуальных показаний счетчиков всех пользователей"),
    GET_SHOW_HISTORY_ALL_USERS("просмотра истории подачи показаний всех пользователей"),
    GET_SHOW_HISTORY_MONTH_ALL_USERS("просмотр показаний за конкретный месяц всех пользователей"),
    GET_LOGS("просмотр действий пользователей"),
    ADD_METER_READING("добавить новый тип показания"),
    EXIT("выход");

    private String title;

    ItemsMainMenu(String title) {
        this.title = title;
    }

    /**
     * Return the name of the menu item
     *
     * @return name of the menu item
     */
    public String getTitle() {
        return title;
    }
}
