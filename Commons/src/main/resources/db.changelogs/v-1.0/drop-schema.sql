alter table monitoring_service.meter_readings
drop constraint FK_meter_readings_To_accounts;
GO

drop table monitoring_service.meter_readings;
GO

drop table monitoring_service.accounts;
GO

drop schema monitoring_service;