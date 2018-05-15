package me.kwiatkowsky.PrideChat.service;

import me.kwiatkowsky.PrideChat.domain.User;
import me.kwiatkowsky.PrideChat.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ServiceInterface<User> {

    private UserRepositoryInterface userRepository;

    @Autowired
    public UserService(UserRepositoryInterface userRepositoryInterface){
        this.userRepository = userRepositoryInterface;
    }

    @Override
    public List<User> getList() {
        List<User> usersList = userRepository.findAll();
        return usersList;
    }

    @Override
    public User create(User obj) {

        if (getList().stream()
                .filter(user -> user.getUsername().equals(obj.getUsername()))
                .findAny().isPresent()) return null;

        return userRepository.save(obj);
    }

    @Override
    public void delete(User obj) {
        getList().stream().filter( user -> user.equals(obj))
                .findAny().ifPresent( user -> userRepository.delete(user));
    }

    @Override
    public void deleteById(String id) {
        getList().stream().filter( user -> user.getId().equals(id))
                .findAny().ifPresent( user -> userRepository.delete(user));
    }

    @Override
    public User findById(String id) {

        User userEntity = null;
        getList().stream().filter( user -> user.getId().equals(id))
                .findAny().ifPresent( user -> {
                    userEntity.setId(user.getId());
                    userEntity.setUsername(user.getUsername());
                    userEntity.setPassword(user.getPassword());
                });

        return userEntity;
    }

    @Override
    public boolean objectIsExist(User obj) {

        return  getList().stream().filter( user -> user.getId().equals(obj.getId()))
                .findAny().isPresent();
    }
}
