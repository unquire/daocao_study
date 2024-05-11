package com.daocao.common.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.vo.RouterVO;
import com.daocao.common.response.DaoCaoResult;

import java.util.List;

/**
 * Author:啦啦啦
 * Package:com.daocao.auth.domain.Service
 * Project:daocao_study
 * name:IUmsMenuService
 * Date:2024/3/13 10:42
 */
public interface IUmsMenuService extends IService<UmsMenu> {

	List<RouterVO> searchSelMenu();

	List<RouterVO> searchMenuList();

	int saveMenu(UmsMenu umsMenu);

	RouterVO searchMenuById(Long id);

	int updateMenu(UmsMenu umsMenu);

	int deleteMenu(Long[] ids);
}
