package com.xyb.aidemoli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages;

    public void setPagination(Integer totalCount, Integer currentPage, Integer size) {

        totalPage = 0;
        pages = new ArrayList<>();
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        this.currentPage = currentPage;
        //是否展示上一页
        if(currentPage == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示下一页
        if(currentPage == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //显示多少页
        pages.add(currentPage);
        for(int i=1;i<=3;i++){
            if(currentPage - i >0)
                pages.add(currentPage - i);
            if(currentPage + i <= totalPage){
                pages.add(currentPage + i);
            }
        }
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
        Collections.sort(pages);
    }
}
