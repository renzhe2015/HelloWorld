package com.cifpay.cifpaylib.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件 工具栏
 * 
 * @author chenkb
 * 
 */
public class FileUtils {

	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;

	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/**
	 * The file copy buffer size (10 MB) （原为30MB，为更适合在手机上使用，将其改为10MB，
	 */
	private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 10;


	public static String[] getAllAssertFileName(Context context) {
		String[] files = null;
		try {
			files = context.getAssets().list("");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

	/**
	 * 返回 后缀名 为 。rule 的文件 名
	 * 
	 * @param files
	 *            要匹配的文件
	 * @param rule
	 *            匹配规则
	 * @return 返回 后缀名 为 。rule 的文件 名
	 */
	public static List<String> getFiles(String[] files, String rule) {

		List<String> ruleFiles = new ArrayList<String>();

		for (String file : files) {
			if (file.endsWith(rule)) {
				ruleFiles.add(file);
			}
		}
		return ruleFiles;
	}

	/**
	 * 
	 * 输入 要检查 的文件名列表 检查 zip 文件 是否解压到 目标目录
	 * 
	 * @param files
	 *            检查的zip 文件
	 * @param toPath
	 *            解压的目录路径
	 * @return 是否解压到 目标文件
	 * @throws FileNotFoundException
	 */

	public static boolean isFileDecompression(String[] files, String toPath)
			throws FileNotFoundException {
		return isFileDecompression(files, toPath, ".zip");
	}

	/**
	 * 
	 * 输入 要检查 的文件名列表 检查 zip 文件 是否解压到 目标目录
	 * 
	 * @param files
	 *            检查的zip 文件
	 * @param toPath
	 *            解压的目录路径
	 * @param rule
	 *            要检测文件的后缀名 可以自定义 不一定是 。zip
	 * @return 是否解压到 目标文件
	 * @throws FileNotFoundException
	 */
	public static boolean isFileDecompression(String[] files, String toPath,
			String rule) throws FileNotFoundException {
		boolean result = true;

		if (files.length == 0)
			throw new FileNotFoundException();

		List<String> zipFiles = getFiles(files, rule);

		for (String zipFile : zipFiles) {
			String dbFile = zipFile.replace(rule, ".db");
			File file = new File(toPath + File.separator + dbFile);

			if (!file.exists()) {
				result = false;
			}

		}

		return result;

	}

	/**
	 * 拷贝 指定 的 单个asset 文件到指定目录
	 * 
	 * @param context
	 * @param assetsFileNmae
	 * @param outDirName
	 * @throws IOException
	 */
	public static void copyAssetsFile(Context context, String assetsFileNmae,
			String outDirName) throws IOException {
		// 判断目录是否存在。如不存在则创建一个目录
		File outDir = new File(outDirName);
		if (!outDir.exists() || !outDir.isDirectory()) {
			outDir.mkdir();
		}
		// 建立输出 文件
		File outFile = new File(outDirName + File.separator + assetsFileNmae);
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		InputStream is = context.getAssets().open(assetsFileNmae);
		OutputStream os = new FileOutputStream(outFile);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}

		os.flush();
		os.close();
		is.close();
	}

	/**
	 * 查到 文件夹 下面 所以 文件 非递归方式
	 * 
	 */
	public static LinkedList<File> listLinkedFiles(String strPath) {
		LinkedList<File> list = new LinkedList<File>();
		File dir = new File(strPath);
		File file[] = dir.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				list.add(file[i]);
			else
				CifpayLog.d("FileKit", file[i].getAbsolutePath());
		}
		File tmp;
		while (!list.isEmpty()) {
			tmp = list.removeFirst();
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);
					else
						CifpayLog.d("FileKit", file[i].getAbsolutePath());
				}
			} else {
				CifpayLog.d("FileKit", tmp.getAbsolutePath());
			}
		}
		return list;
	}

	/**
	 * 查到 文件夹 下面 所以 文件 递归方式
	 * 
	 */

	public static ArrayList<File> listFiles(String strPath) {
		return refreshFileList(strPath);
	}

	private static ArrayList<File> refreshFileList(String strPath) {
		ArrayList<File> filelist = new ArrayList<File>();
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return null;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				refreshFileList(files[i].getAbsolutePath());
			} else {
				if (files[i].getName().toLowerCase().endsWith("zip"))
					filelist.add(files[i]);
			}
		}
		return filelist;
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists())
			return file.delete();
		else
			return false;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			return false;
		}
		// 删除当前目录
		return dirFile.delete();
	}
	
	public static File getFile(String fileName) throws Exception {
		File file = new File(fileName);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
		
		return file;
	}
	
	public static void makeDirectory(String fileDir) {
		File file = null;
		try {
			file = new File(fileDir);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}
	
	public static File getFile(String filePath, String fileName) {
		File file = null;
		makeDirectory(filePath);
		try {
			file = new File(filePath + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}