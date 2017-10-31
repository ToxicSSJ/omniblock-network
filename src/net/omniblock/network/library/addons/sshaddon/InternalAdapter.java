package net.omniblock.network.library.addons.sshaddon;

import net.omniblock.network.library.addons.sshaddon.connection.ConnectionAdapter;
import net.omniblock.network.library.addons.sshaddon.managers.FileDownloader;
import net.omniblock.network.library.addons.sshaddon.managers.FileUploader;

public class InternalAdapter {

	public static final ConnectionAdapter CONNECTION = new ConnectionAdapter();

	public static final FileDownloader DOWNLOADER = new FileDownloader();
	public static final FileUploader UPLOADER = new FileUploader();

}
