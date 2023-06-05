package gaia3d.service;

import gaia3d.domain.GeoPolicy;

/**
 * 2D, 3D 运营政策
 * @author jeongdae
 *
 */
public interface GeoPolicyService {
	
	/**
	 * 操作策略信息
	 * @return
	 */
	GeoPolicy getGeoPolicy();
	
	/**
	 * 修改空间信息默认
	 * @param geoPolicy
	 * @return
	 */
	int updateGeoPolicy(GeoPolicy geoPolicy);
	
	/**
	 * Geo Server 修改
	 * @param geoPolicy
	 * @return
	 */
	int updateGeoPolicyGeoServer(GeoPolicy geoPolicy);
}
