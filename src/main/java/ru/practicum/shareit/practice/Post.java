package ru.practicum.shareit.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment..get);
    }
}
