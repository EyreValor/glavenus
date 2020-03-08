package cn.glavenus.community.glavenus.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaked by EyreValor on 2020/3/5
 */
@Data
public class PageinationDTO {

    private Integer size;
    private List<QuestionDTO> questions;
    private boolean showPreviousPage;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    /**
     * 设置pageinationDTO的值
     * @param totalPage
     * @param page
     * @param size
     */
    public void setPagsination(Integer totalPage, Integer page,Integer size) {
        this.size = size;
        this.page = page;
        this.totalPage = totalPage;
        //封装分页内容
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        showPreviousPage = !(page == 1);
        showFirstPage = !pages.contains(1);
        showNext = !(page == totalPage);
        showEndPage = !pages.contains(totalPage);
    }
}
