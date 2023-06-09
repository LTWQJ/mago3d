package gaia3d.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gaia3d.domain.DataInfoLog;
import gaia3d.persistence.DataLogMapper;
import gaia3d.service.DataLogService;

/**
 * Data Change Request Log
 * @author jeongdae
 *
 */
@Service
public class DataLogServiceImpl implements DataLogService {

	@Autowired
	private DataLogMapper dataLogMapper;
	
	/**
	 * Data 更改请求数
	 * @param dataInfoLog
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getDataInfoLogTotalCount(DataInfoLog dataInfoLog) {
		return dataLogMapper.getDataInfoLogTotalCount(dataInfoLog);
	}
	
	/**
	 * 数据更改请求日志
	 * @param dataInfoLog
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DataInfoLog> getListDataInfoLog(DataInfoLog dataInfoLog) {
		return dataLogMapper.getListDataInfoLog(dataInfoLog);
	}
	
	/**
	 * data info log 조회
	 * @param dataInfoLogId
	 * @return
	 */
	@Transactional(readOnly=true)
	public DataInfoLog getDataInfoLog(Long dataInfoLogId) {
		return dataLogMapper.getDataInfoLog(dataInfoLogId);
	}
	
	/**
	 * 데이터 변경 이력 등록
	 * @param dataInfoLog
	 * @return
	 */
	@Transactional
	public int insertDataInfoLog(DataInfoLog dataInfoLog) {
		return dataLogMapper.insertDataInfoLog(dataInfoLog);
	}
}
