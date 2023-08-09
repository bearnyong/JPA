package com.study.springdata.menu.repository;

import com.study.springdata.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findById/*service에서 불러온 애 이름 맞추기*/(int menuCode);

}
