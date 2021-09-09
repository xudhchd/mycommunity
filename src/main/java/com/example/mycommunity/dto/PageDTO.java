package com.example.mycommunity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showEnd;
    private boolean showFirst;
    private Integer page;
    private List<Integer> pages= new ArrayList<>();
    private Integer totalPage;

    public void setPagePara(Integer totalPageNum, Integer page, Integer size) {
        Integer totalPage = totalPageNum;
        this.page =page;
        this.totalPage = totalPage;

        if (totalPage < 8) {
            showFirst = false;
            showEnd = false;
            showNext = false;
            showPrevious = false;
            for (int i = 1; i < totalPage; i++) {
                pages.add(i);
            }
        }else{
            for (int i = 1; i < 8; i++) {
                if (page < 4) {
                    pages.add(i);
                } else if (page > totalPage - 4) {
                    pages.add(totalPage-7 + i);
                } else {
                    pages.add(page + i - 4);
                }
            }
            if (page == 1) {
                showFirst = false;
                showPrevious = false;
            } else {
                showFirst = true;
                showPrevious = true;
            }
            if (page == totalPage) {
                showEnd = false;
                showNext = false;
            } else {
                showEnd = true;
                showNext = true;
            }
            if (pages.contains(1)) {
                showFirst = false;
            } else {
                showFirst = true;
            }
            if (pages.contains(totalPage)) {
                showEnd = false;
            } else {
                showEnd = true;
            }
        }

    }
}
