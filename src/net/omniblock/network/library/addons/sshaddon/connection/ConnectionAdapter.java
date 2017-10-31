package net.omniblock.network.library.addons.sshaddon.connection;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import net.omniblock.network.handlers.Handlers;
import net.omniblock.network.library.addons.configaddon.Factory.ConfigType;

public class ConnectionAdapter {

	private JSch ssh = new JSch();

	private Session session = null;

	private Channel channel = null;
	private ChannelSftp sftp = null;

	public void setup() {

		String SFTPHOST = ConfigType.SSH.getConfig().getString("ssh-server.server");
		int SFTPPORT = ConfigType.SSH.getConfig().getInt("ssh-server.port");

		String SFTPUSER = ConfigType.SSH.getConfig().getString("ssh-server.dates.user");
		String SFTPPASS = ConfigType.SSH.getConfig().getString("ssh-server.dates.pass");

		String SFTPWORKINGDIR = ConfigType.SSH.getConfig().getString("ssh-server.working-cd");

		try {
			Handlers.LOGGER.sendInfo("&b--------------------- &7[SSH] &b---------------------", "SSH HOST: " + SFTPHOST,
					"SSH PORT: " + SFTPPORT, "SSH USER: " + SFTPUSER, "SSH PASS: " + SFTPPASS,
					"&b-------------------------------------------------");
			session = ssh.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();

			channel = session.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			sftp.cd(SFTPWORKINGDIR);

		} catch (JSchException | SftpException e) {

			e.printStackTrace();

		}

	}

	public Channel getChannel() {
		return channel;
	}

	public Session getSession() {
		return session;
	}

	public JSch getSSH() {
		return ssh;
	}

	public ChannelSftp getSFTP() {
		return sftp;
	}

}
