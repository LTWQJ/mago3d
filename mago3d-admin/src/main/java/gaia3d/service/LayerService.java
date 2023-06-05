package gaia3d.service;

import java.util.List;
import java.util.Map;

import gaia3d.domain.GeoPolicy;
import gaia3d.domain.Layer;
import gaia3d.domain.LayerFileInfo;

public interface LayerService {

	/**
	 * Layer 总件数
	 * @param accessLog
	 * @return
	 */
	Long getLayerTotalCount(Layer layer);
	
    /**
    * layer 目录
    * @return
    */
    List<Layer> getListLayer(Layer layer);
    
    /**
     * 查询geoserver注册的图层列表
     * @return
     */
    String getListGeoserverLayer(GeoPolicy geoPolicy);

    /**
    * 获取layer的资料
    * @param layerId
    * @return
    */
    Layer getLayer(Integer layerId);
    
    /**
     * layerKey重复检查
     * @param layerKey
     * @return
     */
    Boolean isLayerKeyDuplication(String layerKey);
    
    /**
    * 图层表的评论类型是什么
    * @param layerKey
    * @return
    */
    String getGeometryType(String layerKey);

    /**
     * 查询图层的评论目录
     * @param layerKey
     * @return
     */
    String getLayerColumn(String layerKey);

    /**
    * 图层注册
    * @param layer
    * @return
    */
    Map<String, Object> insertLayer(Layer layer, List<LayerFileInfo> layerFileInfoList);

    /**
    * 利用shape文件修正layer信息
    * @param layer
    * @param isLayerFileInfoExist
    * @param layerFileInfoList
    * @return
    * @throws Exception
    */
    Map<String, Object> updateLayer(Layer layer, boolean isLayerFileInfoExist, List<LayerFileInfo> layerFileInfoList);

    /**
    * ogr2ogr实行
    * @param layer
    * @param isLayerFileInfoExist
    * @param shapeFileName
    * @param shapeEncoding
    * @throws Exception
    */
    void insertOgr2Ogr(Layer layer, boolean isLayerFileInfoExist, String shapeFileName, String shapeEncoding) throws Exception;

    /**
     * 根据数据库信息更新shp文件
     * @param version
     * @return
     */
    void exportOgr2Ogr(LayerFileInfo layerFileInfo, Layer layer) throws Exception;
    
    /**
    * 如果layer未注册，则使用rest api注册layer
     * @param geoPolicy
     * @param layerKey
     * @throws Exception
     */
    void registerLayer(GeoPolicy geoPolicy, String layerKey) throws Exception;
    
    /**
	 * 修正图层的风格信息
	 * @param layer
	 * @return
	 */
	int updateLayerStyle(Layer layer) throws Exception;

    /**
    * 图层回滚处理
    * @param layer
    * @param isLayerFileInfoExist
    * @param layerFileInfo
    * @param deleteLayerFileInfoGroupId
    */
    void rollbackLayer(Layer layer, boolean isLayerFileInfoExist, LayerFileInfo layerFileInfo, Integer deleteLayerFileInfoGroupId);

    /**
    * 用这个shape文件激活layer
    * @param layerId
    * @param layerFileInfoGroupId
    * @param layerFileInfoId
    * @return
    */
    int updateLayerByLayerFileInfoId(Integer layerId, Integer layerFileInfoGroupId, Integer layerFileInfoId);

     /**
    * 删除图层
    * @param layerId
    * @return
    */
    int deleteLayer(Integer layerId);
}
