package gaia3d.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import gaia3d.domain.DataFileInfo;
import gaia3d.domain.DataFileParseLog;
import gaia3d.domain.DataGroup;
import gaia3d.domain.DataInfo;
import gaia3d.domain.DataInfoLog;
import gaia3d.domain.DataInfoSimple;
import gaia3d.domain.DataStatus;
import gaia3d.domain.MethodType;
import gaia3d.domain.ServerTarget;
import gaia3d.domain.SharingType;
import gaia3d.parser.DataFileParser;
import gaia3d.parser.impl.DataFileJsonParser;
import gaia3d.persistence.DataMapper;
import gaia3d.service.DataGroupService;
import gaia3d.service.DataLogService;
import gaia3d.service.DataService;

/**
 * Data
 * @author jeongdae
 *
 */
@Slf4j
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DataMapper dataMapper;
	
	@Autowired
	private DataGroupService dataGroupService;
	
	@Autowired
	private DataLogService dataLogService;
	
	/**
	 * Data 수
	 * @param dataInfo
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getDataTotalCount(DataInfo dataInfo) {
		return dataMapper.getDataTotalCount(dataInfo);
	}
	
	/**
	 * 데이터 상태별 통계 정보
	 * @param status
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getDataTotalCountByStatus(String status) {
		return dataMapper.getDataTotalCountByStatus(status);
	}
	
	/**
	 * Data 목록
	 * @param dataInfo
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DataInfo> getListData(DataInfo dataInfo) {
		System.out.println("============shuju============================");
		System.out.println(dataMapper.getListData(dataInfo));
		System.out.println("============shuju============================");
		return dataMapper.getListData(dataInfo);
	}
	
	/**
	 * 데이터 그룹에 포함되는 모든 데이터를 취득
	 * @param dataGroupId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DataInfoSimple> getListAllDataByDataGroupId(Integer dataGroupId) {
		return dataMapper.getListAllDataByDataGroupId(dataGroupId);
	}
	
	/**
	 * 获取数据信息
	 * @param dataInfo
	 * @return
	 */
	@Transactional(readOnly=true)
	public DataInfo getData(DataInfo dataInfo) {
		return dataMapper.getData(dataInfo);
	}
	
	/**
	 * Data 정보 취득
	 * @param dataInfo
	 * @return
	 */
	@Transactional(readOnly=true)
	public DataInfo getDataByDataKey(DataInfo dataInfo) {
		return dataMapper.getDataByDataKey(dataInfo);
	}
	
	/**
	 * 최상위 root dataInfo 정보 취득
	 * @param dataGroupId
	 * @return
	 */
	@Transactional(readOnly=true)
	public DataInfo getRootDataByDataGroupId(Integer dataGroupId) {
		return dataMapper.getRootDataByDataGroupId(dataGroupId);
	}
	
	/**
	 * Data 정보 취득
	 * @param dataInfo
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DataInfo> getDataByConverterJob(DataInfo dataInfo) {
		return dataMapper.getDataByConverterJob(dataInfo);
	}

	/**
	 * 데이터 현황
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DataInfo> getDataTypeCount() {
		return dataMapper.getDataTypeCount();
	}
	
	/**
	 * Data 注册
	 * @param dataInfo
	 * @return
	 */
	@Transactional
	public int insertData(DataInfo dataInfo) {
		dataMapper.insertData(dataInfo);
		DataInfoLog dataInfoLog = new DataInfoLog(dataInfo);
		dataInfoLog.setChangeType(MethodType.INSERT.name().toLowerCase());
		return dataLogService.insertDataInfoLog(dataInfoLog);
	}
	
	/**
	 * Data 수정
	 * @param dataInfo
	 * @return
	 */
	@Transactional
	public int updateData(DataInfo dataInfo) {
		// TODO 환경 설정 값을 읽어 와서 update 할 건지, delete 할건지 분기를 타야 함
		dataMapper.updateData(dataInfo);
		dataInfo = dataMapper.getData(dataInfo);
		DataInfoLog dataInfoLog = new DataInfoLog(dataInfo);
		dataInfoLog.setChangeType(MethodType.UPDATE.name().toLowerCase());
		dataInfoLog.setUpdateUserId(dataInfo.getUserId());
		return dataLogService.insertDataInfoLog(dataInfoLog);
	}
	
	/**
	 * data bulk登记
	 * @param dataFileInfo
	 * @return
	 */
	@Transactional
	public DataFileInfo upsertBulkData(DataFileInfo dataFileInfo) {
		Integer dataGroupId = dataFileInfo.getDataGroupId();
		String userId = dataFileInfo.getUserId();
		
		// 保存文件历史
		dataMapper.insertDataFileInfo(dataFileInfo);
		//跳转到DataFileParser接口--问题调试
		DataFileParser dataFileParser = new DataFileJsonParser();
		Map<String, Object> map = dataFileParser.parse(dataGroupId, dataFileInfo);
		
		DataGroup dataGroup = DataGroup.builder().dataGroupId(dataGroupId).build();
		dataGroup = dataGroupService.getDataGroup(dataGroup);
		
		@SuppressWarnings("unchecked")
//				从map中拿到dataInfoList数据
		List<DataInfo> dataInfoList = (List<DataInfo>) map.get("dataInfoList");
		
		DataFileParseLog dataFileParseLog = new DataFileParseLog();
		dataFileParseLog.setDataFileInfoId(dataFileInfo.getDataFileInfoId());
		dataFileParseLog.setLogType(DataFileParseLog.DB);
		
		BigDecimal firstLongitude = BigDecimal.ZERO;
		BigDecimal firstLatitude = BigDecimal.ZERO;
		BigDecimal firstAltitude = BigDecimal.ZERO;
		int insertSuccessCount = 0;
		int updateSuccessCount = 0;
		int insertErrorCount = 0;
		String dataGroupTarget = ServerTarget.ADMIN.name().toLowerCase();
		String sharing = SharingType.COMMON.name().toLowerCase();
		String status = DataStatus.USE.name().toLowerCase();
		int count = 0;
		for(DataInfo dataInfo : dataInfoList) {//出问题地方----datainfolist为空，导致语句执行不到for循环体中
			if(count == 0) {
				// 数据组的位置
				firstLongitude = dataInfo.getLongitude();
				firstLatitude = dataInfo.getLatitude();
				firstAltitude = dataInfo.getAltitude();
			}
			// TODO 계층 관련 코딩이 있어야 함
			try {
				dataInfo.setDataGroupId(dataGroupId);
				DataInfo dbDataInfo = dataMapper.getDataByDataKey(dataInfo);
				if(dbDataInfo == null) {
					dataInfo.setDataGroupTarget(dataGroupTarget);
					dataInfo.setSharing(sharing);
					dataInfo.setUserId(userId);
					dataInfo.setStatus(status);
					dataMapper.insertBulkData(dataInfo);//数据存不到data_info数据库--2023.5.10 ----主要观察这里--insertBulkData()函数无问题
					insertSuccessCount++;
				} else {
					dataInfo.setDataId(dbDataInfo.getDataId());
					dataMapper.updateData(dataInfo);
					updateSuccessCount++;
				}
			} catch(DataAccessException e) {
				log.info("@@@@@@@@@@@@ dataAccess exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				dataFileParseLog.setIdentifierValue(dataFileInfo.getUserId());
				dataFileParseLog.setErrorCode(e.getMessage());
				dataMapper.insertDataFileParseLog(dataFileParseLog);
				insertErrorCount++;
			} catch(RuntimeException e) {
				log.info("@@@@@@@@@@@@ runtime exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				dataFileParseLog.setIdentifierValue(dataFileInfo.getUserId());
				dataFileParseLog.setErrorCode(e.getMessage());
				dataMapper.insertDataFileParseLog(dataFileParseLog);
				insertErrorCount++;
			} catch(Exception e) {
				log.info("@@@@@@@@@@@@ exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				dataFileParseLog.setIdentifierValue(dataFileInfo.getUserId());
				dataFileParseLog.setErrorCode(e.getMessage());
				dataMapper.insertDataFileParseLog(dataFileParseLog);
				insertErrorCount++;
			}
			count++;
		}
//========调试时添加下列代码，数据为给定数据====>ltw---2023.5.12
        DataInfo dataInfo=new DataInfo();
		    dataInfo.setDataId(1L);
			dataInfo.setDataGroupTarget(dataGroupTarget);
			dataInfo.setSharing(sharing);
		    dataInfo.setDataGroupId(dataGroupId);
			dataInfo.setUserId(userId);
			dataInfo.setDataKey("11111");
			dataInfo.setStatus(status);
			dataMapper.insertBulkData(dataInfo);//数据存不到data_info数据库--2023.5.10 ----主要观察这里，证明insertBlukData函数无问题
			insertSuccessCount++;
//==========================================================================
		dataFileInfo.setTotalCount((Integer) map.get("totalCount"));
		dataFileInfo.setParseSuccessCount((Integer) map.get("parseSuccessCount"));
		dataFileInfo.setParseErrorCount((Integer) map.get("parseErrorCount"));
		dataFileInfo.setInsertSuccessCount(insertSuccessCount);
		dataFileInfo.setUpdateSuccessCount(updateSuccessCount);
		dataFileInfo.setInsertErrorCount(insertErrorCount);
		
		dataMapper.updateDataFileInfo(dataFileInfo);
		
		// data group update
		String dataGroupLocation = null;
		if(dataGroup.getLongitude() == null || dataGroup.getLongitude().longValue() <= 0) {
			dataGroupLocation = "POINT(" + firstLongitude + " " + firstLatitude + ")";
		}
		
		int dataCount = dataGroup.getDataCount() + insertSuccessCount;
		dataGroup = DataGroup.builder()
				.dataGroupId(dataGroupId)
				.dataCount(dataCount)
				.build();
		if(dataGroupLocation != null) {
			dataGroup.setLocation(dataGroupLocation);
			dataGroup.setAltitude(firstAltitude);
		}
		dataGroupService.updateDataGroup(dataGroup);
		
		return dataFileInfo;
	}
	
	/**
	 * 修改数据状态
	 * @param dataInfo
	 * @return
	 */
	@Transactional
	public int updateDataStatus(DataInfo dataInfo) {
		return dataMapper.updateDataStatus(dataInfo);
	}
	
	/**
	 * Data 삭제
	 * @param dataInfo
	 * @return
	 */
	@Transactional
	public int deleteData(DataInfo dataInfo) {
		// 데이터 그룹 count -1
		dataInfo = dataMapper.getData(dataInfo);
		
		DataGroup dataGroup = new DataGroup();
		dataGroup.setDataGroupId(dataInfo.getDataGroupId());
		dataGroup = dataGroupService.getDataGroup(dataGroup);
		
		DataGroup tempDataGroup = DataGroup.builder()
				.dataGroupId(dataGroup.getDataGroupId())
				.dataCount(dataGroup.getDataCount() - 1).build();
		dataGroupService.updateDataGroup(tempDataGroup);
		
		return dataMapper.deleteData(dataInfo);
		// TODO 디렉토리도 삭제 해야 함
	}
	
	/**
	 * 일괄 Data 삭제
	 * @param checkIds
	 * @return
	 */
	@Transactional
	public int deleteDataList(String userId, String checkIds) {
		// TODO sql in 으로 한번 query 가능 함. 수정해야 함
		
		String[] dataIds = checkIds.split(",");
		for(String dataId : dataIds) {
			DataInfo dataInfo = new DataInfo();
			dataInfo.setUserId(userId);
			dataInfo.setDataId(Long.valueOf(dataId));
			return dataMapper.deleteData(dataInfo);
		}
		
		return checkIds.length();
	}
	
	/**
	 * Data 에 속하는 모든 Object ID를 삭제
	 * @param dataId
	 * @return
	 */
	@Transactional
	public int deleteDataObjects(Long dataId) {
		return dataMapper.deleteDataObjects(dataId);
	}
	
	/**
	 * Data 삭제
	 * @param dataInfo
	 * @return
	 */
	@Transactional
	public int deleteDataByConverterJob(DataInfo dataInfo) {
		return dataMapper.deleteDataByConverterJob(dataInfo);
	}
	
	/**
	 * user data 삭제
	 */
	@Transactional
	public int deleteDataByUserId(String userId) {
		return dataMapper.deleteDataByUserId(userId);
	}
}
