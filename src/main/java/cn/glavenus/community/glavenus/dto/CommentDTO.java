package cn.glavenus.community.glavenus.dto;

import lombok.Data;

/**
 * Creaked by EyreValor on 2020/3/14
 */
@Data
public class CommentDTO {
    private long parentId;
    private String comment;
    private Integer type;
}
