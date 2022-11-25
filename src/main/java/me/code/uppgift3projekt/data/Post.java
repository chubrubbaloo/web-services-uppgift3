package me.code.uppgift3projekt.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.code.uppgift3projekt.exception.NullContentException;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    public void setContent(String content) throws NullContentException {
        if (content == null)
            throw new NullContentException();

        this.content = content;
    }



    private String title;
    private String content;
    private User creator;

}
