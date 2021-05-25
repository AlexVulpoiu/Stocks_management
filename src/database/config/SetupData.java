package database.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupData {

    public void setup() {
        createTables();
        createFunctions();
    }

    private void createTables() {
        String queryCategories = "create table if not exists categories(\n" +
                "   id varchar(6) primary key,\n" +
                "   name varchar(50) not null unique\n" +
                ")\n";

        String queryDistributors = "create table if not exists distributors(\n" +
                "   id varchar(7) primary key,\n" +
                "   name varchar(50) not null unique,\n" +
                "   country varchar(20) not null\n" +
                ")\n";

        String queryProduct = "   id varchar(14) primary key,\n" +
                "   name varchar(50) not null unique,\n" +
                "   category_id varchar(6) not null,\n" +
                "   distributor_id varchar(7) not null,\n" +
                "   price double not null,\n" +
                "   stock int not null,\n" +
                "   warranty int not null,\n";

        String queryForeignKeys = " foreign key(category_id)\n" +
                "   references categories(id)\n" +
                "   on update cascade\n" +
                "   on delete cascade,\n" +
                "   foreign key(distributor_id)" +
                "   references distributors(id)\n" +
                "   on update cascade\n" +
                "   on delete cascade\n" +
                ")\n";

        String queryAudioSpeaker = "create table if not exists audio_speakers(\n" +
                queryProduct  +
                "   power int not null,\n" +
                "   is_wireless bit not null,\n" +
                "   has_bluetooth bit not null,\n" +
                queryForeignKeys;

        String queryAudioSystem = "create table if not exists audio_systems(\n" +
                queryProduct +
                "   power int not null,\n" +
                "   pieces int not null,\n" +
                "   is_wireless bit not null,\n" +
                "   has_bluetooth bit not null,\n" +
                queryForeignKeys;

        String queryFridges = "create table if not exists fridges(\n" +
                queryProduct +
                "   min_temp int not null,\n" +
                "   max_temp int not null,\n" +
                "   height double not null,\n" +
                "   width double not null,\n" +
                "   length double not null,\n" +
                "   has_freezer bit not null,\n" +
                queryForeignKeys;

        String queryGasCookers = "create table if not exists gas_cookers(\n" + queryProduct + queryForeignKeys;

        String queryHeadphones = "create table if not exists headphones(\n" + queryProduct + queryForeignKeys;

        String queryLaptops = "create table if not exists laptops(\n" +
                queryProduct +
                "   diagonal double not null,\n" +
                "   cpu varchar(30) not null,\n" +
                "   ram int not null,\n" +
                "   memory int not null,\n" +
                "   storage_type varchar(10) not null,\n" +
                "   graphics_card varchar(30) not null,\n" +
                "   usb_ports int not null,\n" +
                "   use_category varchar(10) not null,\n" +
                queryForeignKeys;

        String queryMobilePhones = "create table if not exists mobile_phones(\n" +
                queryProduct +
                "   diagonal double not null,\n" +
                "   ram int not null,\n" +
                "   memory int not null,\n" +
                "   cameras int not null,\n" +
                queryForeignKeys;

        String queryMouses = "create table if not exists mouses(\n" + queryProduct + "  wireless bit not null,\n" + queryForeignKeys;

        String queryPowerBanks = "create table if not exists power_banks(\n" + queryProduct + "  capacity int not null,\n" + queryForeignKeys;

        String querySmartwatches = "create table if not exists smartwatches(\n" + queryProduct + queryForeignKeys;

        String queryTVs = "create table if not exists tvs(\n" +
                queryProduct +
                "   diagonal double not null,\n" +
                "   resolution varchar(10) not null,\n" +
                "   room varchar(20) not null,\n" +
                queryForeignKeys;

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {

            Statement statement = connection.createStatement();
            statement.execute(queryCategories);
            statement.execute(queryDistributors);
            statement.execute(queryAudioSpeaker);
            statement.execute(queryAudioSystem);
            statement.execute(queryFridges);
            statement.execute(queryGasCookers);
            statement.execute(queryHeadphones);
            statement.execute(queryLaptops);
            statement.execute(queryMobilePhones);
            statement.execute(queryMouses);
            statement.execute(queryPowerBanks);
            statement.execute(querySmartwatches);
            statement.execute(queryTVs);

        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void createFunctions() {
        String removeChangeName = "drop function if exists stocks_management.change_name\n";
        String queryChangeName = "create function stocks_management.change_name(category_id varchar(6), new_name varchar(50)) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.categories\n" +
                "set name = new_name\n" +
                "where id = category_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeDistributorName = "drop function if exists stocks_management.change_distributor_name\n";
        String queryChangeDistributorName = "create function stocks_management.change_distributor_name(distributor_id varchar(7), new_name varchar(50)) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.distributors\n" +
                "set name = new_name\n" +
                "where id = distributor_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeCountry = "drop function if exists stocks_management.change_country\n";
        String queryChangeCountry = "create function stocks_management.change_country(distributor_id varchar(7), new_country varchar(20)) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.distributors\n" +
                "set country = new_country\n" +
                "where id = distributor_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeAudioSpeakerPrice = "drop function if exists stocks_management.change_audio_speaker_price\n";
        String queryChangeAudioSpeakerPrice = "create function stocks_management.change_audio_speaker_price(audio_speaker_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.audio_speakers\n" +
                "set price = new_price\n" +
                "where id = audio_speaker_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeAudioSpeakerStock = "drop function if exists stocks_management.change_audio_speaker_stock\n";
        String queryChangeAudioSpeakerStock = "create function stocks_management.change_audio_speaker_stock(audio_speaker_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.audio_speakers\n" +
                "set stock = new_stock\n" +
                "where id = audio_speaker_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeAudioSystemPrice = "drop function if exists stocks_management.change_audio_system_price\n";
        String queryChangeAudioSystemPrice = "create function stocks_management.change_audio_system_price(audio_system_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.audio_systems\n" +
                "set price = new_price\n" +
                "where id = audio_system_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeAudioSystemStock = "drop function if exists stocks_management.change_audio_system_stock\n";
        String queryChangeAudioSystemStock = "create function stocks_management.change_audio_system_stock(audio_system_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.audio_systems\n" +
                "set stock = new_stock\n" +
                "where id = audio_system_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeFridgePrice = "drop function if exists stocks_management.change_fridge_price\n";
        String queryChangeFridgePrice = "create function stocks_management.change_fridge_price(fridge_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.fridges\n" +
                "set price = new_price\n" +
                "where id = fridge_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeFridgeStock = "drop function if exists stocks_management.change_fridge_stock\n";
        String queryChangeFridgeStock = "create function stocks_management.change_fridge_stock(fridge_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.fridges\n" +
                "set stock = new_stock\n" +
                "where id = fridge_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeGasCookerPrice = "drop function if exists stocks_management.change_gas_cooker_price\n";
        String queryChangeGasCookerPrice = "create function stocks_management.change_gas_cooker_price(gas_cooker_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.gas_cookers\n" +
                "set price = new_price\n" +
                "where id = gas_cooker_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeGasCookerStock = "drop function if exists stocks_management.change_gas_cooker_stock\n";
        String queryChangeGasCookerStock = "create function stocks_management.change_gas_cooker_stock(gas_cooker_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.gas_cookers\n" +
                "set stock = new_stock\n" +
                "where id = gas_cooker_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeHeadphonesPrice = "drop function if exists stocks_management.change_headphones_price\n";
        String queryChangeHeadphonesPrice = "create function stocks_management.change_headphones_price(headphones_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.headphones\n" +
                "set price = new_price\n" +
                "where id = headphones_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeHeadphonesStock = "drop function if exists stocks_management.change_headphones_stock\n";
        String queryChangeHeadphonesStock = "create function stocks_management.change_headphones_stock(headphones_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.headphones\n" +
                "set stock = new_stock\n" +
                "where id = headphones_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeLaptopPrice = "drop function if exists stocks_management.change_laptop_price\n";
        String queryChangeLaptopPrice = "create function stocks_management.change_laptop_price(laptop_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.laptops\n" +
                "set price = new_price\n" +
                "where id = laptop_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeLaptopStock = "drop function if exists stocks_management.change_laptop_stock\n";
        String queryChangeLaptopStock = "create function stocks_management.change_laptop_stock(laptop_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.laptops\n" +
                "set stock = new_stock\n" +
                "where id = laptop_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeMobilePhonePrice = "drop function if exists stocks_management.change_mobile_phone_price\n";
        String queryChangeMobilePhonePrice = "create function stocks_management.change_mobile_phone_price(mobile_phone_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.mobile_phones\n" +
                "set price = new_price\n" +
                "where id = mobile_phone_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeMobilePhoneStock = "drop function if exists stocks_management.change_mobile_phone_stock\n";
        String queryChangeMobilePhoneStock = "create function stocks_management.change_mobile_phone_stock(mobile_phone_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.mobile_phones\n" +
                "set stock = new_stock\n" +
                "where id = mobile_phone_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeMousePrice = "drop function if exists stocks_management.change_mouse_price\n";
        String queryChangeMousePrice = "create function stocks_management.change_mouse_price(mouse_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.mouses\n" +
                "set price = new_price\n" +
                "where id = mouse_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeMouseStock = "drop function if exists stocks_management.change_mouse_stock\n";
        String queryChangeMouseStock = "create function stocks_management.change_mouse_stock(mouse_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.mouses\n" +
                "set stock = new_stock\n" +
                "where id = mouse_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangePowerBankPrice = "drop function if exists stocks_management.change_power_bank_price\n";
        String queryChangePowerBankPrice = "create function stocks_management.change_power_bank_price(power_bank_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.power_banks\n" +
                "set price = new_price\n" +
                "where id = power_bank_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangePowerBankStock = "drop function if exists stocks_management.change_power_bank_stock\n";
        String queryChangePowerBankStock = "create function stocks_management.change_power_bank_stock(power_bank_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.power_banks\n" +
                "set stock = new_stock\n" +
                "where id = power_bank_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeSmartwatchPrice = "drop function if exists stocks_management.change_smartwatch_price\n";
        String queryChangeSmartwatchPrice = "create function stocks_management.change_smartwatch_price(smartwatch_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.smartwatches\n" +
                "set price = new_price\n" +
                "where id = smartwatch_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeSmartwatchStock = "drop function if exists stocks_management.change_smartwatch_stock\n";
        String queryChangeSmartwatchStock = "create function stocks_management.change_smartwatch_stock(smartwatch_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.smartwatches\n" +
                "set stock = new_stock\n" +
                "where id = smartwatch_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeTVPrice = "drop function if exists stocks_management.change_tv_price\n";
        String queryChangeTVPrice = "create function stocks_management.change_tv_price(tv_id varchar(14), new_price double) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.tvs\n" +
                "set price = new_price\n" +
                "where id = tv_id;\n" +
                "return row_count();\n" +
                "end\n";

        String removeChangeTVStock = "drop function if exists stocks_management.change_tv_stock\n";
        String queryChangeTVStock = "create function stocks_management.change_tv_stock(tv_id varchar(14), new_stock int) returns int\n" +
                "deterministic\nbegin\n" +
                "update stocks_management.tvs\n" +
                "set stock = new_stock\n" +
                "where id = tv_id;\n" +
                "return row_count();\n" +
                "end\n";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();

            statement.execute(removeChangeName);
            statement.execute(queryChangeName);

            statement.execute(removeChangeDistributorName);
            statement.execute(queryChangeDistributorName);
            statement.execute(removeChangeCountry);
            statement.execute(queryChangeCountry);

            statement.execute(removeChangeAudioSpeakerPrice);
            statement.execute(queryChangeAudioSpeakerPrice);
            statement.execute(removeChangeAudioSpeakerStock);
            statement.execute(queryChangeAudioSpeakerStock);

            statement.execute(removeChangeAudioSystemPrice);
            statement.execute(queryChangeAudioSystemPrice);
            statement.execute(removeChangeAudioSystemStock);
            statement.execute(queryChangeAudioSystemStock);

            statement.execute(removeChangeFridgePrice);
            statement.execute(queryChangeFridgePrice);
            statement.execute(removeChangeFridgeStock);
            statement.execute(queryChangeFridgeStock);

            statement.execute(removeChangeGasCookerPrice);
            statement.execute(queryChangeGasCookerPrice);
            statement.execute(removeChangeGasCookerStock);
            statement.execute(queryChangeGasCookerStock);

            statement.execute(removeChangeHeadphonesPrice);
            statement.execute(queryChangeHeadphonesPrice);
            statement.execute(removeChangeHeadphonesStock);
            statement.execute(queryChangeHeadphonesStock);

            statement.execute(removeChangeLaptopPrice);
            statement.execute(queryChangeLaptopPrice);
            statement.execute(removeChangeLaptopStock);
            statement.execute(queryChangeLaptopStock);

            statement.execute(removeChangeMobilePhonePrice);
            statement.execute(queryChangeMobilePhonePrice);
            statement.execute(removeChangeMobilePhoneStock);
            statement.execute(queryChangeMobilePhoneStock);

            statement.execute(removeChangeMousePrice);
            statement.execute(queryChangeMousePrice);
            statement.execute(removeChangeMouseStock);
            statement.execute(queryChangeMouseStock);

            statement.execute(removeChangePowerBankPrice);
            statement.execute(queryChangePowerBankPrice);
            statement.execute(removeChangePowerBankStock);
            statement.execute(queryChangePowerBankStock);

            statement.execute(removeChangeSmartwatchPrice);
            statement.execute(queryChangeSmartwatchPrice);
            statement.execute(removeChangeSmartwatchStock);
            statement.execute(queryChangeSmartwatchStock);

            statement.execute(removeChangeTVPrice);
            statement.execute(queryChangeTVPrice);
            statement.execute(removeChangeTVStock);
            statement.execute(queryChangeTVStock);

        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }
}
