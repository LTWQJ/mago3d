package gaia3d.utils;
import java.net.URL;
import java.util.List;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
public class Autopubgeoserver {
//    @Value("http://localhost:8080/geoserver")
//    private String geoServerUrl;
//
//    @Value("admin")
//    private String geoServerUsername;
//
//    @Value("geoserver")
//    private String geoServerPassword;
//
//    @Value("localhost")
//    private String postgisHost;
//
//    @Value("5432")
//    private int postgisPort;
//
//    @Value("postgres")
//    private String postgisUsername;
//
//    @Value("ltw123")
//    private String postgisPassword;
//
//    @Value("mago3d")
//    private String postgisDatabase;

    public  void autopubgeoserver(String layerName,String layerprojection) throws Exception {
        log.info("+++++++++++++++++已经进入自动发布图层函数中+++++++++++++++++++");
        String wsName = "mago3d" ; //待创建和发布图层的workspace
//        String layerName = "7777" ; // 数据库要发布的表名称,后面图层名称和表名保持一致
        boolean publishPostGISLayer =publishPostGISLayer(wsName, layerName,layerprojection);
        if(publishPostGISLayer=false){
            System.out.println("此Publish layer已存在");
        }
        System.out.println(publishPostGISLayer);
    }
    public  boolean publishPostGISLayer(String wsName, String layerName,String layerprojection) throws Exception {
        //geoserver连接配置
        String geoServerUrl = "http://localhost:8080/geoserver" ;
        String geoServerUsername = "admin" ;
        String geoServerPassword = "geoserver" ;
        //postgis连接配置
        String postgisHost = "localhost" ;
        int postgisPort= 5432;
        String postgisUsername = "postgres" ;
        String postgisPassword= "ltw123" ;
          String pgSchema="public";
        String postgisDatabase="mago3d";
//            String ws = "lyf" ; //待创建和发布图层的workspace
//            String store_name = "lyf" ; //数据库连接要创建的store
//            String table_name = "xxx" ; // 数据库要发布的表名称,后面图层名称和表名保持一致
        Boolean isPublished=null;
        GeoServerRESTManager geoServerRESTManager = new GeoServerRESTManager(new URL(geoServerUrl), geoServerUsername, geoServerPassword);
        GeoServerRESTPublisher geoServerRESTPublisher = geoServerRESTManager.getPublisher();
        GeoServerRESTReader geoServerRESTReader=geoServerRESTManager.getReader();

        //判断workspace是否存在，不存在则创建
        //创建一个workspace
        List<String> workspaces = geoServerRESTReader.getWorkspaceNames();
        if(!workspaces.contains( wsName)){
            boolean createws = geoServerRESTPublisher.createWorkspace( wsName);
            System.out.println("create ws : " + createws);
        }else {
            System.out.println("工作空间已经存在了,ws :" +  wsName);

        }
        // 判断数据存储（datastore）是否已经存在，不存在则创建
        String storeName = wsName;
        RESTDataStore restStore =geoServerRESTReader.getDatastore(wsName, storeName);
        if (restStore == null) {
            GSPostGISDatastoreEncoder store = new GSPostGISDatastoreEncoder(storeName);
            store.setHost(postgisHost);
            store.setPort(postgisPort);
            store.setUser(postgisUsername);
            store.setPassword(postgisPassword);
            store.setDatabase(postgisDatabase);
            store.setSchema(pgSchema);
            store.setConnectionTimeout(300);
            store.setMaxConnections(20);
            store.setMinConnections(1);
            store.setExposePrimaryKeys(true);
            boolean isCreateStore = geoServerRESTManager.getStoreManager().create(wsName,store);
            System.out.println("Create store : " + isCreateStore);
        } else {
            log.info("数据存储已经存在, store:" + storeName);
        }
        RESTLayer layer = geoServerRESTReader.getLayer(wsName, layerName);
        // 若图层已存在，则不发布
        if (layer == null) {
            // publisher.removeLayer(wsName, layerName);
            geoServerRESTPublisher.unpublishFeatureType(wsName, storeName, layerName);
        }
        GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
        fte.setTitle(layerName);
        fte.setName(layerName);
        fte.setSRS(layerprojection);
        GSLayerEncoder layerEncoder = new GSLayerEncoder();
        // layerEncoder.setDefaultStyle(layerName); // 样式和图层名字相同
        isPublished = geoServerRESTPublisher.publishDBLayer(wsName, storeName, fte, layerEncoder);
        log.info("Publish layer: " + isPublished);
        System.out.println("Publish layer: " + isPublished);
        log.info("发布geoserver图层成功。");
        return isPublished;

    }
}