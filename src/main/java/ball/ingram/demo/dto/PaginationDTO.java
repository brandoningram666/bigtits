package ball.ingram.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private  boolean showFirstPage;
    private boolean showEndPage;
    private boolean showNextPage;
    private boolean showPreviousPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage=totalPage;
        this.page = page;
        pages.add(page);
        for (int i = 1;i < 4 ; i++){
            if(page - i > 0 ){
                pages.add(0 , page - i);
            }
            if(page + i <= totalPage ){
                pages.add(page + i);
            }
        }
        //是否展示前一页
        if(page == 1){
            showPreviousPage = false;
        }else{
            showPreviousPage = true;
        }
        //是否展示后一页
        if(page == totalPage){
            showNextPage = false;
        }else{
            showNextPage = true;
        }
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }

    }
}
