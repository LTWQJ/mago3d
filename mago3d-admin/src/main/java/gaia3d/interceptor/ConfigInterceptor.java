package gaia3d.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;
import gaia3d.domain.CacheManager;
import gaia3d.domain.Key;
import gaia3d.domain.Menu;
import gaia3d.domain.Policy;
import gaia3d.domain.UserGroupMenu;
import gaia3d.domain.UserSession;
import gaia3d.domain.YOrN;
import gaia3d.service.PolicyService;

/**
 * 负责处理整个网站的设置
 *  
 * @author jeongdae
 *
 */
@Slf4j
@Component
public class ConfigInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	String uri = request.getRequestURI();
    	HttpSession session = request.getSession();
    	
    	Policy policy = CacheManager.getPolicy();
    	
    	// TODO 너무 비 효율 적이다. 좋은 방법을 찾자.
    	// 세션이 존재하지 않는 경우
    	UserSession userSession = (UserSession)session.getAttribute(Key.USER_SESSION.name());
    	if(userSession != null && userSession.getUserId() != null && !"".equals(userSession.getUserId())) {
	    	List<UserGroupMenu> userGroupMenuList = CacheManager.getUserGroupMenuList(userSession.getUserGroupId());
	    	Integer clickParentId = null;
			Integer clickMenuId = null;
			Integer clickDepth = null;
			Menu menu = null;
			Menu parentMenu = null;
			
			for(UserGroupMenu userGroupMenu : userGroupMenuList) {
				if(uri.equals(userGroupMenu.getUrl())) {
					clickMenuId = userGroupMenu.getMenuId();
					if(userGroupMenu.getDepth() == 1) {
						clickParentId = userGroupMenu.getMenuId();
					} else {
						clickParentId = Integer.valueOf(userGroupMenu.getParent().toString());
					}
					clickDepth = userGroupMenu.getDepth();
					
					if( userGroupMenu.getDepth() == 1 && (uri.indexOf("/main/index")>=0) ) {
						break;
					} else if( userGroupMenu.getDepth() == 2) {
						break;
					} else {
						// pass
					}
				}
			}
			
			menu = CacheManager.getMenuMap().get(clickMenuId);
			parentMenu = CacheManager.getMenuMap().get(clickParentId);
			if(menu != null) {
				if(YOrN.Y == YOrN.valueOf(menu.getDisplayYn())) {
					menu.setAliasName(null);
					parentMenu.setAliasName(null);
				} else {
					Integer aliasMenuId = CacheManager.getMenuUrlMap().get(menu.getUrlAlias());
					Menu aliasMenu = CacheManager.getMenuMap().get(aliasMenuId);
					menu.setAliasName(aliasMenu.getName());
					parentMenu.setAliasName(aliasMenu.getName());
				}
			}
			
//			Integer contentLoadBalancingIntervalValue = policy.getContent_load_balancing_interval().intValue() * 1000;
//			request.setAttribute("contentLoadBalancingInterval", contentLoadBalancingIntervalValue);
			
			request.setAttribute("clickMenuId", clickMenuId);
//			request.setAttribute("clickParentId", clickParentId);
//			request.setAttribute("clickDepth", clickDepth);
			request.setAttribute("menu", menu);
			request.setAttribute("parentMenu", parentMenu);
			
			request.setAttribute("cacheUserGroupMenuList", userGroupMenuList);
			request.setAttribute("cacheUserGroupMenuListSize", userGroupMenuList.size());
			request.setAttribute("contentCacheVersion", policy.getContentCacheVersion());
    	}
    	
        return true;
    }
}
