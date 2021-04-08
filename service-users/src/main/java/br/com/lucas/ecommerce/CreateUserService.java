package br.com.lucas.ecommerce;

import br.com.lucas.ecommerce.kafka.KafkaService;
import br.com.lucas.ecommerce.models.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class CreateUserService {

    private final Connection connection;

    CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db";
        this.connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table Users ( " +
                    "uuid varchar(200) primary key, email varchar(200) " +
                    ")");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {

        var createUserService = new CreateUserService();
        var kafkService = new KafkaService<Order>(
                CreateUserService.class.getName(),
                "ECOMMERCE_NEW_ORDER",
                createUserService::parse,
                Order.class,
                Map.of()
        );

        kafkService.run();
    }

    private void parse(ConsumerRecord<String, Order> record) throws SQLException {
        System.out.println("-----------------------------------------------------");
        System.out.println("Processando nova ordem de compra, checando se usuário existe no banco.");
        System.out.println(record.value());

        var order = record.value();

        if(isNewUser(order.getEmail())){
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(String email) {
        try {
            var ps = connection.prepareStatement("insert into users (uuid, email) values (?, ?) ");

            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, email);
            
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isNewUser(String email) throws SQLException {
        var ps = connection.prepareStatement("select uuid from users where email = ? limit 1");

        ps.setString(1, email);

        ResultSet results = ps.executeQuery();

        return !results.next();
    }

}
