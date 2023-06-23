package gaia3d.domain.uploaddata;

/**
 * 3DS 不能直接用作 enum
 * @author Jeongdae
 *
 */
public enum UploadDataType {
	AUTODESK_3DS("3ds"),
	OBJ("obj"),
	DAE("dae"),
	COLLADA("collada"),
	IFC("ifc"),
	LAS("las"),
	GML("gml"),
	CITYGML("citygml"),
	INDOORGML("indoorgml"),
	
	JPG("jpg"),
	JPEG("jpeg"),
	GIF("gif"),
	PNG("png"),
	BMP("bmp"),
	DDS("dds"),
	
	ZIP("zip"),
	MTL("mtl"),
	FBX("fbx");
	
	private final String value;
	
	UploadDataType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	/**
	 * TODO values for loop 转换为
	 * @param value
	 * @return
	 */
	public static UploadDataType findBy(String value) {
		for(UploadDataType uploadDataType : values()) {
			if(uploadDataType.getValue().equals(value)) return uploadDataType;
		}
		return null;
	}
}
