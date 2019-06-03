package ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.xfer.FileSystemFile;
import properties.Properties;
import util.Util;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SSH
{
	public static SSH connection = new SSH();


	private SSHClient sshClient;

	private SSH()
	{
		try
		{
			this.sshClient = new SSHClient();
			this.sshClient.loadKnownHosts();
			this.sshClient.addHostKeyVerifier("3c:7e:e2:dc:2d:c1:f2:30:6a:81:4d:b6:63:eb:82:a3");
			this.sshClient.connect(Properties.values.get("ip-address").toString(), 22);
			this.sshClient.authPassword(Properties.values.get("username").toString(), Properties.values.get("password").toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.sshClient = null;
		}

	}
	public void uploadFile(String filepath, String destination)
	{
		try (SFTPClient sftp = sshClient.newSFTPClient())
		{
			sftp.put(new FileSystemFile(filepath), destination);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void removeFile(String filepath)
	{

	}

	public void restartArcade()
	{
		try (Session session = sshClient.startSession())
		{
			Session.Command cmd = session.exec("sudo shutdown -r now");
		}
		catch (ConnectionException e)
		{
			e.printStackTrace();
		}
		catch (TransportException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public DefaultMutableTreeNode getDirectories(String root)
	{
		if (this.sshClient != null)
			return createDirectoryTree(root);

		return null;
	}

	public void closeClient()
	{
		if (this.sshClient != null)
		{
			try
			{
				this.sshClient.disconnect();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private DefaultMutableTreeNode createDirectoryTree(String currentDir)
	{
		String[] split = currentDir.split("/");
		String name = split[split.length - 1].toLowerCase();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);

		if (!Util.getFileExtension(name).equals(""))
			return node;

		try (Session session = sshClient.startSession())
		{
			Session.Command cmd = session.exec("ls -a " + currentDir);
			String[] data = IOUtils.readFully(cmd.getInputStream()).toString().split("\n");
			for (String d: data)
			{
				if (!d.equals(".") && !d.equals("..") && !d.equals(""))
					node.add(createDirectoryTree(currentDir + "/" + d));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return node;
	}

	public static void main(String[] args)
			throws IOException
	{
		final SSHClient ssh = new SSHClient();
		ssh.loadKnownHosts();
		ssh.addHostKeyVerifier("3c:7e:e2:dc:2d:c1:f2:30:6a:81:4d:b6:63:eb:82:a3");
		ssh.connect(Properties.values.get("ip-address").toString(), 22);
		Session session = null;
		try
		{
			ssh.authPassword(Properties.values.get("username").toString(), Properties.values.get("password").toString());
			session = ssh.startSession();
			final Session.Command cmd = session.exec("ls -a");
			System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
			cmd.join(5, TimeUnit.SECONDS);
		} finally
		{
			try
			{
				if (session != null)
				{
					session.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			ssh.disconnect();
		}
	}

}
