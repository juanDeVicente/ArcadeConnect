package frame.fileManager;

import ssh.SSH;

public class FileManagerBIOS implements IFileManager
{
	@Override
	public void uploadFile(String filepath)
	{
		SSH.connection.uploadFile(filepath, "/home/pi/RetroPie/BIOS");
	}
}
