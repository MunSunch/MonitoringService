create table monitoring_service.accounts (
    id serial not null primary key,
    login text not null unique,
    password text not null,
    role text not null,
    is_blocked smallint not null
);
GO

create table monitoring_service.meter_readings (
    id serial not null primary key,
    date timestamp with time zone not null,
    account_id integer not null,
    readings jsonb
);
GO

alter table monitoring_service.meter_readings
    add constraint FK_meter_readings_To_accounts foreign key (account_id) references monitoring_service.accounts(id);