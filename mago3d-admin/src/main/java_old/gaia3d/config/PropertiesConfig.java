package gaia3d.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:mago3d.properties")
@ConfigurationProperties(prefix = "mago3d")
public class PropertiesConfig {
	
	/**
	 * 本地:local，开发:develop，运营:product
	 */
	private String profile;
	
    /**
     * 本地环境是WINDOW，操作环境是LINUX
     */
    private String osType;
    
    /**
     * 是否在本地环境中使用mock
     */
    private boolean mockEnable;
    private boolean callRemoteEnable;
    private String serverIp;

    // TODO 원래는 external_service table로 관리됨. 임시 버전
    private String cacheClientUrl;
    
    // http, https
    private String restTemplateMode;
    private String restAuthKey;
    private String gisRestServer;
    private String restServer;

    // layer 파일 업로딩 디렉토리
    private String layerUploadDir;
    // layer export 용 임시 디렉토리
    private String layerExportDir;
    // 디자인 레이어 파일 업로딩 디렉토리
    private String designLayerUploadDir;
    // 디자인 레이어 export 용 임시 디렉토리
    private String deisgnLayerExportDir;
    
    private String queueServerHost;
	private String queueServerPort;
	private String queueUser;
	private String queuePassword;
	private String queueName;
	private String exchange;
	private String routingKey;

    // 这是转换F4D文件的Root路径，也是mago3DJS请求文件的Root路径。ServletConfig映射
    private String dataServiceDir;
    // F4D转换结果显示日志存储路径
    private String dataConverterLogDir;
    // 数据库F4D转换结果日志存储路径
    private String dataLibraryConverterLogDir;
    // 管理自用
    private String adminDataServiceDir;
    private String adminDataLibraryServiceDir;
    private String adminDataServicePath;
    private String adminDataLibraryServicePath;
    // 使用自用
    private String userDataServiceDir;
    private String userDataLibraryServiceDir;
    private String userDataServicePath;
    private String userDataLibraryServicePath;
    
    private String dataUploadDir;
    private String dataBulkUploadDir;
    // smart tiling 데이터 업로드 디렉토리
    private String dataSmartTilingDir;
    private String dataAttributeDir;
    private String dataAttributeUploadDir;
    private String dataObjectAttributeUploadDir;

    // 데이터 라이브러리 업로드 디렉토리
    private String dataLibraryUploadDir;
    
    private String guideDataServiceDir;
    
    private String ogr2ogrEnviromentPath;
    private String ogr2ogrHost;
    private String ogr2ogrPort;
}
