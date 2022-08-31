package com.pustak.loader.book;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Saurabh
 *
 */
@Repository
public interface BookRepo extends CassandraRepository<Book, String> {

}
