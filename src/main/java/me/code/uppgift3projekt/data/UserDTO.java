package me.code.uppgift3projekt.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;

    public UserDTO(User user){
        this.setUsername(user.getUsername());
    }
}
