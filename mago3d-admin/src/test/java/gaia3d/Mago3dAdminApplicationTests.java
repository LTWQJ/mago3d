package gaia3d;

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
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j

class Mago3dAdminApplicationTests {

//		@Test
		/*public  void test() throws Exception {
			String wsName = "mago3d" ; //待创建和发布图层的workspace
			String layerName = "7777" ; // 数据库要发布的表名称,后面图层名称和表名保持一致
			boolean publishPostGISLayer =publishPostGISLayer(wsName, layerName);
			if(publishPostGISLayer=false){
				System.out.println("此Publish layer已存在");
			}
			System.out.println(publishPostGISLayer);
		}
		public  boolean publishPostGISLayer(String wsName, String layerName) throws Exception {
			//geoserver连接配置
			String url = "http://localhost:8080/geoserver" ;
			String username = "admin" ;
			String passwd = "geoserver" ;
			//postgis连接配置
			String pgHost = "localhost" ;
			int pgPort = 5432;
			String pgUser = "postgres" ;
			String pgPwd = "ltw123" ;
			String pgSchema="public";
			String pgDatabase="mago3d";
//            String ws = "lyf" ; //待创建和发布图层的workspace
//            String store_name = "lyf" ; //数据库连接要创建的store
//            String table_name = "xxx" ; // 数据库要发布的表名称,后面图层名称和表名保持一致
			Boolean isPublished=null;
			GeoServerRESTManager geoServerRESTManager = new GeoServerRESTManager(new URL(url), username, passwd);
			GeoServerRESTPublisher geoServerRESTPublisher = geoServerRESTManager.getPublisher();
			GeoServerRESTReader geoServerRESTReader=geoServerRESTManager.getReader();

			//判断workspace是否存在，不存在则创建
			//创建一个workspace
			List<String> workspaces = geoServerRESTReader.getWorkspaceNames();
			if(!workspaces.contains( wsName)){
				boolean createws = geoServerRESTPublisher.createWorkspace( wsName);
				System.out.println("create ws : " + createws);
			}else {
				System.out.println("workspace已经存在了,ws :" +  wsName);

			}
			// 判断数据存储（datastore）是否已经存在，不存在则创建
			String storeName = wsName;
			RESTDataStore restStore =geoServerRESTReader.getDatastore(wsName, storeName);
			if (restStore == null) {
				GSPostGISDatastoreEncoder store = new GSPostGISDatastoreEncoder(storeName);
				store.setHost(pgHost);
				store.setPort(pgPort);
				store.setUser(pgUser);
				store.setPassword(pgPwd);
				store.setDatabase(pgDatabase);
				store.setSchema(pgSchema);
				store.setConnectionTimeout(300);
				store.setMaxConnections(20);
				store.setMinConnections(1);
				store.setExposePrimaryKeys(true);
				boolean isCreateStore = geoServerRESTManager.getStoreManager().create(wsName,store);
				System.out.println("Create store : " + isCreateStore);
			} else {
				log.info("DataStore already exist, store:" + storeName);
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
			fte.setSRS("EPSG:4326");
			GSLayerEncoder layerEncoder = new GSLayerEncoder();
			// layerEncoder.setDefaultStyle(layerName); // 样式和图层名字相同
			isPublished = geoServerRESTPublisher.publishDBLayer(wsName, storeName, fte, layerEncoder);
			log.info("Publish layer: " + isPublished);
			System.out.println("Publish layer: " + isPublished);

			return isPublished;
		}*/
	}


