package cn.glavenus.community.glavenus.dto;

import cn.glavenus.community.glavenus.model.User;
import lombok.Data;

/**
 * Creaked by EyreValor on 2020/3/3
 */
@Data
public class QuestionDTO {
    private long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private String userAvatarUrl;
    private String userName;
}
