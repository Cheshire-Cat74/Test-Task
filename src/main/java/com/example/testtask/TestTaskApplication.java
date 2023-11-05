package com.example.testtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Для корректной работы необходимо создать схему test_task,
 * эту проблему можно решить через библиотеку preliquibase
 * не сделала, так как вне ТЗ
 */
@EnableFeignClients
@SpringBootApplication
public class TestTaskApplication {

    /**
     * Также для тестирования функционала метода getAllCertainUsers()
     * нужно добавить записей в БД, написан скрипт changelog-002.xml там есть инсерты в БД, можно дополнить скрипт
     */
    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }
}
