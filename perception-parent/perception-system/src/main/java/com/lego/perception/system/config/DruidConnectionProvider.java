package com.lego.perception.system.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.utils.ConnectionProvider;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
public class DruidConnectionProvider implements ConnectionProvider {

    //Druid连接池
    private DruidDataSource datasource;

    private static DruidDataSource temp;

    public static DruidDataSource getTemp() {
        return temp;
    }

    public static void setTemp(DruidDataSource temp) {
        DruidConnectionProvider.temp = temp;
    }

    @Override
    public Connection getConnection() throws SQLException {

        return datasource.getConnection();
    }

    @Override
    public void shutdown() throws SQLException {
        if(!datasource.isClosed()){
            datasource.close();
        }
    }

    @Override
    public void initialize() throws SQLException {
        datasource = DruidConnectionProvider.getTemp();
    }

    public DruidDataSource getDatasource() {
        return datasource;
    }
    public void setDatasource(DruidDataSource datasource) {
        this.datasource = datasource;
    }

}
