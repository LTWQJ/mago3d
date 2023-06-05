package gaia3d.domain.uploaddata;

/**
 * 目录结构
 * @author Cheon JeongDae
 *
 */
public enum UploadDirectoryType {
	// 年、月、日
	YEAR, YEAR_MONTH, YEAR_MONTH_DAY,
	// 用户名下的年、月、日
	USERID_YEAR, USERID_YEAR_MONTH, USERID_YEAR_MONTH_DAY,
	// 年、月、日下的用户名
	YEAR_USERID, YEAR_MONTH_USERID, YEAR_MONTH_DAY_USERID
}
