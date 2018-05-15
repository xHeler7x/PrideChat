package me.kwiatkowsky.PrideChat.repository;

import me.kwiatkowsky.PrideChat.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryInterface extends MongoRepository<User, String>{
}
