package gaia3d.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import gaia3d.domain.UserGroup;
import gaia3d.domain.UserGroupMenu;
import gaia3d.domain.UserGroupRole;

@Repository
public interface UserGroupMapper {

	/**
     * 用户组列表
     * @return
     */
    List<UserGroup> getListUserGroup();

    /**
     * 查看用户组信息查询
     * @param userGroup
     * @return
     */
    UserGroup getUserGroup(UserGroup userGroup);

    /**
     * 查看默认用户组信息
     * @return
     */
    UserGroup getBasicUserGroup();

    /**
     * 按父菜单和显示顺序查看菜单
     * @param userGroup
     * @return
     */
    UserGroup getUserGroupByParentAndViewOrder(UserGroup userGroup);
    
    /**
     * 具有相同祖先的用户组 ID 列表
     * @param userGroupId
     * @return
     */
    List<Integer> getUserGroupIdByAncestor(Integer userGroupId);
    
    /**
     * 具有相同父级的用户组 ID 列表
     * @param userGroupId
     * @return
     */
    List<Integer> getUserGroupIdByParent(Integer userGroupId);

    /**
     * 检查用户组密钥重复项
     * @param userGroup
     * @return
     */
    Boolean isUserGroupKeyDuplication(UserGroup userGroup);

	/**
	 * 사용자 그룹 메뉴 권한 목록
	 * @param userGroupMenu
	 * @return
	 */
	List<UserGroupMenu> getListUserGroupMenu(UserGroupMenu userGroupMenu);

	/**
	 * 사용자 그룹 Role 목록
	 * @param userGroupRole
	 * @return
	 */
	List<UserGroupRole> getListUserGroupRole(UserGroupRole userGroupRole);

	/**
	 * 사용자 그룹 Role Key 목록
	 * @param userGroupRole
	 * @return
	 */
	List<String> getListUserGroupRoleKey(UserGroupRole userGroupRole);

    /**
     * 注册用户组
     * @param userGroup
     * @return
     */
    int insertUserGroup(UserGroup userGroup);

	/**
	 * 사용자 그룹 메뉴 등록
	 * @param userGroupMenu
	 * @return
	 */
	int insertUserGroupMenu(UserGroupMenu userGroupMenu);

	/**
	 * 사용자 그룹 Role 등록
	 * @param userGroupRole
	 * @return
	 */
	int insertUserGroupRole(UserGroupRole userGroupRole);

	/**
	 * 사용자 그룹 수정
	 * @param userGroup
	 * @return
	 */
	int updateUserGroup(UserGroup userGroup);

	/**
	 * 사용자 그룹 표시 순서 수정 (up/down)
	 * @param userGroup
	 * @return
	 */
	int updateUserGroupViewOrder(UserGroup userGroup);

	/**
	 * 사용자 그룹 삭제
	 * @param userGroup
	 * @return
	 */
	int deleteUserGroup(UserGroup userGroup);

	/**
	 * ancestor를 이용하여 데이터 그룹 삭제
	 * @param userGroup
	 * @return
	 */
	int deleteUserGroupByAncestor(UserGroup userGroup);

	/**
	 * parent를 이용하여 데이터 그룹 삭제
	 * @param userGroup
	 * @return
	 */
	int deleteUserGroupByParent(UserGroup userGroup);

	/**
	 * 사용자 그룹 메뉴 삭제
	 * @param userGroupId
	 * @return
	 */
	int deleteUserGroupMenu(Integer userGroupId);

	/**
	 * 사용자 그룹 Role 삭제
	 * @param userGroupId
	 * @return
	 */
	int deleteUserGroupRole(Integer userGroupId);
}
