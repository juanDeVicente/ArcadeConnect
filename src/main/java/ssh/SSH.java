package ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import properties.Properties;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SSH
{
	public static SSH connection = new SSH();

	private SSHClient sshClient;

	private SSH()
	{
		SSHClient ssh = new SSHClient();
		try
		{
			ssh.loadKnownHosts();
			ssh.addHostKeyVerifier("3c:7e:e2:dc:2d:c1:f2:30:6a:81:4d:b6:63:eb:82:a3");
			ssh.connect(Properties.values.get("ip-address").toString(), 22);
			ssh.authPassword(Properties.values.get("username").toString(), Properties.values.get("password").toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	public void uploadFile(String filepath, String destination)
	{

	}

	public void removeFile(String filepath, String origin)
	{

	}

	public DefaultMutableTreeNode getDirectories(String root)
	{
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Hola");

		return top;
	}

	public void closeClient()
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
				// Do Nothing
			}

			ssh.disconnect();
		}
	}

}
