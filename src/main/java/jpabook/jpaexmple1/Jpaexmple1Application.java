package jpabook.jpaexmple1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jpaexmple1Application {

	public static void main(String[] args) {
		SpringApplication.run(Jpaexmple1Application.class, args);

		Hello hello = new Hello();
		hello.setHello("ddd");
		System.out.println("hello.getHello() = " + hello.getHello());
	}

}
