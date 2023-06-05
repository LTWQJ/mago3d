package gaia3d.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 세션에 저장될 사용자 정보
 * @author jeongdae
 *
 */
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSession implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7501829694372184669L;

	/******* 会话劫持检查 *******/
	private String signinIp;

	// 唯一编号
	private String userId;
	// 用户组唯一编号
	private Integer userGroupId;
	// 用户组名称（用于屏幕）
	private String userGroupName;
	// 이름
	private String userName;
	// 密码
	private String password;
	// SALT(spring5부터 사용 안함)
	private String salt;
	
	// 首次登录时用户 Role 权限检查通行证功能
	private String userRoleCheckYn;
	// 用户状态。0：忙，1：停用（管理员），2：锁定（超过密码失败次数），3：休眠（登录期间），4：过期（使用期限结束），5：删除（屏幕不显示，policy.user_delete_method=0），6：临时密码
	private String status;
	
	// 사인인 실패 횟수
	private Integer failSigninCount;
	// 一段时间内未连接时的锁定处理结果值
	private Boolean userLastSigninLockOver;
	// 密码更改周期值
	private Boolean passwordChangeTermOver;
	
	// 最后一个正弦日期
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastSigninDate;
}
