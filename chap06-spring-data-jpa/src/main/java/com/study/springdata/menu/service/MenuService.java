package com.study.springdata.menu.service;

import com.study.springdata.menu.dto.MenuDTO;
import com.study.springdata.menu.entity.Menu;
import com.study.springdata.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu findMenuByCode(int menuCode) {
        System.out.println("2.service에서 실행됨 : " + menuCode);
//        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalAccessError::new); //없을 경우도 생각해서 orElseThrow로 처리해준다.
        Menu menu = menuRepository.findById/*MenuRepository에 이름 가져가기..*/(menuCode);
        return menu;
    }

    public List<Menu> findAllMenu() {
        List<Menu> menuList = menuRepository.findAll(); //MenuRepository에 따로 추가 안해줘도 됨...
        return menuList;
    }

    @Transactional
    public int registMenu(Menu menu) {
        Menu result = menuRepository.save(menu);
        System.out.println(result);

        if (Objects.isNull(result)) {
            return 0; //null이 아닐 경우
        } else {
            return 1;
        }

    }

    @Transactional
    public int updateMenu(Menu findMenu, Menu updaMenu) {
        if (Objects.isNull(updaMenu.getMenuName())) {
            findMenu.setMenuName(updaMenu.getMenuName());
            System.out.println("menu -> : " + findMenu.getMenuName());
        }
        Menu result = menuRepository.save(findMenu);

        if (Objects.isNull(result)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Transactional
    public void deleteCode(int del) {
//        menuRepository.delete(/*이렇게 하면 쿼리 한 번 더 타야됨*/);
        menuRepository.deleteById(del); //존재하는지 먼저 찾기 -> 영속화에서 제거 -> null값

        Menu menu = menuRepository.findById(del);
        System.out.println(menu);
    }
}
