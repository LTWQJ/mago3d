package gaia3d.persistence;

import gaia3d.domain.uploaddata.UploadData;
import gaia3d.domain.uploaddata.UploadDataFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadDataMapper {

	/**
	 * 上传数据总数
	 * @param uploadData
	 * @return
	 */
	Long getUploadDataTotalCount(UploadData uploadData);
	
	/**
	 * 上传数据列表
	 * @param uploadData
	 * @return
	 */
	List<UploadData> getListUploadData(UploadData uploadData);
	
	/**
	 * 上传信息
	 * @param uploadData
	 * @return
	 */
	UploadData getUploadData(UploadData uploadData);
	
//	/**
//	 * 업로딩 데이터 파일 총 건수
//	 * @param uploadDataFile
//	 * @return
//	 */
//	Long getUploadDataFileTotalCount(UploadDataFile uploadDataFile);
//
//	/**
//	 * 업로딩 데이터 파일 총 용량
//	 * @param uploadDataFile
//	 * @return
//	 */
//	Long getUploadDataFileTotalSize(UploadDataFile uploadDataFile);
	
	/**
	 * 업로딩 데이터 파일 목록
	 * @param uploadData
	 * @return
	 */
	List<UploadDataFile> getListUploadDataFile(UploadData uploadData);

	/**
	 * 업로딩 데이터 파일
	 * @param uploadDataFile
	 * @return	업로딩 데이터 파일
	 */
	UploadDataFile getUploadDataFile(UploadDataFile uploadDataFile);

	/**
	 * 업로드 데이터 타입 집계
	 * @return
	 */
	List<UploadData> getUploadDataType();
	
	/**
	 * 사용자 파일 정보 업로딩
	 * @param uploadData
	 * @return
	 */
	int insertUploadData(UploadData uploadData);
	
	/**
	 * 사용자 파일 업로딩
	 * @param uploadDataFile
	 * @return
	 */
	int insertUploadDataFile(UploadDataFile uploadDataFile);
	
	/**
	 * 修改用户文件信息
	 * @param uploadData
	 * @return
	 */
	int updateUploadData(UploadData uploadData);
	
	/**
	 * 사용자 파일 업로딩 수정
	 * @param uploadDataFile
	 * @return
	 */
	int updateUploadDataFile(UploadDataFile uploadDataFile);

	/**
	 * 업로딩 데이터 정보 삭제
	 * @param uploadData
	 * @return
	 */
	int deleteUploadData(UploadData uploadData);
	
	/**
	 * 업로딩 데이터 파일 삭제
	 * @param uploadData
	 * @return
	 */
	int deleteUploadDataFile(UploadData uploadData);
}
