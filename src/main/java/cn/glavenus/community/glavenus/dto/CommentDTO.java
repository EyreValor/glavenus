package cn.glavenus.community.glavenus.dto;

import cn.glavenus.community.glavenus.model.User;
import lombok.Data;

/**
 * Creaked by EyreValor on 2020/3/15
 */
@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModefied;
    private Long likeCount;
    private String comment;
    private Integer commentCount;
    private User user;
}
