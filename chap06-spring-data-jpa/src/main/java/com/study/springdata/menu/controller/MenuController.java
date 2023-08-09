package com.study.springdata.menu.controller;

import com.study.springdata.menu.dto.MenuDTO;
import com.study.springdata.menu.entity.Menu;
import com.study.springdata.menu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/menu") //도메인 (원래는 menus로 작성해야됨)
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        //bean에서 등록 될 수 있도록 설정
        this.menuService = menuService;
    }

    /* RestAPI 기반
     * GET(조회) : /menus/1 -> 메뉴들 중 한가지를 확인하고자 할 때
     * GET(조회) : /menus? -> 메뉴 전체를 대상으로 무언가를 하고 싶을 때(ex.주문 목록 확인할 때 /menus?order)
     * POST(등록)  /menus
     * PUT : /menus/1?
     * DELETE : /menus/1 -> /menus일 경우, 전체를 삭제한다는 의미이기 떄문에 말이 안됨...
     * */
    @GetMapping("/test")
    public void test() {
        //localhost:8000/menu/test
        System.out.println("test");
    }

    @GetMapping("/{menuCode}") //@Get->조회할 때
    public ResponseEntity<Object> findMenyByCode(@PathVariable int menuCode) {
        System.out.println("1.controller에서 실행됨 : " + menuCode);
        Menu menu = menuService.findMenuByCode(menuCode);

        if (Objects.isNull(menu)/*menu가 없을 경우*/) {
            return ResponseEntity.status(404).body(new String("사용자 화면: 똑바로 입력해주세요")); //사용자 잘못
        }

        return ResponseEntity.ok().body(menu); //ok코드: 기본적으로 200번 코드를 갖는다고 생각...
    }

    @GetMapping("/list")
    public ResponseEntity<List<?>> findAllMenu() {
        List<Menu> menuList = menuService.findAllMenu();
        if (menuList.size() <= 0) {
            List<String> error = new ArrayList<>();
            error.add("이렇게 오류 보여주기...");
            return ResponseEntity.status(404).body(error);
        }

        return ResponseEntity.ok().body(menuList);
    }

    @PostMapping("/regist") //@Post -> 등록할 때
    public ResponseEntity<?> regist(Menu menu) {
        System.out.println("1.controller에서 실행됨 : " + menu);
        int result = menuService.registMenu(menu);

        return ResponseEntity.ok().body("성공");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(Menu menu/*원래 DTO로 받아와야함...*/) {

        //update: 영속 상태인 친구를 찾아와야함...
        Menu findMenu = menuService.findMenuByCode(menu.getMenuCode());
        if (Objects.isNull(findMenu)) {
            //조회 성공: 엔티티가 존재함..
            //조회 실패 : 업데이트 대상이 존재하지 않음
            return ResponseEntity.ok().body("데이터가 존재하지 않습니다...");
        }

        //스냅샷을 기준으로 변경전_[0,0,0,0] -> 변경후_[0,0,0,1] .save(id)메서드 호출 후 두 개 값 비교
        //------> 영속성 컨텍스트_[0,0,0,1] -> DB에 반영됨
        //영속성 컨텍스트에 없는 경우, 영속화를 진행후 .save() 호출시 DB에 반영됨 --> 그래서 있는지 없는지 확인하는 findMenu가 필요
        int result = menuService.updateMenu(findMenu, menu);

        if (result > 0) {
            return ResponseEntity.ok().body("수정 완료");
        } else {
            return ResponseEntity.status(400).body("수정 실패");
        }
    }

    @DeleteMapping("/{delete}")
    public ResponseEntity<?/*와일드카드: 타입을 동적으로 지원해줌*/> delete(@PathVariable int delete) {
        menuService.deleteCode(delete);

        return ResponseEntity.ok().body("삭제 완료");
    }
}
