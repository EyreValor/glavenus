package cn.glavenus.community.glavenus.dto;

import lombok.Data;

import java.util.List;

/**
 * Creaked by EyreValor on 2020/4/9
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
