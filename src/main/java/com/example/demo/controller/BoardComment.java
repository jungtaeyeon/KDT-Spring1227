package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * [
 *  {},
 *  {comments: [{},{},{}]}
 * ]
 * 요런 형태
 */
public class BoardComment {
    public static void main(String[] args) {
        List<Map<String,Object>> boardDetail = new ArrayList<>();
        Map<String,Object> dmap = new HashMap<>();
        dmap.put("b_title","5개월 PT권 양도합니다.");
        dmap.put("b_content","갑자기 지방발령으로 양도합니다. 원하시면 아래 이메일로 연락바랍니다.");
        dmap.put("b_date","2024-01-03");
        boardDetail.add(dmap);
        List<Map<String,Object>> commentList = new ArrayList<>();
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("bc_writer","키위");        
        rmap.put("bc_comment","혹시 몇회가 남은 건지 궁금합니다.");        
        commentList.add(rmap);
         rmap = new HashMap<>();
        rmap.put("bc_writer","사과");        
        rmap.put("bc_comment","혹시 5개월동안 헬스도 이용이 가능한 걸까요?.");        
        commentList.add(rmap);
         rmap = new HashMap<>();
        rmap.put("bc_writer","바나나");        
        rmap.put("bc_comment","혹시 사용기간이 제한되어 있는 걸까요?.");        
        commentList.add(rmap);
        if(commentList.size()>0){
             Map<String,Object> cmap = new HashMap<>();
             cmap.put("comments", commentList);
            boardDetail.add(1, cmap);
        }

        System.out.println("상세보기 내용 출력");
        //리스트에서 0번째 방에 있는 정보를 출력하면 된다.
        Map<String,Object> detail = boardDetail.get(0);
        System.out.println(detail);
        System.out.println("=============[[ 댓글목록 보기 ]]===============");
        //댓글을 출력해본다 - 동일한 list에서 가져오는데 단 1번째 방에 있는 정보를 출력한다
        Map<String,Object> comments = (Map)boardDetail.get(1);
        if(comments.containsKey("comments")){
            List<Map<String,Object>> comList = (List)comments.get("comments");
            for(int i=0;i<comList.size();i++){
                Map<String,Object> bcMap = comList.get(i);
                System.out.println(bcMap);
            }
        }
        else{
            System.out.println("댓글이 없습니다.");
        }
    }//end of main
}