package com.pustak.loader;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.pustak.loader.author.AuthorMigrator;
import com.pustak.loader.book.BookMigrator;
import com.pustak.loader.connection.DataStaxAstraProperties;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class PustakDataLoaderApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PustakDataLoaderApplication.class, args);
		AuthorMigrator authorM = (AuthorMigrator) ctx.getBean("authorMigrator");

		authorM.authorLoader();

		BookMigrator bookM = (BookMigrator) ctx.getBean("bookMigrator");

		bookM.bookLoader();

	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties) {
		Path bundle = properties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);

	}

}
