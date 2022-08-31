package com.pustak.loader.author;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Saurabh
 *
 */
@Component
public class AuthorMigrator {

	@Value("${data.author}")
	private String authorDataLocation;

	@Value("${data.works}")
	private String worksDataLocation;

	@Autowired
	AuthorRepo authorRepo;

	public void authorLoader() {

		Path path = Paths.get(authorDataLocation);

		try (Stream<String> lines = Files.lines(path)) {

			lines.forEach(line -> {
				String jsonString = line.substring(line.indexOf("{"));
				try {
					JSONObject jsonObject = new JSONObject(jsonString);

					Author author = new Author();
					author.setId(jsonObject.optString("key").replace("/authors/", ""));
					author.setName(jsonObject.optString("name"));
					author.setPersonalName(jsonObject.optString("personal_name"));
					
					

					authorRepo.save(author);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
