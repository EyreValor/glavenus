package cn.glavenus.community.glavenus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.glavenus.community.glavenus.mapper")
public class GlavenusApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlavenusApplication.class, args);
    }

}
