package com.hjhtest.community.data_transfer_object;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PageDTO
 * @Description:
 * @author: hjh
 * @date: 2022/2/22 11:47
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;



    public void setPagination(Integer totalcount, Integer page, Integer size) {

        totalPage =  (int)Math.ceil((double)totalcount/(double)size);

        if(page<1){
            page =1;
        }
        if(page > totalPage){
            page = totalPage;
        }


        this.page = page;

        int start = page-3;
        int end = page+3;
        if(start < 1 ){
            start = 1;
        }
        if(end > totalPage){
            end = totalPage;
        }

        for(int i = start; i<=end;i++){
            pages.add(i);
        }


        //前一页
        if(page == 1){
            showPrevious = false;
        }
        else {
            showPrevious = true;
        }

        //后一页
        if(page == totalPage){
            showNext = false;
        }
        else {
            showNext = true;
        }


        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }
        else {
            showFirstPage = true;
        }


        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }
        else {
            showEndPage = true;
        }


    }
}
