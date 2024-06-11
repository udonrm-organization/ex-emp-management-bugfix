package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 *
 * @author igamasayuki
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  /**
   * 使用するフォームオブジェクトをリクエストスコープに格納する.
   *
   * @return フォーム
   */
  @ModelAttribute
  public UpdateEmployeeForm setUpForm() {
    return new UpdateEmployeeForm();
  }

  /////////////////////////////////////////////////////
  // ユースケース：従業員一覧を表示する
  /////////////////////////////////////////////////////

  /**
   * 従業員一覧画面を出力します.
   *
   * @param model モデル
   * @return 従業員一覧画面
   */
  @GetMapping("/showList")
  public String showList(Model model, @RequestParam(value = "name", required = false) String name) {
    List<Employee> employeeList;
    if (name != null) {
      model.addAttribute("name", name);
      employeeList = employeeService.showListByName(name);
      if (employeeList.isEmpty()) {
        model.addAttribute("NotFoundMessage", "検索条件に合致する従業員は1件も存在しませんでした");
      }
      if (name.equals("")) {
        employeeList = employeeService.showList();
      }
    } else {
      employeeList = employeeService.showList();
    }
    model.addAttribute("employeeList", employeeList);
    return "employee/list";
  }

  /////////////////////////////////////////////////////
  // ユースケース：従業員詳細を表示する
  /////////////////////////////////////////////////////

  /**
   * 従業員詳細画面を出力します.
   *
   * @param id    リクエストパラメータで送られてくる従業員ID
   * @param model モデル
   * @return 従業員詳細画面
   */
  @GetMapping("/showDetail")
  public String showDetail(String id, Model model) {
    Employee employee = employeeService.showDetail(Integer.parseInt(id));
    model.addAttribute("employee", employee);
    return "employee/detail";
  }

  /////////////////////////////////////////////////////
  // ユースケース：従業員詳細を更新する
  /////////////////////////////////////////////////////

  /**
   * 従業員詳細(ここでは扶養人数のみ)を更新します.
   *
   * @param form 従業員情報用フォーム
   * @return 従業員一覧画面へリダクレクト
   */
  @PostMapping("/update")
  public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return showDetail(form.getId(), model);
    }
    Employee employee = new Employee();
    employee.setId(form.getIntId());
    employee.setDependentsCount(form.getIntDependentsCount());
    employeeService.update(employee);
    return "redirect:/employee/showList";
  }

  @ResponseBody
  @GetMapping("/autoComplete")
  public List<String> getAutoComplete(String name) {
    List<String> employeeNameList = new ArrayList<>();
    for (Employee employee : employeeService.showListByName(name)) {
      employeeNameList.add(employee.getName());
    }
    return employeeNameList;
  }
}
