package net.omniblock.network.library.addons.sshaddon.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.addons.sshaddon.InternalAdapter;
import net.omniblock.network.library.addons.xmladdon.XMLType;

public class FileDownloader {

	protected static final String REMOTE_PATH = "/home/Omniblock Network/Core/plugins/";
	protected static final String DOWNLOAD_PATH = OmniNetwork.getInstance().getDataFolder().getAbsolutePath()
			+ "/temp/";

	@SuppressWarnings("unused")
	public List<String> getNetworkXML(XMLType xml) {

		String path = UUID.randomUUID().toString().substring(1, 5) + ".xml";

		ChannelSftp sftp = InternalAdapter.CONNECTION.getSFTP();
		InputStream stream = null;

		try {

			stream = sftp.get(

					REMOTE_PATH + xml.getPath()

			);

		} catch (SftpException e) {

			e.printStackTrace();

		}

		if (stream == null)
			return new ArrayList<String>();
		return readInputStream(stream);

	}

	public void downloadUpdate(String name, String artifact, String local) {

		ChannelSftp sftp = InternalAdapter.CONNECTION.getSFTP();

		try {

			sftp.get(

					REMOTE_PATH + artifact, local

			);

		} catch (SftpException e) {

			e.printStackTrace();

		}

		return;

	}

	private List<String> readInputStream(InputStream stream) {

		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line;

		List<String> responseData = new ArrayList<String>();

		try {

			while ((line = in.readLine()) != null) {
				responseData.add(line);
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return responseData;

	}

}
