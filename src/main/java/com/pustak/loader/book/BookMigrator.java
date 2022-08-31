package com.pustak.loader.book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pustak.loader.author.Author;
import com.pustak.loader.author.AuthorRepo;

/**
 * @author Saurabh
 *
 */
@Component
public class BookMigrator {

	@Value("${data.works}")
	private String worksDataLocation;

	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	AuthorRepo authorRepo;

	public void bookLoader() {

		Path path = Paths.get(worksDataLocation);

		try (Stream<String> lines = Files.lines(path)) {

			lines.forEach(line -> {
				String jsonString = line.substring(line.indexOf("{"));
				try {
					JSONObject jsonObject = new JSONObject(jsonString);

					Book book = new Book();

					book.setId(jsonObject.optString("key").replace("/works/", ""));
					
					book.setName(jsonObject.optString("title"));
					
					JSONObject descJSONObj = jsonObject.optJSONObject("description");
					if(descJSONObj != null)
					{
					book.setDescription(descJSONObj.optString("value"));
					}

					String dateStr = jsonObject.optJSONObject("created").optString("value");
					
					
					book.setPublishedDate(LocalDate.parse(dateStr.substring(0, dateStr.indexOf("T"))));
					
					

					JSONArray coversJSONArr = jsonObject.optJSONArray("covers");
					if( coversJSONArr != null) {
					List<String> coverList = new ArrayList<>();
					
						for (int i = 0; i < coversJSONArr.length(); i++) {
							Integer cover =  coversJSONArr.getInt(i);
							coverList.add(cover.toString());
						}
					

					book.setCoverIds(coverList);
					}

					JSONArray authorsJSONArr = jsonObject.getJSONArray("authors");
					if(authorsJSONArr != null) {
						List<String> authorIdList = new ArrayList<>();
						for(int i=0; i<authorsJSONArr.length(); i++)
						{
							String authorId = authorsJSONArr.optJSONObject(i).optJSONObject("author").optString("key")
							.replace("/authors/", "");
							
							authorIdList.add(authorId);
						}
						book.setAuthorIds(authorIdList);
						
						List<String> authorNameList = new ArrayList<>();
						
						for(String authorId : authorIdList)
						{
							Optional<Author> author = authorRepo.findById(authorId);
							
							if(author.isPresent()) authorNameList.add(author.get().getName());
							else authorNameList.add("Unknown Author");
						}
						
						book.setAuthorName(authorNameList);
						
						
					}
					
					bookRepo.save(book);
					
				
					
				} catch (JSONException e) {
					e.printStackTrace();
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
