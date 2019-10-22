package base.api.rest.forum.categories.dto;

import lombok.Data;

@Data
public class CategoryAdditionalDto {

  private Integer categoryTopicsNumber;
  private Integer categoryPostsNumber;

  private String categoryNewestPost;
  private String categoryNewestPostTopic;
  private String categoryNewestPostAuthor;
  private String categoryNewestPostDate;
}
