package org.glydar.glydar.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZLibOperations {
	public static byte[] compress(byte[] data) throws Exception {
		Deflater def = new Deflater();
		def.setInput(data);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);

		def.finish();

		byte[] buf = new byte[1024];

		while (!def.finished()) {

			int compressed = def.deflate(buf);

			baos.write(buf, 0, compressed);
		}

		baos.close();

		return baos.toByteArray();

	}

	public static byte[] decompress(byte[] data) throws Exception {
		if (data.length == 0)
			return null;

		Inflater inf = new Inflater();
		inf.setInput(data);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);

		byte[] buf = new byte[1024];

		while (!inf.finished()) {

			int decompressed = inf.inflate(buf);

			baos.write(buf, 0, decompressed);
		}

		inf.end();
		baos.close();

		return baos.toByteArray();
	}
}
