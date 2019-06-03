package frame.fileManager;

import properties.Properties;
import ssh.SSH;
import util.Util;

public class FileManagerROMS implements IFileManager
{
	@Override
	public void uploadFile(String filepath)
	{
		SSH.connection.uploadFile(filepath, "/home/pi/RetroPie/roms/" + Properties.values.getFolderOfExtension(Util.getFileExtension(filepath)));
	}
}
