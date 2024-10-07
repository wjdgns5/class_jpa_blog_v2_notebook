package com.tenco.blog_v1.board;



import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {

    // DI
    private final BoardNativeRepository boardNativeRepository;
    private final BoardRepository boardRepository;

    /**
     * 특정 게시글 요청 확인
     * 주소설계 - http://localhost:8080/board/1
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
//      JPA API 사용
//        Board board = boardNativeRepository.findById(id);
//        request.setAttribute("board", board);

        // JPQL FETCH join 사용
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);

        return "board/detail";
    }

    /**
     * w주소 설계 : http://localhost:8080/
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        List<Board> boardList = boardNativeRepository.findAll();
        model.addAttribute("boardList", boardList);
        log.warn("여기까지 오나?"); // 경고로그
        log.info("boardList : " + boardList); // 정보로그
        System.out.println("boardList : " + boardList.toString());
        return "index";
    }

    /**
     * 주소 설계 - http://localhost:8080/board/save-form
     * 게시글 작성 화면
     * @return
     */
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    /**
     * 게시글 저장
     * 주소설계 : http://localhost:8080/board/save
     * @param title
     * @param content
     * @return
     */
    @PostMapping("/board/save")
    public String save(@RequestParam(name = "title") String title, @RequestParam(name = "content")String content) {
        // name 이 title , content 가 POST를 통해 값을 여기로 전달받는다.

        // 파라미터가 올바르게 전달되었는지 확인하기 위해서 사용
        log.info("save 실행 : 제목={}, 내용={}", title, content);
        // log.warn("save 실행 : 제목={}, 내용={}", title, content);
        boardNativeRepository.save(title, content);
        return "redirect:/";
    }



    /**
     * 특정 게시글 삭제 요청
     * 주소설계 - http://localhost:8080/board/1/delete
     * @param id
     * @return
     */
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id) {
        boardNativeRepository.deleteById(id);
        return "redirect:/";
    }


    /**
     * 게시글 수정 화면 요청
     * 주소설계 http://localhost:8080/board/1/update-form
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Board board = boardNativeRepository.findById(id);
        request.setAttribute("board", board);
        return "board/update-form";
    }

    /**
     * 게시글 수정 요청 기능
     * @param id
     * @param title
     * @param content
     * @return
     */
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable(name = "id") Integer id, @RequestParam(name = "title") String title, @RequestParam(name = "content") String content) {
        boardNativeRepository.updateById(id, title, content);
        // 업데이트 기능이 완료되면 디테일로 자기자신을 바라본다?
        return "redirect:/board/detail" + id;
    }
}


