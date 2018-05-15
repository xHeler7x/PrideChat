package me.kwiatkowsky.PrideChat.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
public class User {

    @Id
    private String id;

    @NotNull
    @Size(min = 7, max = 16, message = "Nazwa użytkownika musi zawierać od 7 do 16 znaków!")
    private String username;

    @NotNull
    @Size(min = 7, max = 16, message = "Hasło użytkownika musi zawierać od 7 do 16 znaków!")
    private String password;

    public User(){

    }

    public User(String username, String password){

        this.id = new ObjectId().toHexString();
        this.password = password;
        this.username = username;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }
}
