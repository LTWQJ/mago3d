package gaia3d.geospatial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFileType;
import org.geotools.data.shapefile.files.ShpFiles;

import lombok.extern.slf4j.Slf4j;
import gaia3d.domain.ShapeFileExt;
import gaia3d.domain.ShapeFileField;

/**
 * Shape file 관련 유틸 
 *
 */
@Slf4j
public class ShapeFileParser {
	
	// shapefile 경로 
	private String filePath; 
	
	public ShapeFileParser(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * 检查shape file的必需专栏
	 * @return
	 */
	public Boolean fieldValidate() {
		DbaseFileReader reader = null;
		Boolean fieldValid = false; 
        try {
            ShpFiles shpFile = new ShpFiles(filePath);
            if(!shpFile.exists(ShpFileType.SHP)) {
            	return true;
            }
            // 因为只会检查场地，所以没有另外设定编码

            reader = new DbaseFileReader(shpFile, false, Charset.defaultCharset());
            System.out.println("数据值==========================================================="+Charset.defaultCharset()+"====================================================");
            DbaseFileHeader header = reader.getHeader();
            int filedValidCount = 0;
            // 现场计数
            int numFields = header.getNumFields();
            for(int iField=0; iField < numFields; ++iField) {
                String fieldName = header.getFieldName(iField);
                if(ShapeFileField.findBy(fieldName) != null) filedValidCount++;
            }
            // 确认是否有必要的评论，然后再回报
            fieldValid = (filedValidCount == ShapeFileField.values().length) ? true : false;
            reader.close();
        } catch (MalformedURLException e) {
            log.info("MalformedURLException ============ {}", e.getMessage());
        } catch (IOException e) {
            log.info("IOException ============== {} ", e.getMessage());
        }
        
        return fieldValid;
	}
	/**
	 * 破解shape文件
	 * @param fileName
	 */
	public void parse(String fileName) {
//		try {
//			File file = new File(fileName);
//			FileDataStore myData = FileDataStoreFinder.getDataStore(file);
//			SimpleFeatureSource source = myData.getFeatureSource();
//			SimpleFeatureType schema = source.getSchema();
//	
//			Query query = new Query(schema.getTypeName());
//			// query.setMaxFeatures(1);
//	
//			FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(query);
//			try (FeatureIterator<SimpleFeature> features = collection.features()) {
//				while (features.hasNext()) {
//					SimpleFeature feature = features.next();
//					log.info(feature.getID() + ": ");
//					for (Property attribute : feature.getProperties()) {
//						if (attribute.getType() instanceof GeometryTypeImpl) {
//							log.info("\t\t" + attribute.getName() + ":" + attribute.getValue());
//						} else {
//							log.info("\t" + attribute.getName() + ":" + attribute.getValue());
//						}
//					}
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
}
