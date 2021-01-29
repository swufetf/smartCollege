package edu.tfswufe.controller;

import java.util.List;

import edu.tfswufe.entity.Department;
import edu.tfswufe.entity.Personnel;
import edu.tfswufe.entity.Overtime;
import edu.tfswufe.service.DepartmentService;
import edu.tfswufe.service.PersonnelService;
import edu.tfswufe.service.OvertimeService;
import edu.tfswufe.util.MTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.plugins.Page;

@Controller
@RequestMapping("/overtime")
public class OvertimeController {

	@Autowired
	private OvertimeService overtimeService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/listPage.do")
	public String selectListByPgae(Model model, int pageNo){
		Page<Overtime> page = overtimeService.selectListByPage(pageNo);
		model.addAttribute("page",page);
		return "admin/overtime_list";
	}

	@RequestMapping("/toAdd.do")
	public String toAdd(Model model){
		//查询出所有的部门
		List<Department> dList = departmentService.selectList();
		model.addAttribute("dList", dList);
		//查询出所有的员工
		List<Personnel> eList = personnelService.selectList();
		model.addAttribute("eList", eList );
		return "admin/overtime_add";
	}

	@RequestMapping("/add.do")
	public String add(Overtime overtime, String date){
		overtime.setDay(MTimeUtil.stringParse(date));
		overtimeService.insert(overtime);
		return "forward:/overtime/listPage.do?pageNo=1";
	}

	@RequestMapping("/{id}/toUpdate.do")
	public String toUpdate(Model model, @PathVariable Integer id){
		//查询出要修改的记录信息
		Overtime overtime = overtimeService.selectById(id);
		model.addAttribute("overtime", overtime);
		//查询出所有的部门
		List<Department> dList = departmentService.selectList();
		model.addAttribute("dList", dList);
		//查询出所有的员工
		List<Personnel> eList = personnelService.selectList();
		model.addAttribute("eList", eList );
		return "admin/overtime_update";
	}

	@RequestMapping("/{id}/update.do")
	public String updateById(@PathVariable Integer id,  String date, Overtime overtime){
		overtime.setId(id);
		overtime.setDay(MTimeUtil.stringParse(date));
		overtimeService.updateById(overtime);
		return "forward:/overtime/listPage.do?pageNo=1";
	}

	@RequestMapping("/{id}/delete.do")
	public String deleteById(@PathVariable Integer id){
		overtimeService.deleteById(id);
		return "forward:/overtime/listPage.do?pageNo=1";
	}

	@RequestMapping("/{personnelNumber}/oneself.do")
	public String select(Model model, @PathVariable Integer personnelNumber, int pageNo){
		Page<Overtime> page = overtimeService.selectByPersonnel(pageNo, personnelNumber);
		model.addAttribute("page",page);
		return "admin/oneself_overtime";
	}

}
