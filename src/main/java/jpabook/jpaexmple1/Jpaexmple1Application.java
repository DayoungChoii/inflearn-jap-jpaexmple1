package jpabook.jpaexmple1;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpaexmple1Application {

	public static void main(String[] args) {
		SpringApplication.run(Jpaexmple1Application.class, args);

		Hello hello = new Hello();
		hello.setHello("ddd");
		System.out.println("hello.getHello() = " + hello.getHello());
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		//강제 지연 로딩 설정
//		hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
		return hibernate5Module;
	}

}
