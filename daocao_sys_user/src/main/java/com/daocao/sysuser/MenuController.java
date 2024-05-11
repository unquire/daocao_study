package com.daocao.sysuser;

import com.daocao.common.Service.IUmsMenuService;
import com.daocao.common.Service.utils.security.DaoCaoSecurityUtil;
import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.vo.RouterVO;
import com.daocao.common.response.DaoCaoResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.sysuser
 * Project:daocao_study
 * name:MenuController
 * Date:2024/3/21 14:30
 */
@RestController
@RequestMapping("sys/menu")
public class MenuController {
	private final IUmsMenuService menuService;

	public MenuController(IUmsMenuService menuService) {
		this.menuService = menuService;
	}
	@PostMapping
	public DaoCaoResult saveMenu(@Valid @RequestBody UmsMenu umsMenu){
		return DaoCaoResult.success(menuService.saveMenu(umsMenu));
	}
	/*
	* 查询自己的菜单
	* */
	@GetMapping("self")
	public DaoCaoResult searchSelfMenu(){
		// 获取当前登录的用户id，都在SecurityContextHolder中存储
		List<RouterVO> menusList =  menuService.searchSelMenu();
		return DaoCaoResult.success(menusList);
	}
	@GetMapping("list")
	public DaoCaoResult searchMenuList(){
		List<RouterVO> menusList =  menuService.searchMenuList();
		return DaoCaoResult.success(menusList);
	}
	@GetMapping("{id}")
	public DaoCaoResult searchMenuById(@PathVariable("id") Long id ){
		RouterVO menusList = menuService.searchMenuById(id);
		return DaoCaoResult.success(menusList);
	}
	@PutMapping
	public DaoCaoResult updateMenu(@Valid @RequestBody UmsMenu umsMenu){
		int row = menuService.updateMenu(umsMenu);
		if(row>0){
			return DaoCaoResult.success("修改菜单成功!");
		}else{
			return DaoCaoResult.error("修改菜单失败!");
		}
	}
	@DeleteMapping
	public DaoCaoResult deleteMenu(@RequestBody Long[] ids){
		int row = menuService.deleteMenu(ids);
		if(row>0){
			return DaoCaoResult.success("删除菜单成功!");
		}else{
			return DaoCaoResult.error("删除菜单失败");
		}
	}
}
