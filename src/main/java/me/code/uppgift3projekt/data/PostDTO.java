package me.code.uppgift3projekt.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
        String title;
        String content;
        String creator;

        public PostDTO(Post post){
            this.title = post.getTitle();
            this.content = post.getContent();
            this.creator = post.getCreator().getUsername();
        }

}
