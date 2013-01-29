package com.saman.util.ftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import sun.misc.BASE64Decoder;

public class FTPUtil
{
	private static final String PATH_SPILIT = "/";
	private static String nginxServer = "http://static.cpsdna.com";
	private static String url = "58.215.50.16"; //FTP地址
	private static int port = 21; //FTP端口
	private static String username = "cpsdna"; //FTP登录名
	private static String password = "cpsdna@58629930"; //FTP密码

	private static final String LINE = "_";
	private static final String DOT = ".";

	/*
	 * 刷新FTP参数
	 */
	public static void refreshFtpProperties(String ftpUrl, int ftpPort, String ftpUsername, String ftpPassword, String nginxServerUrl)
	{
		url = ftpUrl;
		port = ftpPort;
		username = ftpUsername;
		password = ftpPassword;
		nginxServer = nginxServerUrl;
	}
	
	/*
	 * 文件存储目录策略，以天为单位保存
	 */
	private static String filePathStrategy()
	{
		return new SimpleDateFormat("yyyyMMdd").format(new Date()) + PATH_SPILIT;
	}
	
	
	public static boolean isBnull(String content)
	{
		if (content != null && !"".equals(content.trim()))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param filePath
	 *            FTP服务器保存目录
	 * @param fileName
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回HTTP访问路径
	 */
	public static String uploadFile(String filePath, String fileName, String fileType, String fileContent)
	{
		if (isBnull(filePath) || isBnull(fileContent))
		{
			System.out.println("null or empty parameters!");
			return null;
		}
		if ((!isBnull(fileName) && !fileName.contains(DOT)) || !filePath.startsWith(PATH_SPILIT) || !filePath.endsWith(PATH_SPILIT))
		{
			System.out.println("wrong parameters!");
			return null;
		}

		InputStream input = null;
		try
		{
			input = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(fileContent));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("wrong file content in base64!");
			return null;
		}

		if (isBnull(fileName))
		{
			String autoFileName = String.valueOf(System.currentTimeMillis());
			if (!isBnull(fileType))
			{
				fileName = autoFileName + DOT + fileType;
			}
			else
			{
				//此处需要从byte中判断文件类型，暂未实现
				fileName = autoFileName;
			}
		}

		FTPClient ftpClient = new FTPClient();
		try
		{
			ftpClient.connect(url, port);//连接FTP服务器  
			//登录
			if (!ftpClient.login(username, password))
			{
				System.out.println("fail to login ftp server!");
				return null;
			}
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply))
			{
				ftpClient.disconnect();
				System.out.println("fail to get reply code from ftp server!");
				return null;
			}

			filePath += filePathStrategy();
			
			// 设置以二进制流的方式传输  
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			if (!ftpClient.changeWorkingDirectory(new String(filePath.getBytes("GBK"), "iso-8859-1")))
			{
				ftpClient.makeDirectory(new String(filePath.getBytes("GBK"), "iso-8859-1"));
				if (!ftpClient.changeWorkingDirectory(new String(filePath.getBytes("GBK"), "iso-8859-1")))
				{
					System.out.println("fail to change working directory on ftp server!");
					return null;
				}
			}
			if (ftpClient.listNames(new String(fileName.getBytes("GBK"), "iso-8859-1")).length > 0)
			{
				System.out.println("file name exist, generate new file name");
				int lastIndex = fileName.lastIndexOf(DOT);
				fileName = fileName.substring(0, lastIndex) + LINE + System.currentTimeMillis() + fileName.substring(lastIndex);
			}
			if (!ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), input))
			{
				System.out.println("fail to store file on ftp server!");
				return null;
			}
			input.close();
			ftpClient.logout();
			return nginxServer + filePath + URLEncoder.encode(fileName, "gb2312");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ftpClient.isConnected())
			{
				try
				{
					ftpClient.disconnect();
				}
				catch (IOException ioe)
				{
				}
			}
		}
		return null;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String username, String password, String remotePath, String fileName, String localPath)
	{
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try
		{
			int reply;
			ftp.connect(url, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
			ftp.login(username, password);//登录  
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply))
			{
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs)
			{
				if (ff.getName().equals(fileName))
				{
					File localFile = new File(localPath + PATH_SPILIT + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			success = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				}
				catch (IOException ioe)
				{
				}
			}
		}
		return success;
	}


}
