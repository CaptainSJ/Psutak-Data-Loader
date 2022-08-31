package com.pustak.loader.author;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Saurabh
 *
 */
@Repository
public interface AuthorRepo extends CassandraRepository<Author, String> {

}
