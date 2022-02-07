package ru.inbox.vinnikov.simbirsoftparsing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.inbox.vinnikov.simbirsoftparsing.job.ParseTask;

@SpringBootApplication
public class SimbirsoftparsingApplication {

	public static void main(String[] args) {
		System.out.println("----------start");
		SpringApplication.run(SimbirsoftparsingApplication.class, args);
		System.out.println("----------start parseTask");
		ParseTask parseTask = new ParseTask();
		parseTask.parseNewNews();
		System.out.println("----------finish");
		System.exit(0);
	}
}
