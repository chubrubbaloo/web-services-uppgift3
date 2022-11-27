package me.code.uppgift3projekt.data;

import lombok.Getter;
import lombok.Setter;
import me.code.uppgift3projekt.exception.NullContentException;
import me.code.uppgift3projekt.exception.NullTitleException;

@Getter
@Setter
public class Post {
    public void setContent(String content) throws NullContentException {
        if (content == null)
            throw new NullContentException();

        this.content = content;
    }


    private String title;
    private String content;
    private User creator;

    public Post(String title, String content, User creator) throws NullTitleException, NullContentException {
        this.setTitle(title);
        this.setContent(content);
        this.setCreator(creator);
    }

    public void setTitle(String title) throws NullTitleException {
        if (title == null)
            throw new NullTitleException();

        this.title = title;
    }

    public PostDTO toDTO(){
        return new PostDTO(this);
    }
}
