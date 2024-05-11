package com.daocao.common.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daocao.common.Service.IUmsMenuService;
import com.daocao.common.Service.utils.security.DaoCaoSecurityUtil;
import com.daocao.common.constants.HttpStatus;
import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.entity.UmsRole;
import com.daocao.common.domain.vo.RouterVO;
import com.daocao.common.exception.ServiceException;
import com.daocao.common.mapper.UmsMenuMapper;
import com.daocao.common.mapper.UmsRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.Service.impl
 * Project:daocao_study
 * name:UmsMenuImpl
 * Date:2024/3/13 10:45
 */
@Service
@Slf4j
public class UmsMenuImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {
	private final UmsRoleMapper roleMapper;

	public UmsMenuImpl(UmsRoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	/*
	* 获取个人菜单列表
	* */
	@Override
	public List<RouterVO> searchSelMenu() {
		// 获取用户id,根据用户ID去查询内容
		Long userId = DaoCaoSecurityUtil.getUserId();
		List<Long> roleIds = roleMapper.selectByUserId(userId);
		log.info("roleIds============>"+roleIds);
		// 根据角色id查用户的权限列表
		List<UmsMenu> menus = baseMapper.selectByRoleIds(roleIds);
		log.info("menus=========>"+menus);
		// [UmsMenu(id=1, parentId=0, menuName=首页,
		// path=/index, componentPath=index, perms=index, icon=, menuType=0, sort=0, status=null,
		// creator=null, updater=null, createTime=null, updateTime=null, remark=null, deleted=null), ...
		// 通过递归设置菜单的树形结构
		// 1. 获取所有的1级菜单【parentId】
		// 2. 遍历1级菜单，获取他所有的子元素【其他数据的parentId = 当前元素的id】
		List<RouterVO> router =  getRouter(menus);
		router.forEach(System.out::println);
		//RouterVO(id=1, parentId=0, menuName=首页, path=/index, componentPath=index, icon=, menuType=0, children=[])
		// RouterVO(id=2, parentId=0, menuName=系统管理, path=system, componentPath=system, icon=, menuType=0,
		// children=[RouterVO(id=5, parentId=2, menuName=用户管理, path=system/user/index, componentPath=sysUser, icon=, menuType=1,
		// children=[RouterVO(id=9, parentId=5, menuName=新增用户, path=null, componentPath=null, icon=, menuType=2, children=[])]),
		// RouterVO(id=6, parentId=2, menuName=角色管理, path=system/role/index, componentPath=sysRole, icon=, menuType=1, children=[]),
		// RouterVO(id=7, parentId=2, menuName=菜单管理, path=system/menu/index, componentPath=sysMenu, icon=, menuType=1, children=[])])
		// RouterVO(id=3, parentId=0, menuName=系统工具, path=tools, componentPath=tools, icon=, menuType=0,
		// children=[RouterVO(id=8, parentId=3, menuName=代码生成, path=tools/gen/index, componentPath=toolsGen, icon=, menuType=1, children=[])])
		// RouterVO(id=4, parentId=0, menuName=支付管理, path=pay, componentPath=pay, icon=, menuType=0, children=[])
		return router;
	}

	@Override
	public List<RouterVO> searchMenuList() {
		List<UmsMenu> menuList = baseMapper.selectList(null);
		List<RouterVO> router = getRouter(menuList);
		return router;
	}
	/*
	* 添加表单
	* */
	@Override
	public int saveMenu(UmsMenu umsMenu) {
		// 判断是否登录
		String creator = DaoCaoSecurityUtil.getUserName();
		umsMenu.setCreator(creator);
		umsMenu.setUpdater(creator);
		// 判断用户是否存在
		Long parentId = umsMenu.getParentId();
		String menuName = umsMenu.getMenuName();
		String path = umsMenu.getPath();
		// 查询条件构造器
		LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UmsMenu::getMenuName,menuName).eq(UmsMenu::getParentId,parentId).or().eq(UmsMenu::getPath,path);
		Long count = baseMapper.selectCount(wrapper);
		if(count>0){
			throw new ServiceException(HttpStatus.ERROR,"该菜单已经存在，或路径已存在");
		}
		return baseMapper.insert(umsMenu);
	}

	@Override
	public RouterVO searchMenuById(Long id) {
		String creator = DaoCaoSecurityUtil.getUserName();
		if(creator == null){
			throw new ServiceException(HttpStatus.ERROR,"用户不存在!");
		}
		UmsMenu menu = baseMapper.selectById(id);
		RouterVO routerVO = new RouterVO();
		// 数据类型转换
		BeanUtil.copyProperties(menu,routerVO);
		return routerVO;
	}

	@Override
	public int updateMenu(UmsMenu umsMenu) {
		String create = DaoCaoSecurityUtil.getUserName();
		umsMenu.setUpdater(create);
		Long parentId = umsMenu.getParentId();
		String menuName = umsMenu.getMenuName();
		LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UmsMenu::getParentId,parentId).eq(UmsMenu::getMenuName,menuName);
		Long count = baseMapper.selectCount(wrapper);
		if(count!=0){
			return baseMapper.updateById(umsMenu);
		}else{
			throw new ServiceException(HttpStatus.ERROR,"更新失败!");
		}
	}

	@Override
	public int deleteMenu(Long[] ids) {
		log.info("ids====>"+Arrays.toString(ids));
		String create = DaoCaoSecurityUtil.getUserName();
		if(create == null){
			log.info("进入到了这里");
			throw new ServiceException(HttpStatus.FORBIDDEN,"用户登录过期");
		}
		return baseMapper.deleteBatchIds(Arrays.asList(ids));
	}

	// 功能是将路由列表变成树状的形式
	private List<RouterVO> getRouter(List<UmsMenu> menusList){
		// menusList是全部的路由列表
		List<RouterVO> routerVos = new ArrayList<>();
		// 获取所有的一级路由
		List<UmsMenu> parentMenu = menusList.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
		for(UmsMenu menu:parentMenu){
			RouterVO routerVO = new RouterVO();
			// 数据类型转换
			BeanUtil.copyProperties(menu,routerVO);
			routerVos.add(routerVO);
		}
		// 获取下一级的所有子菜单
		for(RouterVO routerVO:routerVos){
			List<RouterVO> childrenlist = buildTree(menusList,routerVO.getId());
			routerVO.setChildren(childrenlist);
		}
		return routerVos;
	}
	// 递归获取子路径
	private List<RouterVO> buildTree(List<UmsMenu> parentMenu,Long parentId){
		List<RouterVO> childrenList =  new ArrayList<>();
		// 遍历所有数据
		for(UmsMenu menu:parentMenu){
			// 判断menu的parentId是否与传进来的parentId相同
			if(menu.getParentId().equals(parentId)){
				RouterVO routerVO = new RouterVO();
				BeanUtil.copyProperties(menu,routerVO);
				childrenList.add(routerVO);
			}
		}
		// 递归遍历childrenList可能还有的子节点
		for(RouterVO childrenItem:childrenList){
			childrenItem.setChildren(buildTree(parentMenu,childrenItem.getId()));
		}
		return childrenList;
	}
}
