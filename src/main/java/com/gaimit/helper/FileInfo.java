package com.gaimit.helper;

/**
 * 占쎈씜嚥≪뮆諭� 占쎈쭆 占쎈솁占쎌뵬占쎌벥 占쎌젟癰귣�占쏙옙 占쏙옙占쎌삢占쎈릭疫뀐옙 占쎌맄占쎈립 JavaBeans
 * - 占쎌뵠 占쎄깻占쎌삋占쎈뮞占쎌벥 揶쏆빘猿쒎첎占� 占쎈씜嚥≪뮆諭� 占쎈쭆 占쎈솁占쎌뵬占쎌벥 占쎈땾 筌띾슦寃� 占쎄문占쎄쉐占쎈┷占쎈선 List 占쎌굨占쎄묶嚥∽옙 癰귣떯占쏙옙留귨옙�뼄.
 */
public class FileInfo {
	private String fieldName; 	// <input type="file">占쎌벥 name占쎈꺗占쎄쉐
	private String orginName; 	// 占쎌뜚癰귨옙 占쎈솁占쎌뵬 占쎌뵠�뵳占�
	private String fileDir; 	// 占쎈솁占쎌뵬占쎌뵠 占쏙옙占쎌삢占쎈┷占쎈선 占쎌뿳占쎈뮉 占쎄퐣甕곌쑴湲쏙옙�벥 野껋럥以�
	private String fileName; 	// 占쎄퐣甕곌쑴湲쏙옙�벥 占쎈솁占쎌뵬 占쎌뵠�뵳占�
	private String contentType; // 占쎈솁占쎌뵬占쎌벥 占쎌굨占쎈뻼
	private long fileSize; 		// 占쎈솁占쎌뵬占쎌벥 占쎌뒠占쎌쎗

	// getter + setter + toString() �빊遺쏙옙
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOrginName() {
		return orginName;
	}

	public void setOrginName(String orginName) {
		this.orginName = orginName;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileInfo [fieldName=" + fieldName + ", orginName=" + orginName + ", fileDir=" + fileDir + ", fileName="
				+ fileName + ", contentType=" + contentType + ", fileSize=" + fileSize + "]";
	}

}