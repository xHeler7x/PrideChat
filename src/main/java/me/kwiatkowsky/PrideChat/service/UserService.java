package me.kwiatkowsky.PrideChat.service;

import me.kwiatkowsky.PrideChat.repository.UserRepositoryInterface;
import me.kwiatkowsky.PrideChat.domain.User;
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
                .anyMatch(user -> user.getUsername().equals(obj.getUsername()))) return null;

        return userRepository.save(obj);
    }

    @Override
    public void delete(User obj) {
        getList().stream().filter( user -> user.equals(obj))
                .findAny().ifPresent( user -> userRepository.delete(user));
    }

    @Override
    public void deleteById(String id) {

        User userEntity = findById(id);
        if (!userEntity.getId().isEmpty()) userRepository.delete(userEntity);
    }

    @Override
    public User findById(String id) {

        User userEntity = new User();
        getList().stream().filter( user -> user.getId().equals(id))
                .findAny().ifPresent( user -> {
                    userEntity.setId(user.getId());
                    userEntity.setUsername(user.getUsername());
                    userEntity.setPassword(user.getPassword());
                });

        return userEntity;
    }

    @Override
    public User find(User user){

        User userEntity = findById(user.getId());

        return userEntity;
    }

    public User findByUsername(String username){
        User userEntity = new User();
                getList().stream().filter(userFinded ->
                userFinded.getUsername().equals(username))
                .findAny().ifPresent( userFinded -> {
                    userEntity.setId(userFinded.getId());
                    userEntity.setUsername(userFinded.getUsername());
                    userEntity.setPassword(userFinded.getPassword());
                });

                return userEntity;
    }

    @Override
    public boolean objectIsExist(User obj) {

        return getList().stream().anyMatch(user -> user.getId().equals(obj.getId()));
    }
}
