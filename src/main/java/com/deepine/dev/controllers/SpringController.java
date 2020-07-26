package com.deepine.dev.controllers;

import com.deepine.dev.beans.AccountBean;
import com.deepine.dev.beans.DataBean;
import com.deepine.dev.database.MapperInterface;
import com.deepine.dev.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class SpringController {

    // Data 처리 : 테이블에 데이터 처리를 직접해서 Mapper 클래스를 직접 주입했다.
    @Autowired
    MapperInterface dataMapper;

    // 회원가입 : 테이블에 데이터처리를 Service 클래스로 위임하여 처리한다.
    @Autowired
    AccountService accountService;

    // 화면 출력 ------------------------------------------------------------ //
    // 인덱스 페이지
    @GetMapping("/index")
    public String index() throws Exception{
        return "views/index";
    }

    // 테이블 데이터 출력
    @GetMapping("/viewData")
    public String viewData(Model model) throws Exception{ //Return type이 String인 이유가 html 페이지 주소를 리턴하려하기 때문이다.
        //MapperInterface.java에서 select_data()함수를 호출하여 수행하면 결과값이 List 형태로 리턴된다.
        List<DataBean> list = dataMapper.select_data();
        model.addAttribute("list", list);

        // viewData를 리턴하면 로직 처리 후 WEB-INF/views/viewData.html 페이지로 forward(페이지 부분로딩) 한다.
        // redirect(주소변경필요시 or 새로 페이지 로딩)은 return "redirect:/sample/mysample" 처럼 사용한다.
        return "views/viewData"; //html 페이지 값을 리턴하기 때문에 쌍따옴표로 묶어줘야 한다.
    }

    // 데이터 입력화면 페이지
    @GetMapping("/inputData")
    public String inputData() throws Exception{
        return "views/inputData";
    }

    // 데이터 수정/삭제 화면 페이지
    @GetMapping("/correctData")
    public String correctData(@RequestParam("no") int no, Model model) { //URL 파라미터에 no 값을 읽어서 no라는 변수로 사용
        List<DataBean>list = dataMapper.select_data_filter(no); //select_data_filter에 파라미터 no를 싣어서 수행한다
        model.addAttribute("list", list);

        return "views/correctData";
    }

    // 데이터 입출력 ------------------------------------------------------------ //
    // 데이터 입력 실행 페이지
    @PostMapping("/inputDataExe")
    public String inputDataExe(DataBean dataBean) { //DataBean은 form action에서 넘어온 이름이 동일하게 매칭
        dataMapper.insert_data(dataBean);

        return "views/processingComplete";
    }

    // 데이터 삭제 실행 페이지
    @PostMapping("/deleteDataExe")
    public String deleteDataExe(@RequestParam("no") int no) {
        dataMapper.delete_data(no);

        return "views/processingComplete";
    }

    // 데이터 수정 실행 페이지
    @PostMapping("/updateDataExe")
    public String updateDataExe(DataBean dataBean) {
        dataMapper.update_data(dataBean);

        return "views/processingComplete";
    }

    // 회원가입 ------------------------------------------------------------ //
    // 사용자 생성
    @GetMapping("/createUser")
    public String create(){

        AccountBean accountAdmin = new AccountBean();
        accountAdmin.setId("admin");
        accountAdmin.setPassword("1111");

        System.out.println("-------------------------------------------------------------");
        System.out.println(accountAdmin);

        accountService.save(accountAdmin, "ROLE_ADMIN"); //SecurityConfig.java에 hasRole("ADMIN")은 DB에 저장될때는 앞에 ROLE_ prefix가 붙어야 한다.

        AccountBean accountUser = new AccountBean();
        accountUser.setId("user");
        accountUser.setPassword("1111");
        accountService.save(accountUser, "ROLE_USER"); //SecurityConfig.java에 hasRole("USER")은 DB에 저장될때는 앞에 ROLE_ prefix가 붙어야 한다.

        return "views/processingComplete";
    }

}
