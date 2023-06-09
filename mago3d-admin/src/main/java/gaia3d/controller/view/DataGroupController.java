package gaia3d.controller.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gaia3d.config.PropertiesConfig;
import gaia3d.domain.DataGroup;
import gaia3d.domain.Key;
import gaia3d.domain.Policy;
import gaia3d.domain.UserSession;
import gaia3d.service.DataGroupService;
import gaia3d.service.PolicyService;
import gaia3d.support.SQLInjectSupport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/data-group")
public class DataGroupController {

	@Autowired
	private DataGroupService dataGroupService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	/**
	 * 数据组列表
	 * @param request
	 * @param dataGroup
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(HttpServletRequest request, @ModelAttribute DataGroup dataGroup, Model model) {
		dataGroup.setSearchWord(SQLInjectSupport.replaceSqlInection(dataGroup.getSearchWord()));
		dataGroup.setOrderWord(SQLInjectSupport.replaceSqlInection(dataGroup.getOrderWord()));
		
		log.info("@@ dataGroup = {}", dataGroup);
		
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		dataGroup.setUserId(userSession.getUserId());
		List<DataGroup> dataGroupList = dataGroupService.getListDataGroup(dataGroup);
		
		model.addAttribute("dataGroupList", dataGroupList);

		return "/data-group/list";
	}

	/**
	 * 移动“注册数据组”页
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/input")
	public String input(HttpServletRequest request, Model model) {
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		
		DataGroup dataGroup = new DataGroup();
		dataGroup.setUserId(userSession.getUserId());
		List<DataGroup> dataGroupList = dataGroupService.getListDataGroup(dataGroup);
		
		Policy policy = policyService.getPolicy();
		dataGroup.setParentName(policy.getContentDataGroupRoot());
		dataGroup.setParent(0);
		dataGroup.setParentDepth(0);

		model.addAttribute("policy", policy);
		model.addAttribute("dataGroup", dataGroup);
		model.addAttribute("dataGroupList", dataGroupList);

		return "/data-group/input";
	}

	/**
	 * 移动“修改数据组”页
	 * @param request
	 * @param dataGroupId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modify")
	public String modify(HttpServletRequest request, @RequestParam("dataGroupId") Integer dataGroupId, Model model) {
		DataGroup dataGroup = new DataGroup();
		dataGroup.setDataGroupId(dataGroupId);
		dataGroup = dataGroupService.getDataGroup(dataGroup);
		
		if(StringUtils.isEmpty(dataGroup.getParentName())) {
			Policy policy = policyService.getPolicy();
			dataGroup.setParentName(policy.getContentDataGroupRoot());
		}
		dataGroup.setOldDataGroupKey(dataGroup.getDataGroupKey());
		
		model.addAttribute("dataGroup", dataGroup);
		
		return "/data-group/modify";
	}

	/**
	 * 删除数据组
	 * @param dataGroupId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/delete")
	public String delete(@RequestParam("dataGroupId") Integer dataGroupId, Model model) {
		// TODO validation 需要检查
		if(dataGroupId == null) {
			log.info("@@@ validation error dataGroupId = {}", dataGroupId);
			return "redirect:/data-group/list";
		}
		
		DataGroup dataGroup = new DataGroup();
		dataGroup.setDataGroupId(dataGroupId);

		dataGroupService.deleteDataGroup(dataGroup);
		
		return "redirect:/data-group/list";
	}
}
