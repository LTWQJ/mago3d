package gaia3d.controller.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import gaia3d.config.PropertiesConfig;
import gaia3d.domain.uploaddata.UploadDataType;
import gaia3d.domain.FileType;
import gaia3d.domain.Key;
import gaia3d.domain.Policy;
import gaia3d.domain.uploaddata.UploadData;
import gaia3d.domain.uploaddata.UploadDataFile;
import gaia3d.domain.uploaddata.UploadDirectoryType;
import gaia3d.domain.UserSession;
import gaia3d.service.PolicyService;
import gaia3d.service.UploadDataService;
import gaia3d.utils.DateUtils;
import gaia3d.utils.FileUtils;
import gaia3d.utils.FormatUtils;

/**
 * 3D数据文件上传者
 * TODO 对于设计文件中的 texture ，无法重命名，因为设计文件可能会引用它。
 * @author jeongdae
 *
 */
@Slf4j
@RestController
@RequestMapping("/upload-datas")
public class UploadDataRestController {
	
	// 文件 copy 时的缓冲区大小
	public static final int BUFFER_SIZE = 8192;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private UploadDataService uploadDataService;
	
	/**
	 * TODO 需要异动处理
	 * data upload 处理
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping
	public Map<String, Object> insert(MultipartHttpServletRequest request) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		String dataType = request.getParameter("dataType");
		
		// converter 要转换的文件数
		int converterTargetCount = 0;
		
		Policy policy = policyService.getPolicy();
		// 这里不进行null检查是正确的。 不能有任何混乱
		// 可以上传的文件类型
		String[] uploadTypes = policy.getUserUploadType().toLowerCase().split(",");
		// 可转换文件类型
		String[] converterTypes = policy.getUserConverterType().split(",");
		List<String> uploadTypeList = Arrays.asList(uploadTypes);
		List<String> converterTypeList = Arrays.asList(converterTypes);

		errorCode = dataValidate(request);
		if(!StringUtils.isEmpty(errorCode)) return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);

		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		String userId = userSession.getUserId();
		List<UploadDataFile> uploadDataFileList = new ArrayList<>();
		Map<String, MultipartFile> fileMap = request.getFileMap();
//
		Map<String, Object> uploadMap = null;
		String today = DateUtils.getToday(FormatUtils.YEAR_MONTH_DAY_TIME14);

		// 1 创建文件夹
		String makedDirectory = FileUtils.makeDirectory(userId, UploadDirectoryType.YEAR_MONTH, propertiesConfig.getDataUploadDir());
		log.info("@@@@@@@ = {}", makedDirectory);
//
		// 2 虽然只有一件，但对于zip来说
		boolean isZipFile = false;
		int fileCount = fileMap.values().size();
//		如果文件数==1，执行下面语句
		if(fileCount == 1) {
			// processAsync(policy, userId, fileMap, makedDirectory);
			for (MultipartFile multipartFile : fileMap.values()) {
				String[] divideNames = multipartFile.getOriginalFilename().split("\\.");
				String fileExtension = divideNames[divideNames.length - 1];
				if(UploadData.ZIP_EXTENSION.equalsIgnoreCase(fileExtension)) {
					isZipFile = true;
					// zip 文件
					uploadMap = unzip(policy, uploadTypeList, converterTypeList, today, userId, multipartFile, makedDirectory, dataType);
					log.info("@@@@@@@ uploadMap = {}", uploadMap);

					// validation 체크
					if(uploadMap.containsKey("errorCode")) {
						errorCode = (String)uploadMap.get("errorCode");
						return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
					}

					// converter 要转换的文件数
					converterTargetCount = (Integer)uploadMap.get("converterTargetCount");
					if(converterTargetCount <= 0) {
						errorCode = "converter.target.count.invalid";
						return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
					}

					uploadDataFileList = (List<UploadDataFile>)uploadMap.get("uploadDataFileList");
				}
			}
		}
//
		if(!isZipFile) {
			//如果不是 zip 文件，则默认情况下必须将其放在一个文件夹中

			Map<String, String> fileNameMatchingMap = new HashMap<>();
			String tempDirectory = userId + "_" + System.nanoTime();
			// 将文件复制到 upload 目录
			FileUtils.makeDirectory(makedDirectory + tempDirectory);
			// 3 在其他情况下，递归复制文件
			for (MultipartFile multipartFile : fileMap.values()) {
				log.info("@@@@@@@@@@@@@@@ name = {}, originalName = {}", multipartFile.getName(), multipartFile.getOriginalFilename());

				UploadDataFile uploadDataFile = new UploadDataFile();
				Boolean converterTarget = false;

				// 检查文件默认 validation
				errorCode = fileValidate(policy, uploadTypeList, multipartFile);
				if(!StringUtils.isEmpty(errorCode)) {
					return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
				}

				String originalName = multipartFile.getOriginalFilename();
				String[] divideFileName = originalName.split("\\.");
    			String saveFileName = originalName;

    			// validation
    			if(divideFileName == null || divideFileName.length == 0) {
    				log.info("@@@@@@@@@@@@ upload.file.type.invalid. originalName = {}", originalName);
    				errorCode = "upload.file.type.invalid";
    				return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
    			}

    			String extension = divideFileName[divideFileName.length - 1];
    			// !extList.contains(extension.toLowerCase())
				if(UploadData.ZIP_EXTENSION.equalsIgnoreCase(extension) || !uploadTypeList.contains(extension.toLowerCase())) {
					log.info("@@@@@@@@@@@@ upload.file.type.invalid. originalName = {}", originalName);
					errorCode = "upload.file.type.invalid";
					return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
				}

				// 如果存在匹配的文件名，请按原样使用
				String searchfileNameKey = originalName.substring(0, originalName.length() - extension.length() - 1);
				String sameFileName = fileNameMatchingMap.get(searchfileNameKey);
				// 要转换的文件
				if(converterTypeList.contains(extension.toLowerCase())) {
					if(!dataType.equalsIgnoreCase(extension)) {
							// 数据类型和上传文件扩展名不相等，
							if(	UploadDataType.CITYGML == UploadDataType.findBy(dataType)
									&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)){
								// 数据类型为 citygml，如果扩展名为 gml，则通过
							} else if(UploadDataType.INDOORGML == UploadDataType.findBy(dataType)
									&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
								// 据类型为 indoorgml，如果扩展名为 gml，则通过
							} else {
								// 全部例外
								log.info("@@@@@@@@@@@@ datatype = {}, extension = {}", dataType, extension);
								errorCode = "upload.file.type.invalid";
								return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
							}
					}
//代码运行时注释=====》ltw
					if(UploadDataType.CITYGML == UploadDataType.findBy(dataType) && UploadDataType.INDOORGML == UploadDataType.findBy(extension)) {
						// 全部例外
						log.info("@@@@@@@@@@@@ 数据类型不同时. datatype = {}, extension = {}", dataType, extension);
						errorCode = "file.ext.invalid";
						return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
					}

					if (UploadDataType.CITYGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
						extension = UploadDataType.CITYGML.getValue();
					} else if (UploadDataType.INDOORGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
						extension = UploadDataType.INDOORGML.getValue();
					}

					// 仅重命名要转换的文件，并保留其余文件的名称
					saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;

					converterTarget = true;
					converterTargetCount++;
				}

				long size = 0L;
				try (	InputStream inputStream = multipartFile.getInputStream();
						OutputStream outputStream = new FileOutputStream(makedDirectory + tempDirectory + File.separator + saveFileName)) {

					int bytesRead = 0;
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
						size += bytesRead;
						outputStream.write(buffer, 0, bytesRead);
					}

					uploadDataFile.setFileType(FileType.FILE.name());
					uploadDataFile.setFileExt(extension);
        			uploadDataFile.setFileName(multipartFile.getOriginalFilename());
        			uploadDataFile.setFileRealName(saveFileName);
        			uploadDataFile.setFilePath(makedDirectory + tempDirectory + File.separator);
        			uploadDataFile.setFileSubPath(tempDirectory);
        			uploadDataFile.setFileSize(String.valueOf(size));
        			uploadDataFile.setConverterTarget(converterTarget);
        			uploadDataFile.setDepth(1);
				} catch(IOException e) {
					log.info("@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
					return getResultMap(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), "io.exception", message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				} catch(Exception e) {
					log.info("@@@@@@@@@@@@ file copy exception.");
					return getResultMap(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), "file.copy.exception", message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
				}

				uploadDataFileList.add(uploadDataFile);
			}
		}
//============数据转换数判断----问题就出现在这个位置----注释掉这段代码数据可入库==》ltw--2023.5.11
//		if(converterTargetCount <= 0) {
//			log.info("@@@@@@@@@@@@ converterTargetCount = {}", converterTargetCount);
//			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
//			result.put("errorCode", "converter.target.count.invalid");
//			result.put("message", message);
//
//			return getResultMap(result, HttpStatus.BAD_REQUEST.value(), errorCode, message);
//		}
//================================================================================================
		UploadData uploadData = new UploadData();
		uploadData.setDataName(request.getParameter("dataName"));
		uploadData.setDataGroupId(Integer.valueOf(request.getParameter("dataGroupId")));
		uploadData.setSharing(request.getParameter("sharing"));
		uploadData.setDataType(dataType);
		uploadData.setUserId(userId);
		uploadData.setHeightReference(request.getParameter("heightReference"));
		// 如果是citygml，从converter中自动提取
		if(	UploadDataType.CITYGML != UploadDataType.findBy(dataType) && UploadDataType.LAS != UploadDataType.findBy(dataType)) {
			uploadData.setLongitude(new BigDecimal(request.getParameter("longitude")) );
			uploadData.setLatitude(new BigDecimal(request.getParameter("latitude")) );
			uploadData.setAltitude(new BigDecimal(request.getParameter("altitude")) );
			uploadData.setLocation("POINT(" + request.getParameter("longitude") + " " + request.getParameter("latitude") + ")");
		}
		
		uploadData.setFileCount(uploadDataFileList.size());
		uploadData.setConverterTargetCount(converterTargetCount);
		uploadData.setDescription(request.getParameter("description"));
		
		log.info("@@@@@@@@@@@@ uploadData = {}", uploadData);
		System.out.println("测试");
//		上述过程走完，将赋好值的uploadData,uploadDataFileList传入数据库操作函数
		uploadDataService.insertUploadData(uploadData, uploadDataFileList);
		int statusCode = HttpStatus.OK.value();
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	/**
	 * 解压缩上传文件
	 * @param policy
	 * @param uploadTypeList
	 * @param converterTypeList
	 * @param today
	 * @param userId
	 * @param multipartFile
	 * @param targetDirectory
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> unzip(	Policy policy, 
										List<String> uploadTypeList, 
										List<String> converterTypeList, 
										String today, 
										String userId, 
										MultipartFile multipartFile, 
										String targetDirectory,
										String dataType) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		// converter 要转换的文件数
		int converterTargetCount = 0;
		
		String errorCode = fileValidate(policy, uploadTypeList, multipartFile);
		if(!StringUtils.isEmpty(errorCode)) {
			result.put("errorCode", errorCode);
			return result;
		}
		
		// input directory 생성
		targetDirectory = targetDirectory + userId + "_" + System.nanoTime() + File.separator;
		FileUtils.makeDirectory(targetDirectory);
		
		File uploadedFile = new File(targetDirectory + multipartFile.getOriginalFilename());
		multipartFile.transferTo(uploadedFile);
		
		Map<String, String> fileNameMatchingMap = new HashMap<>();
		List<UploadDataFile> uploadDataFileList = new ArrayList<>();
		// zip 파일을 압축할때 한글이나 다국어가 포함된 경우 java.lang.IllegalArgumentException: malformed input off 같은 오류가 발생. 윈도우가 CP949 인코딩으로 파일명을 저장하기 때문.
		// Charset CP949 = Charset.forName("UTF-8");
//		try ( ZipFile zipFile = new ZipFile(uploadedFile, CP949);) {
		try ( ZipFile zipFile = new ZipFile(uploadedFile);) {
			String directoryPath = targetDirectory;
			String subDirectoryPath = "";
			String directoryName = null;
			int depth = 1;
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			
			while( entries.hasMoreElements() ) {
            	UploadDataFile uploadDataFile = new UploadDataFile();
            	
            	ZipEntry entry = entries.nextElement();
            	String unzipfileName = targetDirectory + entry.getName();
            	Boolean converterTarget = false;
            	
            	if( entry.isDirectory() ) {
            		// 디렉토리인 경우
            		uploadDataFile.setFileType(FileType.DIRECTORY.name());
            		if(directoryName == null) {
            			uploadDataFile.setFileName(entry.getName());
            			uploadDataFile.setFileRealName(entry.getName());
            			directoryName = entry.getName();
            			directoryPath = directoryPath + directoryName;
            			//subDirectoryPath = directoryName;
            		} else {
            			String fileName = null;
            			if(entry.getName().indexOf(directoryName) >=0) {
            				fileName = entry.getName().substring(entry.getName().indexOf(directoryName) + directoryName.length());  
            			} else {
            				// 이전이 디렉토리, 현재도 디렉토리인데 서로 다른 디렉토리
            				if(directoryPath.indexOf(directoryName) >=0) {
            					directoryPath = directoryPath.replace(directoryName, "");
            					directoryName = null;
            				}
            				fileName = entry.getName();
            			}
            			uploadDataFile.setFileName(fileName);
            			uploadDataFile.setFileRealName(fileName);
            			directoryName = fileName;
            			directoryPath = directoryPath + fileName;
            			subDirectoryPath = fileName;
            		}
            		
                	File file = new File(unzipfileName);
                    file.mkdirs();
                    uploadDataFile.setFilePath(directoryPath);
                    uploadDataFile.setFileSubPath(subDirectoryPath);
                    uploadDataFile.setDepth(depth);
                    depth++;
            	} else {
            		// 파일인 경우
            		String fileName = null;
            		String extension = null;
            		String[] divideFileName = null;
            		String saveFileName = null;
            		
            		// TODO zip 파일도 확장자 validation 체크를 해야 함
            		if(directoryName == null) {
            			fileName = entry.getName();
            			divideFileName = fileName.split("\\.");
            			saveFileName = fileName;
            			if(divideFileName != null && divideFileName.length != 0) {
            				extension = divideFileName[divideFileName.length - 1];
            				if(uploadTypeList.contains(extension.toLowerCase())) {
            					
            					String searchfileNameKey = fileName.substring(0, fileName.length() - extension.length() - 1);
        						String sameFileName = fileNameMatchingMap.get(searchfileNameKey);
            					if(converterTypeList.contains(extension.toLowerCase())) {
            						if(!dataType.equalsIgnoreCase(extension)) {
                						// 데이터 타입과 업로딩 파일 확장자가 같지 않고
                						if(	UploadDataType.CITYGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)){
                							// 데이터 타입은 citygml 인데 확장자는 gml 인 경우 통과
                						} else if(UploadDataType.INDOORGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                							// 데이터 타입은 indoorgml 인데 확장자는 gml 인 경우 통과
                						} else {
                							// 전부 예외
                							log.info("@@@@@@@@@@@@ datatype = {}, extension = {}", dataType, extension);
                							result.put("errorCode", "file.ext.invalid");
                							return result;
                						}
                					}
            						
            						if(UploadDataType.CITYGML == UploadDataType.findBy(dataType) && UploadDataType.INDOORGML == UploadDataType.findBy(extension)) {
            							// 전부 예외
            							log.info("@@@@@@@@@@@@ 데이터 타입이 다른 경우. datatype = {}, extension = {}", dataType, extension);
            							result.put("errorCode", "file.ext.invalid");
            							return result;
            						}
            						
            						if (UploadDataType.CITYGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.CITYGML.getValue();
                					} else if (UploadDataType.INDOORGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.INDOORGML.getValue();
                					}
            						
            						// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
            						saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
            						converterTarget = true;
									converterTargetCount++;
            					}
	        				}
            			}
            		} else {
            			if(entry.getName().indexOf(directoryName) >= 0) {
            				// 디렉토리내 파일의 경우
            				fileName = entry.getName().substring(entry.getName().indexOf(directoryName) + directoryName.length());  
            			} else {
            				fileName = entry.getName();
            				if(directoryPath.indexOf(directoryName) >= 0) {
            					directoryPath = directoryPath.replace(directoryName, "");
            					directoryName = null;
            				}
            			}
            			divideFileName = fileName.split("\\.");
            			saveFileName = fileName;
            			if(divideFileName != null && divideFileName.length != 0) {
            				extension = divideFileName[divideFileName.length - 1];
            				if(uploadTypeList.contains(extension.toLowerCase())) {
            					
            					String searchfileNameKey = fileName.substring(0, fileName.length() - extension.length() - 1);
        						String sameFileName = fileNameMatchingMap.get(searchfileNameKey);
            					if(converterTypeList.contains(extension.toLowerCase())) {
            						if(!dataType.equalsIgnoreCase(extension)) {
                						// 데이터 타입과 업로딩 파일 확장자가 같지 않고
                						if(	UploadDataType.CITYGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)){
                							// 데이터 타입은 citygml 인데 확장자는 gml 인 경우 통과
                						} else if(UploadDataType.INDOORGML == UploadDataType.findBy(dataType)
                								&& UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                							// 데이터 타입은 indoorgml 인데 확장자는 gml 인 경우 통과
                						} else {
                							// 전부 예외
                							log.info("@@@@@@@@@@@@ datatype = {}, extension = {}", dataType, extension);
                							result.put("errorCode", "file.ext.invalid");
                							return result;
                						}
                					}
            						
            						if(UploadDataType.CITYGML == UploadDataType.findBy(dataType) && UploadDataType.INDOORGML == UploadDataType.findBy(extension)) {
            							// 전부 예외
            							log.info("@@@@@@@@@@@@ 데이터 타입이 다른 경우. datatype = {}, extension = {}", dataType, extension);
            							result.put("errorCode", "file.ext.invalid");
            							return result;
            						}
            						
            						if (UploadDataType.CITYGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.CITYGML.getValue();
                					} else if (UploadDataType.INDOORGML.getValue().equalsIgnoreCase(dataType) && UploadDataType.GML.getValue().equalsIgnoreCase(extension)) {
                						extension = UploadDataType.INDOORGML.getValue();
                					}
            						
            						// 변환 대상 파일만 이름을 변경하고 나머지 파일은 그대로 이름 유지
            						saveFileName = userId + "_" + today + "_" + System.nanoTime() + "." + extension;
									converterTarget = true;
									converterTargetCount++;
            					}
	        				} else {
	        					// 예외 처리
	        					log.info("@@ file.ext.invalid. extList = {}, extension = {}", uploadTypeList, fileName);
	        					result.put("errorCode", "file.ext.invalid");
	        					return result;
	        				}
            			}
            		}	
            		uploadDataFile = fileCopyInUnzip(uploadDataFile, zipFile, entry, directoryPath, saveFileName, extension, fileName, subDirectoryPath, depth);
                }
            	
            	uploadDataFile.setConverterTarget(converterTarget);
            	uploadDataFile.setFileSize(String.valueOf(entry.getSize()));
            	uploadDataFileList.add(uploadDataFile);
            }
		} catch(RuntimeException ex) {
			log.info("@@@@@@@@@@@@ RuntimeException. message = {}", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
		} catch(IOException ex) {
			log.info("@@@@@@@@@@@@ IOException. message = {}", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
		}
		
		result.put("converterTargetCount", converterTargetCount);
		result.put("uploadDataFileList", uploadDataFileList);
		return result;
	}
	
	/*
	 * unzip 从逻辑复制文件
	 */
	private UploadDataFile fileCopyInUnzip(UploadDataFile uploadDataFile, ZipFile zipFile, ZipEntry entry, String directoryPath, String saveFileName,
									String extension, String fileName, String subDirectoryPath, int depth) {
		long size = 0L;
    	try ( 	InputStream inputStream = zipFile.getInputStream(entry);
    			FileOutputStream outputStream = new FileOutputStream(directoryPath + saveFileName); ) {
    		
    		int bytesRead = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                size += bytesRead;
                outputStream.write(buffer, 0, bytesRead);
            }
            
    		uploadDataFile.setFileType(FileType.FILE.name());
    		uploadDataFile.setFileExt(extension);
    		uploadDataFile.setFileName(fileName);
    		uploadDataFile.setFileRealName(saveFileName);
    		uploadDataFile.setFilePath(directoryPath);
    		uploadDataFile.setFileSubPath(subDirectoryPath);
    		uploadDataFile.setDepth(depth);
    		uploadDataFile.setFileSize(String.valueOf(size));
    	
    	} catch(IOException e) {
    		e.printStackTrace();
    		log.info("@@@@@@@@@@@@ io exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
    		uploadDataFile.setErrorMessage(e.getMessage());
        } catch(Exception e) {
        	e.printStackTrace();
        	log.info("@@@@@@@@@@@@ exception. message = {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        	uploadDataFile.setErrorMessage(e.getMessage());
        }
    	
    	return uploadDataFile;
	}
	
	/**
	 * @param policy
	 * @param multipartFile
	 * @return
	 */
	private static String fileValidate(Policy policy, List<String> extList, MultipartFile multipartFile) {
		
		// 2 文件名
		String fileName = multipartFile.getOriginalFilename();
		if(fileName == null) {
			log.info("@@ fileName is null");
			return "file.name.invalid";
		} else if(fileName.indexOf("..") >= 0 || fileName.indexOf("/") >= 0) {
			// TODO File.seperator 정규 표현식이 안 먹혀서 이렇게 처리함
			log.info("@@ fileName = {}", fileName);
			return "file.name.invalid";
		}
		
		// 3 파일 확장자
		String[] fileNameValues = fileName.split("\\.");
//		if(fileNameValues.length != 2) {
//			log.info("@@ fileNameValues.length = {}, fileName = {}", fileNameValues.length, fileName);
//			uploadLog.setError_code("fileinfo.name.invalid");
//			return uploadLog;
//		}
//		if(fileNameValues[0].indexOf(".") >= 0 || fileNameValues[0].indexOf("..") >= 0) {
//			log.info("@@ fileNameValues[0] = {}", fileNameValues[0]);
//			uploadLog.setError_code("fileinfo.name.invalid");
//			return uploadLog;
//		}
		// LowerCase로 비교
		String extension = fileNameValues[fileNameValues.length - 1];
		
		if(!extList.contains(extension.toLowerCase())) {
			log.info("@@ extList = {}, extension = {}", extList, extension);
			return "file.ext.invalid";
		}
		
		// 4 파일 사이즈
		// TODO 파일은 사이즈가 커서 제한을 해야 할지 의문?
		long fileSize = multipartFile.getSize();
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@ user upload file size = {} KB", (fileSize / 1000));
		if( fileSize > (policy.getUserUploadMaxFilesize() * 1000000l)) {
			log.info("@@ fileSize = {}, user upload max filesize = {} M", (fileSize / 1000), policy.getUserUploadMaxFilesize());
			return "file.size.invalid";
		}
		
		return null;
	}
	
	private Map<String, Object> getResultMap(Map<String, Object> result, int statusCode, String errorCode, String message) {
		log.info("@@@@@@@@@@@@ errorCode = {}", errorCode);
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
        return result;
	}
	
	/**
	 * 修改上传日期
	 * @param request
	 * @param uploadData
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/{uploadDataId:[0-9]+}")
	public Map<String, Object> update(HttpServletRequest request, @PathVariable Long uploadDataId, @Valid UploadData uploadData, BindingResult bindingResult) {
		log.info("@@ uploadData = {}", uploadData);
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		if(bindingResult.hasErrors()) {
			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
			log.info("@@@@@ message = {}", message);
			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
			result.put("errorCode", errorCode);
			result.put("message", message);
            return result;
		}
		
		if(StringUtils.isEmpty(uploadData.getDataName())) {
			errorCode = "data.name.empty";
		}
		if(StringUtils.isEmpty(uploadData.getDataGroupId())) {
			errorCode = "data.group.id.empty";
		}
		if(StringUtils.isEmpty(uploadData.getSharing())) {
			errorCode = "data.sharing.empty";
		}
		if(StringUtils.isEmpty(uploadData.getDataType())) {
			errorCode = "data.type.empty";
		}
		
		// TODO citygml, indoorgml 의 경우 위도, 경도, 높이를 포함하고 있어서 validation 체크를 하지 않음
		// 지금은 converter 가 update를 해 주지 않아서 기본 체크 함
//			if(!dataType.equals(DataType.CITYGML.getValue()) && !dataType.equals(DataType.INDOORGML.getValue())) {
		if(uploadData.getLongitude() == null) {
			errorCode = "data.longitude.empty";
		}
		if(uploadData.getLatitude() == null) {
			errorCode = "data.latitude.empty";
		}
		if(uploadData.getAltitude() == null) {
			errorCode = "data.altitude.empty";
		}
//			}
		
		if(!StringUtils.isEmpty(errorCode)) {
			log.info("@@@@@ errorCode = {}", errorCode);
			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
			result.put("errorCode", errorCode);
			result.put("message", message);
            return result;
		}
	
		uploadData.setLocation("POINT(" + uploadData.getLongitude() + " " + uploadData.getLatitude() + ")");
		//uploadDataService.updateUploadData(uploadData);
		
		//如果源是 gml 文件，则更新数据库，如果将数据类型更改为 citygmlindoorgml，则更改上载路径的文件扩展名。
		// 更新数据库并更改文件扩展名
		uploadDataService.updateUploadDataAndFile(uploadData);
		int statusCode = HttpStatus.OK.value();
			
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}

	/**
	 * 删除选择 upload-data
	 * @param request
	 * @param uploadDataId
	 * @return
	 */
	@DeleteMapping(value = "/{uploadDataId:[0-9]+}")
	public Map<String, Object> deleteDatas(HttpServletRequest request, @PathVariable Long uploadDataId) {
		log.info("@@@@@@@ uploadDataId = {}", uploadDataId);
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		UploadData uploadData = new UploadData();
		//uploadData.setUserId(userSession.getUserId());
		uploadData.setUploadDataId(uploadDataId);
		
		uploadDataService.deleteUploadData(uploadData);
		int statusCode = HttpStatus.OK.value();
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
	
	/**
	 * validation 체크
	 * @param request
	 * @return
	 */
	private String dataValidate(MultipartHttpServletRequest request) {
		if(StringUtils.isEmpty(request.getParameter("dataName"))) {
			return "data.name.empty";
		}
		if(StringUtils.isEmpty(request.getParameter("dataGroupId"))) {
			return "data.group.id.empty";
		}
		if(StringUtils.isEmpty(request.getParameter("sharing"))) {
			return "data.sharing.empty";
		}
		
		String dataType = request.getParameter("dataType");
		if(StringUtils.isEmpty(dataType)) {
			return "data.type.empty";
		}
		
		if(	UploadDataType.CITYGML != UploadDataType.findBy(dataType) && UploadDataType.LAS != UploadDataType.findBy(dataType)) {
			if(StringUtils.isEmpty(request.getParameter("longitude"))) {
				return "data.longitude.empty";
			}
			if(StringUtils.isEmpty(request.getParameter("latitude"))) {
				return "data.latitude.empty";
			}
			if(StringUtils.isEmpty(request.getParameter("altitude"))) {
				return "data.altitude.empty";
			}
		}
		
		Map<String, MultipartFile> fileMap = request.getFileMap();
		if(fileMap.isEmpty()) {
			return "data.file.empty";
		}
		
		return null;
	}
}
