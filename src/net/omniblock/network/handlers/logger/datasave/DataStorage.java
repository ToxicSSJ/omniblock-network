package net.omniblock.network.handlers.logger.datasave;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

public class DataStorage {

	public Calendar calendar = Calendar.getInstance();
	public String filename;

	public File file;
	public Path path;

	public Writer writer;

	@SuppressWarnings("deprecation")
	public DataStorage(String savepath) {

		path = Paths.get(savepath);
		filename = "REG: " + calendar.getTime().getYear() + "-" + calendar.getTime().getMonth() + "-"
				+ calendar.getTime().getDay() + UUID.randomUUID().toString().substring(4);

		file = new File(path.toAbsolutePath() + "/" + filename);
		file.mkdirs();

		try {

			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public void addLine(String line) {

		line = calendar.getTime().toGMTString() + ": " + line;
		if (!file.canWrite())
			throw new UnsupportedOperationException("No se puede escribir en el archivo.");

		try {
			writer.write(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
